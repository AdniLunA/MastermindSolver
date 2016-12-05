package evolution.crossover;

import evolution.IChromosome;

public interface ICrossover {
    IChromosome[] crossover(IChromosome parent1, IChromosome parent2);
}
