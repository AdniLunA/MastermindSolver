package evolution.crossover;

import config.Configuration;
import config.MersenneTwisterFast;
import evolution.IChromosome;
import evolution.NumChromosome;
import evolution.crossover.ICrossover;

import java.util.Arrays;
import java.util.InputMismatchException;

public class UniformCrossover implements ICrossover {
    /***attributes***/
    private MersenneTwisterFast randomGenerator = new MersenneTwisterFast(System.nanoTime());
    private IChromosome parent1;
    private IChromosome parent2;
    private int sequenceLength;
    private float mixingRatio = readValidMixingRatio();
    private IChromosome[] children = new IChromosome[2];
    private int p1Capacity;
    private int p2Capacity;

    /***functions***/
    private float readValidMixingRatio() {
        float ratio = 100 * Configuration.INSTANCE.MIXING_RATIO; /*transform to percent
        /*no 0 or 100 allowed*/
        if (ratio <= 0 || ratio >= 100) {
            throw new InputMismatchException("ERROR: The mixing ratio in the configuration must be a value between 0 and 1.");
        }
        return ratio;
    }

    @Override
    public IChromosome[] crossover(IChromosome[] parents) {
        System.out.println("UniformCrossover - crossover:");
        this.parent1 = parents[0];
        this.parent2 = parents[1];
        sequenceLength = parent1.getLength();

        int numberOfHealthyChildren = 0;
        int tryCounter = 0;
        int maxTries = Configuration.INSTANCE.CROSSOVER_MAX_TRY_AGAIN;

        while(numberOfHealthyChildren < 2 && tryCounter < maxTries){
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

    private IChromosome[] breedChildren() {
        calculateCapacity();
        int[] childSequence1 = new int[sequenceLength];
        int[] childSequence2 = new int[sequenceLength];
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
            /*if capacity is reached by one p, only take gene of the other*/
            if (!chooseRandom){
                choose1to1 = countP1 < p1Capacity;
            } else { /*if capacity is not reached by p1 or p2, choose gene randomized.*/
                /*choose gene random*/
                int randomPointer = randomGenerator.nextInt(0,101);/*incl. 0, excl. 101*/
                choose1to1 = (randomPointer <= mixingRatio);
            }

            /*set parent gene*/
            childSequence1[i] = (choose1to1 ? parentSequence1[i] : parentSequence2[i]);
            childSequence1[i] = (choose1to1 ? parentSequence2[i] : parentSequence1[i]);
        }

        child1 = new NumChromosome(childSequence1);
        child2 = new NumChromosome(childSequence2);
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

    /***getter + setter***/
}
