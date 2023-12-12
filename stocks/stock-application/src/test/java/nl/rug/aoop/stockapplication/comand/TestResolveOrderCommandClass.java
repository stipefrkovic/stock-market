package nl.rug.aoop.stockapplication.comand;

import com.fasterxml.jackson.core.JsonProcessingException;
import nl.rug.aoop.core.order.LimitOrder;
import nl.rug.aoop.core.order.Order;
import nl.rug.aoop.core.order.OrderSerializer;
import nl.rug.aoop.stockapplication.command.ResolveOrderCommand;
import nl.rug.aoop.stockapplication.stock.StockExchange;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * TestResolveOrderCommandClass class that tests the ResolveOrderCommand class.
 */
public class TestResolveOrderCommandClass {

    /**
     * Tests that the ResolveOrderCommand constructor creates a non-null ResolveOrderCommand.
     */
    @Test
    void testConstructor() {
        ResolveOrderCommand ResolveOrderCommand = new ResolveOrderCommand(mock(StockExchange.class));
        assertNotNull(ResolveOrderCommand);
    }

    /**
     * Tests that the ResolveOrderCommand constructor throws an exception when passing a null argument.
     */
    @Test
    void testNullArgConstructor() {
        assertThrows(NullPointerException.class, () -> new ResolveOrderCommand(null));
    }

    /**
     * Tests that ResolveOrderCommand execute method correctly calls the resolveOrder method of the StockExchange.
     * @throws JsonProcessingException Thrown if Json string conversion of Order fails.
     */
    @Test
    void testExecute() throws JsonProcessingException {
        Order order = new LimitOrder.Builder()
                .setTraderId("Trader")
                .setStockId("Stock")
                .setOperation("BUY")
                .setPrice(1)
                .setAmount(1)
                .build();
        Map<String, Object> options = Map.of(
                "Header", "resolveOrder",
                "Body", OrderSerializer.serialize(order)
        );
        StockExchange mockStockExchange = mock(StockExchange.class);
        ResolveOrderCommand ResolveOrderCommand = new ResolveOrderCommand(mockStockExchange);
        ResolveOrderCommand.execute(options);
        verify(mockStockExchange).resolveOrder(order);
    }

}
