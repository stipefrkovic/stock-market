package nl.rug.aoop.messagequeue.queue;

import nl.rug.aoop.messagequeue.message.Message;

import java.util.LinkedList;

import static java.util.Objects.requireNonNull;

/**
 * Class UnorderedMessageQueue that implements MessageQueue. It orders Messages based on when they arrive.
 */
public class UnorderedMessageQueue implements MessageQueue {
    /**
     * LinkedList that acts as a queue for the Messages which are ordered based on when they arrive.
     */
    private final LinkedList<Message> queue;

    /**
     * Constructor that assigns the LinkedList.
     */
    public UnorderedMessageQueue() {
        queue = new LinkedList<>();
    }

    /**
     * Method adds a message to the MessageQueue.
     * @param message Message to be added.
     */
    @Override
    public void enqueue(Message message) {
        queue.add(requireNonNull(message, "Message can't be null."));
    }

    /**
     * Method that retrieves a message from the MessageQueue.
     * @return Message to be retrieved from the MessageQueue.
     */
    @Override
    public Message dequeue() {
        if (queue.size() > 0) {
            return queue.remove();
        }
        return null;
    }

    /**
     * Method that gets the size of the MessageQueue.
     * @return Integer size of the MessageQueue.
     */
    @Override
    public Integer getSize() {
        return queue.size();
    }
}
