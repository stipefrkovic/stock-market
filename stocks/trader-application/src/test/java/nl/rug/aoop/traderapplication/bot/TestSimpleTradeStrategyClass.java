package nl.rug.aoop.traderapplication.bot;

import nl.rug.aoop.core.order.LimitOrder;
import nl.rug.aoop.core.order.Order;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * TestSimpleTradeStrategy tests the SimpleTradeStrategy class.
 */
public class TestSimpleTradeStrategyClass {
    /**
     * Simple trade strategy.
     */
    private SimpleTradeStrategy simpleTradeStrategy;

    /**
     * Tests that the strategy is successfully created.
     */
    @Test
    void testConstructor() {
        simpleTradeStrategy = new SimpleTradeStrategy();
        assertNotNull(simpleTradeStrategy);
    }

    /**
     * Tests that an order is successfully created.
     */
    @Test
    void testCreateOrder() {
        simpleTradeStrategy = new SimpleTradeStrategy();
        Map<String, Object> traderMap = new HashMap<>();
        traderMap.put("id", "testId");
        traderMap.put("name", "Joe Mama");
        traderMap.put("funds", 1000L);
        traderMap.put("ownedShares", Map.of("AAPL", 10L));

        ArrayList<Map<String, Object>> stockArray = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("symbol", "AAPL");
        map.put("name", "Apple");
        map.put("sharesOutstanding", 0L);
        map.put("price", 10.0);
        stockArray.add(map);

        Order order = simpleTradeStrategy.createOrder(traderMap, stockArray);
        assertTrue(order instanceof LimitOrder);
    }
}
