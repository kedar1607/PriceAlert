package kedar.com.pricealertapp.adapters.crypto

import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import kedar.com.pricealertapp.models.CryptoTradeData


class CryptoItemDiffCallBack : DiffUtil.ItemCallback<MutableLiveData<CryptoTradeData>>() {
    override fun areItemsTheSame(
        oldItem: MutableLiveData<CryptoTradeData>,
        newItem: MutableLiveData<CryptoTradeData>
    ): Boolean =
        oldItem.value == newItem.value


    override fun areContentsTheSame(
        oldItem: MutableLiveData<CryptoTradeData>,
        newItem: MutableLiveData<CryptoTradeData>
    ): Boolean {
        return oldItem.value?.symbol == newItem.value?.symbol
    }
}