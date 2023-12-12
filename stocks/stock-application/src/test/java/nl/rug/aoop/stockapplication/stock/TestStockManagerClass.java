package nl.rug.aoop.stockapplication.stock;

import nl.rug.aoop.core.stock.Stock;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * TestStockManagerClass class that tests the StockManager class.
 */
public class TestStockManagerClass {

    /**
     * Tests that the StockManager constructor creates a non-null StockManager.
     */
    @Test
    void testConstructor() {
        StockManager stockManager = new StockManager();
        assertNotNull(stockManager);
    }

    /**
     * Tests that the StockManager getSize method returns the size of its StockCollection(s).
     */
    @Test
    void testGetSize() {
        StockManager stockManager = new StockManager();
        assertEquals(0, stockManager.getSize());
    }

    /**
     * Tests that the StockManager loadStocks method loads the Stocks from the yaml file.
     */
    @Test
    void testLoadStocks() {
        StockManager stockManager = new StockManager();
        stockManager.loadStocks();
        assertNotNull(stockManager.getStockCollection());
        assertNotEquals(0, stockManager.getSize());
    }

    /**
     * Tests that the StockManager getStock method gets the Stock from its StockCollection(s).
     */
    @Test
    void testGetStock() {
        StockManager stockManager = new StockManager();
        Stock stock = new Stock("Stock", "Stock", 1L, 1.0, 1.0);
        stockManager.getStockCollection().getStocks().put("Stock", stock);
        assertEquals(stock, stockManager.getStock(stock.getSymbol()));
    }

    /**
     * Tests that the StockManager getStock method throws an exception when a null argument is passed.
     */
    @Test
    void testNullArgGetStock() {
        StockManager stockManager = new StockManager();
        assertThrows(NullPointerException.class, () -> stockManager.getStock(null));
    }


}
