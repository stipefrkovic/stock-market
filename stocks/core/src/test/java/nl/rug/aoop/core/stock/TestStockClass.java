package nl.rug.aoop.core.stock;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * TestStockClass tests the Stock class.
 */
public class TestStockClass {

    /**
     * Method tests that a Stock can be correctly created.
     */
    @Test
    void testConstructor() {
        Stock stock = new Stock("Stock", "Stock", 1L, 1.0, 1.0);
        assertNotNull(stock);
    }

    /**
     * Method tests that the price of a stock can be correctly updated.
     */
    @Test
    void testUpdatePrice() {
        Stock stock = new Stock("Stock", "Stock", 1L, 1.0, 1.0);
        stock.updatePrice(2.0);
        assertEquals(2.0, stock.getPrice());
        assertEquals(2.0, stock.getMarketCapitalization());
    }

    /**
     * Method tests that the market capitalization can be correctly updated.
     */
    @Test
    void testUpdateMarketCapitalization() {
        Stock stock = new Stock("Stock", "Stock", 1L, 1.0, 1.0);
        stock.setPrice(2.0);
        stock.updateMarketCapitalization();
        assertEquals(stock.getSharesOutstanding().floatValue() * stock.getPrice()
                , stock.getMarketCapitalization());
    }
}
