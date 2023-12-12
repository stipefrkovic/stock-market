package nl.rug.aoop.stockapplication.comand;

import nl.rug.aoop.messagequeue.queue.MessageQueue;
import nl.rug.aoop.stockapplication.command.StockFactory;
import nl.rug.aoop.stockapplication.stock.PeriodicUpdater;
import nl.rug.aoop.stockapplication.stock.StockExchange;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

/**
 * TestStockFactoryClass class tests the StockFactory class.
 */
public class TestStockFactoryClass {
    /**
     * StockFactory to be used in tests.
     */
    private StockFactory stockFactory;
    /**
     * StockExchange needed as a constructor parameter for StockFactory.
     */
    private StockExchange stockExchange;
    /**
     * MessageQueue needed as a constructor parameter for StockFactory.
     */
    private MessageQueue messageQueue;
    /**
     * PeriodicUpdater needed as a constructor parameter for StockFactory.
     */
    private PeriodicUpdater periodicUpdater;

    /**
     * Method initializes the mock constructor arguments and creates a StockFactory.
     */
    @BeforeEach
    void createStockFactory() {
        stockExchange = mock(StockExchange.class);
        messageQueue = mock(MessageQueue.class);
        periodicUpdater = mock(PeriodicUpdater.class);
        stockFactory = new StockFactory(stockExchange, messageQueue, periodicUpdater);
    }

    /**
     * Tests that the StockFactory constructor creates a non-null StockFactory.
     */
    @Test
    void testConstructor() {
        assertNotNull(stockFactory);
    }

    /**
     * Tests that StockFactory create method creates the corresponding CommandHandler when correctly called.
     */
    @Test
    void testLegalCreate() {
        assertNotNull(stockFactory.create(String.valueOf(StockFactory.Types.STOCK)));
        assertNotNull(stockFactory.create(String.valueOf(StockFactory.Types.NETWORK)));
    }

    /**
     * Tests that StockFactory create method throws an exception when incorrectly called.
     */
    @Test
    void testIllegalCreate() {
        assertThrows(IllegalArgumentException.class, () -> stockFactory.create("wrong"));
    }
}
