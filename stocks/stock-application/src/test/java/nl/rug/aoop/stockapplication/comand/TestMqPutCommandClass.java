package nl.rug.aoop.stockapplication.comand;

import nl.rug.aoop.messagequeue.message.Message;
import nl.rug.aoop.messagequeue.queue.MessageQueue;
import nl.rug.aoop.messagequeue.queue.OrderedMessageQueue;
import nl.rug.aoop.stockapplication.command.MqPutCommand;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Class TestMqPutCommandClass tests the MqPutCommand class.
 */
public class TestMqPutCommandClass {
    /**
     * Message queue to be passed into the command.
     */
    private MessageQueue queue;
    /**
     * The command that will be tested.
     */
    private MqPutCommand command;

    /**
     * Create the command.
     */
    void createCommand() {
        queue = new OrderedMessageQueue();
        command = new MqPutCommand(queue);
    }

    /**
     * Tests that the constructor works with correct parameters.
     */
    @Test
    void testLegalConstructor() {
        createCommand();

        assertEquals(queue, command.getMessageQueue());
    }

    /**
     * Tests that the constructor throws the appropriate exception with
     */
    @Test
    void testIllegalConstructor() {
        assertThrows(NullPointerException.class, () -> new MqPutCommand(null));
    }


    /**
     * Tests that the command is executed correctly.
     * This involves checking that an item has been correctly added to the message queue.
     */
    @Test
    void testExecute() {
        createCommand();

        Assertions.assertEquals(0, queue.getSize());
        Message message = new Message("header", "body");
        Map<String, Object> map = new HashMap<>();
        map.put("Body", message.toJson());
        command.execute(map);
        Assertions.assertEquals(1, queue.getSize());
    }
}
