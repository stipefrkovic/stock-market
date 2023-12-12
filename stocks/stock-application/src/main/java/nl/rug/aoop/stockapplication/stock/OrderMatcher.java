package nl.rug.aoop.stockapplication.stock;

import lombok.Getter;
import nl.rug.aoop.core.order.Order;
import nl.rug.aoop.core.order.OrderCollection;

import java.util.Map;

/**
 * Abstract class OrderMatcher that matches a given Order with the given Map of OrderCollections.
 */
@Getter
public abstract class OrderMatcher {
    /**
     * Method matches the given Order with the given Map of OrderCollections.
     * @param order Order to be matched.
     * @param orderCollectionMap Map(String, OrderCollection) to be matched.
     * @return OrderCollection containing Orders that can be matched with the given Order.
     */
    public abstract OrderCollection matchOrder(Order order, Map<String, OrderCollection> orderCollectionMap);
}
