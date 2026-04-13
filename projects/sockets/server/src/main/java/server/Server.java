package server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Server
{
    private static final Logger log = LoggerFactory.getLogger(Server.class);

    public static final Set<Connection> CONNECTIONS = new HashSet<>();
    public static final LinkedBlockingQueue<String> MESSAGES = new LinkedBlockingQueue<>();

    public static void main(String[] args)
    {
        log.info("Server started.");

        Thread sender = new Thread(() -> {
            log.info("Sender started.");

            while (true) {
                String message = null;
                try {
                    message = MESSAGES.take();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                for (Connection c : CONNECTIONS) {
                    c.sendMessage(message);
                }
            }

        }, "Sender");

        sender.start();


        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            while (true) {
                Socket socket = serverSocket.accept();

                Connection connection = new Connection(socket);
                synchronized (CONNECTIONS) {
                    CONNECTIONS.add(connection);
                }

                Thread thread = new Thread(connection);
                thread.start();
            }
        } catch (IOException e) {
            log.error("Error occurred in network communication.", e);
        }

        log.info("Server terminated.");
    }
}
