package evolution.mutation;

import config.Configuration;
import engine.GameEngine;
import evolution.ArrayBuilder;
import evolution.IChromosome;
import evolution.NumChromosome;

import java.util.Arrays;

public class DisplacementMutation extends MutatorBasics {
    @Override
    public IChromosome[] mutateGenes(IChromosome[] genePool){
        System.out.println("DisplacementMutation - mutateGenes");
        for (int chromosomeCount = 0; chromosomeCount < genePool.length; chromosomeCount++) {
            IChromosome mutatedChromosome = genePool[chromosomeCount];

            /*test if current chromosomeCount should be manipulated*/
            if (generator.nextFloat() <= Configuration.INSTANCE.MUTATION_RATIO) {
                boolean validGeneFound = false;
                int countTries = 0;
                do {
                    int[] splitPos = super.generateTwoSplitPositions(genePool.length);
                    int illegalPos = splitPos[0];

                    ArrayBuilder builder = new ArrayBuilder();
                    int[] sequence = genePool[chromosomeCount].getSequence();
                    builder.addToQueue(Arrays.copyOf(sequence, splitPos[0]));
                    builder.addToQueue(Arrays.copyOfRange(sequence, splitPos[1], genePool.length));

                    int insertionPos;
                    do {
                        insertionPos = generator.nextInt(0, builder.getLength());
                    } while (insertionPos == illegalPos);

                    int[] mutatedSequence = builder.insert(insertionPos, Arrays.copyOfRange(sequence, splitPos[0], splitPos[1]));

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
