package application;

import engine.GameEngine;
import evolution.NumChromosome;
import org.apache.logging.log4j.LogManager;

import java.io.IOException;

public class Mastermind_console {

    /*MAIN*/
    public static void main(String... args) throws IOException {
        LogManager.getLogger(Mastermind_console.class).info("");

        try{
            /*start application in console*/
            GameEngine engine = new GameEngine();
            engine.startGame(new NumChromosome());
        } catch (Exception e) {
            LogManager.getLogger(Mastermind_gui.class).error("Exception : \n                        " + e.toString());
            e.printStackTrace();
        }
    }
}
