package evolution.crossover;

import config.LoggerGenerator;
import engine.GameSettings;
import evolution.ArrayBuilder;
import evolution.IChromosome;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;

public class OnePointCrossover extends CrossoverBasics {
    /*--
     * debugging
     */
    private final Logger logger = LoggerGenerator.onePointCrossover;

    /*--attributes*/
    private int splitPos;

    /*--functions*/
    @Override
    public IChromosome[] crossParents(IChromosome[] parents) {
        super.parent1 = parents[0];
        super.parent2 = parents[1];
        generateRandomSplitPos();

        breedChildren();
        super.setChildrenHealthy();

        if (GameSettings.INSTANCE.loggingEnabled) {
            logger.info("    Children: " + children[0].toString() + " and " + children[1].toString());
            logger.info("    Fitness of children: " + children[0].getSickness() + " and " + children[1].getSickness());
        }
        return children;
    }

    private void generateRandomSplitPos() {
        /*nextInt: incl. minimum, incl. maximum*/
        splitPos = super.randomGenerator.nextInt(1, sequenceLength - 2);
    }

    private void breedChildren() {
        ArrayBuilder builder = new ArrayBuilder();
        int[] dnaForC1;
        int[] dnaForC2;

        /*first part*/
        dnaForC1 = parent1.getSequence();
        dnaForC2 = parent2.getSequence();
        /*copyOfRange: from incl., to excl.*/
        builder.addToQueue(1, Arrays.copyOfRange(dnaForC1, 0, splitPos));
        builder.addToQueue(2, Arrays.copyOfRange(dnaForC2, 0, splitPos));

        /*second part*/
        dnaForC1 = parent2.getSequence();
        dnaForC2 = parent1.getSequence();
        /*copyOfRange: from incl., to excl.*/
        builder.addToQueue(1, Arrays.copyOfRange(dnaForC1, splitPos, sequenceLength));
        builder.addToQueue(2, Arrays.copyOfRange(dnaForC2, splitPos, sequenceLength));

        child1Sequence = builder.getChild1Sequence();
        child2Sequence = builder.getChild2Sequence();
    }
}
