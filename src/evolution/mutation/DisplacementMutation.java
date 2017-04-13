package evolution.mutation;

import engine.GameSettings;
import evolution.IChromosome;
import evolution.NumChromosome;
import evolution.SingleArrayBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;

public class DisplacementMutation extends MutatorBasics {
    /*--
     * debugging
     */
    private final Logger logger = LogManager.getLogger(this);

    @Override
    public IChromosome[] mutateGenes(IChromosome[] genePool) {
        logger.info("");
        for (int chromosomeCount = 0; chromosomeCount < genePool.length; chromosomeCount++) {
            IChromosome mutatedChromosome = genePool[chromosomeCount];

            /*test if current chromosomeCount should be manipulated*/
            if (generator.nextFloat() <= GameSettings.INSTANCE.mutationRatio) {
                boolean validGeneFound = false;
                int countTries = 0;
                do {
                    int[] splitPos = super.generateTwoSplitPositions(genePool.length);
                    int illegalPos = splitPos[0];

                    SingleArrayBuilder builder = new SingleArrayBuilder();
                    int[] sequence = genePool[chromosomeCount].getSequence();
                    builder.addToQueue(Arrays.copyOf(sequence, splitPos[0]));
                    builder.addToQueue(Arrays.copyOfRange(sequence, splitPos[1], genePool.length));

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
                        validGeneFound = true;
                    }
                } while (!validGeneFound);

                logger.info("Mutated " + genePool[chromosomeCount].toString() + " to " + mutatedChromosome);
                genePool[chromosomeCount] = mutatedChromosome;
            }
        }
        return genePool;
    }
}
