package htl.leonding.endpoints;

import htl.leonding.entities.PepperMessage;
import jakarta.inject.Inject;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;

@ServerEndpoint("/sayMessageWebsocket")
public class sayMessageWebsocket {

    @Inject
    PepperMessage pepperMessage;
    @OnOpen
    public void onOpen(Session session) {
        // Handle WebSocket connection
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        // Handle incoming messages
        // You can manually send a response back to the client here
        session.getAsyncRemote().sendText(pepperMessage.PepperMessage);
    }

    @OnClose
    public void onClose(Session session, CloseReason reason) {
        // Handle WebSocket closure
    }
}
