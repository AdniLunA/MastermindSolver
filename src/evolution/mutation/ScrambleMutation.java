package evolution.mutation;

import config.LoggerGenerator;
import config.MersenneTwisterFast;
import engine.GameSettings;
import evolution.IChromosome;
import evolution.NumChromosome;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;

public class ScrambleMutation extends MutatorBasics {
    /*--
     * debugging
     */
    private final Logger logger = LoggerGenerator.scrambleMutation;

    /**
     * TODO
     */
    @Override
    public ArrayList<IChromosome> mutateGenes(ArrayList<IChromosome> genePool) {
        ArrayList<IChromosome> genesToMutate = super.getGenesToMutate(genePool);
        for (IChromosome geneToMutate : genesToMutate) {
            IChromosome mutatedChromosome = mutate(geneToMutate);
            genePool.remove(geneToMutate);
            genePool.add(mutatedChromosome);

            if (GameSettings.INSTANCE.loggingEnabled) {
                logger.info("Mutated " + geneToMutate + " to " + mutatedChromosome);
            }
        }
        return genePool;
    }

    @Override
    public IChromosome mutate(IChromosome chromosomeToMutate) {
        IChromosome mutatedChromosome;

        int[] sequence = chromosomeToMutate.getSequence();
        int[] splitPos = super.generateTwoDistancedPos(sequence.length -1, 3);

        int[] scrambledSequence = scramble(Arrays.copyOfRange(sequence, splitPos[0], splitPos[1]));
        System.arraycopy(scrambledSequence, 0, sequence, splitPos[0], scrambledSequence.length);
        mutatedChromosome = new NumChromosome(sequence);
        mutatedChromosome.setGeneration(chromosomeToMutate.getGeneration() + 1);
        return mutatedChromosome;
    }

    private int[] scramble(int[] sequenceToScramble) {
        ArrayList<Integer> numberPool = new ArrayList<>();
        int[] scrambledSequence = new int[sequenceToScramble.length];
        for (int position : sequenceToScramble) {
            numberPool.add(position);
        }
        MersenneTwisterFast generator = new MersenneTwisterFast(System.nanoTime());
        for (int i = 0; i < scrambledSequence.length; i++) {
            int itemPos = generator.nextInt(0, numberPool.size()-1);
            scrambledSequence[i] = numberPool.get(itemPos);
            numberPool.remove(itemPos);
        }
        return scrambledSequence;
    }
}
