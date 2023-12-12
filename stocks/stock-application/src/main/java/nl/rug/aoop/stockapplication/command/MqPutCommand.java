package nl.rug.aoop.stockapplication.command;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.command.Command;
import nl.rug.aoop.messagequeue.message.Message;
import nl.rug.aoop.messagequeue.queue.MessageQueue;

import java.util.Map;

import static java.util.Objects.requireNonNull;

/**
 * Class MqPutCommand that implements Command.
 * It puts a received Message in the given MessageQueue.
 */
@Slf4j
public class MqPutCommand implements Command {

    /**
     * MessageQueue in which the Command can put Messages.
     */
    @Getter
    private final MessageQueue messageQueue;

    /**
     * Constructor for MqPutCommand that takes in a MessageQueue for the Messages to be put in.
     * @param messageQueue MessageQueue in which the Messages will be put in.
     */
    public MqPutCommand(MessageQueue messageQueue) {
        this.messageQueue = requireNonNull(messageQueue, "MessageQueue can't be null.");
    }

    /**
     * Method puts the Message obtained from Map options in the MessageQueue.
     * @param options Map(String, Object) with Message to be put in the MessageQueue as one of the Objects.
     */
    @Override
    public void execute(Map<String, Object> options) {
        try {
            String messageString = (String) options.get("Body");
            Message message = Message.fromJson(messageString);
            messageQueue.enqueue(message);
        } catch (ClassCastException e) {
            log.error("Error while casting received parameters.", e);
        }
    }
}
