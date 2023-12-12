package nl.rug.aoop.messagequeue.message;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Class TestNetworkMessageClass tests the NetworkMessage class.
 */
public class TestNetworkMessageClass {
    /**
     * Method tests the Message constructor.
     */
    @Test
    void testConstructor() {
        String header = "header", body = "body";
        NetworkMessage message = new NetworkMessage(header, body);
        assertEquals(header, message.header());
        assertEquals(body, message.body());
    }

    /**
     * Tests the correct conversion of a message to Json.
     */
    @Test
    void testJson() {
        String header = "header", body = "body";
        Message message = new Message(header, body);
        String json = message.toJson();
        Message convertedMessage = Message.fromJson(json);
        assertEquals(message, convertedMessage);
    }

}
