package evolution;

import config.Configuration;
import config.MersenneTwisterFast;

import java.util.Arrays;

public class kPointCrossover implements ICrossover{
    //constructor


    //attributes
    MersenneTwisterFast randomGenerator = new MersenneTwisterFast(System.nanoTime());
    IChromosome parent1;
    IChromosome parent2;
    int kForKPointC = Configuration.INSTANCE.K_FOR_CROSS_OVER;
    IChromosome[] children = new IChromosome[2];
    int[] splits;

    //functions
    @Override
    public IChromosome[] crossover(IChromosome parent1, IChromosome parent2) {
        this.parent1 = parent1;
        this.parent2 = parent2;

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
        splits = new int[kForKPointC];
        splits[0] = 0;
        splits[1] = parent1.getLength();
        for (int i = 2; i < kForKPointC+2; i++) {
            splits[i] = createValidRandomSplitPos(i);
        }
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
                //nextInt: incl. minimum, incl. maximum
                pos = randomGenerator.nextInt(1, parent1.getLength() - 2);
                invalid = false;
                for (int i = currentPos; (i > 1 && !invalid); i--) {
                    if (pos == splits[i]) {
                        invalid = true;
                    }
                }
            } while (invalid);
        } catch (RuntimeException r){
            System.out.println("kPointCrossover - createValidRandomSplitPos - cannot resolve new position, last value: "+pos);
            System.out.println(r);
        }

        return pos;
    }

    private IChromosome[] breedChildren() {
        int[] childSequence1 = new int[parent1.getLength()];
        int[] childSequence2 = new int[parent1.getLength()];
        IChromosome dnaForC1;
        IChromosome dnaForC2;
        IChromosome child1;
        IChromosome child2;
        for (int i = 1; i < splits.length; i++) {
            dnaForC1 = (i % 2 == 0) ? parent1 : parent2;
            dnaForC2 = (i % 2 == 0) ? parent2 : parent1;

            //copyOfRange: from incl., to excl.
            childSequence1 = Arrays.copyOfRange(dnaForC1.getSequence(), splits[i - 1], splits[i]);
            childSequence2 = Arrays.copyOfRange(dnaForC2.getSequence(), splits[i - 1], splits[i]);
        }
        child1 = new NumChromosome(parent1.getLength(), parent1.getNumberOfColors(), childSequence1);
        child2 = new NumChromosome(parent1.getLength(), parent1.getNumberOfColors(), childSequence2);
        IChromosome[] children = {child1, child2};
        return children;
    }

    //getter + setter
}
