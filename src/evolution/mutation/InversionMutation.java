package evolution.mutation;

import config.LoggerGenerator;
import evolution.IChromosome;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class InversionMutation extends MutatorBasics {
    /*--
     * debugging
     */
    private final Logger logger = LoggerGenerator.inversionMutation;

    /**
     * TODO
     */
    @Override
    public ArrayList<IChromosome> mutateGenes(ArrayList<IChromosome> genePool) {
        return null;
    }
}
