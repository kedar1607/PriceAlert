package kedar.com.pricealertapp.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.tinder.scarlet.Scarlet
import com.tinder.scarlet.WebSocket
import com.tinder.scarlet.messageadapter.moshi.MoshiMessageAdapter
import com.tinder.scarlet.streamadapter.rxjava2.RxJava2StreamAdapterFactory
import kedar.com.websockets.R
import kedar.com.websockets.services.PolygonStocksService
import okhttp3.OkHttpClient
import com.tinder.scarlet.websocket.okhttp.newWebSocketFactory
import kedar.com.AlertApplication
import kedar.com.websockets.models.AuthAction
import kedar.com.websockets.models.SubscribeAction
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navController = Navigation.findNavController(this, R.id.mainNavFragment)
        val bottomTabs = HashSet<Int>().apply {
            add(R.id.navigation_track)
            add(R.id.navigation_dashboard)
        }
        appBarConfiguration = AppBarConfiguration.Builder(bottomTabs).setDrawerLayout(drawerLayout).build()
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)
        hideBackButton()
        tabs.setupWithNavController(navController)



    }






    private fun hideBackButton() {
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setDisplayShowHomeEnabled(false)
    }

}