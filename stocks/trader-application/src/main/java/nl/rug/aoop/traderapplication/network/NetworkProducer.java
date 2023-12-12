package nl.rug.aoop.traderapplication.network;

import nl.rug.aoop.messagequeue.message.Message;
import nl.rug.aoop.messagequeue.message.NetworkMessage;
import nl.rug.aoop.messagequeue.process.MQProducer;
import nl.rug.aoop.networking.client.Client;

import static java.util.Objects.requireNonNull;

/**
 * Class NetworkProducer that implements MQProducer. It calls the given Client to send a NetworkMessage that contains
 * a Message and String header "MqPut" that signals that the Message should put into the queue.
 */
public class NetworkProducer implements MQProducer {
    /**
     * Client that can send a Message.
     */
    private final Client client;

    /**
     * Constructor for NetworkProducer that takes in a Client.
     * @param client Client that can send a Message.
     */
    public NetworkProducer(Client client) {
        this.client = requireNonNull(client, "Client can't be null.");
    }

    /**
     * Method sends a NetworkMessage in JSON string form with header 'MqPut'
     * (command to put Message in MQ) to the client.
     * @param message The Message to be sent through a Client.
     */
    @Override
    public void put(Message message) {
        NetworkMessage networkMessage = new NetworkMessage("mqPut", message.toJson());
        client.sendMessage(networkMessage.toJson());
    }

}
