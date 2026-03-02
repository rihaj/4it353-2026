package cz.vse.java4it353.app;

import cz.vse.java4it353.decoder.Decoder;
import cz.vse.java4it353.processor.Processor;

import cz.vse.java4it353.decoder.Worker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        System.out.println("App is using Logback library.");
        log.info("App is using Logback library.");

        Worker.work();

        Decoder.decode();

        Processor.process();
    }
}
