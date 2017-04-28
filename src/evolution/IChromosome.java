package evolution;

public interface IChromosome {
    int getSickness();

    int getChromosomeAtPos(int position);

    void incrementGeneration();

    int[] getSequence();

    int getGeneration();

    boolean checkValidity();

    /*Java.util.Arrays.sort(int[])*/
    int[] getSequenceSorted();

    void setGeneration(int generation);
}
