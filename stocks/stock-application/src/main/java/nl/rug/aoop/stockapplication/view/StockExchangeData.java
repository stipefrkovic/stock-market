package nl.rug.aoop.stockapplication.view;

import nl.rug.aoop.model.StockDataModel;
import nl.rug.aoop.model.StockExchangeDataModel;
import nl.rug.aoop.model.TraderDataModel;
import nl.rug.aoop.stockapplication.stock.StockExchange;

/**
 * StockExchangeData class allows the view to retrieve information from the stock exchange.
 */
public class StockExchangeData implements StockExchangeDataModel {
    /**
     * The stock exchange the data model will draw from.
     */
    private StockExchange stockExchange;

    /**
     * Constructor creates the data model.
     * @param stockExchange The stock exchange the data model will draw from.
     */
    public StockExchangeData(StockExchange stockExchange) {
        this.stockExchange = stockExchange;
    }

    /**
     * Retrieves a stock from the stock exchange by its index.
     *
     * @param index The index of the stock that should be accessed.
     * @return The stock at that index.
     */
    @Override
    public StockDataModel getStockByIndex(int index) {
        return new StockData(stockExchange.getStockManager(), index);
    }

    /**
     * Retrieves the number of different stocks that can be traded on this stock exchange. Not to be confused with the
     * total number of shares.
     *
     * @return The total number of different stock symbols available at this stock exchange.
     */
    @Override
    public int getNumberOfStocks() {
        return stockExchange.getStockManager().getSize();
    }

    /**
     * Retrieves a trader from the stock exchange by its index.
     *
     * @param index The index of the trader that should be accessed.
     * @return The trader at that index.
     */
    @Override
    public TraderDataModel getTraderByIndex(int index) {
        return new TraderData(stockExchange.getTraderManager(), index);
    }

    /**
     * Retrieves the total number of traders trading on this stock exchange.
     *
     * @return The total number of traders trading on this stock exchange.
     */
    @Override
    public int getNumberOfTraders() {
        return stockExchange.getTraderManager().getSize();
    }
}
