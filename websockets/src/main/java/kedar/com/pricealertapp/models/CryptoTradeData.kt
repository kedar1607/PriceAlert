package kedar.com.pricealertapp.models

import kedar.com.websockets.models.CryptoExchange
import kedar.com.websockets.models.CryptoTrade

data class CryptoTradeData(
    val symbol: String,
    val exchange: CryptoExchange,
    val hitPrice: Double? = null,
    val currentPrice: Double,
    val time: Long
) {
    override fun equals(other: Any?): Boolean {
        other as CryptoTradeData
        return symbol == other.symbol
                && exchange.id == other.exchange.id
                && hitPrice == other.hitPrice
    }

    override fun hashCode(): Int {
        var result = symbol.hashCode()
        result = 31 * result + exchange.hashCode()
        result = 31 * result + (hitPrice?.hashCode() ?: 0)
        result = 31 * result + time.hashCode()
        return result
    }
}

fun CryptoTrade.transformToCryptoTradeData(): CryptoTradeData {
    return CryptoTradeData(
        symbol = this.pair.split("-")[0],
        exchange = CryptoExchange(id = this.x),
        currentPrice = this.p,
        time = this.t
    )
}
