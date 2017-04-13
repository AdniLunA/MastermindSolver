package evolution.mutation;

import engine.GameSettings;
import evolution.IChromosome;
import evolution.NumChromosome;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ExchangeMutation extends MutatorBasics {
    /*--
     * debugging
     */
    private final Logger logger = LogManager.getLogger(this);

    /*--
     * attributes
     */

    /*--
     * functions
     */
    @Override
    public IChromosome[] mutateGenes(IChromosome[] genePool) {
        logger.info("");
        for (int chromosomeCount = 0; chromosomeCount < genePool.length; chromosomeCount++) {
            IChromosome mutatedChromosome = genePool[chromosomeCount];

            /*test if current chromosomeCount should be manipulated*/
            if (generator.nextFloat() <= GameSettings.INSTANCE.mutationRatio) {
                boolean validGeneFound = false;
                int countTries = 0;
                String mutatedMsg = "Mutated " + genePool[chromosomeCount].toString();
                do {
                    int[] mutatedSequence = genePool[chromosomeCount].getSequence();
                    int[] splitPos = super.generateTwoSplitPositions(genePool[chromosomeCount].getLength() - 1);

                    int[] saveSwapValues = new int[2];
                    saveSwapValues[0] = mutatedSequence[splitPos[0]];
                    saveSwapValues[1] = mutatedSequence[splitPos[1]];

                    /*swap*/
                    mutatedSequence[splitPos[0]] = saveSwapValues[1];
                    mutatedSequence[splitPos[1]] = saveSwapValues[0];

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

                logger.info(mutatedMsg + " to " + mutatedChromosome);

                genePool[chromosomeCount] = mutatedChromosome;
            }
        }
        return genePool;
    }
}
