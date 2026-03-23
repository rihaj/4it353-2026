package server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

public class Server
{
    private static final Logger log = LoggerFactory.getLogger(Server.class);

    public static void main(String[] args)
    {
        log.info("Server started.");

        Set<Connection> connections = new HashSet<>();

        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            while (true) {
                Socket socket = serverSocket.accept();

                Connection connection = new Connection(socket, connections);
                connections.add(connection);

                Thread thread = new Thread(connection);
                thread.start();
            }
        } catch (IOException e) {
            log.error("Error occurred in network communication.", e);
        }

        log.info("Server terminated.");
    }
}
