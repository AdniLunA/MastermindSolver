package evolution.mutation;

import engine.GameSettings;
import evolution.IChromosome;
import evolution.NumChromosome;
import evolution.SingleArrayBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;

public class DisplacementMutation extends MutatorBasics {
    /*--
     * debugging
     */
    private final Logger logger = LogManager.getLogger(this);

    @Override
    public ArrayList<IChromosome> mutateGenes(ArrayList<IChromosome> genePool) {
        //logger.info("");
        for (int chromosomeCount = 0; chromosomeCount < genePool.size(); chromosomeCount++) {
            IChromosome chromosomeToMutate = genePool.get(chromosomeCount);
            IChromosome mutatedChromosome;

            /*test if current chromosomeCount should be manipulated*/
            if (generator.nextFloat() <= GameSettings.INSTANCE.mutationRatio) {
                boolean validGeneFound;
                int countTries = 0;
                boolean keepTrying = true;
                do {
                    int[] splitPos = super.generateTwoSplitPositions(genePool.size());
                    int illegalPos = splitPos[0];

                    SingleArrayBuilder builder = new SingleArrayBuilder();
                    int[] sequence = genePool.get(chromosomeCount).getSequence();
                    builder.addToQueue(Arrays.copyOf(sequence, splitPos[0]));
                    builder.addToQueue(Arrays.copyOfRange(sequence, splitPos[1], genePool.size()));

                    int insertionPos;
                    do {
                        insertionPos = generator.nextInt(0, builder.getLength());
                    } while (insertionPos == illegalPos);

                    int[] mutatedSequence = builder.insert(insertionPos, Arrays.copyOfRange(sequence, splitPos[0], splitPos[1]));

                    mutatedChromosome = new NumChromosome(mutatedSequence);
                    validGeneFound = mutatedChromosome.checkValidity();

                    if (validGeneFound) {
                        mutatedChromosome.incrementGeneration();
                    }
                    countTries++;
                    if (countTries >= GameSettings.INSTANCE.mutationMaxTryAgain) {
                        /*give up*/
                        keepTrying = false;
                    }
                } while (!validGeneFound && keepTrying);

                if (validGeneFound) {
                    if (GameSettings.INSTANCE.loggingEnabled) {
                        logger.info("Mutated " + chromosomeToMutate + " to " + mutatedChromosome);
                    }
                    genePool.remove(chromosomeCount);
                    genePool.add(mutatedChromosome);
                }
            }
        }
        return genePool;
    }
}
