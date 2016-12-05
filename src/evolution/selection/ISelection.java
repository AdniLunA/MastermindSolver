package evolution.selection;

import config.SelectionEnum;
import evolution.IChromosome;
import evolution.IPopulation;

public interface ISelection {
    public IChromosome[] getParents(IPopulation population);

}
