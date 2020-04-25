package kedar.com

import android.app.*
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.LiveData
import kedar.com.pricealertapp.activities.MainActivity
import kedar.com.pricealertapp.services.ConnectionEstablisher
import kedar.com.pricealertapp.services.MyRealTimeService
import kedar.com.websockets.R
import kedar.com.websockets.repos.PolygonCryptoRepo


class AlertApplication: Application() {


    lateinit var mService: MyRealTimeService
    var mBound: Boolean = false


    private val connectionEstablisher = object : ConnectionEstablisher {
        override fun connectionStarted(data: LiveData<String>) {

        }
    }

    /** Defines callbacks for service binding, passed to bindService()  */
    private val connection = object : ServiceConnection {

        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            val binder = service as MyRealTimeService.LocalBinder
            mService = binder.getService()
            runAsForeground()
            binder.setConnectionEstablished(connectionEstablisher)
            mBound = true
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            mBound = false
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        Intent(this, MyRealTimeService::class.java).also { intent ->
//            ContextCompat.startForegroundService(this, intent)
            bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onTerminate() {
        super.onTerminate()
        unbindService(connection)
        mBound = false
    }


    private fun createNotification(text: String){
        createNotificationChannel()
        val builder = NotificationCompat.Builder(this, "stock_notification_alert")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Stock price alert")
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(this)) {
            // notificationId is a unique int for each notification that you must define
            notify(52, builder.build())
        }
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Stock notification channel"
            val descriptionText = "This is where you get up to date stock info."
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("stock_notification_alert", name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }


    private fun createNotificationChannelForForegroundService() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Stock notification channel"
            val descriptionText = "This is where you get up to date stock info."
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("foreground_service_channel", name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun runAsForeground() {
        createNotificationChannelForForegroundService()
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        val notification: Notification = NotificationCompat.Builder(this).setChannelId("foreground_service_channel")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentText("Looking for alerts....")
                .setContentIntent(pendingIntent).build()

        mService.startForeground(78, notification)
    }


    companion object {
        lateinit var instance: AlertApplication
            private set
    }
}