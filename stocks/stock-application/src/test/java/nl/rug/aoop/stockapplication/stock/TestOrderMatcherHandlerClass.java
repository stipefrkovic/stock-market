package nl.rug.aoop.stockapplication.stock;

import nl.rug.aoop.core.order.Order;
import nl.rug.aoop.core.order.OrderCollection;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * TestOrderMatcherHandlerClass class tests the OrderMatcherHandler class.
 */
public class TestOrderMatcherHandlerClass {

    /**
     * Tests that the OrderMatcherHandler constructor creates a non-null OrderMatcherHandler.
     */
    @Test
    void testConstructor() {
        OrderMatcherHandler orderMatcherHandler = new OrderMatcherHandler();
        assertNotNull(orderMatcherHandler);
    }

    /**
     * Tests that the OrderMatcherHandler registerOrderMatcher method registers an OrderMatcher.
     */
    @Test
    void testRegisterOrderMatcher() {
        OrderMatcherHandler orderMatcherHandler = new OrderMatcherHandler();
        OrderMatcher mockOrderMatcher = mock(OrderMatcher.class);
        String mockOrderMatcherType = "LimitOrder";
        orderMatcherHandler.registerOrderMatcher(mockOrderMatcherType, mockOrderMatcher);
        assertEquals(mockOrderMatcher, orderMatcherHandler.getOrderMatcherMap().get(mockOrderMatcherType));
    }

    /**
     * Tests that the OrderMatcherHandler registerOrderMatcher method throws an exception when a null argument is passed.
     */
    @Test
    void testNullArgRegisterOrderMatcher() {
        OrderMatcherHandler orderMatcherHandler = new OrderMatcherHandler();
        assertThrows(NullPointerException.class, () -> orderMatcherHandler.registerOrderMatcher("Order", null));
        assertThrows(NullPointerException.class, () -> orderMatcherHandler.registerOrderMatcher(null, mock(OrderMatcher.class)));
    }

    /**
     * Tests that the OrderMatcherHandler matchOrder method calls the matchOrder method of the correct MatchHandler.
     */
    @Test
    void testMatchOrder() {
        OrderMatcher mockOrderMatcher = mock(OrderMatcher.class);
        String mockOrderMatcherType = "LimitOrder";

        OrderMatcherHandler orderMatcherHandler = new OrderMatcherHandler();
        orderMatcherHandler.registerOrderMatcher(mockOrderMatcherType, mockOrderMatcher);

        String mockOrderType = "LimitOrder";
        Order mockOrder = mock(Order.class);
        when(mockOrder.getType()).thenReturn(mockOrderType);

        Map<String, OrderCollection> orderCollectionMap = Map.of("Asks", new OrderCollection());
        orderMatcherHandler.matchOrder(mockOrder, orderCollectionMap);
        verify(mockOrderMatcher).matchOrder(mockOrder, orderCollectionMap);
    }

    /**
     * Tests that the OrderMatcherHandler matchOrder throws an exception when a null argument is passed.
     */
    @Test
    void testNullArgMatchOrder() {
        OrderMatcherHandler orderMatcherHandler = new OrderMatcherHandler();
        Map<String, OrderCollection> orderCollectionMap = Map.of("Asks", new OrderCollection());
        assertThrows(NullPointerException.class, () -> orderMatcherHandler.matchOrder(null, orderCollectionMap));
        assertThrows(NullPointerException.class, () -> orderMatcherHandler.matchOrder(mock(Order.class), null));
    }
}
