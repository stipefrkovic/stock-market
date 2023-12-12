package nl.rug.aoop.stockapplication.command;

import nl.rug.aoop.messagequeue.message.Message;
import nl.rug.aoop.messagequeue.process.MQConsumer;
import nl.rug.aoop.messagequeue.queue.MessageQueue;

import static java.util.Objects.requireNonNull;

/**
 * A simple implementation of the Consumer class.
 */
public class SimpleConsumer implements MQConsumer {
    /**
     * MessageQueue from which the Consumer should poll.
     */
    private final MessageQueue queue;

    /**
     * Constructor creates the Consumer.
     * @param queue Message queue from which the Message should be polled from.
     */
    public SimpleConsumer(MessageQueue queue) {
        this.queue = requireNonNull(queue, "MessageQueue can't be null.");
    }

    /**
     * Method polls a Message from the MessageQueue.
     */
    @Override
    public Message poll() {
        return queue.dequeue();
    }
}
