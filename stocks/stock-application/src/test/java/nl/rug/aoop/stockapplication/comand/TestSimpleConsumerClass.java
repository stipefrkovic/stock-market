package nl.rug.aoop.stockapplication.comand;

import nl.rug.aoop.messagequeue.message.Message;
import nl.rug.aoop.messagequeue.queue.MessageQueue;
import nl.rug.aoop.messagequeue.queue.OrderedMessageQueue;
import nl.rug.aoop.stockapplication.command.SimpleConsumer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

/**
 * Class TestSimpleConsumerClass tests the SimpleConsumer class.
 */
public class TestSimpleConsumerClass {

    /**
     * Tests the constructor with legal parameters.
     */
    @Test
    void testLegalConstructor() {
        MessageQueue mockQueue = mock(MessageQueue.class);
        SimpleConsumer consumer = new SimpleConsumer(mockQueue);
        assertNotNull(consumer);
    }

    /**
     * Tests the constructor with illegal parameters.
     */
    @Test
    void testIllegalConstructor() {
        assertThrows(NullPointerException.class, () -> new SimpleConsumer(null));
    }

    /**
     * Tests whether a message has been correctly polled by a Consumer.
     */
    @Test
    void testPoll() {
        OrderedMessageQueue q = new OrderedMessageQueue();
        Message m = new Message("Hello", "Simple message");
        q.enqueue(m);
        Integer oldSize = q.getSize();
        SimpleConsumer consumer = new SimpleConsumer(q);
        consumer.poll();
        assertEquals(oldSize - 1, q.getSize());
    }
}
