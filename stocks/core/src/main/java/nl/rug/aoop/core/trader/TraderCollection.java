package nl.rug.aoop.core.trader;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * Trader collection represents a collection of traders.
 */
@Getter
public class TraderCollection {
    /**
     * Map of trader ids to traders.
     */
    private final Map<String, Trader> traders = new HashMap<>();

    /**
     * Retrieves the size of the stock collection.
     * @return Size.
     */
    public int getSize() {
        return traders.size();
    }

    /**
     * Method modifies a trader if it is already in the collection, otherwise adds a new trader.
     * @param trader Trader in question.
     */
    public void updateTrader(Trader trader) {
        traders.put(trader.getId(), trader);
    }

    /**
     * Retrieves a specific trader based on trader id.
     * @param traderId Trader id.
     * @return Retrieved trader.
     */
    public Trader getTrader(String traderId) {
        return traders.get(traderId);
    }
}
