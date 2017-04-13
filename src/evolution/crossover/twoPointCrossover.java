package evolution.crossover;

import config.MersenneTwisterFast;
import engine.GameSettings;
import evolution.ArrayBuilder;
import evolution.IChromosome;
import evolution.NumChromosome;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;

public class TwoPointCrossover implements ICrossover {
    /*--
     * debugging
     */
    private final Logger logger = LogManager.getLogger(this);

    /*--
     * attributes
     */
    private MersenneTwisterFast randomGenerator = new MersenneTwisterFast(System.nanoTime());
    private IChromosome parent1;
    private IChromosome parent2;
    private int sequenceLength;
    private IChromosome[] children = new IChromosome[2];
    private int[] splitPos = new int[4];

    /*--
     * functions
     */
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
        splitPos[0] = 0;
        splitPos[1] = sequenceLength;
        splitPos[2] = createValidRandomSplitPos(2);
        splitPos[3] = createValidRandomSplitPos(3);
        Arrays.sort(splitPos);
    }

    private int createValidRandomSplitPos(int currentPos) {
        int pos = 0;
        boolean invalid = false;
        try {
            /**
             * calculate splitting position.
             * search if new position is duplicate.
             * if that is the case, search new position.
             */
            do {
                /*nextInt: incl. minimum, incl. maximum*/
                pos = randomGenerator.nextInt(1, sequenceLength - 1);
                invalid = false;
                for (int i = currentPos; (i > 1 && !invalid); i--) {
                    if (pos == splitPos[i]) {
                        invalid = true;
                    }
                }
            } while (invalid);
        } catch (RuntimeException r) {
            System.out.println("TwoPointCrossover - createValidRandomSplitPos - cannot resolve new position, last value: " + pos);
            r.printStackTrace();
        }

        return pos;
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
