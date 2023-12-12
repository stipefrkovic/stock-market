package nl.rug.aoop.stockapplication.network;

import nl.rug.aoop.command.CommandHandler;
import nl.rug.aoop.messagequeue.message.NetworkMessage;
import nl.rug.aoop.networking.Communicator;
import nl.rug.aoop.networking.MessageHandler;

import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.requireNonNull;

/**
 * ServerMessageHandler handles all the incoming messages. Implements the MessageHandler Interface.
 */
public class ServerMessageHandler implements MessageHandler {
    /**
     * CommandHandler which will execute the commands handled by the ServerMessageHandler.
     */
    private final CommandHandler serverCommandHandler;

    /**
     * Constructor creates the ServerMessageHandler.
     * @param serverCommandHandler CommandHandler.
     */
    public ServerMessageHandler(CommandHandler serverCommandHandler) {
        this.serverCommandHandler = requireNonNull(serverCommandHandler, "Constructor parameter cannot be null.");
    }

    /**
     * Handles the incoming message by calling upon the CommandHandler to execute a command.
     * @param jsonMessage String to be handled.
     * @param out Communicator that allows a response to be sent back.
     */
    @Override
    public void handleMessage(String jsonMessage, Communicator out) {
        NetworkMessage networkMessage = NetworkMessage.fromJson(jsonMessage);
        Map<String, Object> map = new HashMap<>();
        map.put("Header", networkMessage.header());
        map.put("Body", networkMessage.body());
        map.put("Communicator", out);
        serverCommandHandler.executeCommand(networkMessage.header(), map);
    }
}
