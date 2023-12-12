package nl.rug.aoop.core.order;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;

/**
 * OrderCollection represents the collection of orders.
 */
@Getter
public class OrderCollection {
    /**
     * List of orders.
     */
    private final ArrayList<Order> orders = new ArrayList<>();

    /**
     * Static method filters out all orders with the same stock.
     * @param orderCollection Order collection.
     * @param stockId Id of the stock based on which filtering is done.
     * @return New order collection with filtered orders.
     */
    public static OrderCollection filterStock(OrderCollection orderCollection, String stockId) {
        if (orderCollection == null || stockId == null) {
            return null;
        }
        OrderCollection filteredOrderCollection = new OrderCollection();
        for (Order order : orderCollection.getOrders()) {
            if (Objects.equals(order.getStockId(), stockId)) {
                filteredOrderCollection.addOrder(order);
            }
        }
        return filteredOrderCollection;
    }

    /**
     * Static method filters out all orders with price lower than a certain amount.
     * @param orderCollection Order collection.
     * @param price Price cutoff.
     * @return New collection with filtered orders.
     */
    public static OrderCollection filterPriceLower(OrderCollection orderCollection, Integer price) {
        if (orderCollection == null || price == null) {
            return null;
        }
        OrderCollection filteredOrderCollection = new OrderCollection();
        for (Order order : orderCollection.getOrders()) {
            if (order.getPrice() <= price) {
                filteredOrderCollection.addOrder(order);
            }
        }
        return filteredOrderCollection;
    }

    /**
     * Static method filters out all orders with price higher than a certain amount.
     * @param orderCollection Order collection.
     * @param price Price cutoff.
     * @return New collection with filtered orders.
     */
    public static OrderCollection filterPriceHigher(OrderCollection orderCollection, Integer price) {
        if (orderCollection == null || price == null) {
            return null;
        }
        OrderCollection filteredOrderCollection = new OrderCollection();
        for (Order order : orderCollection.getOrders()) {
            if (order.getPrice() >= price) {
                filteredOrderCollection.addOrder(order);
            }
        }
        return filteredOrderCollection;
    }

    /**
     * Static method sorts the order collection from low to high.
     * @param orderCollection Order collection.
     * @return Sorted order collection.
     */
    public static OrderCollection sortPriceLowHigh(OrderCollection orderCollection) {
        if (orderCollection == null) {
            return null;
        }
        orderCollection.getOrders().sort(Comparator.comparing(Order::getPrice));
        return orderCollection;
    }

    /**
     * Static method sorts the order collection from high to low.
     * @param orderCollection Order collection.
     * @return Sorted order collection.
     */
    public static OrderCollection sortPriceHighLow(OrderCollection orderCollection) {
        if (orderCollection == null) {
            return null;
        }
        sortPriceLowHigh(orderCollection);
        Collections.reverse(orderCollection.getOrders());
        return orderCollection;
    }

    /**
     * Method returns the size of orders.
     * @return Size.
     */
    public int getSize() {
        return orders.size();
    }

    /**
     * Adds order to the collection.
     * @param order Added order.
     */
    public void addOrder(Order order) {
        orders.add(order);
    }

    /**
     * Removes order from the collection.
     * @param order Removed order.
     */
    public void removeOrder(Order order) {
        orders.remove(order);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OrderCollection that = (OrderCollection) o;
        return Objects.equals(orders, that.orders);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orders);
    }
}
