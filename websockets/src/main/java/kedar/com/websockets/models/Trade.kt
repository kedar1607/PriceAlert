package kedar.com.websockets.models


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Trade(
    @Json(name = "c")
    val c: List<Int>,
    @Json(name = "ev")
    val ev: String,
    @Json(name = "i")
    val i: String,
    @Json(name = "p")
    val p: Double,
    @Json(name = "s")
    val s: Int,
    @Json(name = "sym")
    val sym: String,
    @Json(name = "t")
    val t: Long,
    @Json(name = "x")
    val x: Int,
    @Json(name = "z")
    val z: Int
)