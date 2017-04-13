package evolution.crossover;

import config.MersenneTwisterFast;
import engine.GameSettings;
import evolution.ArrayBuilder;
import evolution.IChromosome;
import evolution.NumChromosome;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;

public class OnePointCrossover implements ICrossover {
    /*--
     * debugging
     */
    private final Logger logger = LogManager.getLogger(this);

    /*--attributes*/
    private MersenneTwisterFast randomGenerator = new MersenneTwisterFast(System.nanoTime());
    private IChromosome parent1;
    private IChromosome parent2;
    private int sequenceLength;
    private IChromosome[] children = new IChromosome[2];
    private int splitPos;

    /*--functions*/
    @Override
    public IChromosome[] crossover(IChromosome[] parents) {
        logger.info("");
        this.parent1 = parents[0];
        this.parent2 = parents[1];
        sequenceLength = parent1.getLength();

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
        logger.info("    Children: " + children[0].toString() + " and " + children[1].toString());
        logger.info("    Fitness of children: " + children[0].getFitness() + " and " + children[1].getFitness());
        return children;
    }

    private void generateRandomSplitPos() {
        /*nextInt: incl. minimum, incl. maximum*/
        splitPos = randomGenerator.nextInt(1, sequenceLength - 2);
    }

    private IChromosome[] breedChildren() {
        ArrayBuilder builder = new ArrayBuilder();
        int[] dnaForC1;
        int[] dnaForC2;
        IChromosome child1;
        IChromosome child2;

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

        child1 = new NumChromosome(builder.getChild1Sequence());
        child2 = new NumChromosome(builder.getChild2Sequence());
        return new IChromosome[]{child1, child2};
    }

    /*--getter + setter*/
}
