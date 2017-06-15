package evolution.mutation;


import config.LoggerGenerator;
import config.MersenneTwisterFast;
import engine.GameSettings;
import evolution.IChromosome;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class MutatorBasics implements IMutation {
    /*--
     * debugging
     */
    private final Logger logger = LoggerGenerator.mutatorBasics;

    /*--
     * attributes
     */
    MersenneTwisterFast generator = new MersenneTwisterFast(System.nanoTime());

    /*--
     * functions
     */
    ArrayList<IChromosome> getGenesToMutate(ArrayList<IChromosome> genePool) {
        ArrayList<IChromosome> genesToMutate = new ArrayList<>();
        for (IChromosome chromosomeToMutate : genePool) {
            /*test if current chromosomeCount should be manipulated*/
            if (generator.nextFloat() <= GameSettings.INSTANCE.mutationRatio) {
                if (chromosomeToMutate.getSickness() != 0) {
                    genesToMutate.add(chromosomeToMutate);
                }
            }
        }
        return genesToMutate;
    }

    int[] generateTwoPositions(int max) {
        if (max == 2) {
            return new int[]{0, 1};
        }
        MersenneTwisterFast generator = new MersenneTwisterFast(System.nanoTime());
        int[] splitPos = new int[2];
        splitPos[0] = generator.nextInt(0, max);
        do {
            splitPos[1] = generator.nextInt(0, max);
        } while (splitPos[0] == splitPos[1]);
        Arrays.sort(splitPos);
        return splitPos;
    }

    int[] generateTwoDistancedPos(int max, int minDistance) {
        if (minDistance > max / 2) {
            minDistance = max / 2;
        }
        if (max <= minDistance) {
            return new int[]{0, max};
        }
        int[] splitPos = new int[2];
        splitPos[0] = generator.nextInt(0, max);
        ArrayList<Integer> possibleOther = new ArrayList<>();
        for (int posCandidate = 0; posCandidate < max; posCandidate++) {
            if (Math.abs(splitPos[0] - posCandidate) >= minDistance) {
                possibleOther.add(posCandidate);
            }
        }
        if (possibleOther.isEmpty()) {
            return generateTwoDistancedPos(max, minDistance);
        }
        splitPos[1] = possibleOther.get(generator.nextInt(0, possibleOther.size() - 1));

        Arrays.sort(splitPos);
        return splitPos;
    }
}
