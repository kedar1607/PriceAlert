package kedar.com.websockets.models


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CryptoExchange(
    @Json(name = "id")
    val id: Int,
    @Json(name = "market")
    val market: String? = null,
    @Json(name = "name")
    val name: String? = null,
    @Json(name = "type")
    val type: String? = null,
    @Json(name = "url")
    val url: String? = null
)