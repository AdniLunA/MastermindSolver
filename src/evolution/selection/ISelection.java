package evolution.selection;

import evolution.IChromosome;

public interface ISelection {
    public IChromosome[] getParents(IChromosome[] genePool);

}
