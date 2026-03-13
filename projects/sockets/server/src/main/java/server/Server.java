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

public class Server
{
    private static final Logger log = LoggerFactory.getLogger(Server.class);

    public static void main(String[] args)
    {
        log.info("Server started.");

        // Vytvoří server-socket pro příjem požadavků o síťové spojení
        // a aktivně čeká na příchozí požadavek o spojení (aplikace zde
        // čeká, dokud spojení není navázáno)
        log.debug("Creating server-socket and waiting for the first connection.");
        try (ServerSocket serverSocket = new ServerSocket(8080);
             Socket socket = serverSocket.accept()) {

            // Získá a vhodně obalí streamy pro příjem a odesílání dat, pro převod mezi byty a znaky se používá kodování UTF-8
            log.debug("Preparing streams.");
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            PrintWriter pw = new PrintWriter(socket.getOutputStream(), true, StandardCharsets.UTF_8);

            // Čeká na zprávu, kterou vypíše do konzole
            log.debug("Waiting for message.");
            System.out.println(br.readLine());

            // Odešle odpověď
            log.debug("Sending response.");
            pw.println("The server!");
        } catch (IOException e) {
            log.error("Error occurred in network communication.", e);
        }
    }
}
