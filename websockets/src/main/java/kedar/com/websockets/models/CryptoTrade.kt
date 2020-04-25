package kedar.com.websockets.models


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CryptoTrade(
    @Json(name = "c")
    val c: List<Int>,
    @Json(name = "ev")
    val ev: String,
    @Json(name = "i")
    val i: String,
    @Json(name = "p")
    val p: Double,
    @Json(name = "pair")
    val pair: String,
    @Json(name = "r")
    val r: Long,
    @Json(name = "s")
    val s: Double,
    @Json(name = "t")
    val t: Long,
    @Json(name = "x")
    val x: Int
)