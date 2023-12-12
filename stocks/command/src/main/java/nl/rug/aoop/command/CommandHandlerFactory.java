package nl.rug.aoop.command;

/**
 * CommandHandlerFactory Interace allows for the creation of command handler factories.
 */
public interface CommandHandlerFactory {
    /**
     * Method creates the appropriate command handler based on specified type.
     * @param type Command handler type.
     * @return New command handler.
     */
    CommandHandler create(String type) throws IllegalArgumentException;
}
