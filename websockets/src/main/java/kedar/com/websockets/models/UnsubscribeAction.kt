package kedar.com.websockets.models


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UnsubscribeAction(
    @Json(name = "action")
    val action: String,
    @Json(name = "params")
    val params: String
)