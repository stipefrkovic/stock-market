package nl.rug.aoop.command;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Class TestCommandHandlerClass tests the CommandHandler class.
 */
public class TestCommandHandlerClass {

    /**
     * Map that will be passed to CommandHandler to be used as commandMap.
     */
    Map<String, Command> map;
    /**
     * CommandHandler that will be used to test.
     */
    CommandHandler commandHandler;

    /**
     * Method initializes the CommandHandler with Map.
     */
    public void createCommandHandler() {
        commandHandler = new CommandHandler();
    }

    /**
     * Method tests if with correct parameters the CommandHandler is created.
     */
    @Test
    void testConstructor() {
        createCommandHandler();

        CommandHandler commandHandler = new CommandHandler();
        assertNotNull(commandHandler);
    }

    /**
     * Method tests if method getNumOfCommands works correctly on an empty Map.
     */
    @Test
    void testNumOfCommands() {
        createCommandHandler();

        assertEquals(0, commandHandler.getNumOfCommands());
    }

    /**
     * Method tests if method registerCommand adds a Command to the Map.
     */
    @Test
    void testLegalRegisterCommand() {
        createCommandHandler();

        assertEquals(0, commandHandler.getNumOfCommands());
        Command mockCommand = mock(Command.class);
        commandHandler.registerCommand("mockCommand", mockCommand);
        assertEquals(1, commandHandler.getNumOfCommands());
    }

    /**
     * Method tests if method registerCommand throws a NullPointerException when a null is given as Command.
     */
    @Test
    void testNullRegisterCommand() {
        createCommandHandler();

        Exception e1 = assertThrows(NullPointerException.class, () -> commandHandler.registerCommand("mockCommand", null));
        Exception e2 = assertThrows(NullPointerException.class, () -> commandHandler.registerCommand(null, mock(Command.class)));
    }

    /**
     * Method tests if method executeCommand executes a Command in the Map.
     */
    @Test
    void testLegalExecuteCommand() {
        createCommandHandler();

        Command mockCommand = mock(Command.class);
        commandHandler.registerCommand("mockCommand", mockCommand);
        Map<String, Object> options = new HashMap<>();
        commandHandler.executeCommand("mockCommand", options);
        verify(mockCommand).execute(options);
    }

    /**
     * Method tests if method executeCommand throws a NullPointerException when a null is given as Map key.
     */
    @Test
    void testNullExecuteCommand() {
        createCommandHandler();

        Command mockCommand = mock(Command.class);
        commandHandler.registerCommand("mockCommand", mockCommand);
        Map<String, Object> options = new HashMap<>();
        Exception e1 = assertThrows(NullPointerException.class, () -> commandHandler.executeCommand(null, options));
    }

    /**
     * Method tests if method executeCommand throws an IllegalArgumentException
     * when a key for a Command that is not in the Map is given.
     */
    @Test
    void testIllegalExecuteCommand() {
        createCommandHandler();

        Command mockCommand = mock(Command.class);
        commandHandler.registerCommand("mockCommand", mockCommand);
        Map<String, Object> options = new HashMap<>();
        Exception e1 = assertThrows(IllegalArgumentException.class, () -> commandHandler.executeCommand("not mockCommand", options));
    }
}
