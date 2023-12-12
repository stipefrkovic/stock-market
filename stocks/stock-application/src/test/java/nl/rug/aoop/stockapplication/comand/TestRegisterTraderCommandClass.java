package nl.rug.aoop.stockapplication.comand;

import nl.rug.aoop.networking.Communicator;
import nl.rug.aoop.stockapplication.command.RegisterTraderCommand;
import nl.rug.aoop.stockapplication.stock.PeriodicUpdater;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Class TestRegisterTraderCommandClass tests the RegisterTraderCommand class.
 */
public class TestRegisterTraderCommandClass {

    /**
     * Tests that the RegisterTraderCommand constructor creates a non-null RegisterTraderCommand.
     */
    @Test
    void testConstructor() {
        RegisterTraderCommand registerTraderCommand = new RegisterTraderCommand(mock(PeriodicUpdater.class));
        assertNotNull(registerTraderCommand);
    }

    /**
     * Tests that the RegisterTraderCommand constructor throws an exception when passing a null argument.
     */
    @Test
    void testNullArgConstructor() {
        assertThrows(NullPointerException.class, () -> new RegisterTraderCommand(null));
    }

    /**
     * Tests that the RegisterTraderCommand execute method correctly calls the registerTrader method in PeriodicUpdater.
     */
    @Test
    void testExecute() {
        String headerString = "headerString";
        String bodyString = "bodyString";
        Communicator mockCommunicator = mock(Communicator.class);
        Map<String, Object> options = Map.of(
                "Header", headerString,
                "Body", bodyString,
                "Communicator", mockCommunicator
        );
        
        PeriodicUpdater mockPeriodicUpdater = mock(PeriodicUpdater.class);
        RegisterTraderCommand registerTraderCommand = new RegisterTraderCommand(mockPeriodicUpdater);
        registerTraderCommand.execute(options);
        verify(mockPeriodicUpdater).registerTrader(bodyString, mockCommunicator);
    }
}
