package evolution.mutation;

import evolution.IChromosome;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class InsertionMutation extends MutatorBasics {
    /*--
     * debugging
     */
    private final Logger logger = LogManager.getLogger(this);

    /**TODO*/
     @Override
     public ArrayList<IChromosome> mutateGenes(ArrayList<IChromosome> genePool) {
        return null;
     }
}
