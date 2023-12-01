package beatCode.room_management.debugging;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ChannelInterceptor;

public class MessageChannelInterceptor implements ChannelInterceptor {
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        System.out.println("\n\n");

        System.out.println("Incoming message: " + message.toString());
        return message;
    }
}
