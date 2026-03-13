package client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client
{
    private static final Logger log = LoggerFactory.getLogger(Client.class);

    public static void main(String[] args)
    {
        log.info("Client started.");

        try {
            // Vytvoří socket a pokusí se navázat síťové spojení se serverem
            Socket socket = new Socket("127.0.0.1", 8080);
        } catch (UnknownHostException e) {
            log.error("Error occurred while connecting to server.", e);
        } catch (IOException e) {
            log.error("Error occurred in network communication.", e);
        }
    }
}
