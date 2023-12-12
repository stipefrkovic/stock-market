package nl.rug.aoop.stockapplication.view;

import nl.rug.aoop.core.stock.Stock;
import nl.rug.aoop.model.StockDataModel;
import nl.rug.aoop.stockapplication.stock.StockManager;

/**
 * StockData class allows the view to retrieve information about stocks.
 */
public class StockData implements StockDataModel {
    /**
     * StockManager containing information on the stocks.
     */
    private StockManager stockManager;
    /**
     * Current stock.
     */
    private Stock stock;

    /**
     * Constructor creates the class.
     * @param stockManager StockManager containing information on the stocks.
     * @param index Index of a stock.
     */
    public StockData(StockManager stockManager, int index) {
        this.stockManager = stockManager;
        stock = stockManager.getStockCollection().getStocks().values().stream().toList().get(index);
    }

    /**
     * Retrieves the symbol of a stock. Usually a 3-letter string of all upper-case letters.
     *
     * @return The symbol of the stock.
     */
    @Override
    public String getSymbol() {
        return stock.getSymbol();
    }

    /**
     * Retrieves the name of the company associated with the stock.
     *
     * @return Name of the company.
     */
    @Override
    public String getName() {
        return stock.getName();
    }

    /**
     * Retrieves the number of shares available for trading.
     *
     * @return Total number of shares outstanding.
     */
    @Override
    public long getSharesOutstanding() {
        return stock.getSharesOutstanding();
    }

    /**
     * Retrieves the total market cap of the company. Calculated as sharesOutstanding * price
     *
     * @return Market cap of the company.
     */
    @Override
    public double getMarketCap() {
        return stock.getMarketCapitalization();
    }

    /**
     * Retrieves the price of a single share. Represents the latest price a share was traded at.
     *
     * @return The price of a single share.
     */
    @Override
    public double getPrice() {
        return stock.getPrice();
    }
}
