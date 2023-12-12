package nl.rug.aoop.core.trader;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * Trader class represents the traders of the stock exchange.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Slf4j
public class Trader {
    /**
     * Trader id.
     */
    private String id;
    /**
     * Trader name.
     */
    private String name;
    /**
     * Trader funds.
     */
    private Long funds;
    /**
     * Map of the shares the trader owns.
     */
    private Map<String, Long> ownedShares = new HashMap<>();
    /**
     * List of transactions involving the trader.
     */
    private final List<Transaction> transactionHistory = new ArrayList<>();

    /**
     * Constructor creates the trader.
     * @param id Trader id.
     * @param name Trader name.
     * @param funds Trader funds.
     */
    public Trader(String id, String name, Long funds) {
        this.id = id;
        this.name = name;
        this.funds = funds;
    }

    /**
     * Default constructor.
     */
    public Trader() {}

    /**
     * Converts a trader to json String format.
     * @param trader Trader to be converted.
     * @return Json String of trader.
     */
    public static String toString(Trader trader) throws JsonProcessingException {
        return (new ObjectMapper()).writeValueAsString(trader);
    }

    /**
     * Converts a json String back to a trader.
     * @param string Json String.
     * @return Converted trader.
     */
    public static Trader fromString(String string) throws JsonProcessingException {
        return (new ObjectMapper()).readValue(string, Trader.class);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Trader trader = (Trader) o;
        return funds.equals(trader.funds)
                && id.equals(trader.id)
                && name.equals(trader.name)
                && ownedShares.equals(trader.ownedShares)
                && transactionHistory.equals(trader.transactionHistory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, funds, ownedShares, transactionHistory);
    }

    /**
     * Add a transaction to the transaction history.
     * @param transaction Transaction to be added.
     */
    public void addTransaction(Transaction transaction) {
        transactionHistory.add(transaction);
    }

    /**
     * Retrieves the amount of a particular stock that the trader owns.
     * @param stockId Id of stock.
     * @return Amount of owned stock.
     */
    public Long getStockAmount(String stockId) {
        return ownedShares.getOrDefault(stockId, 0L);
    }

    /**
     * Sets the amount of a particular stock the trader owns.
     * @param stockId Id of stock.
     * @param amount New amount.
     */
    public void setStockAmount(String stockId, Long amount) {
        ownedShares.put(stockId, amount);
    }

    /**
     * Checks if trader has a specific amount of a stock.
     * @param stockId Id of stock.
     * @param amount Amount.
     * @return True if trader has the amount, otherwise False.
     */
    public boolean hasStockAmount(String stockId, Integer amount) {
        if (!ownedShares.containsKey(stockId)) {
            return false;
        }
        return ownedShares.get(stockId) >= amount;
    }
}
