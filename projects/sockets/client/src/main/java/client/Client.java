package client;

import api.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
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

            // Získá a vhodně obalí streamy pro příjem a odesílání serializovaných objektů
            log.debug("Preparing streams.");
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

            // Příznak, zda se budou dále načítat a odesílat zprávy
            boolean keepAlive = true;

            while (keepAlive) {
                // Načte zprávu z konzole
                log.debug("Waiting for user input.");
                String data = scanner.nextLine();

                // Pokud klient zadal QUIT, jedná se o poslední zprávu
                if ("Q".equals(data) || "QUIT".equals(data)) {
                    log.info("Terminating communication.");
                    keepAlive = false;
                }

                // Odešle zprávu
                log.debug("Serializing and sending message: {}", data);
                Message message = new Message(data);
                oos.writeObject(message);

                // Čeká na odpověď, kterou vypíše do konzole
                log.debug("Waiting for response.");

                Message response = (Message) ois.readObject();
                System.out.println(response.getTimestamp() + ": " + response.getBody());
            }
        } catch (IOException e) {
            log.error("Error occurred in network communication.", e);
        } catch (ClassNotFoundException e) {
            log.error("Error occurred while deserializing incoming message.", e);
        }

        log.info("Client stopped.");
    }
}
