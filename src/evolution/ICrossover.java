package evolution;

public interface ICrossover {
    IChromosome[] crossover(IChromosome parent1, IChromosome parent2);
}
