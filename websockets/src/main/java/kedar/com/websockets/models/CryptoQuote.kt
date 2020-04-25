package kedar.com.websockets.models


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CryptoQuote(
    @Json(name = "ap")
    val ap: Double,
    @Json(name = "as")
    val asX: Double,
    @Json(name = "bp")
    val bp: Double,
    @Json(name = "bs")
    val bs: Double,
    @Json(name = "ev")
    val ev: String,
    @Json(name = "lp")
    val lp: Double,
    @Json(name = "ls")
    val ls: Double,
    @Json(name = "pair")
    val pair: String,
    @Json(name = "r")
    val r: Long,
    @Json(name = "t")
    val t: Long,
    @Json(name = "x")
    val x: Int
)