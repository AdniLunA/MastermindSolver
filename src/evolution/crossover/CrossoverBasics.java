package evolution.crossover;

import config.MersenneTwisterFast;
import engine.GameSettings;
import evolution.IChromosome;

import java.util.Arrays;

public abstract class CrossoverBasics implements ICrossover{
    /*--
     * attributes
     */
    MersenneTwisterFast randomGenerator = new MersenneTwisterFast(System.nanoTime());
    IChromosome parent1;
    IChromosome parent2;
    int sequenceLength;
    IChromosome[] children = new IChromosome[2];
    int[] splitPos;

    /*--
     * functions
     */

    int createValidRandomSplitPos(int currentPos) {
        int pos = 0;
        boolean invalid = false;
        try {
            /**
             * calculate splitting position.
             * search if new position is duplicate.
             * if that is the case, search new position.
             */
            do {
                /*nextInt: incl. minimum, incl. maximum*/
                pos = randomGenerator.nextInt(1, sequenceLength - 2);
                invalid = false;
                for (int i = currentPos; (i > 1 && !invalid); i--) {
                    if (pos == splitPos[i]) {
                        invalid = true;
                    }
                }
            } while (invalid);
        } catch (RuntimeException r) {
            System.out.println("Crossover - createValidRandomSplitPos: - cannot resolve new position, last value: " + pos);
            r.printStackTrace();
        }

        return pos;
    }
}
