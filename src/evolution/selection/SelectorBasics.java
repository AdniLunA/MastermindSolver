package evolution.selection;

import config.MersenneTwisterFast;
import evolution.IChromosome;
import evolution.IPopulation;
import evolution.Population;

import java.util.ArrayList;

abstract class SelectorBasics implements ISelection{

    /*--attributes*/
    IPopulation fatherPool;
    IPopulation motherPool;

    void splitPopulation(ArrayList<IChromosome> genePool) {
        /*inclusive from, exclusive to*/
        ArrayList<IChromosome> fatherAL = new ArrayList<IChromosome>(genePool.subList(0, (genePool.size()/2)));
        ArrayList<IChromosome> motherAL = new ArrayList<IChromosome>(genePool.subList((genePool.size()/2), genePool.size()));
        fatherPool = new Population(fatherAL);
        motherPool = new Population(motherAL);
    }
}
