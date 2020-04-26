package kedar.com.pricealertapp.viewmodels

import android.util.ArrayMap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kedar.com.AlertApplication
import kedar.com.pricealertapp.models.StockTradeData
import kedar.com.pricealertapp.models.transformToStockTradeData
import kedar.com.websockets.repos.PolygonStocksRepo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.collections.set


class StocksViewModel: ViewModel() {
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO

    private val map = ArrayMap<String, MutableLiveData<StockTradeData>>()
    private val _liveMap = MutableLiveData<ArrayMap<String, MutableLiveData<StockTradeData>>>()
    val liveMap: LiveData<ArrayMap<String, MutableLiveData<StockTradeData>>> = _liveMap

    private val polygonStocksRepo: PolygonStocksRepo = PolygonStocksRepo(AlertApplication.instance)


    init {
        polygonStocksRepo.tradeData.observeForever { tradeList ->
            tradeList.forEach { trade ->
                when{
                    map.contains(trade.sym) -> {
                        map[trade.sym]?.postValue(trade.transformToStockTradeData())
                    }
                    else -> {
                        map[trade.sym] =
                            MutableLiveData(trade.transformToStockTradeData())
                        _liveMap.postValue(map)
                    }
                }
            }
        }
    }

    fun observe(symbol: String): LiveData<StockTradeData>? {
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
            polygonStocksRepo.openConnection(symbol)
        }
    }

    fun unsubscribe(symbol: String){
        viewModelScope.launch(dispatcher){
            val success = polygonStocksRepo.unsubscribeTrades(symbol)
            if (success) {
                map.remove(symbol)
                _liveMap.postValue(map)
            }
        }
    }

}