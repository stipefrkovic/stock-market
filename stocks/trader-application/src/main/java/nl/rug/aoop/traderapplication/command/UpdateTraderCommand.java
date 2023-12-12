package nl.rug.aoop.traderapplication.command;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.command.Command;
import nl.rug.aoop.core.trader.Trader;
import nl.rug.aoop.messagequeue.message.Message;
import nl.rug.aoop.traderapplication.interactor.LocalTraderManager;

import java.util.Map;


/**
 * UpdateTraderCommand updates the local trader information in the trader application.
 */
@Slf4j
@Getter
public class UpdateTraderCommand implements Command {
    /**
     * LocalTraderManager manages the local trader.
     */
    private LocalTraderManager localTraderManager;

    /**
     * Constructor creates the class.
     * @param localTraderManager LocalTraderManager manages the local trader.
     */
    public UpdateTraderCommand(LocalTraderManager localTraderManager) {
        this.localTraderManager = localTraderManager;
    }

    /**
     * Method deserializes the trader and passes it onto the trader manager.
     * @param options Map(String, Object) with Objects for the Command to use.
     */
    @Override
    public void execute(Map<String, Object> options) {
        log.info("Updating traders.");
        try {
            Message message = Message.fromJson((String) options.get("Body"));
            Trader trader = Trader.fromString(message.body());
            localTraderManager.updateTraders(trader.getId(), trader);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error while deserializing String into TraderCollection.");
        }
    }
}
