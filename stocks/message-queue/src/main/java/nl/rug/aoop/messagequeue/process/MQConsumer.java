package nl.rug.aoop.messagequeue.process;

import nl.rug.aoop.messagequeue.message.Message;

/**
 * Interface for a Consumer. A Consumer accepts messages.
 */
public interface MQConsumer {
    /**
     * Polls a message from a given MessageQueue.
     * @return Message that is polled.
     */
    Message poll();
}
