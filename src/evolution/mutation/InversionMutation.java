package evolution.mutation;

import config.LoggerGenerator;
import engine.GameSettings;
import evolution.IChromosome;
import evolution.NumChromosome;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;

public class InversionMutation extends MutatorBasics {
    /*--
     * debugging
     */
    private final Logger logger = LoggerGenerator.inversionMutation;

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
        int[] splitPos = super.generateTwoDistancedPos(sequence.length - 1, 2);

        int[] inversedSequence = invert(Arrays.copyOfRange(sequence, splitPos[0], splitPos[1]));
        System.arraycopy(inversedSequence, 0, sequence, splitPos[0], inversedSequence.length);
        mutatedChromosome = new NumChromosome(sequence);
        mutatedChromosome.setGeneration(chromosomeToMutate.getGeneration() + 1);
        return mutatedChromosome;
    }

    private int[] invert(int[] sequenceToInverse) {
        int length = sequenceToInverse.length;
        int[] reversed = new int[length];
        for (int i = 0; i < length; i++) {
            reversed[i] = sequenceToInverse[length - i - 1];
        }
        return reversed;
    }
}
