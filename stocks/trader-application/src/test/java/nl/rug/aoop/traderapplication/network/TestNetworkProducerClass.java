package nl.rug.aoop.traderapplication.network;

import nl.rug.aoop.messagequeue.message.Message;
import nl.rug.aoop.messagequeue.message.NetworkMessage;
import nl.rug.aoop.networking.client.Client;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

/**
 * Class TestNetworkProducerClass tests the NetworkProducer class.
 */
public class TestNetworkProducerClass {
    /**
     * The client used to create the producer.
     */
    private Client client;

    /**
     * The NetworkProducer being tested.
     */
    private NetworkProducer producer;

    /**
     * Creates the NetworkProducer.
     */
    void createNetworkProducer() {
        this.client = mock(Client.class);
        this.producer = new NetworkProducer(client);
    }

    /**
     * Tests that the NetworkProducer constructor functions correctly.
     */
    @Test
    void testLegalConstructor() {
        createNetworkProducer();

        assertNotNull(producer);
    }

    /**
     * Tests that the NetworkProducer throws an appropriate exception with illegal parameters.
     */
    @Test
    void testIllegalConstructor() {
        assertThrows(NullPointerException.class, () -> producer = new NetworkProducer(null));
    }

    /**
     * Tests that the NetworkProducer forms a NetworkMessage with header 'MqPut' and sends to the client as Json String.
     */
    @Test
    void testPut() {
        createNetworkProducer();

        Message message = new Message("Hello", "Simple message");
        producer.put(message);
        NetworkMessage networkMessage = new NetworkMessage("mqPut", message.toJson());
        Mockito.verify(client).sendMessage(networkMessage.toJson());
    }

}
