package nl.rug.aoop.stockapplication.network;

import nl.rug.aoop.command.CommandHandler;
import nl.rug.aoop.messagequeue.message.Message;
import nl.rug.aoop.messagequeue.message.NetworkMessage;
import nl.rug.aoop.networking.Communicator;
import nl.rug.aoop.networking.MessageHandler;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

/**
 * Class TestServerMessageHandlerClass tests the ServerMessageHandler class.
 */
public class TestServerMessageHandler {
    /**
     * Mock CommandHandler used to create the ServerMessageHandler.
     */
    CommandHandler mockCommandHandler;
    /**
     * ServerMessageHandler to be used in testing.
     */
    MessageHandler serverMessageHandler;

    /**
     * Creates the ServerMessageHandler.
     */
    void setupServerMessageHandler() {
        mockCommandHandler = mock(CommandHandler.class);
        serverMessageHandler = new ServerMessageHandler(mockCommandHandler);
    }

    /**
     * Tests that the ServerMessageHandler has been correctly created.
     */
    @Test
    void testLegalConstructor() {
        setupServerMessageHandler();

        assertNotNull(serverMessageHandler);
    }

    /**
     * Tests if an invalid argument will throw the correct exception upon creation.
     */
    @Test
    void testIllegalConstructor() {
        assertThrows(NullPointerException.class, () -> new ServerMessageHandler(null));
    }

    /**
     * Tests whether a message is correctly handled. Creates a mock CommandHandler to verify
     * correct behavior once we pass in the relevant arguments.
     */
    @Test
    void testLegalHandleMessage() {
        Message message = new Message("Message header", "Message body");
        NetworkMessage networkMessage = new NetworkMessage("Network message header", message.toJson());
        Map<String, Object> map = new HashMap<>();
        map.put("Header", networkMessage.header());
        map.put("Body", networkMessage.body());
        Communicator mockCommunicator = mock(Communicator.class);
        map.put("Communicator", mockCommunicator);
        String networkMessageJsonString = networkMessage.toJson();

        setupServerMessageHandler();

        serverMessageHandler.handleMessage(networkMessageJsonString, mockCommunicator);
        verify(mockCommandHandler, atLeastOnce()).executeCommand(networkMessage.header(), map);
    }

    /**
     * Tests that given null input or empty message, the handleMessage() method will throw the correct exception.
     */
    @Test
    void testNullHandleMessage() {
        setupServerMessageHandler();

        Exception e1 = assertThrows(NullPointerException.class,
                () -> serverMessageHandler.handleMessage(null, mock(Communicator.class)));
        Exception e2 = assertThrows(NullPointerException.class,
                () -> serverMessageHandler.handleMessage("", null));
    }

    /**
     * Tests that given a message that is not a Json String, handleMessage() will throw the correct exception.
     */
    @Test
    void testIllegalHandleMessage() {
        setupServerMessageHandler();

        Exception e1 = assertThrows(Exception.class,
                () -> serverMessageHandler.handleMessage("Not a json string.", mock(Communicator.class)));
    }
}
