package evolution;

public interface IChromosome {
    public void generateRandom();

    public int getFitness();

    void incrementGeneration();

    public int[] getSequence();

    public int getGeneration();

    boolean checkValidity();

    /*Java.util.Arrays.sort(int[])*/
    int[] getSequenceSorted();

    int getNumberOfColors();

    void setGeneration(int generation);

    int getLength();
}
