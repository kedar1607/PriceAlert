package kedar.com.websockets.models


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class StockExchange(
    @Json(name = "id")
    val id: Int,
    @Json(name = "market")
    val market: String? = null,
    @Json(name = "mic")
    val mic: String? = null,
    @Json(name = "name")
    val name: String? = null,
    @Json(name = "tape")
    val tape: String? = null,
    @Json(name = "type")
    val type: String? = null
)