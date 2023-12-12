package nl.rug.aoop.stockapplication.command;

import nl.rug.aoop.command.CommandHandler;
import nl.rug.aoop.command.CommandHandlerFactory;
import nl.rug.aoop.messagequeue.queue.MessageQueue;
import nl.rug.aoop.stockapplication.stock.PeriodicUpdater;
import nl.rug.aoop.stockapplication.stock.StockExchange;

/**
 * Class StockFactory that implements CommandHandlerFactory. It creates CommandHandlers for the stock-application.
 */
public class StockFactory implements CommandHandlerFactory {
    /**
     * StockExchange needed for ResolveOrderCommand.
     */
    private final StockExchange stockExchange;
    /**
     * MessageQueue needed for MqPutCommand.
     */
    private final MessageQueue messageQueue;
    /**
     * PeriodicUpdater needed for RegisterTraderCommand.
     */
    private final PeriodicUpdater periodicUpdater;

    /**
     * Constructor for StockFactory.
     *
     * @param stockExchange   StockExchange needed for ResolveOrderCommand.
     * @param messageQueue    MessageQueue needed for MqPutCommand.
     * @param periodicUpdater PeriodicUpdater needed for RegisterTraderCommand.
     */
    public StockFactory(StockExchange stockExchange, MessageQueue messageQueue, PeriodicUpdater periodicUpdater) {
        this.stockExchange = stockExchange;
        this.messageQueue = messageQueue;
        this.periodicUpdater = periodicUpdater;
    }

    /**
     * Creates the CommandHandler of selected type.
     *
     * @param type String of the CommandHandler type.
     * @return CommandHandler.
     * @throws IllegalArgumentException if an unrecognised CommandHandler type is given.
     */
    @Override
    public CommandHandler create(String type) throws IllegalArgumentException {
        if (type.equals(Types.STOCK.toString())) {
            CommandHandler stockCommandHandler = new CommandHandler();
            stockCommandHandler.registerCommand("resolveOrder", new ResolveOrderCommand(stockExchange));
            return stockCommandHandler;
        } else if (type.equals(Types.NETWORK.toString())) {
            CommandHandler serverCommandHandler = new CommandHandler();
            serverCommandHandler.registerCommand("mqPut", new MqPutCommand(messageQueue));
            serverCommandHandler.registerCommand("registerTrader", new RegisterTraderCommand(periodicUpdater));
            return serverCommandHandler;
        } else {
            throw new IllegalArgumentException("Command handler type not recognised.");
        }
    }

    /**
     * enum holds the available StockFactory CommandHandler Types.
     */
    public enum Types {STOCK, NETWORK}
}
