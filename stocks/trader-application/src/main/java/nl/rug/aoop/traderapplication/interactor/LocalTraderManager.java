package nl.rug.aoop.traderapplication.interactor;

import lombok.Getter;
import nl.rug.aoop.core.trader.Trader;

import java.util.HashMap;
import java.util.Map;

/**
 * LocalTraderManager manages the local traders.
 */
public class LocalTraderManager {
    /**
     * A map of traders and their ids.
     */
    @Getter
    private Map<String, LocalTrader> localTraders = new HashMap<>();

    /**
     * Default constructor.
     */
    public LocalTraderManager() {}

    /**
     * Updates the local trader.
     * @param traderID Id of the updated trader.
     * @param trader New trader.
     */
    public void updateTraders(String traderID, Trader trader) {
        localTraders.get(traderID).updateTrader(trader);
    }
}
