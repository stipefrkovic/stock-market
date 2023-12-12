package nl.rug.aoop.core.trader;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * TestTraderCollectionClass tests the TraderCollection class.
 */
public class TestTraderCollectionClass {

    /**
     * Method tests that the collection can be correctly created.
     */
    @Test
    void testConstructor() {
        TraderCollection traderCollection = new TraderCollection();
        assertNotNull(traderCollection);
    }

    /**
     * Method tests that the size of the collection can be correctly retrieved.
     */
    @Test
    void testGetSize() {
        TraderCollection traderCollection = new TraderCollection();
        assertEquals(0, traderCollection.getSize());
    }

    /**
     * Method checks that a trader can be correctly retrieved.
     */
    @Test
    void testGetTrader() {
        TraderCollection traderCollection = new TraderCollection();
        Trader trader = new Trader("Trader", "Trader", 1L);
        traderCollection.getTraders().put(trader.getId(), trader);
        assertEquals(trader, traderCollection.getTrader(trader.getId()));
    }

    /**
     * Method tests that a trader can be added if it is not already present.
     */
    @Test
    void testUpdateTraderAdd() {
        TraderCollection traderCollection = new TraderCollection();
        assertEquals(0, traderCollection.getSize());
        Trader trader = new Trader("Trader", "Trader", 1L);
        traderCollection.updateTrader(trader);
        assertEquals(1, traderCollection.getSize());
        assertEquals(trader, traderCollection.getTrader(trader.getId()));
    }

    /**
     * Method tests that a trader is modified in case it is already present.
     */
    @Test
    void testUpdateTraderModify() {
        TraderCollection traderCollection = new TraderCollection();
        Trader trader = new Trader("Trader", "Trader", 1L);
        traderCollection.getTraders().put(trader.getId(), trader);
        assertEquals(trader, traderCollection.getTrader(trader.getId()));
        Trader newTrader = new Trader("Trader", "Trader", 2L);
        traderCollection.updateTrader(newTrader);
        assertEquals(newTrader, traderCollection.getTrader(trader.getId()));
    }

}
