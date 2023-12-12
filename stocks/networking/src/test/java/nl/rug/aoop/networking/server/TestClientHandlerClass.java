package nl.rug.aoop.networking.server;

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

/**
 * Class TestClientHandlerClass tests the ClientHandler class.
 */
@Slf4j
public class TestClientHandlerClass {
    /**
     * int of Port on which the Server is created.
     */
    private int serverPort;
    /**
     * boolean that shows if the Server has been started or not.
     */
    private boolean serverStarted = false;
    /**
     * ClientHandler that is used to test the ClientHandler class.
     */
    private ClientHandler clientHandler;
    /**
     * Socket of the ClientHandler.
     */
    private Socket clientHandlerSocket = null;
    /**
     * MessageHandler for ClientHandler that handles incoming Messages.
     */
    private MessageHandler mockMessageHandler;
    /**
     * Socket of the Client.
     */
    private Socket clientSocket;
    /**
     * PrintWriter that wraps the Client's Socket's outputStream.
     */
    private PrintWriter clientOut;
    /**
     * BufferedReader that wraps the Client's Socket's inputStream.
     */
    private BufferedReader clientIn;
    /**
     * int value of timeout in seconds.
     */
    private final int TIMEOUT = 1;

    /**
     * Method starts the impromptu Server in a Thread.
     */
    private void startServer() {
        new Thread(() ->{
            try (ServerSocket socket = new ServerSocket(0)) {
                serverPort = socket.getLocalPort();
                serverStarted = true;
                clientHandlerSocket = socket.accept();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
        await().atMost(TIMEOUT, TimeUnit.SECONDS).until(() -> serverStarted);
    }

    /**
     * Method starts the impromptu Client.
     * @throws IOException Thrown if Client's Socket failed to connect.
     */
    private void startClient() throws IOException {
        clientSocket = new Socket();
        clientSocket.connect(new InetSocketAddress("localhost", serverPort));
        await().atMost(TIMEOUT, TimeUnit.SECONDS).until(() -> clientHandlerSocket != null);
        clientOut = new PrintWriter(clientSocket.getOutputStream(), true);
        clientIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    /**
     * Method starts the ClientHandler in a Thread.
     * @throws IOException Thrown if ClientHandler's socket failed to connect.
     */
    private void startClientHandler() throws IOException {
        mockMessageHandler = mock(MessageHandler.class);
        clientHandler = new ClientHandler(clientHandlerSocket, mockMessageHandler);
        new Thread(clientHandler).start();
        await().atMost(TIMEOUT, TimeUnit.SECONDS).until(clientHandler::isRunning);
    }

    /**
     * Method starts the impromptu Server, impromptu Client, and ClientHandler.
     * @throws IOException Thrown if Socket failed to connect.
     */
    public void startNetwork() throws IOException {
        startServer();
        startClient();
        startClientHandler();
    }

    /**
     * Method tests the constructor of Client with valid parameters.
     * @throws IOException Thrown if Socket failed to connect.
     */
    @Test
    void testLegalConstructor() throws IOException {
        startNetwork();

        assertTrue(clientHandler.isConnected());
    }

    /**
     * Method tests the constructor of Client with illegal parameters.
     */
    @Test
    void testIllegalConstructor() {
        assertThrows(NullPointerException.class, () -> clientHandler = new ClientHandler(null, mock(MessageHandler.class)));
        assertThrows(NullPointerException.class, () -> clientHandler = new ClientHandler(mock(Socket.class), null));
    }

    /**
     * Method tests if the ClientHandler can send a legal message.
     * @throws IOException Thrown if Socket fails to connect or IO fails.
     */
    @Test
    public void testLegalSendMessage() throws IOException {
        startNetwork();

        String message = "Legal message";
        clientHandler.sendMessage(message);
        assertEquals(message, clientIn.readLine());
    }

    /**
     * Method tests if the ClientHandler will throw an appropriate exception if an illegal message is sent.
     * @throws IOException Thrown if Socket failed to connect.
     */
    @Test
    public void testIllegalSendMessage() throws IOException {
        startNetwork();

        Exception e1 = assertThrows(IllegalArgumentException.class, () -> clientHandler.sendMessage(null));
        Exception e2 = assertThrows(IllegalArgumentException.class, () -> clientHandler.sendMessage(""));
    }

    /**
     * Method tests if the ClientHandler terminates.
     * @throws IOException Thrown if Socket failed to connect.
     */
    @Test
    public void testTerminate() throws IOException {
        startNetwork();

        clientHandler.terminate();
        assertTrue(clientHandler.getSocket().isClosed());
    }
}
