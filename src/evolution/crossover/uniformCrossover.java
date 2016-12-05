package evolution.crossover;

import config.Configuration;
import config.MersenneTwisterFast;
import evolution.IChromosome;
import evolution.NumChromosome;
import evolution.crossover.ICrossover;

import java.util.Arrays;

public class uniformCrossover implements ICrossover {
    //constructor


    IChromosome parent1;
    //attributes
    private MersenneTwisterFast randomGenerator = new MersenneTwisterFast(System.nanoTime());
    private IChromosome parent2;
    private int sequenceLength;
    private float mixingRatio = readValidMixingRatio();
    private IChromosome[] children = new IChromosome[2];
    private int p1Capacity;
    private int p2Capacity;

    //functions
    private float readValidMixingRatio() {
        float ratio = 100 * Configuration.MIXING_RATIO; //transform to percent
        //no 0 or 100 allowed
        if (ratio <= 0 || ratio >= 100) {
            throw new IllegalArgumentException("The mixing ratio in the configuration must be a value between 0 and 1.");
        }
        return ratio;
    }

    @Override
    public IChromosome[] crossover(IChromosome parent1, IChromosome parent2) {
        this.parent1 = parent1;
        this.parent2 = parent2;
        sequenceLength = parent1.getLength();

        int numberOfHealthyChildren = 0;

        while(numberOfHealthyChildren < 2){
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

    private IChromosome[] breedChildren() {
        calculateCapacity();
        int[] childSequence1 = new int[parent1.getLength()];
        int[] childSequence2 = new int[parent1.getLength()];
        int[] parentSequence1 = parent1.getSequence();
        int[] parentSequence2 = parent2.getSequence();
        IChromosome child1;
        IChromosome child2;
        int countP1 = 0;
        int countP2 = 0;
        boolean chooseRandom = true;
        boolean choose1to1;

        for(int i = 0; i < sequenceLength; i++) {

            /*choose parent gene*/
            chooseRandom = (countP1 < p1Capacity) && (countP2 < p2Capacity) ;
            //if capacity is reached by one p, only take gene of the other
            if (!chooseRandom){
                choose1to1 = countP1 < p1Capacity;
            } else { //if capacity is not reached by p1 or p2, choose gene randomized.
                //choose gene random
                int randomPointer = randomGenerator.nextInt(0,101);//incl. 0, excl. 101
                choose1to1 = (randomPointer <= mixingRatio);
            }

            /*set parent gene*/
            childSequence1[i] = (choose1to1 ? parentSequence1[i] : parentSequence2[i]);
            childSequence1[i] = (choose1to1 ? parentSequence2[i] : parentSequence1[i]);
        }

        child1 = new NumChromosome(parent1.getLength(), parent1.getNumberOfColors(), childSequence1);
        child2 = new NumChromosome(parent1.getLength(), parent1.getNumberOfColors(), childSequence2);
        return new IChromosome[]{child1, child2};
    }

    private void calculateCapacity(){
        p1Capacity = (int) (mixingRatio * sequenceLength);
        if(p1Capacity < 1){
            p1Capacity = 1;
        }
        if(p1Capacity > 99){
            p1Capacity = 99;
        }
        p2Capacity = sequenceLength - p1Capacity;
    }

    //getter + setter
}
