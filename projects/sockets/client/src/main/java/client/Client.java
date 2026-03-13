package client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Client
{
    private static final Logger log = LoggerFactory.getLogger(Client.class);

    public static void main(String[] args)
    {
        log.info("Client started.");

        Socket socket = null;
        try {
            // Vytvoří socket a pokusí se navázat síťové spojení se serverem
            log.debug("Creating socket.");
            socket = new Socket("127.0.0.1", 8080);

            // Získá a vhodně obalí streamy pro příjem a odesílání dat, pro převod mezi byty a znaky se používá kodování UTF-8
            log.debug("Preparing streams.");
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            PrintWriter pw = new PrintWriter(socket.getOutputStream(), true, StandardCharsets.UTF_8);

            // Odešle zprávu
            log.debug("Sending message.");
            pw.println("Who's there?");

            // Čeká na odpověď, kterou vypíše do konzole
            log.debug("Waiting for response.");
            System.out.println(br.readLine());
        } catch (IOException e) {
            log.error("Error occurred in network communication.", e);
        } finally {
            if (socket == null) {
                log.debug("Socket was not created, no need to close it.");
            } else {
                try {
                    // Pokud bylo spojení navázáno, pokusí se ho uzavřít
                    log.debug("Closing socket.");
                    socket.close();
                } catch (IOException ee) {
                    // I při uzavírání spojení může samozřejmě dojít k výjimce
                    log.debug("Error occurred while closing socket can be ignored.", ee);
                }
            }
        }
    }
}
