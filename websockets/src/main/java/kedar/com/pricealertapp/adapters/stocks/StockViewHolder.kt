package kedar.com.pricealertapp.adapters.stocks

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.robinhood.ticker.TickerUtils

import kedar.com.pricealertapp.extentions.getCurrencyFormat
import kedar.com.websockets.R
import kedar.com.websockets.models.CryptoTrade
import kedar.com.websockets.models.Trade
import kotlinx.android.synthetic.main.item_stock.view.*

class StockViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(
        trade: MutableLiveData<Trade>,
        onClick: ((Trade?) -> Unit)
    ) {

        itemView.tv_price.setCharacterLists(TickerUtils.provideNumberList())
        trade.observeForever {
            itemView.tag = it
            itemView.tv_symbol.text = it.sym
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
            onClick.invoke(trade.value)
        }
    }

}