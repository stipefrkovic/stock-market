package nl.rug.aoop.traderapplication.interactor;

import lombok.Getter;
import nl.rug.aoop.core.stock.StockCollection;

/**
 * LocalStockManager class manages the stock information of the trader application.
 */
public class LocalStockManager {
    /**
     * Stock collection containing information on all the stocks.
     */
    @Getter
    private StockCollection stockCollection = null;

    /**
     * Default constructor.
     */
    public LocalStockManager() {
    }

    /**
     * Method updates the stock collection.
     * @param stockCollection New stock collection.
     */
    public void updateStocks(StockCollection stockCollection) {
        this.stockCollection = stockCollection;
    }
}
