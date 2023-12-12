package nl.rug.aoop.networking;

import nl.rug.aoop.networking.client.Client;
import nl.rug.aoop.networking.server.ClientHandler;
import nl.rug.aoop.networking.server.Server;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Integration test class for the Networking module.
 */
public class TestNetworkingModule {
    /**
     * int value of timeout in seconds.
     */
    private final int TIMEOUT = 1;
    /**
     * MessageHandler (mocked) of Server.
     */
    private MessageHandler mockServerMessageHandler;
    /**
     * Server used for testing.
     */
    private Server server;
    /**
     * MessageHandler (mocked) of Client.
     */
    private MessageHandler mockClientMessageHandler;
    /**
     * Client used for testing.
     */
    private Client client;
    /**
     * Port of Server.
     */
    private int serverPort;

    /**
     * Method starts the Server in a Thread.
     * @throws IOException Thrown if Socket fails.
     */
    private void startServer() throws IOException {
        mockServerMessageHandler = mock(MessageHandler.class);
        server = new Server(0, mockServerMessageHandler);
        serverPort = server.getPort();
        new Thread(server).start();
        await().atMost(TIMEOUT, TimeUnit.SECONDS).until(server::isRunning);
    }

    /**
     * Method starts the Client in a Thread.
     * @throws IOException Thrown if Socket fails.
     */
    private void startClient() throws IOException {
        mockClientMessageHandler = mock(MessageHandler.class);
        InetSocketAddress address = new InetSocketAddress("localhost",serverPort);
        client = new Client(address, mockClientMessageHandler);
        new Thread(client).start();
        await().atMost(TIMEOUT, TimeUnit.SECONDS).until(client::isRunning);
    }

    /**
     * Tests if the Client can send a message to the Server.
     * @throws IOException Thrown if Socket fails.
     */
    @Test
    public void testClientToServerMessage() throws IOException {
        startServer();
        startClient();

        String clientMessage = "client message";
        client.sendMessage(clientMessage);
        verify(mockServerMessageHandler).handleMessage(eq(clientMessage), any(Communicator.class));
    }
}
