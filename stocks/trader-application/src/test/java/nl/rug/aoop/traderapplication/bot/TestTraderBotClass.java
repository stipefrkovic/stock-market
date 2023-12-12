package nl.rug.aoop.traderapplication.bot;

import nl.rug.aoop.traderapplication.interactor.StockInteractor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.*;

/**
 * TestTraderBotClass tests the TraderBot class.
 */
public class TestTraderBotClass {
    /**
     * Trader bot to be tested.
     */
    private TraderBot traderBot;
    /**
     * Int value of timeout in seconds.
     */
    private final int TIMEOUT = 1;

    /**
     * Method starts the bot before each test.
     */
    @BeforeEach
    void startBot() {
        traderBot = new TraderBot();
        new Thread(traderBot).start();
        await().atMost(TIMEOUT, TimeUnit.SECONDS).until(traderBot::isRunning);
    }

    /**
     * Tests that the bot has been successfully created.
     */
    @Test
    void testConstructor() {
        assertNotNull(traderBot);
    }

    /**
     * Tests that the bot runs.
     */
    @Test
    void testRun() {
        assertTrue(traderBot.isRunning());
    }

    /**
     * Tests that the bot terminates.
     */
    @Test
    void testTerminate() {
        traderBot.terminate();
        assertFalse(traderBot.isRunning());
    }
}
