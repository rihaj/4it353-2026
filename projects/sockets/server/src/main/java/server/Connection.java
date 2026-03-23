package server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

public class Connection implements Runnable
{
    private static final Logger log = LoggerFactory.getLogger(Connection.class);

    private Socket socket;
    private Set<Connection> connections;

    private BufferedReader br;
    private PrintWriter pw;

    public Connection(Socket socket, Set<Connection> connections) {
        this.socket = socket;
        this.connections = connections;

        try {
            br = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            pw = new PrintWriter(socket.getOutputStream(), true, StandardCharsets.UTF_8);
        } catch (IOException e) {
            try {
                socket.close();
            } catch (IOException ex) {
                log.error("Error occurred while initializing connection.", e);
            }
        }
    }

    public void sendMessage(String message) {
        pw.println(message);
    }

    @Override
    public void run() {
        log.info("Handling connection: {}.", socket.toString());

        try {
            while (true) {
                String message = br.readLine();

                System.out.println(message);

                for (Connection c : connections) {
                    c.sendMessage(message);
                }
            }
        } catch (IOException e) {
            log.error("Error occurred in network communication.", e);
        } finally {
            connections.remove(this);
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException ee) {
                    log.debug("Error occurred while closing socket.", ee);
                }
            }
        }

        log.info("Handling terminated: {}.", socket);
    }
}
