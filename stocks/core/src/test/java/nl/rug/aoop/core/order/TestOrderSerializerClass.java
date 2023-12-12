package nl.rug.aoop.core.order;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * TestOrderSerializerClass tests the OrderSerializer class.
 */
public class TestOrderSerializerClass {

    /**
     * Method checks that an order can be correctly converted to json and back.
     */
    @Test
    void testStringConversion() throws JsonProcessingException {
        Order order = LimitOrder.newBuilder()
                .setTraderId("NIELS")
                .setStockId("AAPL")
                .setOperation("BUY")
                .setPrice(10)
                .setAmount(100)
                .build();
        String jsonOrder = OrderSerializer.serialize(order);
        Order convertedOrder = OrderSerializer.deserialize(jsonOrder);

        assertEquals(order, convertedOrder);
    }
}
