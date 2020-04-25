package kedar.com.pricealertapp.models

import java.math.BigDecimal

@Deprecated("This model should not be used any more")
data class AlertSetUp(val symbol: String, val tippingPoint: BigDecimal, val magnitude: Magnitude, val notificationId: Int, var livePrice: BigDecimal? = null, var enabled: Boolean? = null){
    override fun equals(other: Any?): Boolean {
        val otherSetUp = other as AlertSetUp
        return this.symbol == otherSetUp.symbol && magnitude == otherSetUp.magnitude && tippingPoint == otherSetUp.tippingPoint
    }

    override fun hashCode(): Int {
        var result = symbol.hashCode()
        result = 31 * result + tippingPoint.hashCode()
        result = 31 * result + magnitude.hashCode()
        return result
    }
}

enum class Magnitude{
    POSITIVE,
    NEGATIVE
}