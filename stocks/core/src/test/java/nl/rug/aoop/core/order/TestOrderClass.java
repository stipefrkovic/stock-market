package nl.rug.aoop.core.order;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * TestOrderClass tests the Order class.
 */
public class TestOrderClass {
    /**
     * Tests that an order is successfully created.
     */
    @Test
    void testCreation() {
        Order normalStock = Order.newBuilder()
                .setTraderId("Trader")
                .setStockId("Stock")
                .setOperation("BUY")
                .setPrice(5)
                .setAmount(1)
                .build();
        assertNotNull(normalStock);
    }
}
