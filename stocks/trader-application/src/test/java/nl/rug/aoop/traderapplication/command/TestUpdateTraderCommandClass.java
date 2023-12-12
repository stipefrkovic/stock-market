package nl.rug.aoop.traderapplication.command;

import com.fasterxml.jackson.core.JsonProcessingException;
import nl.rug.aoop.core.trader.Trader;
import nl.rug.aoop.messagequeue.message.Message;
import nl.rug.aoop.traderapplication.interactor.LocalTrader;
import nl.rug.aoop.traderapplication.interactor.LocalTraderManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * TestUpdateTraderCommandClass tests the UpdateTraderCommand class.
 */
public class TestUpdateTraderCommandClass {
    /**
     * Command to be tested.
     */
    private UpdateTraderCommand command;
    /**
     * LocalTraderManager used in the command.
     */
    private LocalTraderManager localTraderManager;
    /**
     * Trader id used for testing.
     */
    private String testTraderId;

    /**
     * Method creates the command before each test.
     */
    @BeforeEach
    void createCommand() {
        testTraderId = "Niels";
        Map<String, LocalTrader> map = new HashMap<>();
        Trader testTrader = new Trader(testTraderId, "Niels",
                1000000L);
        LocalTrader testLocalTrader = new LocalTrader();
        map.put(testTraderId, testLocalTrader);
        localTraderManager = new LocalTraderManager();
        command = new UpdateTraderCommand(localTraderManager);
    }

    /**
     * Method tests that the command is correctly created.
     */
    @Test
    void testConstructor() {
        assertEquals(localTraderManager, command.getLocalTraderManager());
    }

    /**
     * Method tests that the command executes correctly. Checks that the trader
     * inside local trader manager's local trader has been correctly updated.
     */
    @Test
    void testExecute() throws JsonProcessingException {
        Trader testNewTrader = new Trader(testTraderId, "Niels",
                10000000L);
        Map<String, Object> map = new HashMap<>();
        Message traderMessage = new Message(testNewTrader.getId(), Trader.toString(testNewTrader));
        map.put("Body", traderMessage.toJson());
        localTraderManager.getLocalTraders().put(testTraderId, new LocalTrader());
        command.execute(map);
        assertEquals(testNewTrader, localTraderManager.getLocalTraders().get(testTraderId).getTrader());
    }
}
