package evolution.crossover;

import config.MersenneTwisterFast;
import evolution.IChromosome;
import evolution.NumChromosome;
import evolution.ArrayBuilder;

import java.util.Arrays;

public class twoPointCrossover implements ICrossover {
    /***attributes***/
    private MersenneTwisterFast randomGenerator = new MersenneTwisterFast(System.nanoTime());
    private IChromosome parent1;
    private IChromosome parent2;
    private int sequenceLength;
    private IChromosome[] children = new IChromosome[2];
    private int[] splits = new int[4];

    /***functions***/
    @Override
    public IChromosome[] crossover(IChromosome parent1, IChromosome parent2) {
        this.parent1 = parent1;
        this.parent2 = parent2;
        sequenceLength = parent1.getLength();

        int numberOfHealthyChildren = 0;

        while(numberOfHealthyChildren < 2){
            generateRandomSplitPos();

            children = breedChildren();
            if(children[0].checkValidity()){
                numberOfHealthyChildren++;
            }
            if(children[1].checkValidity()){
                numberOfHealthyChildren++;
            }
        }
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
            System.out.printf("twoPointCrossover - createValidRandomSplitPos - cannot resolve new position, last value: "+pos+"/n"+r);
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
        child1 = new NumChromosome(builder.getChild1Sequence(), parent1.getNumberOfColors());
        child2 = new NumChromosome(builder.getChild2Sequence(), parent1.getNumberOfColors());
        return new IChromosome[]{child1, child2};
    }

    /***getter + setter***/
}
