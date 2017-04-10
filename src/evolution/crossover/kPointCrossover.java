package evolution.crossover;

import config.ConfigurationManager;
import config.MersenneTwisterFast;
import de.bean900.logger.Logger;
import evolution.IChromosome;
import evolution.NumChromosome;
import evolution.ArrayBuilder;

import java.util.Arrays;

public class KPointCrossover implements ICrossover {
    /*--
     * debugging
     */
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    /*--
     * attributes
     */
    private MersenneTwisterFast randomGenerator = new MersenneTwisterFast(System.nanoTime());
    private IChromosome parent1;
    private IChromosome parent2;
    private int sequenceLength;
    private IChromosome[] children = new IChromosome[2];
    private int[] splitPos;

    /*--
     * functions
     */
    @Override
    public IChromosome[] crossover(IChromosome[] parents) {
        logger.info("crossover", "");
        this.parent1 = parents[0];
        this.parent2 = parents[1];
        sequenceLength = parent1.getLength();

        int numberOfHealthyChildren = 0;
        int tryCounter = 0;
        int maxTries = ConfigurationManager.INSTANCE.CROSSOVER_MAX_TRY_AGAIN;

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
        logger.info("crossover", "    Children: " + children[0].toString() + " and " + children[1].toString());
        logger.info("crossover", "    Fitness of children: " + children[0].getFitness() + " and " + children[1].getFitness());
        return children;
    }

    private void generateRandomSplitPos() {
        int kForKPointC = ConfigurationManager.INSTANCE.K_FOR_CROSS_OVER;
        splitPos = new int[kForKPointC + 2];
        splitPos[0] = 0;
        splitPos[1] = sequenceLength;
        for (int i = 2; i < kForKPointC + 2; i++) {
            splitPos[i] = createValidRandomSplitPos(i);
        }
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
                pos = randomGenerator.nextInt(1, sequenceLength - 2);
                invalid = false;
                for (int i = currentPos; (i > 1 && !invalid); i--) {
                    if (pos == splitPos[i]) {
                        invalid = true;
                    }
                }
            } while (invalid);
        } catch (RuntimeException r) {
            logger.error("createValidRandomSplitPos: - cannot resolve new position, last value: " + pos, r);
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
