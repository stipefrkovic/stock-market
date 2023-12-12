package nl.rug.aoop.traderapplication.interactor;

import lombok.Getter;
import lombok.Setter;
import nl.rug.aoop.core.trader.Trader;

/**
 * LocalTrader keeps track of the information on the current trader.
 */
public class LocalTrader {
    /**
     * Trader id.
     */
    @Getter
    @Setter
    private String id;
    /**
     * Trader object.
     */
    @Getter
    private Trader trader = null;

    /**
     * Default constructor.
     */
    public LocalTrader() {}

    /**
     * Updates the trader.
     * @param trader New trader.
     */
    void updateTrader(Trader trader) {
        this.trader = trader;
    }
}
