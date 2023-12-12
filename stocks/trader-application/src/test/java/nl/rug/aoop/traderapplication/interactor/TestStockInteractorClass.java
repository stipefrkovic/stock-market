package nl.rug.aoop.traderapplication.interactor;

import com.fasterxml.jackson.core.JsonProcessingException;
import nl.rug.aoop.core.order.LimitOrder;
import nl.rug.aoop.core.order.Order;
import nl.rug.aoop.core.order.OrderSerializer;
import nl.rug.aoop.core.stock.Stock;
import nl.rug.aoop.core.stock.StockCollection;
import nl.rug.aoop.core.trader.Trader;
import nl.rug.aoop.messagequeue.message.Message;
import nl.rug.aoop.networking.client.Client;
import nl.rug.aoop.traderapplication.network.NetworkProducer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * TestStockInteractorClass tests the StockInteractor class.
 */
public class TestStockInteractorClass {
    /**
     * StockInteractor to be tested.
     */
    private StockInteractor stockInteractor;
    /**
     * Client used in the StockInteractor
     */
    private Client client;
    /**
     * NetworkProducer used in the StockInteractor.
     */
    private NetworkProducer networkProducer;

    /**
     * Method creates the StockInteractor before every test.
     */
    @BeforeEach
    void createStockInteractor() {
        client = mock(Client.class);
        networkProducer = mock(NetworkProducer.class);
        stockInteractor = new StockInteractor(client, networkProducer);
    }

    /**
     * Method tests that an order message is correctly sent.
     */
    @Test
    void testSendMessage() {
        Order testOrder = new LimitOrder.Builder()
                .setTraderId("test")
                .setStockId("test")
                .setOperation("BUY")
                .setPrice(10)
                .setAmount(10)
                .build();
        stockInteractor.sendOrder(testOrder);
        verify(networkProducer).put(any(Message.class));
    }

    /**
     * Method tests that a register trader message is correctly sent.
     */
    @Test
    void testRegisterTrader() {
        stockInteractor.registerTrader("test");
        verify(client).sendMessage(any(String.class));
    }

    /**
     * Method tests that the StockInteractor correctly returns a Map of a stock collection.
     */
    @Test
    void testGetStocks() {
        LocalStockManager localStockManager = new LocalStockManager();
        StockCollection stockCollection = new StockCollection();
        Stock appleStock = new Stock("AAPL", "Apple", 0L, 10.0, 10.0);
        stockCollection.updateStock(appleStock);
        localStockManager.updateStocks(stockCollection);
        stockInteractor.setLocalStockManager(localStockManager);
        assertNotNull(stockInteractor.getStocks());
    }

    /**
     * Method tests that the StockInteractor correctly returns a map of trader information.
     */
    @Test
    void testGetTrader() {
        LocalTrader localTrader = new LocalTrader();
        Trader nielsTrader = new Trader("NIELS", "Niels",
                1000000L);
        localTrader.updateTrader(nielsTrader);
        stockInteractor.setLocalTrader(localTrader);
        assertNotNull(stockInteractor.getTrader());
    }
}
