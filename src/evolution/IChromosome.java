package evolution;

public interface IChromosome {
    public void generateRandom();
    public int getFitness();
    public int[] getSequence();
}
