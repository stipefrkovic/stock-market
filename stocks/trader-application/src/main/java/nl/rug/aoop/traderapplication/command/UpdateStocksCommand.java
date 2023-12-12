package nl.rug.aoop.traderapplication.command;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.command.Command;
import nl.rug.aoop.core.stock.StockCollection;
import nl.rug.aoop.messagequeue.message.Message;
import nl.rug.aoop.traderapplication.interactor.LocalStockManager;

import java.util.Map;

/**
 * UpdateStocksCommand class updates the stock information in the trader application.
 */
@Slf4j
@Getter
public class UpdateStocksCommand implements Command {
    /**
     * LocalStockManager manages the stock collection.
     */
    private LocalStockManager localStockManager;

    /**
     * Constructor creates the class.
     * @param localStockManager LocalStockManager manages the stock collection.
     */
    public UpdateStocksCommand(LocalStockManager localStockManager) {
        this.localStockManager = localStockManager;
    }

    /**
     * Method deserializes the stock collection and passes it onto the stock manager.
     * @param options Map(String, Object) with Objects for the Command to use.
     */
    @Override
    public void execute(Map<String, Object> options) {
        log.info("Updating stocks.");
        try {
            Message collectionMessage = Message.fromJson((String) options.get("Body"));
            String collectionString = collectionMessage.body();
            StockCollection stockCollection = StockCollection.fromString(collectionString);
            localStockManager.updateStocks(stockCollection);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error while deserializing String into StockCollection.");
        }
    }
}
