package kedar.com.pricealertapp.viewmodels

import android.util.ArrayMap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kedar.com.AlertApplication
import kedar.com.websockets.models.Trade
import kedar.com.websockets.repos.PolygonStocksRepo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class StocksViewModel: ViewModel() {
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO

    private val map = ArrayMap<String, MutableLiveData<Trade>>()
    private val _liveMap = MutableLiveData<ArrayMap<String, MutableLiveData<Trade>>>()
    val liveMap: LiveData<ArrayMap<String, MutableLiveData<Trade>>> = _liveMap

    private val polygonCryptoRepo: PolygonStocksRepo = PolygonStocksRepo(AlertApplication.instance)

    init {
        polygonCryptoRepo.tradeData.observeForever{ tradeList ->
            tradeList.forEach { cryptoTrade ->
                when{
                    map.contains(cryptoTrade.sym) -> {
                        map[cryptoTrade.sym]?.postValue(cryptoTrade)
                    }
                    else -> {
                        map[cryptoTrade.sym] = MutableLiveData(cryptoTrade)
                        _liveMap.postValue(map)
                    }
                }
            }
        }
    }

    fun observe(symbol: String): LiveData<Trade>?{
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
            polygonCryptoRepo.unsubscribeForTrades(symbol)
        }
    }

}