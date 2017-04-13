package evolution.mutation;

import evolution.IChromosome;

public interface IMutation {

    IChromosome[] mutateGenes(IChromosome[] genePool);

    int[] generateTwoSplitPositions(int max);
}
