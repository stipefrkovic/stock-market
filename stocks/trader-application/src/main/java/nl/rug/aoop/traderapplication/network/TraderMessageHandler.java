package nl.rug.aoop.traderapplication.network;

import nl.rug.aoop.command.CommandHandler;
import nl.rug.aoop.messagequeue.message.NetworkMessage;
import nl.rug.aoop.networking.Communicator;
import nl.rug.aoop.networking.MessageHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * TraderMessageHandler class handles messages from the trader application client.
 */
public class TraderMessageHandler implements MessageHandler {
    /**
     * Command handler used to execute specific commands.
     */
    private final CommandHandler commandHandler;

    /**
     * Constructor creates the class.
     * @param commandHandler Command handler used to execute specific commands.
     */
    public TraderMessageHandler(CommandHandler commandHandler) {
        this.commandHandler = commandHandler;
    }

    /**
     * Handles the message. Creates a map which is passed to the command handler to execute
     * the appropriate command.
     * @param jsonMessage String to be handled.
     * @param out Communicator that allows a response to be sent back.
     */
    @Override
    public void handleMessage(String jsonMessage, Communicator out) {
        NetworkMessage networkMessage = NetworkMessage.fromJson(jsonMessage);
        Map<String, Object> map = new HashMap<>();
        map.put("Header", networkMessage.header());
        map.put("Body", networkMessage.body());
        map.put("Interface", out);
        commandHandler.executeCommand(networkMessage.header(), map);
    }
}
