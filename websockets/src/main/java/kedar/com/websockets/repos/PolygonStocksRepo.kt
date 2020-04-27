package kedar.com.websockets.repos

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tinder.scarlet.Scarlet
import com.tinder.scarlet.WebSocket
import com.tinder.scarlet.messageadapter.moshi.MoshiMessageAdapter
import com.tinder.scarlet.streamadapter.rxjava2.RxJava2StreamAdapterFactory
import com.tinder.scarlet.websocket.okhttp.newWebSocketFactory
import io.reactivex.disposables.Disposable
import kedar.com.websockets.Constants.ACTION_SUBSCRIBE
import kedar.com.websockets.Constants.ACTION_UNSUBSCRIBE
import kedar.com.websockets.Constants.AUTH
import kedar.com.websockets.Constants.POLYGON_STOCK_BASE_URL
import kedar.com.websockets.Constants.SUBSCRIBE_CRYPTO_TRADE
import kedar.com.websockets.Constants.SUBSCRIBE_STOCK_TRADE
import kedar.com.websockets.Logger
import kedar.com.websockets.R
import kedar.com.websockets.models.AuthAction
import kedar.com.websockets.models.ConnectionState
import kedar.com.websockets.models.SubscribeAction
import kedar.com.websockets.models.Trade
import kedar.com.websockets.services.PolygonStocksService
import okhttp3.OkHttpClient

class PolygonStocksRepo(val context: Context) {

    private val disposables = mutableMapOf<String, Disposable>()

    private val _tradeData = MutableLiveData<List<Trade>>()
    val tradeData: LiveData<List<Trade>> = _tradeData

    private val scarletInstance = Scarlet.Builder()
        .webSocketFactory(OkHttpClient().newWebSocketFactory(POLYGON_STOCK_BASE_URL))
        .addMessageAdapterFactory(MoshiMessageAdapter.Factory())
        .addStreamAdapterFactory(RxJava2StreamAdapterFactory())


    private lateinit var polygonService: PolygonStocksService

    fun openConnection(symbol: String) {
        polygonService = scarletInstance.build().create()
        val connectionOpen = polygonService.observeWebSocketEvent()
            .filter { it is WebSocket.Event.OnConnectionOpened<*> }.subscribe {
                if (disposables.containsKey(symbol))
                    authenticate(symbol)
                Log.d(Logger.CONNECTION_LOGGER, "Connection opened")
            }
        disposables[symbol] = connectionOpen
    }

    fun authenticate(symbol: String): Boolean {
        val authAction = AuthAction(
            action = AUTH,
            params = context.getString(R.string.POLYGON_IO_API_KEY)
        )
        var success = false

        if (polygonService.sendAuthentication(authAction)) {
            Log.d(Logger.CONNECTION_LOGGER, "Authenticated")
            success = subscribeForTrades(symbol)
            ConnectionState.AUTHENTICATED
        } else {
            Log.d(Logger.CONNECTION_LOGGER, "Authentication failed")
            ConnectionState.AUTHENTICATION_FAILED
        }

        return success
    }

    fun subscribeForTrades(symbol: String): Boolean {
        val subscribed = polygonService.subscribeAction(
            SubscribeAction(
                ACTION_SUBSCRIBE,
                SUBSCRIBE_STOCK_TRADE + symbol
            )
        )
        if (subscribed) {
            val disposable = polygonService.observeTrades().subscribe {
                if (!it.isNullOrEmpty()) {
                    _tradeData.postValue(it)
                }
            }
            Log.d(Logger.CONNECTION_LOGGER, "Stream subscribed for $symbol")
            disposables[symbol] = disposable
        }

        return subscribed

    }

    fun unsubscribeTrades(symbol: String): Boolean {
        val result = polygonService.subscribeAction(
            SubscribeAction(
                ACTION_UNSUBSCRIBE,
                SUBSCRIBE_CRYPTO_TRADE + symbol
            )
        )
        if (result) {
            disposables[symbol]?.dispose()
            disposables.remove(symbol)
        }
        return result
    }

    fun cancelEverything() {
        disposables.keys.forEach {
            disposables[it]?.dispose()
        }
        disposables.clear()
    }
}