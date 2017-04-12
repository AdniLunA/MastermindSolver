package application;

import config.ConfigurationManager;
import config.CrossoverEnum;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.InputMismatchException;

public class Mastermind_console {
    /*--
     * debugging
     */
    private final Logger logger = LogManager.getLogger(this);


    /*MAIN*/
    public static void main(String... args) throws IOException {
        LogManager.getLogger(Mastermind_console.class).info("");

        int k = ConfigurationManager.INSTANCE.K_FOR_CROSS_OVER;
        int kMax = ConfigurationManager.INSTANCE.MAX_LENGTH_OF_CODE - 1;
        if (ConfigurationManager.INSTANCE.CROSSOVER_TYPE == CrossoverEnum.K_POINT && k > kMax) {
            throw new InputMismatchException("ERROR: K-Point Crossover selected in \"Configuratoin\" file. K has to be < "
                    + kMax + ". K is set to: " + k);
        }

        /*start application in console*/
    }
}
