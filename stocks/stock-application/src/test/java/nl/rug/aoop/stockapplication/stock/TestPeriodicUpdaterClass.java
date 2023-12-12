package nl.rug.aoop.stockapplication.stock;

import com.fasterxml.jackson.core.JsonProcessingException;
import nl.rug.aoop.core.stock.Stock;
import nl.rug.aoop.core.stock.StockCollection;
import nl.rug.aoop.core.trader.Trader;
import nl.rug.aoop.messagequeue.message.Message;
import nl.rug.aoop.messagequeue.message.NetworkMessage;
import nl.rug.aoop.networking.Communicator;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * TestPeriodicUpdaterClass class tests the PeriodicUpdater class.
 */
public class TestPeriodicUpdaterClass {
    /**
     * StockExchange from which the PeriodicUpdater sources updates.
     */
    StockExchange mockStockExchange;
    /**
     * StockManager ('inside StockExchange') from which the PeriodicUpdater sources Stock updates.
     */
    StockManager mockStockManager;
    /**
     * TraderManager ('inside StockExchange') from which the PeriodicUpdater sources Trader updates.
     */
    TraderManager mockTraderManager;
    /**
     * Communicator through which the PeriodicUpdater will send updates.
     */
    Communicator mockTraderCommunicator;
    /**
     * PeriodicUpdater used for testing.
     */
    PeriodicUpdater periodicUpdater;

    /**
     * Method sets up the PeriodicUpdater and the mock arguments and objects.
     */
    void setupPeriodicUpdater() {
        mockStockExchange = mock(StockExchange.class);
        mockStockManager = mock(StockManager.class);
        mockTraderManager = mock(TraderManager.class);
        mockTraderCommunicator = mock(Communicator.class);
        periodicUpdater = new PeriodicUpdater(mockStockExchange);

    }

    /**
     * Tests that the PeriodicUpdater constructor creates a non-null PeriodicUpdater.
     */
    @Test
    void testConstructor() {
        setupPeriodicUpdater();
        assertNotNull(periodicUpdater);
    }

    /**
     * Tests that the PeriodicUpdater constructor throws an exception when a null argument is passsed.
     */
    @Test
    void testNullArgConstructor() {
        assertThrows(NullPointerException.class, () -> periodicUpdater = new PeriodicUpdater(null));
    }

    /**
     * Tests that the PeriodicUpdater run method is called when the PeriodicUpdater thread is started.
     */
    @Test
    void testRunning() {
        setupPeriodicUpdater();

        new Thread(periodicUpdater).start();
        await().atMost(1, TimeUnit.SECONDS).until(periodicUpdater::isRunning);
        assertTrue(periodicUpdater.isRunning());
    }

    /**
     * Tests that the PeriodicUpdater terminate method terminates the PeriodicUpdater.
     */
    @Test
    void testTerminate() {
        setupPeriodicUpdater();

        new Thread(periodicUpdater).start();
        await().atMost(1, TimeUnit.SECONDS).until(periodicUpdater::isRunning);
        assertTrue(periodicUpdater.isRunning());

        periodicUpdater.terminate();
        assertFalse(periodicUpdater::isRunning);
    }

    /**
     * Tests that the PeriodicUpdater registerTrader method correctly registers a Trader and its Communicator.
     */
    @Test
    void testRegisterTrader() {
        setupPeriodicUpdater();

        String traderId = "Trader";

        periodicUpdater.registerTrader(traderId, mockTraderCommunicator);
        assertEquals(mockTraderCommunicator, periodicUpdater.getTraderCommunicators().get(traderId));
    }

    /**
     * Tests that the PeriodicUpdater getStockUpdate method returns the correct update of StockManager (form StockExchange).
     * @throws JsonProcessingException Thrown if Json String conversion fails.
     */
    @Test
    void testGetStockUpdate() throws JsonProcessingException {
        setupPeriodicUpdater();

        String traderId = "Trader";
        Stock stock = new Stock("Stock", "Stock", 1L, 1.0, 1.0);
        StockCollection stockCollection = new StockCollection();
        stockCollection.getStocks().put(stock.getSymbol(), stock);
        when(mockStockManager.getStockCollection()).thenReturn(stockCollection);
        when(mockStockManager.getSize()).thenReturn(stockCollection.getSize());
        when(mockStockExchange.getStockManager()).thenReturn(mockStockManager);

        Message expectedMessage = new Message(traderId, StockCollection.toString(stockCollection));
        NetworkMessage expectedNetworkMessage = new NetworkMessage("updateStocks", expectedMessage.toJson());

        NetworkMessage actualNetworkMessage = NetworkMessage.fromJson(periodicUpdater.getStockUpdate(traderId));
        assertEquals(expectedNetworkMessage.header(), actualNetworkMessage.header());
        Message actualMessage = Message.fromJson(actualNetworkMessage.body());
        assertEquals(expectedMessage.header(), actualMessage.header());
        assertEquals(expectedMessage.body(), actualMessage.body());
    }

    /**
     * Tests that the PeriodicUpdater getTraderUpdate method returns the correct update of TraderManager (form StockExchange).
     * @throws JsonProcessingException Thrown if Json String conversion fails.
     */
    @Test
    void testGetTraderUpdate() throws JsonProcessingException {
        setupPeriodicUpdater();

        Trader trader = new Trader("Trader", "Trader", 1L);
        when(mockTraderManager.getTrader(trader.getId())).thenReturn(trader);
        when(mockStockExchange.getTraderManager()).thenReturn(mockTraderManager);

        Message expectedMessage = new Message(trader.getId(), Trader.toString(trader));
        NetworkMessage expectedNetworkMessage = new NetworkMessage("updateTrader", expectedMessage.toJson());

        NetworkMessage actualNetworkMessage = NetworkMessage.fromJson(periodicUpdater.getTraderUpdate(trader.getId()));
        assertEquals(expectedNetworkMessage.header(), actualNetworkMessage.header());
        Message actualMessage = Message.fromJson(actualNetworkMessage.body());
        assertEquals(expectedMessage.header(), actualMessage.header());
        assertEquals(expectedMessage.body(), actualMessage.body());
    }

    /**
     * Tests that the PeriodicUpdater sendUpdates method sends the correct (Stock and Trader) updates.
     */
    @Test
    void testSendUpdates() {
        setupPeriodicUpdater();

        Trader trader = new Trader("Trader", "Trader", 1L);
        when(mockTraderManager.getTrader(trader.getId())).thenReturn(trader);
        when(mockStockExchange.getTraderManager()).thenReturn(mockTraderManager);

        periodicUpdater.registerTrader(trader.getId(), mockTraderCommunicator);

        Stock stock = new Stock("Stock", "Stock", 1L, 1.0, 1.0);
        StockCollection stockCollection = new StockCollection();
        stockCollection.getStocks().put(stock.getSymbol(), stock);
        when(mockStockManager.getSize()).thenReturn(stockCollection.getSize());
        when(mockStockManager.getStockCollection()).thenReturn(stockCollection);
        when(mockStockExchange.getStockManager()).thenReturn(mockStockManager);

        periodicUpdater.sendUpdates();
        verify(mockTraderCommunicator, times(2)).sendMessage(any(String.class));
    }

    /**
     * Tests that the PeriodicUpdater sendUpdates method doesn't send an update if no Trader is registered.
     */
    @Test
    void testSendUpdatesToNoOne() {
        setupPeriodicUpdater();

        Stock stock = new Stock("Stock", "Stock", 1L, 1.0, 1.0);
        StockCollection stockCollection = new StockCollection();
        stockCollection.getStocks().put(stock.getSymbol(), stock);
        when(mockStockManager.getStockCollection()).thenReturn(stockCollection);
        when(mockStockManager.getSize()).thenReturn(stockCollection.getSize());
        when(mockStockExchange.getStockManager()).thenReturn(mockStockManager);

        Trader trader = new Trader("Trader", "Trader", 1L);
        when(mockTraderManager.getTrader(trader.getId())).thenReturn(trader);
        when(mockStockExchange.getTraderManager()).thenReturn(mockTraderManager);

        assertDoesNotThrow(() -> periodicUpdater.sendUpdates());
    }

    /**
     * Tests that the PeriodicUpdater sendUpdates method doesn't send a Stock update if no update is available i.e. StockManager is empty.
     */
    @Test
    void testEmptyStockUpdates() {
        setupPeriodicUpdater();

        Trader trader = new Trader("Trader", "Trader", 1L);
        when(mockTraderManager.getTrader(trader.getId())).thenReturn(trader);
        when(mockStockExchange.getTraderManager()).thenReturn(mockTraderManager);

        periodicUpdater.registerTrader(trader.getId(), mockTraderCommunicator);

        when(mockStockManager.getSize()).thenReturn(0);
        when(mockStockManager.getStockCollection()).thenReturn(null);
        when(mockStockExchange.getStockManager()).thenReturn(mockStockManager);

        periodicUpdater.sendUpdates();
        verify(mockTraderCommunicator, times(0)).sendMessage(any(String.class));
    }

    /**
     * Tests that the PeriodicUpdater sendUpdates method doesn't send a Trader update if no update is available i.e. TraderManager is empty.
     */
    @Test
    void testEmptyTraderUpdates() {
        setupPeriodicUpdater();

        String traderId = "Trader";
        periodicUpdater.registerTrader(traderId, mockTraderCommunicator);

        Stock stock = new Stock("Stock", "Stock", 1L, 1.0, 1.0);
        StockCollection stockCollection = new StockCollection();
        stockCollection.getStocks().put(stock.getSymbol(), stock);
        when(mockStockManager.getStockCollection()).thenReturn(stockCollection);
        when(mockStockManager.getSize()).thenReturn(stockCollection.getSize());
        when(mockStockExchange.getStockManager()).thenReturn(mockStockManager);

        when(mockTraderManager.getTrader(traderId)).thenReturn(null);
        when(mockStockExchange.getTraderManager()).thenReturn(mockTraderManager);

        periodicUpdater.sendUpdates();
        verify(mockTraderCommunicator, times(0)).sendMessage(any(String.class));
    }

}
