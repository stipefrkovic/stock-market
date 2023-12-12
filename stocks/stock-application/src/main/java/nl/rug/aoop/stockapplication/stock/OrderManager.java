package nl.rug.aoop.stockapplication.stock;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.core.order.Order;
import nl.rug.aoop.core.order.OrderCollection;

import java.util.Map;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

/**
 * Class OrderManager that contains OrderCollections: asks, bids and provides methods to operate on them.
 */
@Getter
@Slf4j
public class OrderManager {
    /**
     * OrderCollection asks containing Orders of Sell operation.
     */
    private final OrderCollection asks;
    /**
     * OrderCollection bids containing Orders of Buy operation.
     */
    private final OrderCollection bids;

    /**
     * Constructor for OrderManage that initializes the contained OrderCollections.
     */
    public OrderManager() {
        asks = new OrderCollection();
        bids = new OrderCollection();
    }

    /**
     * Method gets the OrderCollections in a Map.
     * @return Map(String, OrderCollection) of contained OrderCollections.
     */
    public Map<String, OrderCollection> getOrderCollectionMap() {
        return Map.of(
                "Asks", asks,
                "Bids", bids
        );
    }

    /**
     * Method stores an Order in the corresponding contained OrderCollection.
     * @param order Order that should be stored.
     */
    public void storeOrder(Order order) {
        requireNonNull(order);
        if (Objects.equals(order.getOperation(), "BUY")) {
            bids.addOrder(order);
        } else if (Objects.equals(order.getOperation(), "SELL")) {
            asks.addOrder(order);
        }
    }

    /**
     * Method removes an Order from the corresponding contained OrderCollection.
     * @param order Order that should be removed.
     */
    public void removeOrder(Order order) {
        requireNonNull(order);
        if (Objects.equals(order.getOperation(), "BUY")) {
            bids.removeOrder(order);
        } else if (Objects.equals(order.getOperation(), "SELL")) {
            asks.removeOrder(order);
        }
    }

}
