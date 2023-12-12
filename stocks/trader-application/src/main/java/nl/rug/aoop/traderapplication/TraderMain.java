package nl.rug.aoop.traderapplication;

import java.io.IOException;

/**
 * TraderMain sets up the trader application.
 */
public class TraderMain {
    /**
     * Main method.
     * @param args Arguments.
     */
    public static void main(String[] args) throws IOException {
        TraderInit traderInit = new TraderInit();
        traderInit.initialize();
    }
}