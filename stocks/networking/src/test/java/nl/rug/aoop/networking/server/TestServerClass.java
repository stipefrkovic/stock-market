package nl.rug.aoop.networking.server;

import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.networking.MessageHandler;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

/**
 * Class TestServerClass tests the Server class.
 */
@Slf4j
public class TestServerClass {
    /**
     * int value of timeout in seconds.
     */
    private final int TIMEOUT = 1;
    /**
     * Server that is used to test the Server class.
     */
    private Server server;
    /**
     * List of clients' sockets.
     */
    private Socket clientSocket;

    /**
     * Starts Server in a thread.
     * @throws IOException IOException Thrown if Server fails to initialize.
     */
    void startServer() throws IOException {
        MessageHandler mockMessageHandler = mock(MessageHandler.class);
        server = new Server(0, mockMessageHandler);
        new Thread(server).start();
        await().atMost(TIMEOUT, TimeUnit.SECONDS).until(server::isRunning);
    }

    /**
     * Starts an impromptu Client.
     */
    void startClient() {
        try {
            clientSocket = new Socket();
            clientSocket.connect(new InetSocketAddress("localhost", server.getPort()), 1000);
            await().atMost(TIMEOUT, TimeUnit.SECONDS).until(clientSocket::isConnected);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method tests the constructor of Server with valid parameters.
     * @throws IOException Thrown if Socket failed to connect.
     */
    @Test
    void testLegalConstructor() throws IOException {
        startServer();

        assertTrue(server.isInitialized());
    }

    /**
     * Method tests the constructor of Server with illegal parameters.
     */
    @Test
    void testIllegalConstructor() {
        assertThrows(IllegalArgumentException.class, () -> server = new Server(-1, mock(MessageHandler.class)));
        assertThrows(NullPointerException.class, () -> server = new Server(0, null));
    }

    /**
     * Method tests if the Server can establish a single connection.
     * @throws IOException Thrown if Socket fails to connect.
     */
    @Test
    void testSingleConnection() throws IOException {
        startServer();
        startClient();

        assertEquals(1, server.getNumOfClients());
    }


    /**
     * Method tests if the Server terminates.
     * @throws IOException Thrown if Socket fails.
     */
    @Test
    void testTerminate() throws IOException {
        startServer();
        startClient();

        server.terminate();
        assertFalse(server.isRunning());
    }
}
