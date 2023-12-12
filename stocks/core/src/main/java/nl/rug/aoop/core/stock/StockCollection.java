package nl.rug.aoop.core.stock;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * StockCollection represents a collection of stocks.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class StockCollection {
    /**
     * Map of stocks.
     */
    private Map<String, Stock> stocks = new HashMap<>();

    /**
     * Converts the stock collection to json String format.
     *
     * @param stockCollection Collection to be converted.
     * @return Json String of stock.
     */
    public static String toString(StockCollection stockCollection) throws JsonProcessingException {
        return (new ObjectMapper()).writeValueAsString(stockCollection);
    }

    /**
     * Converts a json String into a stock collection.
     *
     * @param string Json String to be converted.
     * @return Converted stock collection.
     */
    public static StockCollection fromString(String string) throws JsonProcessingException {
        return (new ObjectMapper()).readValue(string, StockCollection.class);
    }

    /**
     * Updates the stocks in the stock collection. Modifies the stock if it's already present,
     * otherwise adds it.
     *
     * @param stock New stock.
     */
    public void updateStock(Stock stock) {
        stocks.put(stock.getSymbol(), stock);
    }

    /**
     * Retrieves a stock based on its id.
     *
     * @param stockId Stock id.
     * @return Retrieved stock.
     */
    public Stock getStock(String stockId) {
        return stocks.get(stockId);
    }

    /**
     * Retrieves the size of the stock collection.
     *
     * @return Size.
     */
    public int getSize() {
        return stocks.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        StockCollection that = (StockCollection) o;
        return Objects.equals(this.stocks, that.stocks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stocks);
    }
}
