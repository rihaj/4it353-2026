package cz.vse.java4it353.decoder;

import java.util.logging.Logger;

public class Decoder
{
    private static final Logger log = Logger.getLogger(Decoder.class.getName());

    public static void decode() {
        System.out.println("Decoder is using JUL (java.utils.logging) package.");
        log.info("Decoder is using JUL (java.utils.logging) package.");
    }
}
