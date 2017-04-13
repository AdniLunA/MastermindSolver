package application;

import org.apache.logging.log4j.LogManager;
import presentation.ConsoleManager;
import presentation.IPresentationManager;

import java.io.IOException;

public class Mastermind_console {

    /*MAIN*/
    public static void main(String... args) throws IOException {
        LogManager.getLogger(Mastermind_console.class).info("");

        /*start application in console*/
        IPresentationManager consoleManager = new ConsoleManager();
        consoleManager.start();
    }
}
