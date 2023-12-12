package nl.rug.aoop.messagequeue.queue;

import nl.rug.aoop.messagequeue.message.Message;

/**
 * Interface MessageQueue with 3 methods: enqueue, dequeue, and getSize.
 */
public interface MessageQueue {
    /**
     * Method adds a message to the MessageQueue.
     * @param message Message to be added.
     */
    void enqueue(Message message);

    /**
     * Method that retrieves a message from the MessageQueue.
     * @return Message to be retrieved from the MessageQueue.
     */
    Message dequeue();

    /**
     * Method that gets the size of the MessageQueue.
     * @return Integer size of the MessageQueue.
     */
    Integer getSize();
}
