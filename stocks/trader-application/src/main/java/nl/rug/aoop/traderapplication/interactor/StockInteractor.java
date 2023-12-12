package nl.rug.aoop.traderapplication.interactor;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.core.order.Order;
import nl.rug.aoop.core.order.OrderSerializer;
import nl.rug.aoop.core.stock.Stock;
import nl.rug.aoop.messagequeue.message.Message;
import nl.rug.aoop.messagequeue.message.NetworkMessage;
import nl.rug.aoop.networking.client.Client;
import nl.rug.aoop.traderapplication.network.NetworkProducer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * StockInteractor class enables the interaction of traders/bots with the stock application.
 */
@Slf4j
public class StockInteractor {
    /**
     * Local representation of a trader from the stock exchange.
     */
    @Setter
    private LocalTrader localTrader;
    /**
     * Local representation of stocks from the stocks exchange.
     */
    @Setter
    private LocalStockManager localStockManager;
    /**
     * Client through which messages will be sent.
     */
    private final Client client;
    /**
     * NetworkProducer creates messages for the server message queue.
     */
    private final NetworkProducer networkProducer;

    /**
     * Constructor creates the class.
     * @param client Client through which messages will be sent.
     * @param networkProducer NetworkProducer creates messages for the server message queue.
     */
    public StockInteractor(Client client, NetworkProducer networkProducer) {
        this.client = client;
        this.networkProducer = networkProducer;
    }

    /**
     * Method creates order message and asks producer to send it to the server via the client.
     * @param order Order to be sent.
     */
    public void sendOrder(Order order) {
        String orderString = null;
        try {
            orderString = OrderSerializer.serialize(order);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error while attempting to serialize order.");
        }
        Message orderMessage = new Message("resolveOrder", orderString);
        networkProducer.put(orderMessage);
        log.info("Sending order to Server.");
    }

    /**
     * Method sends a message to the server to register a new trader.
     * @param id Trader id.
     */
    public void registerTrader(String id) {
        NetworkMessage networkMessage = new NetworkMessage("registerTrader", id);
        client.sendMessage(networkMessage.toJson());
        log.info("Registering trader: " + id);
    }

    /**
     * Method returns a map of information on the local trader.
     * @return Map of information.
     */
    public Map<String, Object> getTrader() {
        if (localTrader.getTrader() != null) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", localTrader.getTrader().getId());
            map.put("name", localTrader.getTrader().getName());
            map.put("funds", localTrader.getTrader().getFunds());
            map.put("ownedShares", localTrader.getTrader().getOwnedShares());
            return map;
        }
        return null;
    }

    /**
     * Method returns a list of maps of information about local stocks.
     * @return A list of maps.
     */
    public ArrayList<Map<String, Object>> getStocks() {
        if (localStockManager.getStockCollection() != null) {
            Map<String, Stock> list = localStockManager.getStockCollection().getStocks();
            ArrayList<Map<String, Object>> stocks = new ArrayList<>();
            for (Stock stock : list.values()) {
                Map<String, Object> map = new HashMap<>();
                map.put("symbol", stock.getSymbol());
                map.put("name", stock.getName());
                map.put("sharesOutstanding", stock.getSharesOutstanding());
                map.put("price", stock.getPrice());
                stocks.add(map);
            }
            return stocks;
        }
        return null;
    }
}
