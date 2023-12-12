package nl.rug.aoop.networking.client;

import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.networking.MessageHandler;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Class TestClientClass tests the Client class.
 */
@Slf4j
public class TestClientClass {
    /**
     * int of Port on which the Server is created.
     */
    private int serverPort;
    /**
     * PrintWriter that wraps the Server's Socket's outputStream.
     */
    private PrintWriter serverOut;
    /**
     * BufferedReader that wraps the Server's Socket's inputStream.
     */
    private BufferedReader serverIn;
    /**
     * boolean that shows if the Server has been started or not.
     */
    private boolean serverStarted = false;
    /**
     * MessageHandler for Client that handles incoming Messages.
     */
    private MessageHandler mockMessageHandler;
    /**
     * Client that is used to test the Client class.
     */
    private Client client;
    /**
     * int value of timeout in seconds.
     */
    private final int TIMEOUT = 1;

    /**
     * Starts the impromptu Server in a Thread.
     */
    private void startServer() {
        new Thread(() ->{
            try (ServerSocket serverSocket = new ServerSocket(0)) {
                serverPort = serverSocket.getLocalPort();
                serverStarted = true;
                Socket serverSideSocket = serverSocket.accept();
                serverOut = new PrintWriter(serverSideSocket.getOutputStream(), true);
                serverIn = new BufferedReader(new InputStreamReader(serverSideSocket.getInputStream()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
        await().atMost(TIMEOUT, TimeUnit.SECONDS).until(() -> serverStarted);
    }

    /**
     * Starts the client in a Thread.
     * @throws IOException Thrown if Client fails to connect.
     */
    private void startClient() throws IOException {
        InetSocketAddress address = new InetSocketAddress("localhost", serverPort);
        mockMessageHandler = mock(MessageHandler.class);
        client = new Client(address, mockMessageHandler);
        new Thread(client).start();
        await().atMost(TIMEOUT, TimeUnit.SECONDS).until((client::isRunning));
    }

    /**
     * Method starts the impromptu Server and Client.
     * @throws IOException Thrown if Socket fails to connect.
     */
    public void startNetwork() throws IOException {
        startServer();
        startClient();
    }

    /**
     * Method tests the constructor of Client with legal parameters.
     * @throws IOException Thrown if Socket fails to connect.
     */
    @Test
    public void testLegalConstructor() throws IOException {
        startNetwork();

        assertTrue(client.isConnected());
    }

    /**
     * Method tests the constructor of Client with illegal parameters.
     */
    @Test
    public void testIllegalConstructor() {
        assertThrows(NullPointerException.class, () -> client = new Client(null, mock(MessageHandler.class)));
        assertThrows(NullPointerException.class, () -> client = new Client(mock(InetSocketAddress.class), null));
    }

    /**
     * Method tests if the Client can send a legal message.
     * @throws IOException Thrown if Socket IO fails.
     */
    @Test
    public void testLegalSendMessage() throws IOException {
        startNetwork();

        String message = "message";
        client.sendMessage(message);
        assertEquals(message, serverIn.readLine());
    }

    /**
     * Method tests if the Client will throw an appropriate exception if an illegal message is sent.
     * @throws IOException Thrown if Socket fails to connect.
     */
    @Test
    public void testIllegalSendMessage() throws IOException {
        startNetwork();

        Exception e1 = assertThrows(IllegalArgumentException.class, () -> client.sendMessage(null));
        Exception e2 = assertThrows(IllegalArgumentException.class, () -> client.sendMessage(""));
    }

    /**
     * Method tests if the Client terminates.
     * @throws IOException Thrown if Socket fails to connect.
     */
    @Test
    public void testTerminate() throws IOException {
        startNetwork();

        client.terminate();
        assertTrue(client.getSocket().isClosed());
    }
}
