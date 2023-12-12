package nl.rug.aoop.traderapplication.interactor;

import nl.rug.aoop.core.trader.Trader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * TestLocalTraderClass tests the LocalTrader class.
 */
public class TestLocalTraderClass {
    /**
     * Method tests that a local trader is correctly created.
     */
    @Test
    void testConstructor() {
        LocalTrader localTrader = new LocalTrader();
        assertNotNull(localTrader);
    }

    /**
     * Method tests that a local trader can be correctly updated.
     */
    @Test
    void testUpdateTrader() {
        Trader testNewTrader = new Trader("test", "Niels",
                10000000L);
        LocalTrader localTrader = new LocalTrader();
        localTrader.updateTrader(testNewTrader);
        assertEquals(testNewTrader, localTrader.getTrader());
    }
}
