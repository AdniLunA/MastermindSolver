package evolution.mutation;

import config.LoggerGenerator;
import engine.GameSettings;
import evolution.IChromosome;
import evolution.NumChromosome;
import evolution.SingleArrayBuilder;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;

public class DisplacementMutation extends MutatorBasics {
    /*--
     * debugging
     */
    private final Logger logger = LoggerGenerator.displacementMutation;

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
        int[] sequenceToMutate = chromosomeToMutate.getSequence();

        int[] splitPos = super.generateTwoPositions(sequenceToMutate.length-1);
        int illegalPos = splitPos[0];
        SingleArrayBuilder builder = new SingleArrayBuilder();
        builder.addToQueue(Arrays.copyOf(sequenceToMutate, splitPos[0]));
        builder.addToQueue(Arrays.copyOfRange(sequenceToMutate, splitPos[1], chromosomeToMutate.getSequence().length));

        int insertionPos;
        if(builder.getLength() == 1){
            insertionPos = 0;
        } else {
            do {
                insertionPos = generator.nextInt(0, builder.getLength()-1);
            } while (insertionPos == illegalPos || insertionPos >= builder.getLength());
        }

        int[] mutatedSequence = builder.insert(insertionPos, Arrays.copyOfRange(sequenceToMutate, splitPos[0], splitPos[1]));

        mutatedChromosome = new NumChromosome(mutatedSequence);
        mutatedChromosome.setGeneration(chromosomeToMutate.getGeneration() + 1);
        return mutatedChromosome;
    }
}
