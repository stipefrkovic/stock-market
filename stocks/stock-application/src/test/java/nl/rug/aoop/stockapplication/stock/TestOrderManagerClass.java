package nl.rug.aoop.stockapplication.stock;

import nl.rug.aoop.core.order.LimitOrder;
import nl.rug.aoop.core.order.Order;
import nl.rug.aoop.core.order.OrderCollection;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * TestOrderManagerClass tests the OrderManager class.
 */
public class TestOrderManagerClass {

    /**
     * Tests that the OrderManager constructor creates a non-null OrderManager.
     */
    @Test
    void testConstructor() {
        OrderManager orderManager = new OrderManager();
        assertNotNull(orderManager);
    }

    /**
     * Tests that the OrderManager getOrderCollectionMap method returns the correct map of OrderCollections.
     */
    @Test
    void getOrderCollectionMap() {
        OrderManager orderManager = new OrderManager();
        Map<String, OrderCollection> map = Map.of(
                "Asks", orderManager.getAsks(),
                "Bids", orderManager.getBids()
        );
        assertEquals(map, orderManager.getOrderCollectionMap());
    }

    /**
     * Tests that the OrderManager storeOrder method correctly stores a buy Order.
     */
    @Test
    void testStoreBuyOrder() {
        OrderManager orderManager = new OrderManager();
        assertEquals(0, orderManager.getBids().getSize());
        Order buyOrder = new LimitOrder.Builder()
                .setTraderId("Trader1")
                .setStockId("Stock")
                .setOperation("BUY")
                .setPrice(5)
                .setAmount(1)
                .build();
        orderManager.storeOrder(buyOrder);
        assertEquals(1, orderManager.getBids().getSize());
    }

    /**
     * Tests that the OrderManager storeOrder method correctly stores a sell Order.
     */
    @Test
    void testStoreSellOrder() {
        OrderManager orderManager = new OrderManager();
        assertEquals(0, orderManager.getAsks().getSize());
        Order sellOrder = new LimitOrder.Builder()
                .setTraderId("Trader1")
                .setStockId("Stock")
                .setOperation("SELL")
                .setPrice(5)
                .setAmount(1)
                .build();
        orderManager.storeOrder(sellOrder);
        assertEquals(1, orderManager.getAsks().getSize());
    }

    /**
     * Tests that the OrderManager storeOrder method throws an exception when a null argument is passed.
     */
    @Test
    void testNullArgStoreOrder() {
        OrderManager orderManager = new OrderManager();
        assertThrows(NullPointerException.class, () -> orderManager.storeOrder(null));
    }

    /**
     * Tests that the OrderManager removeOrder method correctly removes a buy Order.
     */
    @Test
    void testRemoveBuyOrder() {
        OrderManager orderManager = new OrderManager();
        Order buyOrder = new LimitOrder.Builder()
                .setTraderId("Trader1")
                .setStockId("Stock")
                .setOperation("BUY")
                .setPrice(5)
                .setAmount(1)
                .build();
        orderManager.storeOrder(buyOrder);
        assertEquals(1, orderManager.getBids().getSize());
        orderManager.removeOrder(buyOrder);
        assertEquals(0, orderManager.getBids().getSize());
    }

    /**
     * Tests that the OrderManager removeOrder method correctly removes a sell Order.
     */
    @Test
    void testRemoveSellOrder() {
        OrderManager orderManager = new OrderManager();
        Order sellOrder = new LimitOrder.Builder()
                .setTraderId("Trader1")
                .setStockId("Stock")
                .setOperation("SELL")
                .setPrice(5)
                .setAmount(1)
                .build();
        orderManager.storeOrder(sellOrder);
        assertEquals(1, orderManager.getAsks().getSize());
        orderManager.removeOrder(sellOrder);
        assertEquals(0, orderManager.getAsks().getSize());
    }

    /**
     * Tests that the OrderManager removeOrder method throws an exception when a null argument is passed.
     */
    @Test
    void testNullArgRemoveOrder() {
        OrderManager orderManager = new OrderManager();
        assertThrows(NullPointerException.class, () -> orderManager.removeOrder(null));
    }
}
