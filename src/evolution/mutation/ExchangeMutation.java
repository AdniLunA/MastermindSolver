package evolution.mutation;

import config.Configuration;
import engine.GameEngine;
import evolution.IChromosome;
import evolution.NumChromosome;

public class ExchangeMutation extends MutatorBasics {
    /***
     * attributes
     ***/

    /***
     * functions
     ***/
    @Override
    public IChromosome[] mutateGenes(IChromosome[] genePool) {
        System.out.println("ExchangeMutation - mutateGenes");
        for (int chromosomeCount = 0; chromosomeCount < genePool.length; chromosomeCount++) {
            IChromosome mutatedChromosome = genePool[chromosomeCount];

            /*test if current chromosomeCount should be manipulated*/
            if (generator.nextFloat() <= Configuration.INSTANCE.MUTATION_RATIO) {
                boolean validGeneFound = false;
                int countTries = 0;
                do {
                    int[] mutatedSequence =  genePool[chromosomeCount].getSequence();
                    int[] splitPos = super.generateTwoSplitPositions(genePool[chromosomeCount].getLength() - 1);

                    int[] saveSwapValues = new int[2];
                    saveSwapValues[0] = mutatedSequence[splitPos[0]];
                    saveSwapValues[1] = mutatedSequence[splitPos[1]];

                    /*swap*/
                    genePool[chromosomeCount].getSequence()[splitPos[0]] = saveSwapValues[1];
                    genePool[chromosomeCount].getSequence()[splitPos[1]] = saveSwapValues[0];

                    mutatedChromosome = new NumChromosome(mutatedSequence, GameEngine.getInstance().getNumColors());
                    validGeneFound = mutatedChromosome.checkValidity();

                    if(validGeneFound){
                        mutatedChromosome.incrementGeneration();
                    }
                    countTries++;
                    if (countTries >= Configuration.INSTANCE.MUTATION_MAX_TRY_AGAIN) {
                        /*give up*/
                        validGeneFound = true;
                    }
                } while (!validGeneFound);

                System.out.println("DisplaceMutation: mutated " + genePool[chromosomeCount].toString() + " to " + mutatedChromosome);
                genePool[chromosomeCount] = mutatedChromosome;
            }
        }
        return genePool;
    }
}
