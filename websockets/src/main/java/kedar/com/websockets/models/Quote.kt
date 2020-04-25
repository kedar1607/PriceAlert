package kedar.com.websockets.models


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Quote(
    @Json(name = "ap")
    val ap: Double,
    @Json(name = "as")
    val asX: Int,
    @Json(name = "ax")
    val ax: String,
    @Json(name = "bp")
    val bp: Double,
    @Json(name = "bs")
    val bs: Int,
    @Json(name = "bx")
    val bx: String,
    @Json(name = "c")
    val c: Int,
    @Json(name = "ev")
    val ev: String,
    @Json(name = "sym")
    val sym: String,
    @Json(name = "t")
    val t: Long
)