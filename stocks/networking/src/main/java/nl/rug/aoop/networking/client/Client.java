package nl.rug.aoop.networking.client;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.networking.Communicator;
import nl.rug.aoop.networking.MessageHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

import static java.util.Objects.requireNonNull;

/**
 * Class Client that implements Runnable and Communicator. It communicates to the Server, or rather, it's ClientHandler.
 */
@Slf4j
public class Client implements Runnable, Communicator {
    /**
     * Socket through which the Client communicates.
     */
    @Getter
    private Socket socket;
    /**
     * PrintWriter that wraps the Client's Socket's outputStream.
     */
    private PrintWriter out;
    /**
     * BufferedReader that wraps the Client's Socket's inputStream.
     */
    private BufferedReader in;
    /**
     * MessageHandler that handles incoming Messages.
     */
    private final MessageHandler messageHandler;
    /**
     * int value for the timeout when connecting a Socket.
     */
    private final int TIMEOUT = 10000;
    /**
     * boolean that shows if the Client is running or not.
     */
    @Getter
    private boolean running = false;
    /**
     * boolean that shows if the client's Socket is connected or not.
     */
    @Getter
    private boolean connected = false;

    /**
     * Constructor for Client.
     * @param address InetSocketAddress for the Socket to connect to.
     * @param messageHandler MessageHandler that handles incoming Messages.
     * @throws IOException Thrown if initializing the Socket fails.
     */
    public Client(InetSocketAddress address, MessageHandler messageHandler) throws IOException {
        this.messageHandler = requireNonNull(messageHandler, "MessageHandler can't be null.");
        initSocket(requireNonNull(address, "Address can't be null."));
    }

    /**
     * Connects the Socket based on the given InetSocketAddress.
     * @param address InetSocketAddress for the Socket to connect to.
     * @throws IOException Thrown if connecting to the InetSocketAddress fails.
     */
    public void initSocket(InetSocketAddress address) throws IOException {
        socket = new Socket();
        socket.connect(address, TIMEOUT);

        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

        if (!socket.isConnected()) {
            throw new IOException("Socket not connected.");
        }
        connected = true;
        //log.info("Client connected to server.");
    }

    /**
     * Method sends a String message to the Server.
     * @param message String to be sent.
     * @throws IllegalArgumentException Thrown if the message to be sent is invalid.
     */
    @Override
    public void sendMessage(String message) throws IllegalArgumentException{
        if (message == null || message.equals("")) {
            throw new IllegalArgumentException("Attempting to send an invalid message.");
        }
        out.println(message);
    }

    /**
     * Method starts the Client.
     */
    @Override
    public void run() {
        running = true;
        while (running) {
            try {
                String serverMessage = in.readLine();
                if (serverMessage == null || serverMessage.equals("")) {
                    terminate();
                    break;
                }
                messageHandler.handleMessage(serverMessage, this);
            } catch (IOException e) {
                log.error("Error while communicating with the server.", e);
            }
        }
    }

    /**
     * Method terminates the Client.
     */
    @Override
    public void terminate() {
        log.info("Attempting to terminate Client.");
        running = false;
        try {
            socket.close();
            log.info("Client has been terminated.");
        } catch (IOException e) {
            log.error("Cannot close Socket.", e);
        }
    }
}
