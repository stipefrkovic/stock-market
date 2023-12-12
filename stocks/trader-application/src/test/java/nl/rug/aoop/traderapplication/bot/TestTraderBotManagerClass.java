package nl.rug.aoop.traderapplication.bot;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * TestTraderBotManagerClass tests the TraderBotManager class.
 */
public class TestTraderBotManagerClass {
    /**
     * TraderBotManager to be tested.
     */
    private TraderBotManager traderBotManager;

    /**
     * Tests that the trader bot manager is successfully created.
     */
    @Test
    void testConstructor() {
        traderBotManager = new TraderBotManager();
        assertNotNull(traderBotManager);
    }

    /**
     * Tests that a bot can be added to the bot manager.
     */
    @Test
    void testAdd() {
        traderBotManager = new TraderBotManager();
        int oldSize = traderBotManager.getBots().size();
        TraderBot traderBot = new TraderBot();
        traderBotManager.addBot(traderBot);
        assertEquals(oldSize+1, traderBotManager.getBots().size());
    }

    /**
     * Tests that a bot can be removed from the bot manager.
     */
    @Test
    void testRemove() {
        traderBotManager = new TraderBotManager();
        TraderBot traderBot = new TraderBot();
        traderBotManager.addBot(traderBot);
        int oldSize = traderBotManager.getBots().size();
        traderBotManager.removeBot(traderBot);
        assertEquals(oldSize-1, traderBotManager.getBots().size());
    }
}
