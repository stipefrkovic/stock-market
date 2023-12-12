package nl.rug.aoop.networking.server;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.networking.MessageHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * Class Server that implements Runnable. It receives incoming Client connections and assigns each a ClientHandler.
 */
@Slf4j
public class Server implements Runnable{
    /**
     * ServerSocket that receives incoming connections.
     */
    private final ServerSocket serverSocket;
    /**
     * Port on which the ServerSocket is listening to.
     */
    @Getter
    private final int port;
    /**
     * MessageHandler that handles incoming Messages.
     */
    private final MessageHandler messageHandler;
    /**
     * List that holds the ClientHandlers that were created.
     */
    @Getter
    private List<ClientHandler> clientHandlers;
    /**
     * int shows the total number of Clients that connected.
     */
    @Getter
    private int numOfClients;
    /**
     * boolean shows whether the Sever is running or not.
     */
    @Getter
    private boolean running = false;
    /**
     * boolean shows whether the Sever is initialized or ot.
     */
    @Getter
    private boolean initialized = false;

    /**
     * Constructor for Server.
     * @param port Port to which the ServerSocket is bounded to.
     * @param messageHandler MessageHandler that handles incoming Messages.
     * @throws IOException Thrown if initializing the ServerSocket fails.
     */
    public Server(int port, MessageHandler messageHandler) throws IOException {
        this.serverSocket = new ServerSocket(port);
        this.port = serverSocket.getLocalPort();
        this.messageHandler = requireNonNull(messageHandler, "MessageHandler can't be null.");
        this.clientHandlers = new ArrayList<>();
        this.numOfClients = 0;
        initialized = true;
    }

    /**
     * Method starts the Server.
     */
    @Override
    public void run() {
        log.info("Server started on port: " + serverSocket.getLocalPort());
        running = true;
        while (running) {
            try {
                Socket socket = serverSocket.accept();
                log.info("Spawning thread for Client: " + numOfClients);
                ClientHandler clientHandler = new ClientHandler(socket, messageHandler);
                clientHandlers.add(clientHandler);
                new Thread(clientHandler).start();
                numOfClients++;
            } catch (IOException e) {
                log.error("An error has occurred while trying to spawn a ClientHandler.", e);
            }
        }
    }

    /**
     * Method terminates the Server.
     */
    public void terminate(){
        log.info("Attempting to terminate Server.");
        running = false;
        try {
            for (ClientHandler clientHandler: clientHandlers) {
                clientHandler.terminate();
            }
            serverSocket.close();
            log.info("Server has been terminated.");
        } catch (IOException e) {
            log.error("Cannot close ServerSocket.", e);
        }
    }

}
