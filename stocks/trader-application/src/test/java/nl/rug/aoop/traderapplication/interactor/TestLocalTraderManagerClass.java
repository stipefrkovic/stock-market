package nl.rug.aoop.traderapplication.interactor;

import nl.rug.aoop.core.trader.Trader;
import nl.rug.aoop.core.trader.TraderCollection;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * TestLocalTraderManagerClass tests the LocalTraderManager class.
 */
public class TestLocalTraderManagerClass {
    /**
     * Method tests that a LocalTraderManager is correctly created.
     */
    @Test
    void testConstructor() {
        LocalTraderManager testLocalTraderManager = new LocalTraderManager();
        assertNotNull(testLocalTraderManager);
    }

    /**
     * Method tests that the trader collection gets correctly updated.
     */
    @Test
    void testUpdateTraderCollection() {
        String traderId = "test";
        Trader testTrader = new Trader(traderId, "Niels",
                10000000L);

        LocalTraderManager localTraderManager = new LocalTraderManager();
        localTraderManager.getLocalTraders().put(traderId, new LocalTrader());
        localTraderManager.updateTraders(testTrader.getId(), testTrader);

        assertEquals(testTrader, localTraderManager.getLocalTraders().get(traderId).getTrader());
    }
}
