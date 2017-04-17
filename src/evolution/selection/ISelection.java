package evolution.selection;

import evolution.IChromosome;

public interface ISelection {
    IChromosome[] getParents(IChromosome[] genePool);

}
