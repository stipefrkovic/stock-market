package nl.rug.aoop.core.order;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.NamedType;

/**
 * OrderSerializer class converts the orders to Json String.
 */
public class OrderSerializer {
    /**
     * Creates object mapper.
     * @return New object mapper.
     */
    private static ObjectMapper createMapper() {
        ObjectMapper mapper = new ObjectMapper();

        mapper.registerSubtypes(
                new NamedType(LimitOrder.class, "LimitOrder")
        );

        return mapper;
    }

    /**
     * Method serializes order.
     * @param order Serialized order.
     * @return Order in Json String.
     */
    public static String serialize(Order order) throws JsonProcessingException {
        return createMapper().writeValueAsString(order);
    }

    /**
     * Method deserializes order.
     * @param json Order in json string.
     * @return Deserialized order.
     */
    public static Order deserialize(String json) throws JsonProcessingException {
        return createMapper().readValue(json, Order.class);
    }
}
