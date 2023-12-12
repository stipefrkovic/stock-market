package nl.rug.aoop.stockapplication.comand;

import nl.rug.aoop.command.CommandHandler;
import nl.rug.aoop.messagequeue.message.Message;
import nl.rug.aoop.messagequeue.process.MQConsumer;
import nl.rug.aoop.stockapplication.command.StockPoller;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * TestStockPollerClass class tests the StockPoller class.
 */
public class TestStockPollerClass {

    /**
     * Tests that the StockPoller constructor creates a non-null StockPoller.
     */
    @Test
    void testConstructor() {
        StockPoller stockPoller = new StockPoller(mock(MQConsumer.class), mock(CommandHandler.class));
        assertNotNull(stockPoller);
    }

    /**
     * Tests that the StockPoller constructor throws an exception when passing a null argument.
     */
    @Test
    void testNullArgConstructor() {
        assertThrows(NullPointerException.class, () -> new StockPoller(null, mock(CommandHandler.class)));
        assertThrows(NullPointerException.class, () -> new StockPoller(mock(MQConsumer.class), null));
    }

    /**
     * Tests that the StockPoller run method is called when the StockPoller thread is started..
     */
    @Test
    void testRun() {
        MQConsumer mockMqConsumer = mock(MQConsumer.class);
        CommandHandler mockCommandHandler = mock(CommandHandler.class);

        StockPoller stockPoller = new StockPoller(mockMqConsumer, mockCommandHandler);
        new Thread(stockPoller).start();
        await().atMost(1, TimeUnit.SECONDS).until(stockPoller::isRunning);
        assertTrue(stockPoller.isRunning());
    }

    /**
     * Tests that the StockPoller execute method correctly calls the executeCommand method of the CommandHandler.
     */
    @Test
    void testExecute() {
        Message message = new Message("header", "body");
        Map<String, Object> map = new HashMap<>();
        map.put("Header", message.header());
        map.put("Body", message.body());
        MQConsumer mockMqConsumer = mock(MQConsumer.class);
        when(mockMqConsumer.poll()).thenReturn(message);
        CommandHandler mockCommandHandler = mock(CommandHandler.class);

        StockPoller stockPoller = new StockPoller(mockMqConsumer, mockCommandHandler);
        new Thread(stockPoller).start();
        await().atMost(1, TimeUnit.SECONDS).until(stockPoller::isRunning);
        verify(mockCommandHandler, atLeastOnce()).executeCommand(message.header(), map);
    }

    /**
     * Tests that the StockPoller terminate method terminates the StockPoller.
     */
    @Test
    void testTerminate() {
        Message message = new Message("header", "body");
        Map<String, Object> map = new HashMap<>();
        map.put("Header", message.header());
        map.put("Body", message.body());
        MQConsumer mockMqConsumer = mock(MQConsumer.class);
        when(mockMqConsumer.poll()).thenReturn(message);
        CommandHandler mockCommandHandler = mock(CommandHandler.class);

        StockPoller stockPoller = new StockPoller(mockMqConsumer, mockCommandHandler);
        new Thread(stockPoller).start();
        stockPoller.terminate();
        assertFalse(stockPoller.isRunning());
    }
}
