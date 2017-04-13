package application;

import org.apache.logging.log4j.LogManager;

import java.io.IOException;

public class Mastermind_gui {

    /*MAIN*/
    public static void main(String... args) throws IOException {
        LogManager.getLogger(Mastermind_gui.class).info("");

        InitializeGui initializer = new InitializeGui();
        /*start application in gui*/
        initializer.launch(args);
    }
}
