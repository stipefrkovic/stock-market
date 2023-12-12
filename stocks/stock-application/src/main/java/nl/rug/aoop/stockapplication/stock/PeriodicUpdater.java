package nl.rug.aoop.stockapplication.stock;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.core.stock.StockCollection;
import nl.rug.aoop.core.trader.Trader;
import nl.rug.aoop.messagequeue.message.Message;
import nl.rug.aoop.messagequeue.message.NetworkMessage;
import nl.rug.aoop.networking.Communicator;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Thread.sleep;
import static java.util.Objects.requireNonNull;

/**
 * Class PeriodicUpdater that implements Runnable. It sends periodic updates to the registered
 * Trader(Bot) Communicators about the StockExchange.
 */
@Getter
@Slf4j
public class PeriodicUpdater implements Runnable {
    /**
     * StockExchange from which the updates are sourced.
     */
    private final StockExchange stockExchange;
    /**
     * Map(String, Communicator) holds the Communicators of the registered Trader(Bot)s.
     */
    private final Map<String, Communicator> traderCommunicators;
    /**
     * boolean signaling whether the PeriodicUpdater is running.
     */
    private boolean running;

    /**
     * Constructor for PeriodicUpdater that initializes the Map, running to false, and the given StockExchange.
     * @param stockExchange StockExchange from which the updates are sourced.
     */
    public PeriodicUpdater(StockExchange stockExchange) {
        this.stockExchange = requireNonNull(stockExchange);
        traderCommunicators = new HashMap<>();
        running = false;
    }

    /**
     * Method that will periodically (every 1 second) call SendUpdates.
     */
    @Override
    public void run(){
        log.info("PeriodicUpdater is running.");
        running = true;
        while (running) {
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException("Error while sleeping PeriodicUpdater.");
            }
            sendUpdates();
        }
    }

    /**
     * Method terminates the PeriodicUpdater by stopping the run() method.
     */
    public void terminate() {
        log.info("PeriodicUpdater is terminated.");
        running = false;
    }

    /**
     * Method registers a Trader(Bot)'s Communicator.
     * @param traderId String of the Trader's ID.
     * @param communicator Trader's Communicator.
     */
    public void registerTrader(String traderId, Communicator communicator) {
        traderCommunicators.put(traderId, communicator);
        log.info("Registered Trader: " + traderId);
    }

    /**
     * Method sends StockExchange updates to all registered and initialized Trader(Bot)s
     * with their Trader and Stock information.
     */
    public void sendUpdates() {
        log.info("Sending StockExchange updates to Trader(Bot)s.");
        if(traderCommunicators.size() != 0) {
            for (String traderId : traderCommunicators.keySet()) {
                if (stockExchange.getTraderManager().getTrader(traderId) != null
                        && stockExchange.getStockManager().getSize() != 0) {
                    traderCommunicators.get(traderId).sendMessage(getStockUpdate(traderId));
                    traderCommunicators.get(traderId).sendMessage(getTraderUpdate(traderId));
                }
            }
        }
    }

    /**
     * Method gets the update about the Stocks from the StockExchange.
     * @param traderId String of Trader(Bot)'s Id to send the update to.
     * @return String of NetworkMessage with Header as updateStocks command String and as Body a Message;
     * the Message contains the traderId as Header and the updated StockCollection String as Body.
     */
    public String getStockUpdate(String traderId) {
        try {
            StockCollection stockCollection = stockExchange.getStockManager().getStockCollection();
            Message message = new Message(traderId, StockCollection.toString(stockCollection));
            NetworkMessage networkMessage = new NetworkMessage("updateStocks", message.toJson());
            return networkMessage.toJson();
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert stock collection to string.");
        }
    }

    /**
     * Method gets the update about the Trader from the StockExchange.
     * @param traderId String of Trader(Bot)'s Id to send the update to.
     * @return String of NetworkMessage with Header as updateTrader command String and as Body a Message;
     * the Message contains the traderId as Header and the updated Trader String as Body.
     */
    public String getTraderUpdate(String traderId) {
        try {
            Trader trader = stockExchange.getTraderManager().getTrader(traderId);
            Message message = new Message(traderId, Trader.toString(trader));
            NetworkMessage networkMessage = new NetworkMessage("updateTrader", message.toJson());
            return networkMessage.toJson();
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert stock collection to string.");
        }
    }

}
