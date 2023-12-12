package nl.rug.aoop.stockapplication.command;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.command.Command;
import nl.rug.aoop.core.order.Order;
import nl.rug.aoop.core.order.OrderSerializer;
import nl.rug.aoop.stockapplication.stock.StockExchange;

import java.util.Map;

import static java.util.Objects.requireNonNull;

/**
 * Class ResolveOrderCommand that implements Command. It executes an Order in the contained StockExchange.
 */
@Slf4j
public class ResolveOrderCommand implements Command {
    /**
     * StockExchange that should execute the Order.
     */
    private final StockExchange stockExchange;

    /**
     * Constructor for ResolveOrderCommand.
     * @param stockExchange StockExchange that should execute the Order.
     */
    public ResolveOrderCommand(StockExchange stockExchange) {
        this.stockExchange = requireNonNull(stockExchange);
    }

    /**
     * Method executes the Command with given Map of Objects available to use.
     *
     * @param options Map(String, Object) with Objects for the Command to use.
     */
    @Override
    public void execute(Map<String, Object> options) {
        try {
            String orderString = (String) options.get("Body");
            Order order = OrderSerializer.deserialize(orderString);
            stockExchange.resolveOrder(order);
        } catch (JsonProcessingException e) {
            log.error("Failed to deserialize order.");
        }
    }
}
