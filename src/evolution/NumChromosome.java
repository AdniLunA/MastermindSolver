package evolution;

import java.util.Arrays;

public class NumChromosome implements IChromosome {
    //constructors
    public NumChromosome(int lengthOfCode, int numberOfColors) {
        this.lengthOfCode = lengthOfCode;
        this.numberOfColors = numberOfColors;
    }

    public NumChromosome(int lengthOfCode, int numberOfColors, int[] sequence) {
        this(lengthOfCode, numberOfColors);
        this.sequence = sequence;
    }

    //attributes
    private int lengthOfCode;
    private int numberOfColors;
    private int[] sequence;
    private int fitness = 0;
    private int generation = 0;

    //functions
    @Override
    public int[] getSequence(){
        return sequence;
    }

    @Override
    public void generateRandom() {
        System.out.println("NumChromosome - generateRandom");
        sequence = new int[lengthOfCode];
        for(int i = 0; i < sequence.length; i++){
            sequence[i] = (int) Math.floor((Math.random() * numberOfColors) + 1);
        }
    }

    @Override
    public int getFitness() {
        System.out.println("NumChromosome - getFitness");
        return FitnessCalculator.getInstance().calculateFitness(this);
    }

    //Java.util.Arrays.sort(int[])
    @Override
    public int[] getSequenceSorted(){
        System.out.println("NumChromosome - getSequenceSorted");
        int[] copyOfSequence = sequence;
        Arrays.sort(copyOfSequence);
        return copyOfSequence;
    }

    @Override
    public int getNumberOfColors() {
        return numberOfColors;
    }

    @Override
    public int getGeneration() {
        return generation;
    }

    @Override
    public void setGeneration(int generation) {
        this.generation = generation;
    }

    public String toString(){
        String array = "";
        for (int elem: sequence) {
            array += elem+", ";
        }
        return array;
    }
}
