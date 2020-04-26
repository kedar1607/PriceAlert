package kedar.com.pricealertapp.adapters.stocks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.ListAdapter
import kedar.com.pricealertapp.models.StockTradeData
import kedar.com.websockets.R

class StockAdapter(
    private val viewLifecycleOwner: LifecycleOwner,
    private val onClick: ((StockTradeData?) -> Unit)
) : ListAdapter<MutableLiveData<StockTradeData>, StockViewHolder>(
    StockItemDiffCallBack()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_stock, parent, false)
        return StockViewHolder(view, viewLifecycleOwner)
    }

    override fun onBindViewHolder(holder: StockViewHolder, position: Int) {
        val stockItem  = getItem(position)
        holder.bind(stockItem, onClick)
    }
}