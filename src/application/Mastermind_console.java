package application;

import engine.GameEngine;
import evolution.NumChromosome;
import org.apache.logging.log4j.LogManager;

import java.io.IOException;

public class Mastermind_console {

    public static void main(String... args) throws IOException {
        //LogManager.getLogger(Mastermind_console.class).info("");

        /*start application in console*/
        GameEngine engine = new GameEngine();
        boolean codeSolved = engine.runGameAutomated(new NumChromosome());
        if(codeSolved){
            System.out.println("secret code found!");
        }
    }
}
