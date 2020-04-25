package kedar.com.websockets.models


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AuthResponse(
    @Json(name = "")
    val arrayList: ArrayList<AuthResponseItem>
)