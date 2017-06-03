package evolution.mutation;


import config.MersenneTwisterFast;
import evolution.IChromosome;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;

public abstract class MutatorBasics implements IMutation {
    /*--
     * debugging
     */
    private final Logger logger = LogManager.getLogger(this);

    /*--
     * attributes
     */
    protected MersenneTwisterFast generator = new MersenneTwisterFast(System.nanoTime());

    /*--
     * functions
     */
    @Override
    public int[] generateTwoSplitPositions(int max) {
        //logger.info("");
        MersenneTwisterFast generator = new MersenneTwisterFast(System.nanoTime());
        int[] splitPos = new int[2];
        splitPos[0] = generator.nextInt(0, max);
        do {
            splitPos[1] = generator.nextInt(0, max);
        } while (splitPos[0] == splitPos[1]);
        Arrays.sort(splitPos);
        return splitPos;
    }
}
