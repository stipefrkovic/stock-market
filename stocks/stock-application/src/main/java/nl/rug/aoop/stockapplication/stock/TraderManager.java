package nl.rug.aoop.stockapplication.stock;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.core.trader.Trader;
import nl.rug.aoop.core.trader.TraderCollection;
import nl.rug.aoop.util.YamlLoader;

import java.io.IOException;
import java.nio.file.Path;

import static java.util.Objects.requireNonNull;

/**
 * Class TraderManager that contains the TraderCollection(s) and provides methods to operate on them.
 */
@Getter
@Slf4j
public class TraderManager {
    /**
     * StockCollection holding all the Traders.
     */
    private TraderCollection traderCollection = new TraderCollection();

    /**
     * Method loads the Traders from the yaml file.
     */
    public void loadTraders() {
        Path path = Path.of("yaml", "traders.yaml");
        YamlLoader yamlLoader = new YamlLoader(path);
        try {
            traderCollection = yamlLoader.load(TraderCollection.class);
            log.info("Loaded traders.");
        } catch (IOException e) {
            log.error("Failed to load traders.", e);
        }
    }

    /**
     * Method gets the Trader based on the String traderId.
     * @param traderId String of the Trader's Id.
     * @return Trader matching the Id.
     */
    public Trader getTrader(String traderId) {
        requireNonNull(traderId);
        return traderCollection.getTrader(traderId);
    }

    /**
     * Method gets the size of the StockManager's StockCollection(s).
     * @return int size of the StockManager's StockCollection(s).
     */
    public int getSize() {
        return traderCollection.getSize();

    }
}
