package nl.rug.aoop.networking;

/**
 * Interface Communicator that allows sending of String messages.
 */
public interface Communicator {
    /**
     * Method sends a String message.
     * @param message String to be sent.
     */
    void sendMessage(String message);

    /**
     * Method terminates the implementation.
     */
    void terminate();
}
