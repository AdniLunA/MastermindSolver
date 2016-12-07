package evolution.mutation;

import evolution.IChromosome;
import evolution.IPopulation;

public interface IMutation {

    IChromosome[] getParents(IPopulation population);
}
