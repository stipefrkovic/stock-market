package nl.rug.aoop.stockapplication.stock;

import nl.rug.aoop.core.trader.Trader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * TestTraderManagerClas tests the TraderManager class.
 */
public class TestTraderManagerClass {

    /**
     * Tests that the TraderManager constructor creates a non-null TraderManager.
     */
    @Test
    void testConstructor() {
        TraderManager traderManager = new TraderManager();
        assertNotNull(traderManager);
    }

    /**
     * Tests that the TraderManager getSize method returns the size of its TraderCollection(s).
     */
    @Test
    void testGetSize() {
        TraderManager traderManager = new TraderManager();
        assertEquals(0, traderManager.getSize());
    }

    /**
     * Tests that the TraderManager loadTrader method loads the Traders from the yaml file.
     */
    @Test
    void testLoadTraders() {
        TraderManager traderManager = new TraderManager();
        traderManager.loadTraders();
        assertNotNull(traderManager.getTraderCollection());
        assertNotEquals(0, traderManager.getSize());
    }

    /**
     * Tests that the TraderManager getTrader method gets the Trader from its TraderCollection(s).
     */
    @Test
    void testGetTrader() {
        TraderManager traderManager = new TraderManager();
        Trader trader = new Trader("Trader", "Trader", 0L);
        traderManager.getTraderCollection().getTraders().put(trader.getId(), trader);
        assertEquals(trader, traderManager.getTrader(trader.getId()));
    }

    /**
     * Tests that the TraderManager getTrader method throws an exception if a null argument is passed.
     */
    @Test
    void testNullArgGetTrader() {
        TraderManager traderManager = new TraderManager();
        assertThrows(NullPointerException.class, () -> traderManager.getTrader(null));
    }
}
