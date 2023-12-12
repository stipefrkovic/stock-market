package nl.rug.aoop.command;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.requireNonNull;

/**
 * Class CommandHandler stores the Commands in a map and executes them when called.
 */
@Slf4j
public class CommandHandler {
    /**
     * Map(String, Command) of Commands the CommandHandler should handle.
     */
    private final Map<String, Command> commandMap;

    /**
     * Constructor creates the command handler class.
     */
    public CommandHandler() {
        this.commandMap = new HashMap<>();
    }

    /**
     * Executes a command.
     * @param command Command string.
     * @param options Options map passed into the command.
     */
    public void executeCommand(String command, Map<String, Object> options) throws IllegalArgumentException {
        if (commandMap.containsKey(requireNonNull(command, "Map key cannot be null."))) {
            commandMap.get(command).execute(options);
        } else {
            throw new IllegalArgumentException("Command not found.");
        }
    }

    /**
     * Method registers a Command by adding it to the CommandHandler's Map commandMap.
     * @param name String of the Command name to be used as a key for the Map commandMap.
     * @param command Command to be used as the value for the Map commandMap.
     */
    public void registerCommand(String name, Command command) {
        commandMap.put(requireNonNull(name, "Command key cannot be null"),
                requireNonNull(command, "Command value cannot be null"));
    }

    /**
     * Method retrieves the number of Commands in CommandHandler's Map commandMap.
     * @return int with number of Commands in Map commandMap.
     */
    public int getNumOfCommands() {
        return commandMap.size();
    }
}
