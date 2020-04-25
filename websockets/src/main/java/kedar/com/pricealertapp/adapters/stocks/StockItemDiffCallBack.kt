package kedar.com.pricealertapp.adapters.stocks

import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import kedar.com.pricealertapp.models.AlertSetUp
import kedar.com.pricealertapp.models.LiveUpdateStock
import kedar.com.websockets.models.CryptoTrade
import kedar.com.websockets.models.Trade


class StockItemDiffCallBack : DiffUtil.ItemCallback<MutableLiveData<Trade>>() {
    override fun areItemsTheSame(oldItem: MutableLiveData<Trade>, newItem: MutableLiveData<Trade>): Boolean =
        oldItem.value == newItem.value


    override fun areContentsTheSame(oldItem: MutableLiveData<Trade>, newItem: MutableLiveData<Trade>): Boolean {
        return oldItem.value?.p == newItem.value?.p
    }
}