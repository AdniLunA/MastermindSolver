package evolution;

import config.Configuration;

import java.util.Arrays;

public class NumChromosome implements IChromosome, Comparable<NumChromosome> {
    /***constructors***/
    public NumChromosome(int lengthOfCode, int numberOfColors) {
        this.lengthOfCode = lengthOfCode;
        this.numberOfColors = numberOfColors;
    }

    public NumChromosome(int[] sequence, int numberOfColors) {
        this(sequence.length, numberOfColors);
        this.sequence = sequence;
    }

    /***attributes***/
    private int lengthOfCode;
    private int numberOfColors;
    private int[] sequence;
    private int generation = 0;

    /***functions***/
    @Override
    public void generateRandom() {
        System.out.println("NumChromosome - generateRandom");
        boolean validSequence = false;
        int numOfTries = 0;
        while(!validSequence){
            /*TODO FIX!!!*/
            sequence = new int[lengthOfCode];
            int[] numberPool = new int[numberOfColors];
            for (int i = 0; i < numberOfColors; i++) {
                numberPool[i] = i;
            }
            /*reduce available numbers after one was picked*/
            for (int i = 0; i < sequence.length; i++) {
                int randomPointer = (int) Math.floor((Math.random() * numberPool.length)); /*todo improve with mersenne twister*/
                int randomNumber = numberPool[randomPointer];
                numberPool = removeItemOfArray(numberPool, randomPointer);
                sequence[i] = randomNumber;
            }
            numOfTries++;
            validSequence = checkValidity();
        }
        System.out.println("NumChromosome - generateRandom: Number of tries to find random code: "+numOfTries);
    }

    private int[] removeItemOfArray(int[] numberPool, int numberPosition) {
        ArrayBuilder builder = new ArrayBuilder();
        /*from inclusive, to exclusive*/
        builder.addToQueue(Arrays.copyOfRange(numberPool, 0, numberPosition));
        builder.addToQueue(Arrays.copyOfRange(numberPool, numberPosition+1, numberPool.length));

        return builder.getSequence();
    }

    @Override
    public boolean checkValidity() {
        System.out.println("NumChromosome - checkValidity");
        /*every color should occur max. 1 time*/
        int[] colorCounter = new int[numberOfColors];
        for (int i = 0; i < lengthOfCode; i++) {
            /*a color has to be set at each position*/
            if(sequence[i] >= Configuration.INSTANCE.MAX_NUMBER_OF_COLORS){
                return false;
            }
            /*count color occurrences*/
            colorCounter[sequence[i]]++;
        }
        /*check double occurrences*/
        for (int count : colorCounter) {
            if(count > 1){
                return false;
            }
        }
        return true;
    }

    @Override
    public int getFitness() {
        System.out.println("NumChromosome - getFitness");
        return FitnessCalculator.getInstance().calculateFitness(this);
    }

    @Override
    public int[] getSequenceSorted() {
        System.out.println("NumChromosome - getSequenceSorted");
        int[] copyOfSequence = Arrays.copyOf(sequence, sequence.length);
        Arrays.sort(copyOfSequence);
        return copyOfSequence;
    }

    @Override
    public String toString() {
        StringBuffer array = new StringBuffer();
        int lastElem = sequence.length-1;
        for (int i = 0; i < sequence.length; i++) {
            array.append(sequence[i]);
            if(i != lastElem);
            array.append(", ");
        }
        return array.toString();
    }

    @Override
    public int compareTo(NumChromosome other) {
        /*<0 -> less than other; =0 -> equals; >0 -> greater than other*/
        if (this.getFitness() < other.getFitness()) {
            return -1;
        } else if (this.getFitness() > other.getFitness()) {
            return 1;
        } else {
            return 0;
        }
    }
    /*getters + setters*/
    @Override
    public int[] getSequence() {
        return sequence;
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

    @Override
    public int getLength() {
        return lengthOfCode;
    }
}
