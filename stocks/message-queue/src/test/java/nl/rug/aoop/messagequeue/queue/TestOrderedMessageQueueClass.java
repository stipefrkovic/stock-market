package nl.rug.aoop.messagequeue.queue;

import nl.rug.aoop.messagequeue.message.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Class TestOrderedMessageQueueClass tests the OrderedMessageQueue class.
 */
public class TestOrderedMessageQueueClass {
    /**
     * OrderedMessageQueue object used to test it's class.
     */
    private OrderedMessageQueue queue;

    /**
     * Method initializes the queue.
     */
    @BeforeEach
    public void initQueue() {
        queue = new OrderedMessageQueue();
    }

    /**
     * Method tests the getSize method of OrderedMessageQueue.
     */
    @Test
    void testGetSize() {
        assertEquals(0, queue.getSize());
    }

    /**
     * Method tests that the OrderedMessageQueue enqueue method adds a Message to the queue.
     */
    @Test
    void testEnqueue() {
        Integer oldSize = queue.getSize();
        Message message = new Message("header", "body");
        queue.enqueue(message);
        assertEquals(oldSize + 1, queue.getSize());
    }

    /**
     * Method tests that the OrderedMessageQueue enqueue method doesn't accept a null.
     */
    @Test
    void testNullArgumentEnqueue() {
        Exception e1 = assertThrows(NullPointerException.class, () -> queue.enqueue(null));
    }

    /**
     * Method tests that the OrderedMessageQueue can enqueue multiple Messages with the same timestamp i.e. header.
     */
    @Test
    void testSameKeyEnqueue() {
        Integer oldSize = queue.getSize();
        LocalDateTime timestamp = LocalDateTime.now();
        Message m1 = new Message("Cool", "Kid", timestamp);
        Message m2 = new Message("Cool", "Aid", timestamp);
        queue.enqueue(m1);
        queue.enqueue(m2);
        assertEquals(oldSize + 2, queue.getSize());
    }

    /**
     * Method tests that the OrderedMessageQueue returns null when it's dequeued while empty.
     */
    @Test
    void testEmptyQueueDequeue() {
        assertEquals(0, queue.getSize());
        assertNull(queue.dequeue());
    }

    /**
     * Method tests that the OrderedMessageQueue dequeue method removes a Message form the queue.
     */
    @Test
    void testDequeue() {
        Message message = new Message("header", "body");
        queue.enqueue(message);
        Integer oldSize = queue.getSize();
        queue.dequeue();
        assertEquals(oldSize - 1, queue.getSize());
    }

    /**
     * Method tests that the OrderedMessageQueue orders the Messages based on their timestamps.
     */
    @Test
    void testOrderOfEnqueue() {
        LocalDateTime t1 = LocalDateTime.now();
        Message m1 = new Message("first", "message", t1);
        LocalDateTime t2 = LocalDateTime.now().plusYears(1);
        Message m2 = new Message("second", "message", t2);
        LocalDateTime t3 = LocalDateTime.now().plusYears(2);
        Message m3 = new Message("third", "message", t3);
        queue.enqueue(m2);
        queue.enqueue(m3);
        queue.enqueue(m1);
        assertEquals("first", queue.dequeue().header());
        assertEquals("second", queue.dequeue().header());
        assertEquals("third", queue.dequeue().header());
    }

}
