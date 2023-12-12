package nl.rug.aoop.stockapplication.stock;

import nl.rug.aoop.core.order.LimitOrder;
import nl.rug.aoop.core.order.Order;
import nl.rug.aoop.core.order.OrderCollection;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

/**
 * TestLimitOrderMatcher class tests the LimitOrderMatcher class.
 */
public class TestLimitOrderMatcher {

    /**
     * Tests that the LimitOrderMatcher constructor creates a non-null LimitOrderMatcher.
     */
    @Test
    void testConstructor() {
        LimitOrderMatcher limitOrderMatcher = new LimitOrderMatcher();
        assertNotNull(limitOrderMatcher);
    }

    /**
     * Tests that the LimitOrderMatcher matchOrder method matches a matchable buy Order.
     */
    @Test
    void testMatchMatchableBuyOrder() {
        Order incomingOrder = new LimitOrder.Builder()
                .setTraderId("Trader1")
                .setStockId("Stock")
                .setOperation("BUY")
                .setPrice(5)
                .setAmount(1)
                .build();

        Order goodOrder = new LimitOrder.Builder()
                .setTraderId("Trader2")
                .setStockId("Stock")
                .setOperation("SELL")
                .setPrice(4)
                .setAmount(1)
                .build();
        Order bestOrder = new LimitOrder.Builder()
                .setTraderId("Trader3")
                .setStockId("Stock")
                .setOperation("SELL")
                .setPrice(3)
                .setAmount(1)
                .build();
        Order wrongStockOrder = new LimitOrder.Builder()
                .setTraderId("Trader4")
                .setStockId("NotStock")
                .setOperation("SELL")
                .setPrice(2)
                .setAmount(1)
                .build();
        Order tooExpensiveOrder = new LimitOrder.Builder()
                .setTraderId("Trader5")
                .setStockId("Stock")
                .setOperation("SELL")
                .setPrice(6)
                .setAmount(1)
                .build();

        OrderCollection originalOrderCollection = new OrderCollection();
        originalOrderCollection.addOrder(goodOrder);
        originalOrderCollection.addOrder(bestOrder);
        originalOrderCollection.addOrder(wrongStockOrder);
        originalOrderCollection.addOrder(tooExpensiveOrder);
        Map<String, OrderCollection> orderCollectionMap = Map.of(
                "Asks", originalOrderCollection
        );


        OrderCollection matchedOrderCollection = new OrderCollection();
        matchedOrderCollection.addOrder(bestOrder);
        matchedOrderCollection.addOrder(goodOrder);

        LimitOrderMatcher limitOrderMatcher = new LimitOrderMatcher();
        assertEquals(matchedOrderCollection, limitOrderMatcher.matchOrder(incomingOrder, orderCollectionMap));
    }

    /**
     * Tests that the LimitOrderMatcher matchOrder method doesn't match a non-matchable buy Order.
     */
    @Test
    void testMatchNonMatchableBuyOrder() {
        Order incomingOrder = new LimitOrder.Builder()
                .setTraderId("Trader1")
                .setStockId("Stock")
                .setOperation("BUY")
                .setPrice(5)
                .setAmount(1)
                .build();

        Order tooExpensiveOrder = new LimitOrder.Builder()
                .setTraderId("Trader5")
                .setStockId("Stock")
                .setOperation("SELL")
                .setPrice(6)
                .setAmount(1)
                .build();


        OrderCollection originalOrderCollection = new OrderCollection();
        originalOrderCollection.addOrder(tooExpensiveOrder);
        Map<String, OrderCollection> orderCollectionMap = Map.of(
                "Asks", originalOrderCollection
        );


        LimitOrderMatcher limitOrderMatcher = new LimitOrderMatcher();
        assertNull(limitOrderMatcher.matchOrder(incomingOrder, orderCollectionMap));

    }

    /**
     * Tests that the LimitOrderMatcher matchOrder method matches a matchable sell Order.
     */
    @Test
    void testMatchMatchableSellOrder() {
        Order incomingOrder = new LimitOrder.Builder()
                .setTraderId("Trader1")
                .setStockId("Stock")
                .setOperation("SELL")
                .setPrice(5)
                .setAmount(1)
                .build();

        Order goodOrder = new LimitOrder.Builder()
                .setTraderId("Trader2")
                .setStockId("Stock")
                .setOperation("BUY")
                .setPrice(6)
                .setAmount(1)
                .build();
        Order bestOrder = new LimitOrder.Builder()
                .setTraderId("Trader3")
                .setStockId("Stock")
                .setOperation("BUY")
                .setPrice(7)
                .setAmount(1)
                .build();
        Order wrongStockOrder = new LimitOrder.Builder()
                .setTraderId("Trader4")
                .setStockId("NotStock")
                .setOperation("BUY")
                .setPrice(2)
                .setAmount(1)
                .build();
        Order tooCheapOrder = new LimitOrder.Builder()
                .setTraderId("Trader5")
                .setStockId("Stock")
                .setOperation("BUY")
                .setPrice(3)
                .setAmount(1)
                .build();

        OrderCollection originalOrderCollection = new OrderCollection();
        originalOrderCollection.addOrder(goodOrder);
        originalOrderCollection.addOrder(bestOrder);
        originalOrderCollection.addOrder(wrongStockOrder);
        originalOrderCollection.addOrder(tooCheapOrder);
        Map<String, OrderCollection> orderCollectionMap = Map.of(
                "Bids", originalOrderCollection
        );


        OrderCollection matchedOrderCollection = new OrderCollection();
        matchedOrderCollection.addOrder(bestOrder);
        matchedOrderCollection.addOrder(goodOrder);

        LimitOrderMatcher limitOrderMatcher = new LimitOrderMatcher();
        assertEquals(matchedOrderCollection, limitOrderMatcher.matchOrder(incomingOrder, orderCollectionMap));
    }

    /**
     * Tests that the LimitOrderMatcher matchOrder method doesn't match a non-matchable sell Order.
     */
    @Test
    void testMatchNonMatchableSellOrder() {
        Order incomingOrder = new LimitOrder.Builder()
                .setTraderId("Trader1")
                .setStockId("Stock")
                .setOperation("SELL")
                .setPrice(5)
                .setAmount(1)
                .build();

        Order tooCheapOrder = new LimitOrder.Builder()
                .setTraderId("Trader5")
                .setStockId("Stock")
                .setOperation("BUY")
                .setPrice(3)
                .setAmount(1)
                .build();

        OrderCollection originalOrderCollection = new OrderCollection();
        originalOrderCollection.addOrder(tooCheapOrder);
        Map<String, OrderCollection> orderCollectionMap = Map.of(
                "Bids", originalOrderCollection
        );

        LimitOrderMatcher limitOrderMatcher = new LimitOrderMatcher();
        assertNull(limitOrderMatcher.matchOrder(incomingOrder, orderCollectionMap));
    }

    /**
     * Tests that the LimitOrderMatcher matchOrder method throws an exception when a null argument is passed.
     */
    @Test
    void testNullArgMatchOrder() {
        LimitOrderMatcher limitOrderMatcher = new LimitOrderMatcher();
        assertThrows(NullPointerException.class, () -> limitOrderMatcher.matchOrder(mock(Order.class), null));
        assertThrows(NullPointerException.class, () -> limitOrderMatcher.matchOrder(null, Map.of("Asks", new OrderCollection())));
    }

}
