package nl.rug.aoop.stockapplication.stock;

import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.core.order.LimitOrder;
import nl.rug.aoop.core.order.Order;
import nl.rug.aoop.core.order.OrderCollection;

import java.util.Map;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

/**
 * LimitOrderMatcher that extends OrderMatcher. It matches a given LimitOrder with the given Map of OrderCollections.
 */
@Slf4j
public class LimitOrderMatcher extends OrderMatcher {
    /**
     * Method matches the given LimitOrder with the given Map of OrderCollections.
     * @param order LimitOrder to be matched.
     * @param orderCollectionMap Map(String, OrderCollection) to be matched.
     * @return OrderCollection containing Orders that can be matched with the given LimitOrder.
     */
    @Override
    public OrderCollection matchOrder(Order order, Map<String, OrderCollection> orderCollectionMap) {
        requireNonNull(order);
        requireNonNull(orderCollectionMap);
        LimitOrder limitOrder = (LimitOrder) order;
        if (Objects.equals(limitOrder.getOperation(), "BUY")) {
            return matchBuyOrder(limitOrder, orderCollectionMap);
        } else if (Objects.equals(limitOrder.getOperation(), "SELL")) {
            return matchSellOrder(limitOrder, orderCollectionMap);
        } else {
            throw new IllegalArgumentException("Order operation not recognised.");
        }
    }

    /**
     * Method matches a LimitOrder of Buy operation with the given OrderCollection.
     * @param buyOrder LimitOrder of Buy Operation that should be matched.
     * @param orderCollectionMap OrderCollection that should be matched.
     * @return OrderCollection containing Orders that can be matched with the given LimitOrder.
     */
    private OrderCollection matchBuyOrder(LimitOrder buyOrder, Map<String, OrderCollection> orderCollectionMap) {
        OrderCollection asks = orderCollectionMap.get("Asks");
        OrderCollection stockAsks = OrderCollection.filterStock(asks, buyOrder.getStockId());
        OrderCollection limitAsks = OrderCollection.filterPriceLower(stockAsks, buyOrder.getLimit());
        OrderCollection sortAsks = OrderCollection.sortPriceLowHigh(limitAsks);

        if (sortAsks.getSize() != 0) {
            return sortAsks;
        } else {
            return null;
        }
    }

    /**
     * Method matches a LimitOrder of Sell operation with the given OrderCollection.
     * @param sellOrder LimitOrder of Sell Operation that should be matched.
     * @param orderCollectionMap OrderCollection that should be matched.
     * @return OrderCollection containing Orders that can be matched with the given LimitOrder.
     */
    private OrderCollection matchSellOrder(LimitOrder sellOrder, Map<String, OrderCollection> orderCollectionMap) {
        OrderCollection bids = orderCollectionMap.get("Bids");
        OrderCollection stockBids = OrderCollection.filterStock(bids, sellOrder.getStockId());
        OrderCollection limitBids = OrderCollection.filterPriceHigher(stockBids, sellOrder.getLimit());
        OrderCollection sortBids = OrderCollection.sortPriceHighLow(limitBids);

        if (sortBids.getSize() != 0) {
            return sortBids;
        } else {
            return null;
        }
    }

}
