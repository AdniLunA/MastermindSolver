package evolution;

import engine.GameEngine;
import engine.GameSettings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.InputMismatchException;

public class NumChromosome implements IChromosome, Comparable<NumChromosome> {
    /*--
     * debugging
     */
    private final Logger logger = LogManager.getLogger(this);

    /*--
     * constructors
     */
    public NumChromosome() {
        generateRandom();
        this.generation = 0;
    }

    public NumChromosome(int[] sequence) {
        this(sequence, 0);
    }

    public NumChromosome(int[] sequence, int generation){
        this.sequence = sequence;
        this.generation = generation;
    }

    /*--
     * attributes
     */
    private int[] sequence;
    private int generation;

    /*--
     * functions
     */
    private void generateRandom() {
        /*System.out.println("NumChromosome - generateRandom");*/
        boolean validSequence = false;
        int numOfTries = 0;
        while (!validSequence) {
            sequence = new int[GameSettings.INSTANCE.lengthOfCode];
            int[] numberPool = new int[GameSettings.INSTANCE.numberOfColors];
            for (int i = 0; i < GameSettings.INSTANCE.numberOfColors; i++) {
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
        /*System.out.println("NumChromosome - generateRandom: Number of tries to find random code: "+numOfTries);*/
    }

    private int[] removeItemOfArray(int[] numberPool, int numberPosition) {
        SingleArrayBuilder builder = new SingleArrayBuilder();
        /*from inclusive, to exclusive*/
        builder.addToQueue(Arrays.copyOfRange(numberPool, 0, numberPosition));
        builder.addToQueue(Arrays.copyOfRange(numberPool, numberPosition + 1, numberPool.length));

        try {
            return builder.getSequence();
        } catch (NullPointerException n) {
            System.out.println("removeItemOfArray: ERROR - empty numberPool " + numberPool);
            n.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean checkValidity() {
        /*System.out.println("NumChromosome - checkValidity");*/
        /*every color should occur max. 1 time*/
        int[] colorCounter = new int[GameSettings.INSTANCE.numberOfColors];
        for (int i = 0; i < GameSettings.INSTANCE.lengthOfCode; i++) {
            /*a color has to be set at each position*/
            if (sequence[i] >= GameSettings.INSTANCE.MAX_NUMBER_OF_COLORS) {
                return false;
            }
            /*count color occurrences*/
            colorCounter[sequence[i]]++;
        }
        /*check double occurrences*/
        for (int count : colorCounter) {
            if (count > 1) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int getFitness() {
        /*System.out.println("NumChromosome - getFitness");*/
        try {
            return FitnessCalculator.INSTANCE.calculateFitness(this);
        } catch (ArrayIndexOutOfBoundsException a) {
            System.out.println("getFitness: ERROR while trying to calculate fitness of chromosome " + toString());
            a.printStackTrace();
            return 0;
        }
    }

    @Override
    public int[] getSequenceSorted() {
        /*System.out.println("NumChromosome - getSequenceSorted");*/
        int[] copyOfSequence = Arrays.copyOf(sequence, sequence.length);
        Arrays.sort(copyOfSequence);
        return copyOfSequence;
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        int lastElem = sequence.length - 1;
        for (int i = 0; i < sequence.length; i++) {
            /*always with 2 digits*/
            buffer.append((sequence[i] < 10) ? "0" + sequence[i] : sequence[i]);
            if (i != lastElem) {
                buffer.append(" ");
            }
        }
        return buffer.toString();
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

    @Override
    public void incrementGeneration() {
        generation++;
    }

    /*getters + setters*/
    @Override
    public int[] getSequence() {
        return sequence;
    }

    @Override
    public int getGeneration() {
        return generation;
    }

    @Override
    public void setGeneration(int generation) {
        this.generation = generation;
    }

}
