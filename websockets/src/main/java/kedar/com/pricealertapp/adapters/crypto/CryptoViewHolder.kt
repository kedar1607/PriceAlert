package kedar.com.pricealertapp.adapters.crypto

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.robinhood.ticker.TickerUtils

import kedar.com.pricealertapp.extentions.getCurrencyFormat
import kedar.com.websockets.R
import kedar.com.websockets.models.CryptoTrade
import kotlinx.android.synthetic.main.item_stock.view.*

class CryptoViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(
            cryptoTrade: MutableLiveData<CryptoTrade>,
            onClick: ((CryptoTrade?) -> Unit)
    ) {

        itemView.tv_price.setCharacterLists(TickerUtils.provideNumberList())
        cryptoTrade.observeForever {
            itemView.tag = it
            itemView.tv_symbol.text = it.pair
            val livePrice = it?.p
            itemView.tv_price.text = (livePrice?.toBigDecimal()?.getCurrencyFormat()
                ?: itemView.context.getString(R.string.loading)).toString()
        }

//        itemView.btn_enable_disable.text = when (liveUpdateStock.enabled) {
//            false -> {
//                itemView.btn_enable_disable.isEnabled = true
//                itemView.context.getString(R.string.enable_caps)
//            }
//            true -> {
//                itemView.btn_enable_disable.isEnabled = true
//                itemView.context.getString(R.string.disable_caps)
//            }
//            else -> {
//                itemView.btn_enable_disable.isEnabled = false
//                itemView.context.getString(R.string.loading)
//            }
//        }

        itemView.btn_enable_disable.setOnClickListener {
            onClick.invoke(cryptoTrade.value)
        }
    }

}