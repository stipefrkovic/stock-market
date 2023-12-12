package nl.rug.aoop.core.stock;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * TestStockCollectionClass tests the StockCollection class.
 */
public class TestStockCollectionClass {

    /**
     * Method tests that the stock collection can be converted to string and back.
     * @throws JsonProcessingException
     */
    @Test
    void testStringConversion() throws JsonProcessingException {
        StockCollection stockCollection = new StockCollection();
        Stock appleStock = new Stock("AAPL", "Apple", 0L, 10.0, 10.0);
        stockCollection.updateStock(appleStock);

        String stockCollectionString = StockCollection.toString(stockCollection);
        StockCollection convertedStockCollection = StockCollection.fromString(stockCollectionString);

        assertEquals(stockCollection, convertedStockCollection);
    }

    /**
     * Method tests that size of the collection can be correctly retrieved.
     */
    @Test
    void testGetSize() {
        StockCollection stockCollection = new StockCollection();
        assertEquals(0, stockCollection.getSize());
    }

    /**
     * Method tests that a stock can be correctly retrieved.
     */
    @Test
    void testGetStock() {
        StockCollection stockCollection = new StockCollection();
        Stock appleStock = new Stock("AAPL", "Apple", 0L, 10.0, 10.0);
        stockCollection.getStocks().put(appleStock.getSymbol(), appleStock);
        assertEquals(appleStock, stockCollection.getStock(appleStock.getSymbol()));
    }

    /**
     * Method tests that a new stock can be added if it is not already present.
     */
    @Test
    void testUpdateStockAdd() {
        StockCollection stockCollection = new StockCollection();
        assertEquals(0, stockCollection.getStocks().size());
        Stock appleStock = new Stock("AAPL", "Apple", 0L, 10.0, 10.0);
        stockCollection.updateStock(appleStock);
        assertEquals(1, stockCollection.getStocks().size());
    }

    /**
     * Method tests that a stock can be modified in case it is already present.
     */
    @Test
    void testUpdateStockModify() {
        StockCollection stockCollection = new StockCollection();
        Stock appleStock = new Stock("AAPL", "Apple", 0L, 10.0, 10.0);
        stockCollection.getStocks().put(appleStock.getSymbol(), appleStock);
        Stock newAppleStock = new Stock("AAPL", "Apple", 0L, 20.0, 10.0);
        stockCollection.updateStock(newAppleStock);
        assertEquals(newAppleStock, stockCollection.getStock(appleStock.getSymbol()));
    }

}
