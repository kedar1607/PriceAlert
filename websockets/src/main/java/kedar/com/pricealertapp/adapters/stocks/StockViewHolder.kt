package kedar.com.pricealertapp.adapters.stocks

import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.robinhood.ticker.TickerUtils
import kedar.com.pricealertapp.extentions.getCurrencyFormat
import kedar.com.websockets.R
import kedar.com.websockets.models.Trade
import kotlinx.android.synthetic.main.item_stock.view.*

class StockViewHolder(view: View, private val viewLifecycleOwner: LifecycleOwner) :
    RecyclerView.ViewHolder(view) {

    fun bind(
        trade: MutableLiveData<Trade>,
        onClick: ((Trade?) -> Unit)
    ) {

        itemView.tv_price.setCharacterLists(TickerUtils.provideNumberList())
        trade.observe(viewLifecycleOwner, Observer {
            itemView.tag = it
            itemView.tv_symbol.text = it.sym
            val livePrice = it?.p
            itemView.tv_price.text = (livePrice?.toBigDecimal()?.getCurrencyFormat()
                ?: itemView.context.getString(R.string.loading)).toString()
        })

        itemView.btn_enable_disable.setOnClickListener {
            onClick.invoke(trade.value)
        }
    }

}