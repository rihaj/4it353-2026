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

        try (Scanner scanner = new Scanner(System.in);
             Socket socket = new Socket("127.0.0.1", 8080)) {

            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            PrintWriter pw = new PrintWriter(socket.getOutputStream(), true, StandardCharsets.UTF_8);

            Thread listenerThread = new Thread(() -> {
                try {
                    while (true) {
                        System.out.println(br.readLine());
                    }
                } catch (IOException e) {
                    log.error("Error occurred in network communication.", e);
                }
            }, "Listener");

            listenerThread.start();

            while (true) {
                pw.println(scanner.nextLine());
            }
        } catch (IOException e) {
            log.error("Error occurred in network communication.", e);
        }

        log.info("Client terminated.");
    }
}
