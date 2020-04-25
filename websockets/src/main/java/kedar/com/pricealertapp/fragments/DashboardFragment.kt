package kedar.com.pricealertapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.navGraphViewModels

import kedar.com.pricealertapp.adapters.crypto.CryptoAdapter
import kedar.com.pricealertapp.adapters.stocks.StockAdapter
import kedar.com.pricealertapp.models.LiveUpdateStock
import kedar.com.pricealertapp.viewmodels.CryptoViewModel
import kedar.com.pricealertapp.viewmodels.StocksViewModel
import kedar.com.websockets.R
import kotlinx.android.synthetic.main.fragment_dashboard.*

class DashboardFragment : Fragment(){

    private val cryptoViewModel: CryptoViewModel by navGraphViewModels(R.id.nav_main) { defaultViewModelProviderFactory }

    private val stocksViewModel: StocksViewModel by navGraphViewModels(R.id.nav_main) { defaultViewModelProviderFactory }


    private val cryptoAdapter = CryptoAdapter {

    }

    private val stocksAdapter = StockAdapter {

    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        rcv_crypto_active.adapter = cryptoAdapter
        cryptoViewModel.liveMap.observe(viewLifecycleOwner, Observer {
            cryptoAdapter.submitList(it.values.toList())
        })

        rcv_stocks_active.adapter = stocksAdapter
        stocksViewModel.liveMap.observe(viewLifecycleOwner, Observer {
            stocksAdapter.submitList(it.values.toList())
        })

    }

    private fun activateNewAlert(liveUpdateStock: LiveUpdateStock){
//        viewModel.activateAlert(liveUpdateStock)
    }

    private fun deActivateNewAlert(liveUpdateStock: LiveUpdateStock){
//        viewModel.deActivateAlert(liveUpdateStock)
    }
}