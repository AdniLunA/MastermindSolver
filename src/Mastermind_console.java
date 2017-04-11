import config.ConfigurationManager;
import config.CrossoverEnum;
import de.bean900.logger.Logger;

import java.io.IOException;
import java.util.InputMismatchException;

public class Mastermind_console {
    /*--
     * debugging
     */
    private final Logger logger = Logger.getLogger(this.getClass().getName());


    /*MAIN*/
    public static void main(String... args) throws IOException {
        Logger.getLogger("Mastermind_console").info("main", "");

        int k = ConfigurationManager.INSTANCE.K_FOR_CROSS_OVER;
        int kMax = ConfigurationManager.INSTANCE.MAX_LENGTH_OF_CODE - 1;
        if (ConfigurationManager.INSTANCE.CROSSOVER_TYPE == CrossoverEnum.K_POINT && k > kMax) {
            throw new InputMismatchException("ERROR: K-Point Crossover selected in \"Configuratoin\" file. K has to be < "
                    + kMax + ". K is set to: " + k);
        }

        /*start application in console*/
    }
}
