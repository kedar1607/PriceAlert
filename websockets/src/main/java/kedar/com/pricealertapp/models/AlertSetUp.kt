package kedar.com.pricealertapp.models

data class AlertSetUp(
    val symbol: String,
    val hitPrice: Double,
    val magnitude: Magnitude? = null,
    val notificationId: Int,
    var livePrice: Double? = null,
    var enabled: Boolean? = null
) {
    override fun equals(other: Any?): Boolean {
        val otherSetUp = other as AlertSetUp
        return this.symbol == otherSetUp.symbol && magnitude == otherSetUp.magnitude && hitPrice == otherSetUp.hitPrice
    }

    override fun hashCode(): Int {
        var result = symbol.hashCode()
        result = 31 * result + hitPrice.hashCode()
        result = 31 * result + magnitude.hashCode()
        return result
    }
}

enum class Magnitude{
    POSITIVE,
    NEGATIVE
}