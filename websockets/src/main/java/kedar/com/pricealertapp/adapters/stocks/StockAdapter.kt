package kedar.com.pricealertapp.adapters.stocks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.ListAdapter
import kedar.com.websockets.R
import kedar.com.websockets.models.CryptoTrade
import kedar.com.websockets.models.Trade

class StockAdapter(private val onClick: ((Trade?) -> Unit)) : ListAdapter<MutableLiveData<Trade>, StockViewHolder>(
    StockItemDiffCallBack()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_stock, parent, false)
        return StockViewHolder(view)
    }

    override fun onBindViewHolder(holder: StockViewHolder, position: Int) {
        val stockItem  = getItem(position)
        holder.bind(stockItem, onClick)
    }
}