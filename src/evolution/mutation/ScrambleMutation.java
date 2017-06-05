package evolution.mutation;

import config.LoggerGenerator;
import evolution.IChromosome;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class ScrambleMutation extends MutatorBasics {
    /*--
     * debugging
     */
    private final Logger logger = LoggerGenerator.scrambleMutation;

    /**
     * TODO
     */
    @Override
    public ArrayList<IChromosome> mutateGenes(ArrayList<IChromosome> genePool) {
        return null;
    }
}
