package evolution;

public interface IChromosome {
    public void generateRandom();

    public int getFitness();
    public int[] getSequence();
    public int getGeneration();

    //Java.util.Arrays.sort(int[])
    int[] getSequenceSorted();

    int getNumberOfColors();

    void setGeneration(int generation);
}
