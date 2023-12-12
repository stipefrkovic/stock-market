package nl.rug.aoop.stockapplication.command;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.command.CommandHandler;
import nl.rug.aoop.messagequeue.message.Message;
import nl.rug.aoop.messagequeue.process.MQConsumer;

import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.requireNonNull;

/**
 * Class StockPoller that implements Runnable. It gets new Messages (mostly Orders) from the MQConsumer and calls the
 * CommandHandler with the map from the converted Message string.
 */
@Slf4j
public class StockPoller implements Runnable {
    /**
     * MQConsumer from which the StockPoller will receive Messages.
     */
    private final MQConsumer consumer;
    /**
     * CommandHandler that the StockPoller will call execute on.
     */
    private final CommandHandler stockCommandHandler;
    /**
     * boolean signaling whether the StockPoller is running or not.
     */
    @Getter
    private boolean running = false;

    /**
     * Constructor for StockPoller.
     * @param consumer MQConsumer from which the StockPoller will receive Messages.
     * @param stockCommandHandler CommandHandler that the StockPoller will call execute on.
     */
    public StockPoller(MQConsumer consumer, CommandHandler stockCommandHandler) {
        this.consumer = requireNonNull(consumer);
        this.stockCommandHandler = requireNonNull(stockCommandHandler);
    }

    /**
     * Method that will continuously get Messages and from the MQConsumer and execute the CommandHandler with them.
     */
    @Override
    public void run() {
        running = true;
        log.info("StockPoller is running.");
        while (running) {
            Message message = consumer.poll();
            if (message != null) {
                Map<String, Object> map = new HashMap<>();
                map.put("Header", message.header());
                map.put("Body", message.body());
                stockCommandHandler.executeCommand(message.header(), map);
            }
        }
    }

    /**
     * Method terminates the StockPoller by stopping the run() method.
     */
    public void terminate() {
        log.info("StockPoller is terminated.");
        running = false;
    }

}
