package application;

import engine.GameEngine;
import evolution.NumChromosome;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Mastermind_console {

    public static void main(String... args) throws IOException {
        //LogManager.getLogger(Mastermind_console.class).info("");

        /*start application in console*/
        GameEngine engine = new GameEngine();
        long startTime = System.currentTimeMillis();
        boolean codeSolved = engine.runGameAutomated(new NumChromosome());
        if (codeSolved) {
            System.out.println("secret code found!");
        }
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        int min = (int) TimeUnit.MILLISECONDS.toMinutes(totalTime);
        int sec = (int) (TimeUnit.MILLISECONDS.toSeconds(totalTime) -
                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(totalTime)));
        System.out.printf("Runtime: %3d:%2d\n", min, sec);
    }
}
