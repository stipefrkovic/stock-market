package nl.rug.aoop.messagequeue.queue;

import nl.rug.aoop.messagequeue.message.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Class TestUnorderedMessageQueueClass tests the UnorderedMessageQueue class.
 */
public class TestUnorderedMessageQueueClass {
    /**
     * UnorderedMessageQueue object used to test it's class.
     */
    private UnorderedMessageQueue queue;

    /**
     * Method initializes the queue.
     */
    @BeforeEach
    public void initQueue() {
        queue = new UnorderedMessageQueue();
    }

    /**
     * Method tests the getSize method of UnorderedMessageQueue.
     */
    @Test
    void testGetSize() {
        assertEquals(0, queue.getSize());
    }

    /**
     * Method tests that the UnorderedMessageQueue enqueue method adds a Message to the queue.
     */
    @Test
    void testEnqueue() {
        Integer oldSize = queue.getSize();
        Message message = new Message("header", "body");
        queue.enqueue(message);
        assertEquals(oldSize + 1, queue.getSize());
    }

    /**
     * Method tests that the UnorderedMessageQueue enqueue method doesn't accept a null.
     */
    @Test
    void testNullArgumentEnqueue() {
        assertThrows(NullPointerException.class, () -> queue.enqueue(null));
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
     * Method tests that the UnorderedMessageQueue dequeue method removes a Message form the queue.
     */
    @Test
    void testDequeue() {
        Message message1 = new Message("key1", "value1");
        Message message2 = new Message("key2", "value2");
        queue.enqueue(message1);
        queue.enqueue(message2);
        Integer oldSize = queue.getSize();
        queue.dequeue();
        assertEquals(oldSize - 1, queue.getSize());
    }

    /**
     * Method tests that the UnorderedMessageQueue orders the Messages based on when they arrive.
     */
    @Test
    void testUnorderedQueueOrder() {
        LocalDateTime t1 = LocalDateTime.now();
        Message m1 = new Message("first", "message", t1);
        LocalDateTime t2 = LocalDateTime.now().plusYears(1);
        Message m2 = new Message("second", "message", t2);
        LocalDateTime t3 = LocalDateTime.now().plusYears(2);
        Message m3 = new Message("third", "message", t3);
        queue.enqueue(m2);
        queue.enqueue(m1);
        queue.enqueue(m3);
        assertEquals("second", queue.dequeue().header());
        assertEquals("first", queue.dequeue().header());
        assertEquals("third", queue.dequeue().header());
    }

}
