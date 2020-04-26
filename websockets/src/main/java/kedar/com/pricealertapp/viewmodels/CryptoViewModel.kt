package kedar.com.pricealertapp.viewmodels

import android.util.ArrayMap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kedar.com.AlertApplication
import kedar.com.pricealertapp.models.CryptoTradeData
import kedar.com.pricealertapp.models.transformToCryptoTradeData
import kedar.com.websockets.repos.PolygonCryptoRepo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.collections.set


class CryptoViewModel: ViewModel() {
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO

    private val map = ArrayMap<String, MutableLiveData<CryptoTradeData>>()
    private val _liveMap = MutableLiveData<ArrayMap<String, MutableLiveData<CryptoTradeData>>>()
    val liveMap: LiveData<ArrayMap<String, MutableLiveData<CryptoTradeData>>> = _liveMap

    private val polygonCryptoRepo: PolygonCryptoRepo = PolygonCryptoRepo(AlertApplication.instance)


    init {
        polygonCryptoRepo.tradeData.observeForever{ tradeList ->
            tradeList.forEach { cryptoTrade ->
                when{
                    map.contains(cryptoTrade.pair) -> {
                        map[cryptoTrade.pair]?.postValue(cryptoTrade.transformToCryptoTradeData())
                    }
                    else -> {
                        map[cryptoTrade.pair] =
                            MutableLiveData(cryptoTrade.transformToCryptoTradeData())
                        _liveMap.postValue(map)
                    }
                }
            }
        }
    }

    fun observe(symbol: String): LiveData<CryptoTradeData>? {
        if(!map.contains(symbol)){
            subscribe(symbol)
            throw IllegalAccessException("Please subscribe before observe.")
        }
        return map[symbol]
    }

    fun subscribe(symbolList: List<String>){
        //Todo refactor to make it only one call for multiple symbols
        symbolList.forEach { subscribe(it) }
    }


    fun unsubscribe(symbolList: List<String>){
        //Todo refactor to make it only one call for multiple symbols
        symbolList.forEach { unsubscribe(it) }
    }

    fun subscribe(symbol: String){
        viewModelScope.launch(dispatcher) {
            polygonCryptoRepo.openConnection(symbol)
        }
    }

    fun unsubscribe(symbol: String){
        viewModelScope.launch(dispatcher){
            val success = polygonCryptoRepo.unsubscribeTrades(symbol)
            if (success) {
                map.remove(symbol)
                _liveMap.postValue(map)
            }
        }
    }

}