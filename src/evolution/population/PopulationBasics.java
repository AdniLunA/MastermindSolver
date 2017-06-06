package evolution.population;

import engine.GameSettings;
import evolution.IChromosome;

import java.util.ArrayList;

public class PopulationBasics {

    /*--
         * constructors
         */
    PopulationBasics() {
    }

    public PopulationBasics(ArrayList<IChromosome> genePool) {
        this.genePool.clear();
        this.genePool.addAll(genePool);
        this.populationSize = genePool.size();
    }

    /*--
     * attributes
     */
    ArrayList<IChromosome> genePool = new ArrayList<>(GameSettings.INSTANCE.sizeOfPopulation);
    int populationSize;

    /*--
     * functions
     */
    public IChromosome getFittest() {
        ArrayList<IChromosome> sortedPop = getPopulationSorted();
        return sortedPop.get(0);
    }

    public int getSumPopulationSickness() {
        //logger.info("");
        int sum = 0;
        for (IChromosome chromosome : genePool) {
            sum += chromosome.getSickness();
        }
        return sum;
    }

    ArrayList<IChromosome> getPopulationSorted() {
        ArrayList<IChromosome> sortedCopy = new ArrayList<>();
        sortedCopy.addAll(genePool);
        sortedCopy.sort((c1, c2) -> c1.compare(c1, c2));
        return sortedCopy;
    }

    /*--
     * getter + setter
     */
    public ArrayList<IChromosome> getGenePool() {
        return genePool;
    }
}
