package nl.rug.aoop.stockapplication.view;

import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.core.trader.Trader;
import nl.rug.aoop.model.TraderDataModel;
import nl.rug.aoop.stockapplication.stock.TraderManager;

import java.util.List;

/**
 * TraderData class allows the view to retrieve information about the traders.
 */
@Slf4j
public class TraderData implements TraderDataModel {
    /**
     * TraderManager contains all information regarding traders.
     */
    private TraderManager traderManager;
    /**
     * Current trader.
     */
    private Trader trader;

    /**
     * Constructor creates the class.
     * @param traderManager TraderManager contains all information regarding traders.
     * @param index Index of current trader.
     */
    public TraderData(TraderManager traderManager, int index) {
        //log.info("accessing trader at index: " + String.valueOf(index));
        this.traderManager = traderManager;
        this.trader = traderManager.getTraderCollection().getTraders().values().stream().toList().get(index);
    }

    /**
     * Retrieves the (unique) ID of the trader.
     *
     * @return The ID of the trader.
     */
    @Override
    public String getId() {
        return trader.getId();
    }

    /**
     * Retrieves the name of the trader.
     *
     * @return The name of the trader.
     */
    @Override
    public String getName() {
        return trader.getName();
    }

    /**
     * Retrieves the total amount of funds this trader has available for trading.
     *
     * @return The total number of funds.
     */
    @Override
    public double getFunds() {
        return trader.getFunds();
    }

    /**
     * Retrieves a collection of stocks that the trader owns.
     *
     * @return A list of stock symbols that the trader owns.
     */
    @Override
    public List<String> getOwnedStocks() {
        return trader.getOwnedShares().keySet().stream().toList();
    }
}
