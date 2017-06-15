package evolution.crossover;

import config.LoggerGenerator;
import engine.GameSettings;
import evolution.IChromosome;
import org.apache.logging.log4j.Logger;

import java.util.InputMismatchException;

public class UniformCrossover extends CrossoverBasics {
    /*--
     * debugging
     */
    private final Logger logger = LoggerGenerator.uniformCrossover;

    /*--
     *attributes
    */
    private float mixingRatio = readValidMixingRatio();
    private int capacity1to1;

    /*--
     *functions
     */
    private float readValidMixingRatio() {
        float ratio = 100 * GameSettings.INSTANCE.uniformMixingRatio; /*transform to percent
        /*no 0 or 100 allowed*/
        if (ratio <= 0 || ratio >= 100) {
            throw new InputMismatchException("ERROR: The mixing ratio in the configuration must be a value between 0 and 1.");
        }
        return ratio;
    }

    @Override
    public IChromosome[] crossParents(IChromosome[] parents) {
        super.parent1 = parents[0];
        super.parent2 = parents[1];

        breedChildren();
        super.setChildrenHealthy();

        if (GameSettings.INSTANCE.loggingEnabled) {
            logger.info("    Children: " + children[0].toString() + " and " + children[1].toString());
            logger.info("    Fitness of children: " + children[0].getSickness() + " and " + children[1].getSickness());
        }
        return children;
    }

    private void breedChildren() {
        calculateCapacity();
        int[] parentSequence1 = parent1.getSequence();
        int[] parentSequence2 = parent2.getSequence();
        int count1to1 = 0;
        boolean chooseRandom;
        boolean choose1to1;

        for (int i = 0; i < sequenceLength; i++) {
            /*choose parent gene*/
            chooseRandom = (count1to1 < capacity1to1) && ((i - count1to1) < (sequenceLength - capacity1to1));
            /*if capacity is reached by one method, only choose the other*/
            if (!chooseRandom) {
                /*if one to one has capacity left, choose one to one method.*/
                choose1to1 = count1to1 < capacity1to1;
            } else { /*if capacity is not reached by p1 or p2, choose gene randomized.*/
                int randomPointer = randomGenerator.nextInt(0, 100);/*incl. 0, incl. 100*/
                choose1to1 = (randomPointer <= mixingRatio);
            }
            if (choose1to1) {
                count1to1++;
            }

            /*set parent gene*/
            child1Sequence[i] = (choose1to1 ? parentSequence1[i] : parentSequence2[i]);
            child2Sequence[i] = (choose1to1 ? parentSequence2[i] : parentSequence1[i]);
        }
    }

    private void calculateCapacity() {
        capacity1to1 = (int) (mixingRatio * sequenceLength) / 100;
        if (capacity1to1 < 1) {
            capacity1to1 = 1;
        }
        if (capacity1to1 > sequenceLength - 2) {
            capacity1to1 = sequenceLength - 2;
        }
    }
}
