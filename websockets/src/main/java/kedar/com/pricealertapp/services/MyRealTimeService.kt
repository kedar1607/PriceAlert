package kedar.com.pricealertapp.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.IBinder
import androidx.collection.ArrayMap
import androidx.collection.ArraySet
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import kedar.com.AlertApplication
import kedar.com.pricealertapp.models.AlertSetUp
import kedar.com.websockets.R
import kedar.com.websockets.models.CryptoTrade
import kedar.com.websockets.models.Trade
import kedar.com.websockets.repos.PolygonCryptoRepo
import kedar.com.websockets.repos.PolygonStocksRepo
import java.util.concurrent.Executors

//Todo This needs to to worked on completely
class MyRealTimeService : Service() {
    // Binder given to clients
    private val binder = LocalBinder()

    val es = Executors.newFixedThreadPool(5)

    private lateinit var polygonCryptoRepo: PolygonCryptoRepo

    private lateinit var polygonStocksRepo: PolygonStocksRepo


    private val activeCryptoAlerts = ArraySet<AlertSetUp>()

    private val activeStockAlerts = ArraySet<AlertSetUp>()

    var isObservingCrypto = false

    var isObservingStocks = false

    var notificationIdCount = 1


    /**
     * Class used for the client Binder.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with IPC.
     */
    inner class LocalBinder : Binder() {
        // Return this instance of LocalService so clients can call public methods
        fun getService(): MyRealTimeService = this@MyRealTimeService
    }

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    fun startObservingCrypto() {
        isObservingCrypto = true
        polygonCryptoRepo.tradeData.observeForever { list ->
            val stringToCryptoTrade = ArrayMap<String, CryptoTrade>()
            list.forEach { stringToCryptoTrade[it.pair] = it }
            val alertSetUpToCryptoTrade = ArrayMap<AlertSetUp, CryptoTrade>()
            activeCryptoAlerts.forEach {
                if (stringToCryptoTrade.containsKey(it.symbol))
                    alertSetUpToCryptoTrade[it] = stringToCryptoTrade[it.symbol]
            }

            alertSetUpToCryptoTrade.forEach {
                if (it.value.p >= it.key.hitPrice)
                    createNotification("price of ${it.key.symbol} is $${it.value.p}", it.key)
            }

        }
    }

    fun startObservingStocks() {
        isObservingStocks = true
        polygonStocksRepo.tradeData.observeForever { list ->
            val stringToTrade = ArrayMap<String, Trade>()
            list.forEach { stringToTrade[it.sym] = it }
            val alertSetUpToTrade = ArrayMap<AlertSetUp, Trade>()
            activeStockAlerts.forEach {
                if (stringToTrade.containsKey(it.symbol))
                    alertSetUpToTrade[it] = stringToTrade[it.symbol]
            }

            alertSetUpToTrade.forEach {
                if (it.value.p >= it.key.hitPrice)
                    createNotification("price of ${it.key.symbol} is $${it.value.p}", it.key)
            }

        }
    }

    fun openNewConnectionForCrypto(cryptoTradeData: AlertSetUp) {
        if (!::polygonCryptoRepo.isInitialized) {
            polygonCryptoRepo = PolygonCryptoRepo(AlertApplication.instance)
        }
        if (!isObservingCrypto)
            startObservingCrypto()
        es.execute {
            polygonCryptoRepo.openConnection(cryptoTradeData.symbol)
            activeCryptoAlerts.add(cryptoTradeData)
        }
    }

    fun openNewConnectionForStock(stockTradeData: AlertSetUp) {
        if (!::polygonStocksRepo.isInitialized) {
            polygonStocksRepo = PolygonStocksRepo(AlertApplication.instance)
        }
        if (!isObservingStocks)
            startObservingStocks()
        es.execute {
            polygonStocksRepo.openConnection(stockTradeData.symbol)
            activeStockAlerts.add(stockTradeData)
        }
    }

    private fun createNotification(text: String, trade: AlertSetUp) {
        createNotificationChannel(trade.notificationId.toString())
        val builder = NotificationCompat.Builder(this, trade.notificationId.toString())
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(getString(R.string.stock_price_alert))
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(this)) {
            // notificationId is a unique int for each notification that you must define
            notify(trade.notificationId, builder.build())
        }
    }

    private fun createNotificationChannel(id: String) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.foreground_notification)
            val descriptionText = getString(R.string.foreground_notification_desc)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(id, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun disableAllAlerts(){
        activeCryptoAlerts.forEach { disableCryptoAlert(it?.symbol, it) }
        activeStockAlerts.forEach { disableStockAlert(it?.symbol, it) }
    }

    fun disableCryptoAlert(symbol: String?, alertSetUp: AlertSetUp?) {
        if (::polygonCryptoRepo.isInitialized && symbol != null && alertSetUp != null) {
            polygonCryptoRepo.unsubscribeTrades(symbol)
            activeCryptoAlerts.remove(alertSetUp)
        }
    }

    fun disableStockAlert(symbol: String?, alertSetUp: AlertSetUp?) {
        if (::polygonStocksRepo.isInitialized && symbol != null && alertSetUp != null) {
            polygonStocksRepo.unsubscribeTrades(symbol)
            activeStockAlerts.remove(alertSetUp)
        }
    }
}

