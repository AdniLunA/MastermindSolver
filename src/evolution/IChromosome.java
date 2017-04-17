package evolution;

public interface IChromosome {
    void generateRandom();

    int getFitness();

    void incrementGeneration();

    int[] getSequence();

    int getGeneration();

    boolean checkValidity();

    /*Java.util.Arrays.sort(int[])*/
    int[] getSequenceSorted();

    void setGeneration(int generation);
}
