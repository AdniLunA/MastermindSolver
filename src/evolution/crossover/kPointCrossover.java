package evolution.crossover;

import config.LoggerGenerator;
import config.MersenneTwisterFast;
import engine.GameSettings;
import evolution.ArrayBuilder;
import evolution.IChromosome;
import evolution.NumChromosome;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;

public class KPointCrossover extends CrossoverBasics {
    /*--
     * debugging
     */
    private final Logger logger = LoggerGenerator.kPointCrossover;

    /*--
     * functions
     */
    @Override
    public IChromosome[] crossParents(IChromosome[] parents) {
        super.parent1 = parents[0];
        super.parent2 = parents[1];
        super.sequenceLength = GameSettings.INSTANCE.lengthOfCode;

        int numberOfHealthyChildren = 0;
        int tryCounter = 0;
        int maxTries = GameSettings.INSTANCE.crossoverMaxTryAgain;

        while (numberOfHealthyChildren < 2 && tryCounter < maxTries) {
            numberOfHealthyChildren = 0;
            generateRandomSplitPos();

            children = breedChildren();
            if (children[0].checkValidity()) {
                numberOfHealthyChildren++;
            }
            if (children[1].checkValidity()) {
                numberOfHealthyChildren++;
            }
            tryCounter++;
        }
        if (tryCounter == maxTries) {
            IChromosome[] returnValid = new IChromosome[2];
            if (children[0].checkValidity()) {
                returnValid[0] = children[0];
            } else {
                returnValid[1] = parents[0];
            }
            if (children[1].checkValidity()) {
                returnValid[1] = children[1];
            } else {
                returnValid[1] = parents[1];
            }
            return parents;
        }
        if (GameSettings.INSTANCE.loggingEnabled) {
            logger.info("    Children: " + children[0].toString() + " and " + children[1].toString());
            logger.info("    Fitness of children: " + children[0].getSickness() + " and " + children[1].getSickness());
        }
        return children;
    }

    private void generateRandomSplitPos() {
        int kForKPointC = GameSettings.INSTANCE.kForCrossover;
        splitPos = new int[kForKPointC + 2];
        splitPos[0] = 0;
        splitPos[1] = sequenceLength;
        for (int i = 2; i < kForKPointC + 2; i++) {
            splitPos[i] = createValidRandomSplitPos(i);
        }
        Arrays.sort(splitPos);
    }

    private IChromosome[] breedChildren() {
        ArrayBuilder builder = new ArrayBuilder();
        IChromosome dnaForC1;
        IChromosome dnaForC2;
        IChromosome child1;
        IChromosome child2;
        for (int i = 1; i < splitPos.length; i++) {
            dnaForC1 = (i % 2 == 0) ? parent1 : parent2;
            dnaForC2 = (i % 2 == 0) ? parent2 : parent1;

            /*copyOfRange: from incl., to excl.*/
            builder.addToQueue(1, Arrays.copyOfRange(dnaForC1.getSequence(), splitPos[i - 1], splitPos[i]));
            builder.addToQueue(2, Arrays.copyOfRange(dnaForC2.getSequence(), splitPos[i - 1], splitPos[i]));
        }
        child1 = new NumChromosome(builder.getChild1Sequence());
        child2 = new NumChromosome(builder.getChild2Sequence());
        return new IChromosome[]{child1, child2};
    }

    /*--getter + setter*/
}
