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
     * attributes
     */

    /*--
     * functions
     */
    @Override
    public ArrayList<IChromosome> mutateGenes(ArrayList<IChromosome> genePool) {
        //logger.info("");
        for (int chromosomeCount = 0; chromosomeCount < genePool.size(); chromosomeCount++) {
            IChromosome chromosomeToMutate = genePool.get(chromosomeCount);
            IChromosome mutatedChromosome;

            /*test if current chromosomeCount should be manipulated*/
            if (generator.nextFloat() <= GameSettings.INSTANCE.mutationRatio) {
                String mutatedMsg = "Mutated " + chromosomeToMutate.toString();

                int[] mutatedSequence = chromosomeToMutate.getSequence();
                int[] splitPos = super.generateTwoSplitPositions(GameSettings.INSTANCE.lengthOfCode - 1);

                int[] saveSwapValues = new int[2];
                saveSwapValues[0] = mutatedSequence[splitPos[0]];
                saveSwapValues[1] = mutatedSequence[splitPos[1]];

                /*swap*/
                mutatedSequence[splitPos[0]] = saveSwapValues[1];
                mutatedSequence[splitPos[1]] = saveSwapValues[0];

                mutatedChromosome = new NumChromosome(mutatedSequence);

                if (GameSettings.INSTANCE.loggingEnabled) {
                    logger.info(mutatedMsg + " to " + mutatedChromosome);
                }

                genePool.remove(chromosomeCount);
                genePool.add(mutatedChromosome);
            }
        }
        return genePool;
    }
}
