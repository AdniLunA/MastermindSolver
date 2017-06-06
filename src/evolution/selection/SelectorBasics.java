package evolution.selection;

import evolution.IChromosome;
import evolution.population.PopulationBasics;

import java.util.ArrayList;

abstract class SelectorBasics implements ISelection {

    /*--attributes*/
    PopulationBasics fatherPool;
    PopulationBasics motherPool;

    void splitPopulation(ArrayList<IChromosome> genePool) {
        /*inclusive from, exclusive to*/
        ArrayList<IChromosome> fatherAL = new ArrayList<>(genePool.subList(0, (genePool.size() / 2)));
        ArrayList<IChromosome> motherAL = new ArrayList<>(genePool.subList((genePool.size() / 2), genePool.size()));
        fatherPool = new PopulationBasics(fatherAL) {
        };
        motherPool = new PopulationBasics(motherAL);
    }
}