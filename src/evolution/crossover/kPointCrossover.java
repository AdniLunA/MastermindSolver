package evolution.crossover;

import config.LoggerGenerator;
import engine.GameSettings;
import evolution.IChromosome;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class KPointCrossover extends CrossoverBasics {
    /*--
     * debugging
     */
    private final Logger logger = LoggerGenerator.kPointCrossover;

    /*--
     * attributes
     */
    private int kForKPointC = GameSettings.INSTANCE.kForCrossover;

    /*--
     * functions
     */
    @Override
    public IChromosome[] crossParents(IChromosome[] parents) {
        super.parent1 = parents[0];
        super.parent2 = parents[1];
        super.splitPos = new ArrayList<>(kForKPointC + 2);
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
        splitPos.add(0);
        splitPos.add(sequenceLength);
        for (int i = 2; i < kForKPointC + 2; i++) {
            splitPos.add(createValidRandomSplitPos(i));
        }
        Collections.sort(splitPos);
    }

    private void breedChildren() {
        ArrayBuilder builder = new ArrayBuilder();
        int[] dnaForC1;
        int[] dnaForC2;
        for (int i = 1; i < splitPos.size(); i++) {
            dnaForC1 = (i % 2 == 0) ? parent1.getSequence() : parent2.getSequence();
            dnaForC2 = (i % 2 == 0) ? parent2.getSequence() : parent1.getSequence();

            /*copyOfRange: from incl., to excl.*/
            builder.addToQueue(1, Arrays.copyOfRange(dnaForC1, splitPos.get(i - 1), splitPos.get(i)));
            builder.addToQueue(2, Arrays.copyOfRange(dnaForC2, splitPos.get(i - 1), splitPos.get(i)));
        }
        child1Sequence = builder.getChild1Sequence();
        child2Sequence = builder.getChild2Sequence();
    }
}
