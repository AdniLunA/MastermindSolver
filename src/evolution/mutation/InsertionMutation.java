package evolution.mutation;

import config.LoggerGenerator;
import config.MersenneTwisterFast;
import engine.GameSettings;
import evolution.IChromosome;
import evolution.NumChromosome;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class InsertionMutation extends MutatorBasics {
    /*--
     * debugging
     */
    private final Logger logger = LoggerGenerator.insertionMutation;

    /**
     * TODO
     */
    @Override
    public ArrayList<IChromosome> mutateGenes(ArrayList<IChromosome> genePool) {
        ArrayList<IChromosome> genesToMutate = super.getGenesToMutate(genePool);
        for (IChromosome geneToMutate : genesToMutate) {
            boolean validGeneFound = false;
            int countTries = 0;
            boolean keepTrying = true;
            IChromosome mutatedChromosome;
            do {
                mutatedChromosome = mutate(geneToMutate);
                validGeneFound = mutatedChromosome.checkValidity();

                countTries++;
                if (countTries >= GameSettings.INSTANCE.mutationMaxTryAgain) {
                    keepTrying = false;
                }
            } while (validGeneFound && keepTrying);

            if (validGeneFound) {
                genePool.remove(geneToMutate);
                genePool.add(mutatedChromosome);

                if (GameSettings.INSTANCE.loggingEnabled) {
                    logger.info("Mutated " + geneToMutate + " to " + mutatedChromosome);
                }
            }
        }
        return genePool;
    }

    @Override
    public IChromosome mutate(IChromosome chromosomeToMutate) {
        int[] sequenceToMutate = chromosomeToMutate.getSequence();
        int[] mutatedSequence = new int[sequenceToMutate.length];

        MersenneTwisterFast generator = new MersenneTwisterFast(System.nanoTime());
        int insertionPos = generator.nextInt(0, sequenceToMutate.length-1);
        int geneToInsert;

        ArrayList<Integer> availableGenes = new ArrayList<>();
        for(int i = 0; i < GameSettings.INSTANCE.numberOfColors; i++){
            availableGenes.add(i);
        }
        for (int i = 0; i < (sequenceToMutate.length -1); i++) {
            availableGenes.remove((Integer) sequenceToMutate[i]);
        }

        int genePos = generator.nextInt(0, availableGenes.size()-1);
        geneToInsert = availableGenes.get(genePos);

        if (insertionPos > 0) {
            System.arraycopy(sequenceToMutate, 0, mutatedSequence, 0, insertionPos-1);
        }
        try {
            mutatedSequence[insertionPos] = geneToInsert;
        }catch (Exception e){
            e.printStackTrace();
        }
        System.arraycopy(sequenceToMutate, insertionPos + 1 - 1, mutatedSequence, insertionPos + 1, sequenceToMutate.length - (insertionPos + 1));

        IChromosome mutatedChromosome = new NumChromosome(mutatedSequence);
        mutatedChromosome.setGeneration(chromosomeToMutate.getGeneration() + 1);
        return mutatedChromosome;
    }
}
