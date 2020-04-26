package kedar.com.pricealertapp.adapters.crypto

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.ListAdapter
import kedar.com.pricealertapp.models.CryptoTradeData
import kedar.com.websockets.R

class CryptoAdapter(
    private val viewLifecycleOwner: LifecycleOwner,
    private val onClick: ((CryptoTradeData?) -> Unit)
) : ListAdapter<MutableLiveData<CryptoTradeData>, CryptoViewHolder>(
    CryptoItemDiffCallBack()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_stock, parent, false)
        return CryptoViewHolder(view, viewLifecycleOwner)
    }

    override fun onBindViewHolder(holder: CryptoViewHolder, position: Int) {
        val stockItem  = getItem(position)
        holder.bind(stockItem, onClick)
    }
}