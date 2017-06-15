package evolution.mutation;

import config.LoggerGenerator;
import engine.GameSettings;
import evolution.IChromosome;
import evolution.NumChromosome;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class ExchangeMutation extends MutatorBasics {
    /*--
     * debugging
     */
    private final Logger logger = LoggerGenerator.exchangeMutation;

    /*--
     * functions
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

        int[] mutatedSequence = chromosomeToMutate.getSequence();
        int[] splitPos = super.generateTwoPositions(mutatedSequence.length - 1);

        int[] saveSwapValues = new int[2];
        saveSwapValues[0] = mutatedSequence[splitPos[0]];
        saveSwapValues[1] = mutatedSequence[splitPos[1]];

                /*swap*/
        mutatedSequence[splitPos[0]] = saveSwapValues[1];
        mutatedSequence[splitPos[1]] = saveSwapValues[0];

        mutatedChromosome = new NumChromosome(mutatedSequence);
        mutatedChromosome.setGeneration(chromosomeToMutate.getGeneration() + 1);
        return mutatedChromosome;
    }
}
