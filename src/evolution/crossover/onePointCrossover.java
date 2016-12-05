package evolution.crossover;

import config.MersenneTwisterFast;
import evolution.IChromosome;
import evolution.NumChromosome;

import java.util.Arrays;

public class onePointCrossover implements ICrossover{
    /*attributes*/
    private MersenneTwisterFast randomGenerator = new MersenneTwisterFast(System.nanoTime());
    private IChromosome parent1;
    private IChromosome parent2;
    private int sequenceLength;
    private IChromosome[] children = new IChromosome[2];
    private int splitPos;

    /*functions*/
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
        /*nextInt: incl. minimum, incl. maximum*/
        splitPos = randomGenerator.nextInt(1, sequenceLength - 2);
    }

    private IChromosome[] breedChildren() {
        int[] childSequence1;
        int[] childSequence2 = new int[sequenceLength];
        IChromosome dnaForC1;
        IChromosome dnaForC2;
        IChromosome child1;
        IChromosome child2;

        /*first part*/
        dnaForC1 = parent1;
        dnaForC2 = parent2;
        /*copyOfRange: from incl., to excl.*/
        childSequence1 = Arrays.copyOfRange(dnaForC1.getSequence(), 0, splitPos);
        childSequence2 = Arrays.copyOfRange(dnaForC2.getSequence(), 0, splitPos);

        /*second part*/
        dnaForC1 = parent2;
        dnaForC2 = parent1;
        /*copyOfRange: from incl., to excl.*/
        childSequence1 = Arrays.copyOfRange(dnaForC1.getSequence(), splitPos, sequenceLength);
        childSequence2 = Arrays.copyOfRange(dnaForC2.getSequence(), splitPos, sequenceLength);

        child1 = new NumChromosome(childSequence1, parent1.getNumberOfColors());
        child2 = new NumChromosome(childSequence2, parent1.getNumberOfColors());
        return new IChromosome[]{child1, child2};
    }

    /*getter + setter*/
}
