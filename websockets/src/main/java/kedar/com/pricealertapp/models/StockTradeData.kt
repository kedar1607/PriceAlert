package kedar.com.pricealertapp.models

import kedar.com.websockets.models.StockExchange
import kedar.com.websockets.models.Trade

data class StockTradeData(
    val symbol: String,
    val exchange: StockExchange,
    val hitPrice: Double? = null,
    val currentPrice: Double,
    val time: Long
) {
    override fun equals(other: Any?): Boolean {
        other as StockTradeData
        return symbol == other.symbol
                && exchange.id == other.exchange.id
                && hitPrice == other.hitPrice
                && currentPrice == other.currentPrice
    }

    override fun hashCode(): Int {
        var result = symbol.hashCode()
        result = 31 * result + exchange.hashCode()
        result = 31 * result + (hitPrice?.hashCode() ?: 0)
        result = 31 * result + currentPrice.hashCode()
        result = 31 * result + time.hashCode()
        return result
    }
}

fun Trade.transformToStockTradeData(): StockTradeData {
    return StockTradeData(
        symbol = this.sym,
        exchange = StockExchange(id = this.x),
        currentPrice = this.p,
        time = this.t
    )
}