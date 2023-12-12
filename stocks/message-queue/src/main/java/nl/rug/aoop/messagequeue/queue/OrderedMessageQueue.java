package nl.rug.aoop.messagequeue.queue;

import nl.rug.aoop.messagequeue.message.Message;

import java.util.concurrent.PriorityBlockingQueue;

/**
 * Class OrderedMessageQueue that implements MessageQueue. It orders Messages based on their TimeStamp and supports
 * thread operations because the underlying queue is a PriorityBlockingQueue.
 */
public class OrderedMessageQueue implements MessageQueue{
    /**
     * PriorityBlockingQueue that stores the Messages.
     */
    private final PriorityBlockingQueue<Message> queue;

    /**
     * Constructor for the OrderMessageQueue. It initializes the PriorityBlockingQueue.
     */
    public OrderedMessageQueue() {
        queue = new PriorityBlockingQueue<>();
    }

    /**
     * Method adds a Message to the MessageQueue.
     * @param message Message to be added.
     */
    @Override
    public void enqueue(Message message) {
        queue.add(message);
    }

    /**
     * Method that retrieves a message from the MessageQueue.
     * @return Message to be retrieved from the MessageQueue.
     */
    @Override
    public Message dequeue() {
        return queue.poll();
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
