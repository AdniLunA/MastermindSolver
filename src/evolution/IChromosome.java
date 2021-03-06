package evolution;

public interface IChromosome {
    int getSickness();

    int getChromosomeAtPos(int position);

    int[] getSequence();

    int getGeneration();

    void calculateSickness();

    boolean checkValidity();

    int compare(IChromosome one, IChromosome two);

    void setGeneration(int generation);
}