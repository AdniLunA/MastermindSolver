package evolution.mutation;

import evolution.IChromosome;

import java.util.ArrayList;

public interface IMutation {

    ArrayList<IChromosome> mutateGenes(ArrayList<IChromosome> genePool);

    IChromosome mutate(IChromosome chromosomeToMutate);
}
