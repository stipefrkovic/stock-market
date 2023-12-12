package nl.rug.aoop.networking;

/**
 * Interface MessageHandler that handles String messages and can pass a Communicator for a response to be sent.
 */
public interface MessageHandler {
    /**
     * Method handles a String message.
     * @param message String to be handled.
     * @param out Communicator that allows a response to be sent back.
     */
    void handleMessage(String message, Communicator out);
}
