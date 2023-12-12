package nl.rug.aoop.traderapplication.interactor;

import nl.rug.aoop.core.stock.StockCollection;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * TestLocalStockManagerClass tests the LocalStockManager class.
 */
public class TestLocalStockManagerClass {
    /**
     * Method tests that a constructor has been correctly created.
     */
    @Test
    void testConstructor() {
        LocalStockManager localStockManager = new LocalStockManager();
        assertNotNull(localStockManager);
    }

    /**
     * Method tests that the stock collection is correctly updated.
     */
    @Test
    void testUpdateStockCollection() {
        StockCollection testCollection = new StockCollection();
        LocalStockManager localStockManager = new LocalStockManager();
        localStockManager.updateStocks(testCollection);
        assertEquals(testCollection, localStockManager.getStockCollection());
    }
}
