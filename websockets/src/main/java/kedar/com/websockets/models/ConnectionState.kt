package kedar.com.websockets.models

enum class ConnectionState {
    LOADING, CONNECTION_OPENED, CONNECTION_FAILED, AUTHENTICATED, AUTHENTICATION_FAILED, SUBSCRIBED
}