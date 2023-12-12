package nl.rug.aoop.core.order;

import org.junit.jupiter.api.Test;

import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

/**
 * TestOrderCollectionClass tests the OrderCollection class.
 */
public class TestOrderCollectionClass {
    /**
     * Tested order collection.
     */
    private OrderCollection orderCollection;

    /**
     * Method sets up the order collection.
     */
    void setupOrderCollection() {
        orderCollection = new OrderCollection();
    }

    /**
     * Method tests that an order collection is successfully created.
     */
    @Test
    void testConstructor() {
        setupOrderCollection();
        assertNotNull(orderCollection);
    }

    /**
     * Method tests that correct size is retrieved.
     */
    @Test
    void testGetSize() {
        setupOrderCollection();
        assertEquals(0, orderCollection.getSize());
    }

    /**
     * Method tests that an order can be added.
     */
    @Test
    void testAddOrder() {
        setupOrderCollection();
        assertEquals(0, orderCollection.getSize());
        Order mockOrder = mock(Order.class);
        orderCollection.addOrder(mockOrder);
        assertEquals(1, orderCollection.getSize());
    }

    /**
     * Method tests that an order can be removed.
     */
    @Test
    void testRemoveOrder() {
        setupOrderCollection();
        Order mockOrder = mock(Order.class);
        orderCollection.addOrder(mockOrder);
        assertEquals(1, orderCollection.getSize());
        orderCollection.removeOrder(mockOrder);
        assertEquals(0, orderCollection.getSize());
    }

    /**
     * Method tests that orders with the same stocks can be correctly filtered out.
     */
    @Test
    void testFilterStock() {
        setupOrderCollection();

        Order rightStock1 = new LimitOrder.Builder()
                .setTraderId("Trader")
                .setStockId("Stock")
                .setOperation("BUY")
                .setPrice(5)
                .setAmount(1)
                .build();
        Order rightStock2 = new LimitOrder.Builder()
                .setTraderId("Trader")
                .setStockId("Stock")
                .setOperation("SELL")
                .setPrice(5)
                .setAmount(1)
                .build();
        Order wrongStock1 = new LimitOrder.Builder()
                .setTraderId("Trader")
                .setStockId("Stock1")
                .setOperation("SELL")
                .setPrice(5)
                .setAmount(1)
                .build();
        Order wrongStock2 = new LimitOrder.Builder()
                .setTraderId("Trader")
                .setStockId("Stock2")
                .setOperation("BUY")
                .setPrice(5)
                .setAmount(1)
                .build();

        orderCollection.addOrder(rightStock1);
        orderCollection.addOrder(rightStock2);
        orderCollection.addOrder(wrongStock1);
        orderCollection.addOrder(wrongStock2);

        OrderCollection expectedOrderCollection = new OrderCollection();
        expectedOrderCollection.addOrder(rightStock1);
        expectedOrderCollection.addOrder(rightStock2);

        assertEquals(expectedOrderCollection, OrderCollection.filterStock(orderCollection, rightStock1.getStockId()));
    }

    /**
     * Method tests that orders below a certain price can be filtered out correctly.
     */
    @Test
    void testFilterPriceLower() {
        setupOrderCollection();

        Order normalStock1 = new LimitOrder.Builder()
                .setTraderId("Trader")
                .setStockId("Stock")
                .setOperation("BUY")
                .setPrice(5)
                .setAmount(1)
                .build();
        Order normalStock2 = new LimitOrder.Builder()
                .setTraderId("Trader")
                .setStockId("Stock")
                .setOperation("BUY")
                .setPrice(5)
                .setAmount(1)
                .build();
        Order cheapStock = new LimitOrder.Builder()
                .setTraderId("Trader")
                .setStockId("Stock")
                .setOperation("SELL")
                .setPrice(4)
                .setAmount(1)
                .build();
        Order expensiveStock = new LimitOrder.Builder()
                .setTraderId("Trader")
                .setStockId("Stock1")
                .setOperation("SELL")
                .setPrice(6)
                .setAmount(1)
                .build();

        orderCollection.addOrder(normalStock1);
        orderCollection.addOrder(normalStock2);
        orderCollection.addOrder(cheapStock);
        orderCollection.addOrder(expensiveStock);

        OrderCollection expectedOrderCollection = new OrderCollection();
        expectedOrderCollection.addOrder(normalStock1);
        expectedOrderCollection.addOrder(normalStock2);
        expectedOrderCollection.addOrder(cheapStock);

        assertEquals(expectedOrderCollection, OrderCollection.filterPriceLower(orderCollection, normalStock1.getPrice()));
    }

    /**
     * Method tests that all orders above a certain price can be filtered out correctly.
     */
    @Test
    void testFilterPriceHigher() {
        setupOrderCollection();

        Order normalStock1 = new LimitOrder.Builder()
                .setTraderId("Trader")
                .setStockId("Stock")
                .setOperation("BUY")
                .setPrice(5)
                .setAmount(1)
                .build();
        Order normalStock2 = new LimitOrder.Builder()
                .setTraderId("Trader")
                .setStockId("Stock")
                .setOperation("BUY")
                .setPrice(5)
                .setAmount(1)
                .build();
        Order cheapStock = new LimitOrder.Builder()
                .setTraderId("Trader")
                .setStockId("Stock")
                .setOperation("SELL")
                .setPrice(4)
                .setAmount(1)
                .build();
        Order expensiveStock = new LimitOrder.Builder()
                .setTraderId("Trader")
                .setStockId("Stock1")
                .setOperation("SELL")
                .setPrice(6)
                .setAmount(1)
                .build();

        orderCollection.addOrder(normalStock1);
        orderCollection.addOrder(normalStock2);
        orderCollection.addOrder(cheapStock);
        orderCollection.addOrder(expensiveStock);

        OrderCollection expectedOrderCollection = new OrderCollection();
        expectedOrderCollection.addOrder(normalStock1);
        expectedOrderCollection.addOrder(normalStock2);
        expectedOrderCollection.addOrder(expensiveStock);

        assertEquals(expectedOrderCollection, OrderCollection.filterPriceHigher(orderCollection, normalStock1.getPrice()));
    }

    /**
     * Method tests that orders can be sorted from low to high correctly.
     */
    @Test
    void testSortPriceLowHigh() {
        setupOrderCollection();

        Order normalStock = new LimitOrder.Builder()
                .setTraderId("Trader")
                .setStockId("Stock")
                .setOperation("BUY")
                .setPrice(5)
                .setAmount(1)
                .build();
        Order cheapStock = new LimitOrder.Builder()
                .setTraderId("Trader")
                .setStockId("Stock")
                .setOperation("SELL")
                .setPrice(4)
                .setAmount(1)
                .build();
        Order expensiveStock = new LimitOrder.Builder()
                .setTraderId("Trader")
                .setStockId("Stock1")
                .setOperation("SELL")
                .setPrice(6)
                .setAmount(1)
                .build();

        orderCollection.addOrder(expensiveStock);
        orderCollection.addOrder(normalStock);
        orderCollection.addOrder(cheapStock);

        OrderCollection expectedOrderCollection = new OrderCollection();
        expectedOrderCollection.addOrder(cheapStock);
        expectedOrderCollection.addOrder(normalStock);
        expectedOrderCollection.addOrder(expensiveStock);

        assertEquals(expectedOrderCollection, OrderCollection.sortPriceLowHigh(orderCollection));
    }

    /**
     * Method tests that the orders can be sorted from high to low correctly.
     */
    @Test
    void testSortPriceHighLow() {
        setupOrderCollection();

        Order normalStock = new LimitOrder.Builder()
                .setTraderId("Trader")
                .setStockId("Stock")
                .setOperation("BUY")
                .setPrice(5)
                .setAmount(1)
                .build();
        Order cheapStock = new LimitOrder.Builder()
                .setTraderId("Trader")
                .setStockId("Stock")
                .setOperation("SELL")
                .setPrice(4)
                .setAmount(1)
                .build();
        Order expensiveStock = new LimitOrder.Builder()
                .setTraderId("Trader")
                .setStockId("Stock1")
                .setOperation("SELL")
                .setPrice(6)
                .setAmount(1)
                .build();

        orderCollection.addOrder(cheapStock);
        orderCollection.addOrder(normalStock);
        orderCollection.addOrder(expensiveStock);

        OrderCollection expectedOrderCollection = new OrderCollection();
        expectedOrderCollection.addOrder(expensiveStock);
        expectedOrderCollection.addOrder(normalStock);
        expectedOrderCollection.addOrder(cheapStock);

        assertEquals(expectedOrderCollection, OrderCollection.sortPriceHighLow(orderCollection));
    }


}
