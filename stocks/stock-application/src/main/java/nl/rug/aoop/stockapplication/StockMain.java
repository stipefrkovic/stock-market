package nl.rug.aoop.stockapplication;

import lombok.extern.slf4j.Slf4j;

/**
 * Stock Main sets up the stock application.
 */
@Slf4j
public class StockMain {
    /**
     * Main method.
     * @param args Arguments.
     */
    public static void main(String[] args) {
        StockInit stockInit = new StockInit();
        stockInit.initialize();
    }
}