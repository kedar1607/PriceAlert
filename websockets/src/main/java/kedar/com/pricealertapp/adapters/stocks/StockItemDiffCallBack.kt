package kedar.com.pricealertapp.adapters.stocks

import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import kedar.com.pricealertapp.models.StockTradeData


class StockItemDiffCallBack : DiffUtil.ItemCallback<MutableLiveData<StockTradeData>>() {
    override fun areItemsTheSame(
        oldItem: MutableLiveData<StockTradeData>,
        newItem: MutableLiveData<StockTradeData>
    ): Boolean =
        oldItem.value == newItem.value


    override fun areContentsTheSame(
        oldItem: MutableLiveData<StockTradeData>,
        newItem: MutableLiveData<StockTradeData>
    ): Boolean {
        return oldItem.value?.symbol == newItem.value?.symbol
    }
}