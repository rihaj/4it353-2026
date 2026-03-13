package client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client
{
    private static final Logger log = LoggerFactory.getLogger(Client.class);

    public static void main(String[] args)
    {
        log.info("Client started.");

        // Vytvoří scanner pro čtení vstupu z konzole
        // a socket pro navázání síťového spojení se serverem
        log.debug("Creating scanner and socket.");
        try (Scanner scanner = new Scanner(System.in);
             Socket socket = new Socket("127.0.0.1", 8080)) {

            // Získá a vhodně obalí streamy pro příjem a odesílání dat, pro převod mezi byty a znaky se používá kodování UTF-8
            log.debug("Preparing streams.");
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            PrintWriter pw = new PrintWriter(socket.getOutputStream(), true, StandardCharsets.UTF_8);

            // Donekonečna načítá a odesílá zprávy zadané do konzole
            while (true) {
                // Načte zprávu z konzole
                log.debug("Waiting for user input.");
                String data = scanner.nextLine();

                // Odešle zprávu
                log.debug("Sending message: {}", data);
                pw.println(data);

                // Čeká na odpověď, kterou vypíše do konzole
                log.debug("Waiting for response.");
                System.out.println(br.readLine());
            }
        } catch (IOException e) {
            log.error("Error occurred in network communication.", e);
        }
    }
}
