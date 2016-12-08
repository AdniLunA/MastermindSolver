package evolution.crossover;

import config.Configuration;
import config.MersenneTwisterFast;
import evolution.IChromosome;
import evolution.NumChromosome;
import evolution.ArrayBuilder;

import java.util.Arrays;

public class TwoPointCrossover implements ICrossover {
    /***attributes***/
    private MersenneTwisterFast randomGenerator = new MersenneTwisterFast(System.nanoTime());
    private IChromosome parent1;
    private IChromosome parent2;
    private int sequenceLength;
    private IChromosome[] children = new IChromosome[2];
    private int[] splits = new int[4];

    /***functions***/
    @Override
    public IChromosome[] crossover(IChromosome[] parents) {
        System.out.println("TwoPointCrossover - crossover:");
        this.parent1 = parents[0];
        this.parent2 = parents[1];
        sequenceLength = parent1.getLength();

        int numberOfHealthyChildren = 0;
        int tryCounter = 0;
        int maxTries = Configuration.INSTANCE.CROSSOVER_MAX_TRY_AGAIN;

        while(numberOfHealthyChildren < 2 && tryCounter < maxTries){
            generateRandomSplitPos();

            children = breedChildren();
            if(children[0].checkValidity()){
                numberOfHealthyChildren++;
            }
            if(children[1].checkValidity()){
                numberOfHealthyChildren++;
            }
            tryCounter++;
        }
        if(tryCounter == maxTries){
            return parents;
        }
        System.out.println("    Children: " + children[0].toString() + " and " + children[1].toString());
        System.out.println("    Fitness of children: " + children[0].getFitness() + " and " + children[1].getFitness());
        return children;
    }

    private void generateRandomSplitPos() {
        splits[0] = 0;
        splits[1] = sequenceLength;
        splits[2] = createValidRandomSplitPos(2);
        splits[3] = createValidRandomSplitPos(3);
        Arrays.sort(splits);
    }

    private int createValidRandomSplitPos(int currentPos){
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
                    if (pos == splits[i]) {
                        invalid = true;
                    }
                }
            } while (invalid);
        } catch (RuntimeException r){
            System.out.printf("TwoPointCrossover - createValidRandomSplitPos - cannot resolve new position, last value: "+pos+"/n"+r);
        }

        return pos;
    }

    private IChromosome[] breedChildren() {
        ArrayBuilder builder = new ArrayBuilder();
        IChromosome dnaForC1;
        IChromosome dnaForC2;
        IChromosome child1;
        IChromosome child2;
        for (int i = 1; i < splits.length; i++) {
            dnaForC1 = (i % 2 == 0) ? parent1 : parent2;
            dnaForC2 = (i % 2 == 0) ? parent2 : parent1;

            /*copyOfRange: from incl., to excl.*/
            builder.addToQueue(1, Arrays.copyOfRange(dnaForC1.getSequence(), splits[i - 1], splits[i]));
            builder.addToQueue(2, Arrays.copyOfRange(dnaForC2.getSequence(), splits[i - 1], splits[i]));
        }
        child1 = new NumChromosome(builder.getChild1Sequence());
        child2 = new NumChromosome(builder.getChild2Sequence());
        return new IChromosome[]{child1, child2};
    }

    /***getter + setter***/
}
