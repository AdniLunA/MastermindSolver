package evolution;

public interface IChromosome {
    public void generateRandom();

    public int getFitness();
    public int[] getSequence();

    //Java.util.Arrays.sort(int[])
    int[] getSequenceSorted();

    int getNumberOfColors();
}
