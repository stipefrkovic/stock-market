package nl.rug.aoop.core.stock;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * Stock class represents the stocks of the stock exchange.
 */
@Getter
@Setter
@Slf4j
public class Stock {
    /**
     * Stock symbol/id.
     */
    private String symbol;
    /**
     * Name of stock.
     */
    private String name;
    /**
     * Outstanding shares of stock.
     */
    private Long sharesOutstanding;
    /**
     * Price of stock.
     */
    private Double price;
    /**
     * Stock market capitalization.
     */
    private Double marketCapitalization;

    /**
     * Constructor creates the stock.
     * @param symbol Stock symbol/id.
     * @param name Stock name.
     * @param sharesOutstanding Outstanding shares of stock.
     * @param price Stock price.
     * @param marketCapitalization Stock market capitalization.
     */
    public Stock(String symbol, String name, Long sharesOutstanding, Double price,
                 Double marketCapitalization) {
        this.symbol = symbol;
        this.name = name;
        this.sharesOutstanding = sharesOutstanding;
        this.price = price;
        this.marketCapitalization = marketCapitalization;
    }

    /**
     * Default constructor.
     */
    public Stock() {}

    /**
     * Method updates the price of the order.
     * @param latestPrice New price.
     */
    public void updatePrice(Double latestPrice) {
        price = latestPrice;
        updateMarketCapitalization();
    }

    /**
     * Method updates the market capitalization based on price and outstanding shares.
     */
    public void updateMarketCapitalization() {
        marketCapitalization = price * sharesOutstanding.floatValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Stock stock = (Stock) o;
        return symbol.equals(stock.symbol)
                && name.equals(stock.name)
                && sharesOutstanding.equals(stock.sharesOutstanding)
                && price.equals(stock.price)
                && marketCapitalization.equals(stock.marketCapitalization);
    }

    @Override
    public int hashCode() {
        return Objects.hash(symbol, name, sharesOutstanding, price, marketCapitalization);
    }
}
