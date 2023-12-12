package nl.rug.aoop.messagequeue.process;

import nl.rug.aoop.messagequeue.message.Message;

/**
 * Interface for a Producer. A Producer sends messages.
 */
public interface MQProducer {

    /**
     * Puts a message into a queue.
     * @param message The Message to be put into the queue.
     */
    void put(Message message);
}
