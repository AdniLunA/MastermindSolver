package evolution.mutation;

import evolution.IChromosome;
import evolution.IPopulation;

public interface IMutation {

    IChromosome[] mutateGenes(IChromosome[] genePool);

    int[] generateTwoSplitPositions(int max);
}
