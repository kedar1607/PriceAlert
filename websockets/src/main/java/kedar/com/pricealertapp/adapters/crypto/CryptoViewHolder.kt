package kedar.com.pricealertapp.adapters.crypto

import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.robinhood.ticker.TickerUtils
import kedar.com.pricealertapp.extentions.getCurrencyFormat
import kedar.com.pricealertapp.models.CryptoTradeData
import kedar.com.websockets.R
import kotlinx.android.synthetic.main.item_stock.view.*

class CryptoViewHolder(view: View, private val viewLifecycleOwner: LifecycleOwner) :
    RecyclerView.ViewHolder(view) {

    fun bind(
        cryptoTrade: MutableLiveData<CryptoTradeData>,
        onClick: ((CryptoTradeData?) -> Unit)
    ) {

        itemView.tv_price.setCharacterLists(TickerUtils.provideNumberList())
        cryptoTrade.observe(viewLifecycleOwner, Observer {
            itemView.tag = it
            itemView.tv_symbol.text = it.symbol
            val livePrice = it?.currentPrice
            itemView.tv_price.text = (livePrice?.toBigDecimal()?.getCurrencyFormat()
                ?: itemView.context.getString(R.string.loading)).toString()
        })

        itemView.btn_enable_disable.setOnClickListener {
            onClick.invoke(cryptoTrade.value)
        }
    }

}