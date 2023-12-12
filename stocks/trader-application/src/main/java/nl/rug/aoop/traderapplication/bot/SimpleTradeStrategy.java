package nl.rug.aoop.traderapplication.bot;

import nl.rug.aoop.core.order.LimitOrder;
import nl.rug.aoop.core.order.Order;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

import static java.lang.Math.abs;

/**
 * A simple random strategy using limit orders. Implements the TradeStrategy Interface.
 */
public class SimpleTradeStrategy implements TradeStrategy {
    /**
     * Random generator.
     */
    private final Random random = new Random();
    /**
     * Map containing trader information.
     */
    private Map<String, Object> traderMap;
    /**
     * List of maps containing stock information.
     */
    private ArrayList<Map<String, Object>> stocks;

    /**
     * Method creates an order.
     *
     * @param traderMap Map with all the trader information.
     * @param stocks    List of maps of stock information.
     * @return Created order.
     */
    public Order createOrder(Map<String, Object> traderMap, ArrayList<Map<String, Object>> stocks) {
        this.traderMap = traderMap;
        this.stocks = stocks;

        String operation = selectOperation();
        String traderId = (String) traderMap.get("id");
        String stockId = null;
        Integer amount = null;
        Integer price = null;
        switch (operation) {
            case "BUY":
                stockId = selectStock();
                amount = calculateBuyAmount(stockId);
                price = calculateBuyPrice(stockId);
                break;
            case "SELL":
                stockId = selectOwnStock();
                amount = calculateSellAmount(stockId);
                price = calculateSellPrice(stockId);
                break;
        }
        return new LimitOrder.Builder()
                .setTraderId(traderId)
                .setStockId(stockId)
                .setOperation(operation)
                .setPrice(price)
                .setAmount(amount)
                .build();

    }

    /**
     * Selects order operation from enum list.
     *
     * @return Operation String.
     */
    private String selectOperation() {
        return String.valueOf(Types.values()[random.nextInt(Types.values().length)]);
    }

    /**
     * Selects a random stock from the list of all stocks.
     *
     * @return Stock symbol String
     */
    private String selectStock() {
        Map<String, Object> selectedStock = stocks.get(random.nextInt(stocks.size()));
        return (String) selectedStock.get("symbol");
    }

    /**
     * Selects a random stock from the list of own stocks.
     *
     * @return Stock symbol String
     */
    private String selectOwnStock() {
        Map<String, Long> ownedShares = (Map<String, Long>) traderMap.get("ownedShares");
        return (String) ownedShares.keySet().toArray()[random.nextInt(ownedShares.size())];
    }

    /**
     * Calculates a random amount of stocks to buy.
     *
     * @param stockId Id of bought stock.
     * @return Amount.
     */
    private Integer calculateBuyAmount(String stockId) {
        long traderFunds = (long) traderMap.get("funds");
        Map<String, Object> stock = stocks.stream()
                .filter(x -> x.get("symbol").equals(stockId))
                .findAny()
                .orElse(null);
        Double stockPrice = (Double) stock.get("price");
        double maximumAmount = traderFunds / stockPrice;
        return random.nextInt((int) maximumAmount);
    }

    /**
     * Calculates a random amount of stocks to sell.
     *
     * @param stockId Id of sold stock.
     * @return Amount.
     */
    private Integer calculateSellAmount(String stockId) {
        Map<String, Long> ownedShares = (Map<String, Long>) traderMap.get("ownedShares");
        return random.nextInt(Math.toIntExact(ownedShares.get(stockId)));
    }

    /**
     * Calculates the buy price for bought stock.
     *
     * @param stockId Id of bought stock.
     * @return Buy price.
     */
    private Integer calculateBuyPrice(String stockId) {
        Map<String, Object> stock = stocks.stream()
                .filter(x -> x.get("symbol").equals(stockId))
                .findAny()
                .orElse(null);
        if (stock != null) {
            Double price = (Double) stock.get("price");
            return (int) Math.round(price) + (1 + random.nextInt(abs((int) (price * 0.10))));
        }
        return null;
    }

    /**
     * Calculates sell price for sold stock.
     *
     * @param stockId Id of sold stock.
     * @return Sell price.
     */
    private Integer calculateSellPrice(String stockId) {
        Map<String, Object> stock = stocks.stream()
                .filter(x -> x.get("symbol").equals(stockId))
                .findAny()
                .orElse(null);
        if (stock != null) {
            Double price = (Double) stock.get("price");
            return (int) Math.round(price) - (1 + random.nextInt(abs((int) (price * 0.10))));
        }
        return null;
    }

    /**
     * Enum Types denoting the Types of order.
     */
    private enum Types {BUY, SELL}
}
