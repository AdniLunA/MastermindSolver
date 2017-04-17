package evolution.crossover;

import evolution.IChromosome;

public interface ICrossover {
    IChromosome[] crossParents(IChromosome[] parents);
}
