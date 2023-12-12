package nl.rug.aoop.stockapplication.stock;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.core.order.Order;
import nl.rug.aoop.core.order.OrderCollection;
import nl.rug.aoop.core.trader.Transaction;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

/**
 * Class StockExchange that holds the Orders, Stocks, Trader managers. It resolves new Orders by matching them
 * with old Orders (obtained from the OrderMatcherHandler) and resolving them if possible.
 */
@Getter
@Slf4j
public class StockExchange {
    /**
     * StockManager that manages the Stocks.
     */
    private final StockManager stockManager;
    /**
     * TraderManager that manages the Traders.
     */
    private final TraderManager traderManager;
    /**
     * OrderManager that manages the Orders.
     */
    private final OrderManager orderManager;
    /**
     * OrderMatcherHandler that handles the OrderMatchers.
     */
    private final OrderMatcherHandler orderMatcherHandler;

    /**
     * Constructor for StockExchange that initializes the given StockManager, TraderManager,
     * OrderManager, and OrderMatcherHandler.
     * @param stockManager StockManager that manages the Stocks.
     * @param traderManager TraderManager that manages the Traders.
     * @param orderManager OrderManager that manages the Orders.
     * @param orderMatcherHandler OrderMatcherHandler that handles the OrderMatchers.
     */
    public StockExchange(StockManager stockManager, TraderManager traderManager, OrderManager orderManager,
                         OrderMatcherHandler orderMatcherHandler) {
        this.stockManager = requireNonNull(stockManager);
        this.traderManager = requireNonNull(traderManager);
        this.orderManager = requireNonNull(orderManager);
        this.orderMatcherHandler = requireNonNull(orderMatcherHandler);
    }

    /**
     * Method resolves a new Order with matchable orders from the OrderMatcher. If there are no matchable Orders,
     * the Order is stored. Otherwise, each the new and old Order pairs will be attempted to be (partially) resolved.
     * @param newOrder Order that will be (attempted to be) resolved.
     */
    public void resolveOrder(Order newOrder) {
        requireNonNull(newOrder);
        OrderCollection matchingOrderCollection = orderMatcherHandler.matchOrder(newOrder,
                orderManager.getOrderCollectionMap());

        if(matchingOrderCollection == null) {
            orderManager.storeOrder(newOrder);
            return;
        }
        for (Order oldOrder : matchingOrderCollection.getOrders()) {
            if (attemptResolvePair(newOrder, oldOrder)) {
                return;
            }
        }
        orderManager.storeOrder(newOrder);
    }

    /**
     * Method attempts to resolve a pair of Orders and return true if the pair was resolved.
     * If either of Traders of the two Orders does not have the resources for their Order,
     * the resolving fails. Otherwise, the Order pair is (partially) resolved.
     * @param newOrder Order that will be attempted to be resolved with the old Order.
     * @param oldOrder Order with which the new Order will be attempted to be resolved.
     * @return true if the pair was resolved, false otherwise.
     */
    private boolean attemptResolvePair(Order newOrder, Order oldOrder) {
        if(!validTraderResources(newOrder, oldOrder) || !(validTraderResources(oldOrder, oldOrder))) {
            return false;
        } else {
            resolvePair(newOrder, oldOrder);
            return true;
        }
    }

    /**
     * Method checks that the Trader of the first Order (either the new or old Order) has the resources
     * (funds for a buy Order, stock amount for a sell Order) to resolve it. The second Order is always the old Order
     * (just to determine the price).
     * @param firstOrder Order for which the Trader resources are checked (new or old Order).
     * @param secondOrder Order that determines the price of the Transaction (only old Order).
     * @return true if the Trader has the resources, false otherwise.
     */
    private boolean validTraderResources(Order firstOrder, Order secondOrder) {
        if (Objects.equals(firstOrder.getOperation(), "BUY")) {
            return traderManager.getTrader(firstOrder.getTraderId()).getFunds() >= ((long) firstOrder.getAmount()
                    * secondOrder.getPrice());
        } else if (Objects.equals(firstOrder.getOperation(), "SELL")) {
            return traderManager.getTrader(firstOrder.getTraderId())
                    .hasStockAmount(firstOrder.getStockId(), firstOrder.getAmount());
        } else {
            return false;
        }
    }

    /**
     * Method (partially) resolves a pair of Orders. The non-fully resolved Order(s) are stored with updated amounts,
     * while the fully resolved Orders are removed. The Transaction is made and the Stock price, Trader stock amounts,
     * and Trader Transaction history are updated.
     * @param newOrder Order that will be (partially) resolved with the old Order.
     * @param oldOrder Order with which the new Order will be (partially) resolved.
     */
    private void resolvePair(Order newOrder, Order oldOrder) {
        Transaction transaction;
        if (newOrder.getAmount() > oldOrder.getAmount()) {
            transaction = new Transaction(newOrder.getStockId(), oldOrder.getAmount(), oldOrder.getPrice());
            newOrder.setAmount(newOrder.getAmount() - oldOrder.getAmount());
            orderManager.storeOrder(newOrder);
            orderManager.removeOrder(oldOrder);
        } else if (newOrder.getAmount() < oldOrder.getAmount()) {
            transaction = new Transaction(newOrder.getStockId(), newOrder.getAmount(), oldOrder.getPrice());
            oldOrder.setAmount(oldOrder.getAmount() - newOrder.getAmount());
        } else {
            transaction = new Transaction(newOrder.getStockId(), newOrder.getAmount(), oldOrder.getPrice());
            orderManager.removeOrder(oldOrder);
        }
        traderManager.getTrader(newOrder.getTraderId()).addTransaction(transaction);
        updateTraderOwnedStocks(newOrder, transaction);
        updateTraderOwnedStocks(oldOrder, transaction);
        stockManager.getStock(newOrder.getStockId()).updatePrice(Double.valueOf(transaction.stockPrice()));
        log.info("Resolved order for stock: " + newOrder.getStockId());
    }

    /**
     * Method updates the Transaction history of a Trader based on the resolveOrder and the Transaction.
     * @param resolvedOrder Order that was resolved and whose Trader needs to be updated.
     * @param transaction Transaction holding the resolved price and amount.
     */
    private void updateTraderOwnedStocks(Order resolvedOrder, Transaction transaction) {
        Long currentTraderStockAmount = traderManager.getTrader(resolvedOrder.getTraderId())
                .getStockAmount(resolvedOrder.getStockId());
        long newTraderStockAmount;
        if (Objects.equals(resolvedOrder.getOperation(), "BUY")) {
            newTraderStockAmount = currentTraderStockAmount + transaction.stockAmount();
        } else if (Objects.equals(resolvedOrder.getOperation(), "SELL")) {
            newTraderStockAmount = currentTraderStockAmount - transaction.stockAmount();
        } else {
            return;
        }
        traderManager.getTrader(resolvedOrder.getTraderId())
                .setStockAmount(resolvedOrder.getStockId(), newTraderStockAmount);
    }

}
