package nl.rug.aoop.stockapplication.stock;

import lombok.Getter;
import nl.rug.aoop.core.order.Order;
import nl.rug.aoop.core.order.OrderCollection;

import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.requireNonNull;

/**
 * Class OrderMatcherHandler that holds OrderMatchers based on Order Types and matches Orders correspondingly.
 */
@Getter
public class OrderMatcherHandler {
    /**
     * Map(String, OrderMatcher) holds OrderMatchers based on their Order type.
     */
    private final Map<String, OrderMatcher> orderMatcherMap;

    /**
     * Constructor for OrderMatcherHandler that initializes the Map of OrderMatchers.
     */
    public OrderMatcherHandler() {
        orderMatcherMap = new HashMap<>();
    }

    /**
     * Method registers an OrderMatcher with its Order type String.
     * @param orderMatcherType String of the OrderMatcher Order type.
     * @param orderMatcher OrderMatcher to be registered.
     */
    public void registerOrderMatcher(String orderMatcherType, OrderMatcher orderMatcher) {
        requireNonNull(orderMatcherType);
        requireNonNull(orderMatcher);
        orderMatcherMap.put(orderMatcherType, orderMatcher);
    }

    /**
     * Method matches the given Order with the given Map of OrderCollections by calling the appropriate OrderMatcher.
     * @param order Order to be matched.
     * @param orderCollectionMap Map(String, OrderCollection) to be matched.
     * @return OrderCollection containing Orders that can be matched with the given Order.
     */
    public OrderCollection matchOrder(Order order, Map<String, OrderCollection> orderCollectionMap) {
        requireNonNull(order);
        requireNonNull(orderCollectionMap);
        return orderMatcherMap.get(order.getType()).matchOrder(order, orderCollectionMap);
    }
}
