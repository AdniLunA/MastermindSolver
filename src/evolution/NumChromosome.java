package evolution;

import config.LoggerGenerator;
import engine.GameSettings;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;

public class NumChromosome implements IChromosome, Comparable<NumChromosome> {
    /*--
     * debugging
     */
    private final Logger logger = LoggerGenerator.numChromosome;

    /*--
     * constructors
     */
    public NumChromosome() {
        generateRandom();
        calculateSickness();
    }

    public NumChromosome(int[] sequence) {
        this.sequence = sequence;
        calculateSickness();
    }

    /*--
     * attributes
     */
    private int[] sequence;
    private int generation;
    private int sickness;

    /*--
     * functions
     */
    private void generateRandom() {
        /*System.out.println("NumChromosome - generateRandom");*/
        boolean validSequence = false;
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
    public int getSickness() {
        return sickness;
    }

    @Override
    public void calculateSickness(){
        try {
            sickness = SicknessCalculator.INSTANCE.calculateSickness(this);
        } catch (ArrayIndexOutOfBoundsException a) {
            System.out.println("getSickness: ERROR while trying to calculate sickness of chromosome " + toString());
            a.printStackTrace();
            sickness = 13;
        }
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
        return compare(this, other);
    }

    @Override
    public int compare(IChromosome one, IChromosome two) {
        /*  < 0 -> more sick than other;
            = 0 -> equally sick;
            > 0 -> less sick than other*/
        int sicknessOne = one.getSickness();
        int sicknessTwo = two.getSickness();
        if (sicknessOne < sicknessTwo) {
            return -1;
        }
        if (sicknessOne > sicknessTwo) {
            return 1;
        }
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o == null || !(o instanceof IChromosome)) {
            return false;
        }
        IChromosome other = (IChromosome) o;
        return (sequenceCompare(other.getSequence()));
    }

    private boolean sequenceCompare(int[] otherSequence) {
        for (int i = 0; i < GameSettings.INSTANCE.lengthOfCode; i++) {
            if (sequence[i] != otherSequence[i]) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int getChromosomeAtPos(int position) {
        return sequence[position];
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
