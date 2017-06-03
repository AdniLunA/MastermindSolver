package evolution.mutation;

import evolution.IChromosome;

import java.util.ArrayList;

public interface IMutation {

    ArrayList<IChromosome> mutateGenes(ArrayList<IChromosome> genePool);

    int[] generateTwoSplitPositions(int max);
}
