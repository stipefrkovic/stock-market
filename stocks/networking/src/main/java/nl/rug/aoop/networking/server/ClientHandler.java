package nl.rug.aoop.networking.server;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.networking.Communicator;
import nl.rug.aoop.networking.MessageHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Class ClientHandler that implements Runnable and Communicator and that handles a single Client.
 */
@Slf4j
public class ClientHandler implements Runnable, Communicator {
    /**
     * Socket through which the ClientHandler communicates with the Client.
     */
    @Getter
    private final Socket socket;
    /**
     * PrintWriter that wraps the Socket's outputStream.
     */
    private final PrintWriter out;
    /**
     * BufferedReader that wraps the Socket's inputStream.
     */
    private final BufferedReader in;
    /**
     * MessageHandler that handles incoming Messages.
     */
    @Getter
    private final MessageHandler messageHandler;
    /**
     * boolean that shows if the ClientHandler is running or not.
     */
    @Getter
    private boolean running = false;
    /**
     * boolean that shows if the ClientHandler is connected or not.
     */
    @Getter
    private boolean connected = false;

    /**
     * Constructor for ClientHandler.
     *
     * @param socket         Socket through which the ClientHandler communicates with the Client.
     * @param messageHandler MessageHandler that handles incoming Messages.
     * @throws IOException Thrown if connecting the Socket fails.
     */
    public ClientHandler(Socket socket, MessageHandler messageHandler) throws IOException {
        this.socket = socket;
        this.messageHandler = messageHandler;
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        connected = true;
    }

    /**
     * Method starts the ClientHandler.
     */
    @Override
    public void run() {
        running = true;
        while (running) {
            try {
                String clientMessage = in.readLine();
                if (clientMessage == null || clientMessage.equals("")) {
                    terminate();
                    break;
                }
                //log.info("Handling message: " + clientMessage);
                messageHandler.handleMessage(clientMessage, this);
            } catch (IOException e) {
                log.error("Anq error has occurred while trying to accept a client connection.", e);
            }
        }
    }

    /**
     * Method sends a String message to the Client.
     *
     * @param message String to be sent.
     * @throws IllegalArgumentException Thrown if message to be sent is invalid.
     */
    @Override
    public void sendMessage(String message) throws IllegalArgumentException {
        if (message == null || message.equals("")) {
            throw new IllegalArgumentException("Attempting to send an invalid message.");
        }
        out.println(message);
    }

    /**
     * Method terminates the ClientHandler.
     */
    @Override
    public void terminate() {
        log.info("Attempting to terminate ClientHandler.");
        running = false;
        try {
            socket.close();
            log.info("ClientHandler has been terminated.");
        } catch (IOException e) {
            log.error("Cannot close the Socket.", e);
        }
    }
}
