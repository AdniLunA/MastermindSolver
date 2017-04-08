package evolution.mutation;


import config.MersenneTwisterFast;
import evolution.IChromosome;

import java.util.Arrays;

public class MutatorBasics implements IMutation {
    /***
     * attributes
     ***/
    private MersenneTwisterFast generator = new MersenneTwisterFast(System.nanoTime());

    /***
     * functions
     ***/
    @Override
    public IChromosome[] mutateGenes(IChromosome[] genePool) {
        return genePool;
    }

    @Override
    public int[] generateTwoSplitPositions(int max) {
        System.out.println("MutatorBasics - generateTwoSplitPositions");
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
