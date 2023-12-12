package nl.rug.aoop.traderapplication.command;

import lombok.Getter;
import nl.rug.aoop.command.CommandHandler;
import nl.rug.aoop.command.CommandHandlerFactory;
import nl.rug.aoop.traderapplication.interactor.LocalStockManager;
import nl.rug.aoop.traderapplication.interactor.LocalTraderManager;

/**
 * TraderFactory class creates the command handlers for the trader application.
 * Implements the CommandHandlerFactory Interface.
 */
@Getter
public class TraderFactory implements CommandHandlerFactory {
    /**
     * LocalTraderManager manages local traders.
     */
    private final LocalTraderManager localTraderManager;
    /**
     * LocalStockManager manages the stock collection.
     */
    private final LocalStockManager localStockManager;

    /**
     * Constructor creates the TraderFactory class.
     *
     * @param localTraderManager LocalTraderManager manages local traders.
     * @param localStockManager  LocalStockManager manages the stock collection.
     */
    public TraderFactory(LocalTraderManager localTraderManager, LocalStockManager localStockManager) {
        this.localTraderManager = localTraderManager;
        this.localStockManager = localStockManager;
    }

    /**
     * Method creates the appropriate command handler based on a specified type.
     *
     * @param type Type indicating what command handler is to be created.
     * @return Created command handler.
     */
    @Override
    public CommandHandler create(String type) throws IllegalArgumentException {
        if (type.equals(Types.TRADER.toString())) {
            CommandHandler updateCommandHandler = new CommandHandler();
            updateCommandHandler.registerCommand("updateStocks", new UpdateStocksCommand(localStockManager));
            updateCommandHandler.registerCommand("updateTrader", new UpdateTraderCommand(localTraderManager));
            return updateCommandHandler;
        } else {
            throw new IllegalArgumentException("Command handler type not recognized.");
        }
    }

    /**
     * Enum contains all the Types for command handlers.
     */
    public enum Types {TRADER}
}
