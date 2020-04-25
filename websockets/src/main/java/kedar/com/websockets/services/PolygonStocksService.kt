package kedar.com.websockets.services

import com.tinder.scarlet.WebSocket
import com.tinder.scarlet.ws.Receive
import com.tinder.scarlet.ws.Send
import io.reactivex.Flowable
import kedar.com.websockets.models.*

interface PolygonStocksService {
    @Receive
    fun observeWebSocketEvent(): Flowable<WebSocket.Event>
    @Send
    fun sendAuthentication(authAction: AuthAction): Boolean
    @Send
    fun subscribeAction(subscribeAction: SubscribeAction)   : Boolean
    @Receive
    fun observeTrades(): Flowable<List<Trade>>
}