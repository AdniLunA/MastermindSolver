package evolution;

import config.MersenneTwisterFast;
import engine.GameSettings;

import java.util.ArrayList;

public class NumChromosome implements IChromosome, Comparable<NumChromosome> {
    /*--
     * constructors
     */
    public NumChromosome(MersenneTwisterFast generator) {
        this.generator = generator;
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
    private int sickness = 0;
    private MersenneTwisterFast generator;
    private int nKnownSubmissions = 0;

    /*--
     * functions
     */
    private void generateRandom() {
        boolean validSequence = false;
        while (!validSequence) {
            sequence = new int[GameSettings.INSTANCE.lengthOfCode];
            ArrayList<Integer> numberPool = new ArrayList<>(GameSettings.INSTANCE.numberOfColors);
            for (int i = 0; i < GameSettings.INSTANCE.numberOfColors; i++) {
                numberPool.add(i);
            }
            /*reduce available numbers after one was picked*/
            int posPointer;
            for (int i = 0; i < sequence.length; i++) {
                posPointer = generator.nextInt(0, numberPool.size() - 1);
                sequence[i] = numberPool.get(posPointer);
                numberPool.remove(posPointer);
            }
            validSequence = checkValidity();
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
    public void calculateSickness() {
        try {
            if(!SicknessCalculator.INSTANCE.checkUpToDate(nKnownSubmissions)) {
                sickness = SicknessCalculator.INSTANCE.calculateSickness(this);
                nKnownSubmissions = SicknessCalculator.INSTANCE.getNSubmissions();
            }
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
        if (position >= sequence.length) {
            throw new IndexOutOfBoundsException("Requested pos: " + position + " while length " + sequence.length);
        }
        return sequence[position];
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
