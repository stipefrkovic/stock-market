package nl.rug.aoop.messagequeue.message;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Class TestMessageClass tests the Message class
 */
public class TestMessageClass {
    /**
     * Method tests the Message constructor.
     */
    @Test
    void testConstructor() {
        String header = "header", body = "body";
        Message message = new Message(header, body);
        assertEquals(header, message.header());
        assertEquals(body, message.body());
    }

    /**
     * Tests the correct conversion of a message to Json.
     */
    @Test
    void testJson() {
        String header = "header", body = "body";
        LocalDateTime time = LocalDateTime.now();
        Message message = new Message(header, body, time);

        String json = message.toJson();
        Message convertedMessage = Message.fromJson(json);

        assertEquals(message, convertedMessage);
    }

}
