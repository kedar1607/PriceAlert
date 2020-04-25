package kedar.com.websockets

object Constants {
    const val POLYGON_STOCK_BASE_URL = "wss://socket.polygon.io/stocks"
    const val POLYGON_CRYPTO_BASE_URL = "wss://socket.polygon.io/crypto"
    const val AUTH = "auth"
    const val ACTION_SUBSCRIBE = "subscribe"
    const val ACTION_UNSUBSCRIBE = "unsubscribe"
    const val SUBSCRIBE_STOCK_TRADE = "T."
    const val SUBSCRIBE_CRYPTO_TRADE = "XT."
    const val SUBSCRIBE_STOCK_QUOTE = "Q."
    const val SUBSCRIBE_STOCK_AGGREGATE = "A."
}