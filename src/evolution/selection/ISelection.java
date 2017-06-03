package evolution.selection;

import evolution.IChromosome;

import java.util.ArrayList;

public interface ISelection {
    IChromosome[] getParents(ArrayList<IChromosome> genePool);

}
