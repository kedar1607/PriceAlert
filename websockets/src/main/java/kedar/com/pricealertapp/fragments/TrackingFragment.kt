package kedar.com.pricealertapp.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.navGraphViewModels

import kedar.com.pricealertapp.services.TradeObserver
import kedar.com.pricealertapp.viewmodels.CryptoViewModel
import kedar.com.pricealertapp.viewmodels.StocksViewModel
import kedar.com.websockets.R
import kedar.com.websockets.models.Trade
import kotlinx.android.synthetic.main.fragment_tracking.*

class TrackingFragment: Fragment() {

    private var isPriceEntered = false

    private var isSymbolEntered = false

    private val stocksViewModel: StocksViewModel by navGraphViewModels(R.id.nav_main) { defaultViewModelProviderFactory }

    private val cryptoViewModel: CryptoViewModel by navGraphViewModels(R.id.nav_main) { defaultViewModelProviderFactory }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tracking, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        readStockSymbol()
        readHitPrice()
        btn_test.setOnClickListener { trackStock() }
        btn_disable.setOnClickListener {
        }
    }


    private fun readStockSymbol() {
        et_symbol.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                isSymbolEntered = !s.isNullOrBlank()
                btn_test.isEnabled =  isSymbolEntered && isPriceEntered
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        })
    }

    private fun readHitPrice() {
        et_price.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                isPriceEntered = !s.isNullOrBlank() && s.toString().toBigDecimalOrNull()!= null
                btn_test.isEnabled =   isPriceEntered && isSymbolEntered
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        })
    }

    private fun trackStock(){
        if(!cb_isCrypto.isChecked) {
            stocksViewModel.subscribe(et_symbol.text.toString())
        }else{
            cryptoViewModel.subscribe(et_symbol.text.toString())
        }
        et_price.text.clear()
        et_symbol.text.clear()
        btn_test.isEnabled = false
    }

}