package nl.rug.aoop.command;

import java.util.Map;

/**
 * Interface Command that can be executed.
 */
public interface Command {
    /**
     * Method executes the Command with given Map of Objects available to use.
     * @param options Map(String, Object) with Objects for the Command to use.
     */
    void execute(Map<String, Object> options);
}
