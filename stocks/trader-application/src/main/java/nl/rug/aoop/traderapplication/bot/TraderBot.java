package nl.rug.aoop.traderapplication.bot;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.core.order.Order;
import nl.rug.aoop.traderapplication.interactor.StockInteractor;

import java.util.ArrayList;
import java.util.Map;

import static java.lang.Thread.sleep;

/**
 * TraderBot class functions as a trader in the stock system. Implements Runnable Interface.
 */
@JsonIgnoreProperties(value = {"stockInteractor"})
@Getter
@Slf4j
public class TraderBot implements Runnable {
    /**
     * Trader bot id. Used to identify the trader bot.
     */
    private String id;
    /**
     * Map containing trader information.
     */
    private Map<String, Object> traderMap = null;
    /**
     * List of maps containing stock information.
     */
    private ArrayList<Map<String, Object>> stocks = null;
    /**
     * Flag that indicates whether the server is running.
     */
    private boolean running = false;
    /**
     * The trader bot's trade strategy.
     */
    @Setter
    private TradeStrategy tradeStrategy;
    /**
     * Stock interactor allowing the trader bot to interact with the stock application.
     */
    @Setter
    private StockInteractor stockInteractor;

    /**
     * Empty constructor.
     */
    public TraderBot() {
    }

    /**
     * Method fetches trader information from the stock interactor.
     */
    private void getTraderInfo() {
        traderMap = stockInteractor.getTrader();
    }

    /**
     * Method fetches stock information from the stock interactor.
     */
    private void getStocksInfo() {
        stocks = stockInteractor.getStocks();
    }

    /**
     * Method starts the trader bot.
     */
    @Override
    public void run() {
        log.info("Starting trader bot.");
        running = true;
        while (running) {
            try {
                sleep(4000);
            } catch (InterruptedException e) {
                throw new RuntimeException("Error while sleeping thread (TraderBot).");
            }
            sendOrder();
        }
    }

    /**
     * Method sends order via the stock interactor.
     */
    public void sendOrder() {
        getStocksInfo();
        getTraderInfo();
        if (traderMap != null && stocks != null && traderMap.size() != 0 && stocks.size() != 0) {
            Order order = tradeStrategy.createOrder(traderMap, stocks);
            log.info(id + ": created order.");
            stockInteractor.sendOrder(order);
        }
    }

    /**
     * Method terminates the trader bot.
     */
    public void terminate() {
        running = false;
    }
}
