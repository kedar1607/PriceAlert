package kedar.com.pricealertapp.adapters.crypto

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.ListAdapter
import kedar.com.websockets.R
import kedar.com.websockets.models.CryptoTrade

class CryptoAdapter(private val onClick: ((CryptoTrade?) -> Unit)) : ListAdapter<MutableLiveData<CryptoTrade>, CryptoViewHolder>(
    CryptoItemDiffCallBack()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_stock, parent, false)
        return CryptoViewHolder(view)
    }

    override fun onBindViewHolder(holder: CryptoViewHolder, position: Int) {
        val stockItem  = getItem(position)
        holder.bind(stockItem, onClick)
    }
}