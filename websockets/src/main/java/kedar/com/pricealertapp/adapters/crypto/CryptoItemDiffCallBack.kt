package kedar.com.pricealertapp.adapters.crypto

import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import kedar.com.pricealertapp.models.AlertSetUp
import kedar.com.pricealertapp.models.LiveUpdateStock
import kedar.com.websockets.models.CryptoTrade


class CryptoItemDiffCallBack : DiffUtil.ItemCallback<MutableLiveData<CryptoTrade>>() {
    override fun areItemsTheSame(oldItem: MutableLiveData<CryptoTrade>, newItem: MutableLiveData<CryptoTrade>): Boolean =
        oldItem.value == newItem.value


    override fun areContentsTheSame(oldItem: MutableLiveData<CryptoTrade>, newItem: MutableLiveData<CryptoTrade>): Boolean {
        return oldItem.value?.pair == newItem.value?.pair
    }
}