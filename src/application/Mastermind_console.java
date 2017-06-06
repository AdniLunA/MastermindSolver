package application;

import engine.GameEngine;
import engine.GameSettings;
import evolution.NumChromosome;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Mastermind_console {

    public static void main(String... args) {
        /*start application in console*/
        GameEngine engine = new GameEngine();

        engine.settingsSetLocNocNot(7, 13, 15);
        GameSettings.INSTANCE.setLoggingEnabled(false);
        int[] codeToSolve = {1,2,3,4,5,6,7};

        long startTime = System.currentTimeMillis();
        boolean codeSolved = engine.runGameAutomated(new NumChromosome(codeToSolve));
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
