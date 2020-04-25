package kedar.com.websockets.models


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Aggregate(
    @Json(name = "a")
    val a: Double,
    @Json(name = "av")
    val av: Int,
    @Json(name = "c")
    val c: Double,
    @Json(name = "e")
    val e: Long,
    @Json(name = "ev")
    val ev: String,
    @Json(name = "h")
    val h: Double,
    @Json(name = "l")
    val l: Double,
    @Json(name = "o")
    val o: Double,
    @Json(name = "op")
    val op: Double,
    @Json(name = "s")
    val s: Long,
    @Json(name = "sym")
    val sym: String,
    @Json(name = "v")
    val v: Int,
    @Json(name = "vw")
    val vw: Double
)