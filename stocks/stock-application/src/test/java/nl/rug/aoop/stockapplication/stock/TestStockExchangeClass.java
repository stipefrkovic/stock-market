package nl.rug.aoop.stockapplication.stock;

import nl.rug.aoop.core.order.LimitOrder;
import nl.rug.aoop.core.order.Order;
import nl.rug.aoop.core.order.OrderCollection;
import nl.rug.aoop.core.stock.Stock;
import nl.rug.aoop.core.trader.Trader;
import nl.rug.aoop.core.trader.Transaction;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Objects;

import static java.lang.Math.min;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * TestStockExchangeClass tests the StockExchange class.
 */
public class TestStockExchangeClass {
    /**
     * StockManager contained in the StockExchange.
     */
    private StockManager mockStockManager;
    /**
     * TraderManager contained in the StockExchange.
     */
    private TraderManager mockTraderManager;
    /**
     * OrderManager contained in the StockExchange.
     */
    private OrderManager mockOrderManager;
    /**
     * OrderMatcherHandler contained in the StockExchange.
     */
    private OrderMatcherHandler mockOrderMatcherHandler;
    /**
     * StockExchange used for testing.
     */
    private StockExchange stockExchange;
    /**
     * Order that was just placed.
     */
    private Order newOrder;
    /**
     * Order that was stored.
     */
    private Order oldOrder;
    /**
     * OrderCollection that will hold the old Order.
     */
    private OrderCollection matchingOrderCollection;
    /**
     * Transaction that could be executed.
     */
    private Transaction transaction;
    /**
     * Trader of the newOrder.
     */
    private Trader trader1;
    /**
     * Trader of the oldOrder.
     */
    private Trader trader2;
    /**
     * Stock of the new and old Orders.
     */
    private Stock stock;

    /**
     * Method sets up the StockExchange.
     */
    private void setupStockExchange() {
        mockStockManager = mock(StockManager.class);
        mockTraderManager = mock(TraderManager.class);
        mockOrderManager = mock(OrderManager.class);
        mockOrderMatcherHandler = mock(OrderMatcherHandler.class);
        stockExchange = new StockExchange(mockStockManager, mockTraderManager,
                mockOrderManager, mockOrderMatcherHandler);
    }

    /**
     * Method sets up the new and old Order and creates the Transaction.
     * @param op1 Operation of the newOrder.
     * @param price1 Price of the newOrder.
     * @param amount1 Amount of the newOrder.
     * @param op2 Operation of the oldOrder.
     * @param price2 Price of the oldOrder.
     * @param amount2 Amount of the oldOrder.
     */
    private void setupOrders(String op1, Integer price1, Integer amount1, String op2, Integer price2, Integer amount2) {
        newOrder = new LimitOrder.Builder()
                .setTraderId("Trader1")
                .setStockId("Stock")
                .setOperation(op1)
                .setPrice(price1)
                .setAmount(amount1)
                .build();
        oldOrder = new LimitOrder.Builder()
                .setTraderId("Trader2")
                .setStockId("Stock")
                .setOperation(op2)
                .setPrice(price2)
                .setAmount(amount2)
                .build();

        matchingOrderCollection = new OrderCollection();
        matchingOrderCollection.addOrder(oldOrder);
        Map<String, OrderCollection> map;
        if(Objects.equals(oldOrder.getOperation(), "SELL")) {
            map = Map.of("Asks", matchingOrderCollection);
        } else {
            map = Map.of("Bids", matchingOrderCollection);
        }
        when(mockOrderManager.getOrderCollectionMap()).thenReturn(map);
        when(mockOrderMatcherHandler.matchOrder(newOrder, map)).thenReturn(matchingOrderCollection);

        transaction = new Transaction("Stock", min(newOrder.getAmount(), oldOrder.getAmount()), oldOrder.getPrice());
    }

    /**
     * Method sets up the new and old Order Traders.
     * @param funds1 Funds of the newOrder Trader.
     * @param funds2 funds of the oldOrder Trader.
     */
    private void setupTraders(Long funds1, Long funds2) {
        trader1 = new Trader("Trader1", "Trader1", funds1);
        when(mockTraderManager.getTrader(trader1.getId())).thenReturn(trader1);
        trader2 = new Trader("Trader2", "Trader2", funds2);
        when(mockTraderManager.getTrader(trader2.getId())).thenReturn(trader2);
    }

    /**
     * Method sets up the Stock.
     */
    private void setupStock() {
        stock = new Stock("Stock", "Stock", 1L, 1.0, 1.0);
        when(mockStockManager.getStock("Stock")).thenReturn(stock);
    }

    /**
     * Tests that the StockExchange constructor creates a non-null StockExchange.
     */
    @Test
    void testConstructor() {
        setupStockExchange();
        assertNotNull(stockExchange);
    }

    /**
     * Tests that the StockExchange constructor throws an exception if a null argument is passed.
     */
    @Test
    void testNullArgConstructor() {
        mockStockManager = mock(StockManager.class);
        mockTraderManager = mock(TraderManager.class);
        mockOrderManager = mock(OrderManager.class);
        mockOrderMatcherHandler = mock(OrderMatcherHandler.class);
        assertThrows(NullPointerException.class, () -> new StockExchange(null, mockTraderManager,
                mockOrderManager, mockOrderMatcherHandler));
        assertThrows(NullPointerException.class, () -> new StockExchange(mockStockManager, null,
                mockOrderManager, mockOrderMatcherHandler));
        assertThrows(NullPointerException.class, () -> new StockExchange(mockStockManager, mockTraderManager,
                null, mockOrderMatcherHandler));
        assertThrows(NullPointerException.class, () -> new StockExchange(mockStockManager, mockTraderManager,
                mockOrderManager, null));
    }

    /**
     * Tests that the StockExchange resolve method throws an exception if a null Order is passed.
     */
    @Test
    void testResolveNullOrder() {
        setupStockExchange();
        assertThrows(NullPointerException.class, () -> stockExchange.resolveOrder(null));
    }

    /**
     * Tests that the StockExchange resolve method stores a non-matchable buy Order.
     */
    @Test
    void testResolveNonMatchableBuyOrder() {
        setupStockExchange();

        setupOrders("BUY", 5, 3, "BUY", 5, 1);
        setupTraders(100L, 100L);
        setupStock();

        Order mockOrder = mock(Order.class);
        stockExchange.resolveOrder(mockOrder);
        verify(mockOrderManager).storeOrder(mockOrder);
    }

    /**
     * Tests that the StockExchange resolve method stores a non-matchable sell Order.
     */
    @Test
    void testResolveNonMatchableSellOrder() {
        setupStockExchange();

        setupOrders("SELL", 5, 3, "SELL", 5, 1);
        setupTraders(100L, 100L);
        setupStock();

        Order mockOrder = mock(Order.class);
        stockExchange.resolveOrder(mockOrder);
        verify(mockOrderManager).storeOrder(mockOrder);
    }

    /**
     * Tests that the StockExchange resolve method correctly partially resolves and stores the buy newOrder.
     */
    @Test
    void testResolvePartialBuyOrder1() {
        setupStockExchange();

        setupOrders("BUY", 5, 3, "SELL", 5, 1);
        setupTraders(100L, 100L);
        trader2.setStockAmount("Stock", 100L);
        setupStock();

        stockExchange.resolveOrder(newOrder);
        assertEquals(newOrder.getAmount(), 3 - transaction.stockAmount());
        verify(mockOrderManager).storeOrder(newOrder);
        verify(mockOrderManager).removeOrder(oldOrder);
        assertEquals(1, trader1.getTransactionHistory().size());
        assertEquals(transaction, trader1.getTransactionHistory().get(0));
        assertEquals(Long.valueOf(transaction.stockAmount()), trader1.getStockAmount(newOrder.getStockId()));
        assertEquals(100L - Long.valueOf(transaction.stockAmount()), trader2.getStockAmount(newOrder.getStockId()));
        assertEquals(mockStockManager.getStock(newOrder.getStockId()).getPrice(), Double.valueOf(transaction.stockPrice()));
    }

    /**
     * Tests that the StockExchange resolve method correctly partially resolves the buy newOrder and stores the oldOrder.
     */
    @Test
    void testResolvePartialBuyOrder2() {
        setupStockExchange();

        setupOrders("BUY", 5, 1, "SELL", 5, 3);
        setupTraders(100L, 100L);
        trader2.setStockAmount("Stock", 100L);
        setupStock();

        stockExchange.resolveOrder(newOrder);
        assertEquals(oldOrder.getAmount(), 3 - transaction.stockAmount());
        assertEquals(transaction, trader1.getTransactionHistory().get(0));
        assertEquals(Long.valueOf(transaction.stockAmount()), trader1.getStockAmount(newOrder.getStockId()));
        assertEquals(100L - Long.valueOf(transaction.stockAmount()), trader2.getStockAmount(newOrder.getStockId()));
        assertEquals(mockStockManager.getStock(oldOrder.getStockId()).getPrice(), Double.valueOf(transaction.stockPrice()));
    }

    /**
     * Tests that the StockExchange resolve method correctly partially resolves and stores the sell newOrder.
     */
    @Test
    void testResolvePartialSellOrder1() {
        setupStockExchange();

        setupOrders("SELL", 5, 3, "BUY", 5, 1);
        setupTraders(100L, 100L);
        trader1.setStockAmount("Stock", 100L);
        setupStock();

        stockExchange.resolveOrder(newOrder);
        assertEquals(newOrder.getAmount(), 3 - transaction.stockAmount());
        verify(mockOrderManager).storeOrder(newOrder);
        verify(mockOrderManager).removeOrder(oldOrder);
        assertEquals(1, trader1.getTransactionHistory().size());
        assertEquals(transaction, trader1.getTransactionHistory().get(0));
        assertEquals(100L - Long.valueOf(transaction.stockAmount()), trader1.getStockAmount(newOrder.getStockId()));
        assertEquals(Long.valueOf(transaction.stockAmount()), trader2.getStockAmount(newOrder.getStockId()));
        assertEquals(stock.getPrice(), Double.valueOf(transaction.stockPrice()));
    }

    /**
     * Tests that the StockExchange resolve method correctly partially resolves the sell newOrder and stores the newOrder.
     */
    @Test
    void testResolvePartialSellOrder2() {
        setupStockExchange();

        setupOrders("SELL", 5, 1, "BUY", 5, 3);
        setupTraders(100L, 100L);
        trader1.setStockAmount("Stock", 100L);
        setupStock();

        stockExchange.resolveOrder(newOrder);
        assertEquals(oldOrder.getAmount(), 3 - transaction.stockAmount());
        assertEquals(1, trader1.getTransactionHistory().size());
        assertEquals(transaction, trader1.getTransactionHistory().get(0));
        assertEquals(100L - Long.valueOf(transaction.stockAmount()), trader1.getStockAmount(newOrder.getStockId()));
        assertEquals(Long.valueOf(transaction.stockAmount()), trader2.getStockAmount(newOrder.getStockId()));
        assertEquals(stock.getPrice(), Double.valueOf(transaction.stockPrice()));
    }


    /**
     * Tests that the StockExchange resolve method correctly fully resolves the buy newOrder.
     */
    @Test
    void testResolveFullBuyOrder() {
        setupStockExchange();

        setupOrders("BUY", 5, 3, "SELL", 5, 3);
        setupTraders(100L, 100L);
        trader2.setStockAmount("Stock", 100L);
        setupStock();

        stockExchange.resolveOrder(newOrder);
        verify(mockOrderManager).removeOrder(oldOrder);
        assertEquals(1, trader1.getTransactionHistory().size());
        assertEquals(transaction, trader1.getTransactionHistory().get(0));
        assertEquals(Long.valueOf(transaction.stockAmount()), trader1.getStockAmount(newOrder.getStockId()));
        assertEquals(100L - Long.valueOf(transaction.stockAmount()), trader2.getStockAmount(newOrder.getStockId()));
        assertEquals(stock.getPrice(), Double.valueOf(transaction.stockPrice()));
    }

    /**
     * Tests that the StockExchange resolve method correctly fully resolves the sell newOrder.
     */
    @Test
    void testResolveFullSellOrder() {
        setupStockExchange();

        setupOrders("SELL", 5, 3, "BUY", 5, 3);
        setupTraders(100L, 100L);
        trader1.setStockAmount("Stock", 100L);
        setupStock();

        stockExchange.resolveOrder(newOrder);
        verify(mockOrderManager).removeOrder(oldOrder);
        assertEquals(1, trader1.getTransactionHistory().size());
        assertEquals(transaction, trader1.getTransactionHistory().get(0));
        assertEquals(100L - Long.valueOf(transaction.stockAmount()), trader1.getStockAmount(newOrder.getStockId()));
        assertEquals(Long.valueOf(transaction.stockAmount()), trader2.getStockAmount(newOrder.getStockId()));
        assertEquals(stock.getPrice(), Double.valueOf(transaction.stockPrice()));
    }


    /**
     * Tests that the StockExchange resolve method doesn't resolve and stores an unfunded buy newOrder.
     */
    @Test
    void testResolveNonResolvableBuyOrder1() {
        setupStockExchange();

        setupOrders("BUY", 5, 3, "SELL", 5, 3);
        setupTraders(0L, 100L);
        trader1.setStockAmount("Stock", 100L);
        setupStock();

        stockExchange.resolveOrder(newOrder);
        verify(mockOrderManager).storeOrder(newOrder);
    }

    /**
     * Tests that the StockExchange resolve method doesn't resolve and stores a funded buy newOrder, but amount-deficient oldOrder.
     */
    @Test
    void testResolveNonResolvableBuyOrder2() {
        setupStockExchange();

        setupOrders("BUY", 5, 3, "SELL", 5, 1);
        setupTraders(100L, 100L);
        setupStock();

        stockExchange.resolveOrder(newOrder);
        verify(mockOrderManager).storeOrder(newOrder);
    }

    /**
     * Tests that the StockExchange resolve method doesn't resolve and stores an amount-deficient sell newOrder.
     */
    @Test
    void testResolveNonResolvableSellOrder1() {
        setupStockExchange();

        setupOrders("SELL", 5, 3, "BUY", 5, 3);
        setupTraders(100L, 100L);
        setupStock();

        stockExchange.resolveOrder(newOrder);
        verify(mockOrderManager).storeOrder(newOrder);
    }

    /**
     * Tests that the StockExchange resolve method doesn't resolve and stores an amount-good sell newOrder, but unfunded oldOrder.
     */
    @Test
    void testResolveNonResolvableSellOrder2() {
        setupStockExchange();

        setupOrders("SELL", 5, 3, "BUY", 5, 3);
        setupTraders(100L, 0L);
        trader1.setStockAmount("Stock", 100L);
        setupStock();

        stockExchange.resolveOrder(newOrder);
        verify(mockOrderManager).storeOrder(newOrder);
    }

}
