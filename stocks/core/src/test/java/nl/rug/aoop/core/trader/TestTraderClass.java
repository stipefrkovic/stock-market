package nl.rug.aoop.core.trader;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * TestTraderClass tests the Trader class.
 */
public class TestTraderClass {

    /**
     * Method tests that the trader can be correctly created.
     */
    @Test
    void testConstructor() {
        Trader trader = new Trader("Trader", "Trader", 0L);
        assertNotNull(trader);
    }

    /**
     * Method tests that the trader can be correctly converted to and from String.
     */
    @Test
    void testStringConversion() throws JsonProcessingException {
        Trader trader = new Trader("Trader", "Trader", 0L);
        String traderString = Trader.toString(trader);
        assertEquals(trader, Trader.fromString(traderString));
    }

    /**
     * Method tests that a transaction can be added to the transaction history.
     */
    @Test
    void testAddTransaction() {
        Trader trader = new Trader("Trader", "Trader", 0L);
        assertEquals(0, trader.getTransactionHistory().size());
        Transaction transaction = new Transaction("Stock", 1, 1);
        trader.addTransaction(transaction);
        assertEquals(1, trader.getTransactionHistory().size());
        assertEquals(transaction, trader.getTransactionHistory().get(0));
    }

    /**
     * Method checks that the stock amount of a trader can be correctly retrieved.
     */
    @Test
    void testGetStockAmount() {
        Trader trader = new Trader("Trader", "Trader", 0L);
        assertEquals(0, trader.getStockAmount("Stock"));
    }

    /**
     * Method checks that the trader's stock amount can be set.
     */
    @Test
    void testSetStockAmount() {
        Trader trader = new Trader("Trader", "Trader", 0L);
        assertEquals(0L, trader.getStockAmount("Stock"));
        trader.setStockAmount("Stock", 1L);
        assertEquals(1L, trader.getStockAmount("Stock"));
    }

    /**
     * Method checks that it is correctly determined if trader has certain amount of stock.
     */
    @Test
    void testHasStockAmount() {
        Trader trader = new Trader("Trader", "Trader", 0L);
        trader.setStockAmount("Stock", 1L);
        assertEquals(1L, trader.getStockAmount("Stock"));
        assertEquals(0L, trader.getStockAmount("NoStock"));
    }
}
