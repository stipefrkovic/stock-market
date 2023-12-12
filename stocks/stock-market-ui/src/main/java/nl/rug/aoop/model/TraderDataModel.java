package nl.rug.aoop.model;

import java.util.List;

/**
 * Data model of a trader.
 * Note that a trader may have more properties; these are just the ones needed for the view.
 */
public interface TraderDataModel {
    /**
     * Retrieves the (unique) ID of the trader.
     *
     * @return The ID of the trader.
     */
    String getId();

    /**
     * Retrieves the name of the trader.
     *
     * @return The name of the trader.
     */
    String getName();

    /**
     * Retrieves the total amount of funds this trader has available for trading.
     *
     * @return The total number of funds.
     */
    double getFunds();

    /**
     * Retrieves a collection of stocks that the trader owns.
     *
     * @return A list of stock symbols that the trader owns.
     */
    List<String> getOwnedStocks();
}
