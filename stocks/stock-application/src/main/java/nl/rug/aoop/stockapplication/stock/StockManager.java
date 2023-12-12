package nl.rug.aoop.stockapplication.stock;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.core.stock.Stock;
import nl.rug.aoop.core.stock.StockCollection;
import nl.rug.aoop.util.YamlLoader;

import java.io.IOException;
import java.nio.file.Path;

import static java.util.Objects.requireNonNull;

/**
 * Class StockManager that contains the StockCollection(s) and provides methods to operate on them.
 */
@Getter
@Slf4j
public class StockManager {
    /**
     * StockCollection holding all the Stocks.
     */
    private StockCollection stockCollection = new StockCollection();

    /**
     * Method loads the Stocks from the yaml file.
     */
    public void loadStocks() {
        Path path = Path.of("yaml", "stocks.yaml");
        YamlLoader yamlLoader = new YamlLoader(path);
        try {
            stockCollection = yamlLoader.load(StockCollection.class);
            log.info("Loaded stocks.");
        } catch (IOException e) {
            log.error("Failed to load stocks.", e);
        }
        for (Stock stock : stockCollection.getStocks().values()) {
            stock.updateMarketCapitalization();
        }
    }

    /**
     * Method gets the Stock based on the String stockId.
     * @param stockId String of the Stock's Id (symbol).
     * @return Stock matching the Id.
     */
    public Stock getStock(String stockId) {
        requireNonNull(stockId);
        return stockCollection.getStock(stockId);
    }

    /**
     * Method gets the size of the StockManager's StockCollection(s).
     * @return size of the StockManager's StockCollection(s).
     */
    public int getSize() {
        return stockCollection.getSize();
    }
}
