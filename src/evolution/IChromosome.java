package evolution;

public interface IChromosome {
    public void generateRandom(int lengthOfCode, int numberOfColors);
    public int getFitness();
}
