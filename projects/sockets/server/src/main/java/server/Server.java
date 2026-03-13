package server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;

public class Server
{
    private static final Logger log = LoggerFactory.getLogger(Server.class);

    public static void main(String[] args)
    {
        log.info("Server started.");

        try {
            // Vytvoří server-socket pro příjem požadavků o síťové spojení
            ServerSocket serverSocket = new ServerSocket(8080);

            // Aktivně čeká na příchozí požadavek o spojení (aplikace na tomto
            // řádku čeká, dokud spojení není navázáno)
            serverSocket.accept();
        } catch (IOException e) {
            log.error("Error occurred in network communication.", e);
        }
    }
}
