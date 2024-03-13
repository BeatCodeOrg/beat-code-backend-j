package beatCode.room_management.debugging;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

// @Component
public class WebSocketEventListener {    
    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        System.out.println("COMON ");
        System.out.println("Received a new web socket connection");
    }

    @EventListener
    public void handleWebSocketSubscribeListener(SessionSubscribeEvent event) {
        System.out.println("Received a new web socket subscription");
        System.out.println(event.toString());
    }
}
