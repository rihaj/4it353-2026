package server;

import api.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

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

            // Získá a vhodně obalí streamy pro příjem a odesílání serializovaných objektů
            log.debug("Preparing streams.");
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

            // Příznak, zda se budou dále přijímat a zpracovávat zprávy
            boolean keepAlive = true;

            // Aktuálně nastavené jméno uživatele
            String username = null;

            while (keepAlive) {
                // Zpracuje příchozí zprávu (rozdělí řídící slovo a případný obsah)
                log.debug("Waiting for message.");
                Message m = (Message) ois.readObject();

                String[] parts = m.getBody().split(" ", 2);
                String command = parts[0];
                String message = parts.length > 1 ? parts[1] : null;

                if ("Q".equals(command) || "QUIT".equals(command))
                {
                    // Pokud klient poslal QUIT, jedná se o poslední zprávu
                    log.info("Terminating communication.");
                    keepAlive = false;
                    oos.writeObject(new Message("OK"));
                }
                else if ("U".equals(command) || "USER".equals(command))
                {
                    // Pokud klient poslal USER, nastavíme jméno (nesmí ale být prázdné)
                    if (message == null || message.isBlank()) {
                        oos.writeObject(new Message("ERR Empty username."));
                    } else {
                        username = message;
                        oos.writeObject(new Message("OK"));
                    }
                }
                else if ("M".equals(command) || "MESSAGE".equals(command))
                {
                    // Pokud klient poslal MESSAGE, vypíšeme zprávu na konzoli (musí ale být nastavené jméno)
                    if (username == null) {
                        oos.writeObject(new Message("ERR Username not set."));
                    } else {
                        System.out.println(m.getTimestamp() + " " + username + ": " + message);
                        oos.writeObject(new Message("OK"));
                    }
                }
                else
                {
                    // Pokud klient poslal něco jiného, neumíme to zpracovat
                    log.warn("Received unprocessable message: {} {}", command, message);
                    oos.writeObject(new Message("ERR Unknown command."));
                }
            }
        } catch (IOException e) {
            log.error("Error occurred in network communication.", e);
        } catch (ClassNotFoundException e) {
            log.error("Error occurred while deserializing incoming message.", e);
        }

        log.info("Server stopped.");
    }
}
