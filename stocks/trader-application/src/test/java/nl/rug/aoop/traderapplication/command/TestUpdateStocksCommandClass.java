package nl.rug.aoop.traderapplication.command;

import com.fasterxml.jackson.core.JsonProcessingException;
import nl.rug.aoop.core.stock.Stock;
import nl.rug.aoop.core.stock.StockCollection;
import nl.rug.aoop.messagequeue.message.Message;
import nl.rug.aoop.traderapplication.interactor.LocalStockManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * TestUpdateStocksCommandClass tests the UpdateStocksCommand class.
 */
public class TestUpdateStocksCommandClass {
    /**
     * StockCollection used in the command.
     */
    private StockCollection stockCollection;
    /**
     * LocalStockManager used in the command.
     */
    private LocalStockManager localStockManager;
    /**
     * Tested command.
     */
    private UpdateStocksCommand command;

    /**
     * Method creates the command before every test.
     */
    @BeforeEach
    void createCommand() {
        Stock testStock = new Stock();
        stockCollection = new StockCollection();
        stockCollection.updateStock(testStock);
        localStockManager = new LocalStockManager();
        localStockManager.updateStocks(stockCollection);
        command = new UpdateStocksCommand(localStockManager);
    }

    /**
     * Method tests that the command is correctly constructed.
     */
    @Test
    void testLegalConstructor() {
        assertEquals(localStockManager, command.getLocalStockManager());
    }

    /**
     * Method tests that the command is correctly executed. Checks that the
     * StockCollection in local stock manager has been correctly updated.
     */
    @Test
    void testExecute() throws JsonProcessingException {
        StockCollection newStockCollection = new StockCollection();
        Stock testStock = new Stock("test", "test", 0L, 10.0, 10.0);
        newStockCollection.updateStock(testStock);
        Map<String, Object> map = new HashMap<>();
        Message testCollectionMessage = new Message("test", StockCollection.toString(newStockCollection));
        map.put("Body", testCollectionMessage.toJson());
        command.execute(map);
        assertEquals(newStockCollection, localStockManager.getStockCollection());
    }
}
