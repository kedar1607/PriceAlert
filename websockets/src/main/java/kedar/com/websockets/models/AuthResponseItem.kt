package kedar.com.websockets.models


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AuthResponseItem(
    @Json(name = "ev")
    val ev: String,
    @Json(name = "message")
    val message: String,
    @Json(name = "status")
    val status: String
)