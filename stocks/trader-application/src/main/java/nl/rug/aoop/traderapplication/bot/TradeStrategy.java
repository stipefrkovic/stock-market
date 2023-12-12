package nl.rug.aoop.traderapplication.bot;

import nl.rug.aoop.core.order.Order;

import java.util.ArrayList;
import java.util.Map;

/**
 * TradeStrategy Interface allows for the implementation of various trade strategies.
 */
public interface TradeStrategy {
    /**
     * Method creates an order.
     *
     * @param traderMap Map with all the trader information.
     * @param stocks    List of maps of stock information.
     * @return Created order.
     */
    Order createOrder(Map<String, Object> traderMap, ArrayList<Map<String, Object>> stocks);
}
