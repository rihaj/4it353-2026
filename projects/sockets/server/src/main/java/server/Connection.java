package server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Connection implements Runnable
{
    private static final Logger log = LoggerFactory.getLogger(Connection.class);

    private Socket socket;

    public Connection(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        log.info("Handling connection: {}.", socket.toString());

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            PrintWriter pw = new PrintWriter(socket.getOutputStream(), true, StandardCharsets.UTF_8);

            while (true) {
                System.out.println(br.readLine());
                pw.println("OK");
            }
        } catch (IOException e) {
            log.error("Error occurred in network communication.", e);
        } finally {
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
