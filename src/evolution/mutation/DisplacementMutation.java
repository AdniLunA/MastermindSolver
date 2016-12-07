package evolution.mutation;

import com.sun.deploy.config.Config;
import config.Configuration;
import config.MersenneTwisterFast;
import evolution.IChromosome;
import evolution.IPopulation;

public class DisplacementMutation implements IMutation {
    /***
     * attributes
     ***/
    MersenneTwisterFast generator = new MersenneTwisterFast(System.nanoTime());

    /***
     * functions
     ***/
    @Override
    public IChromosome[] getParents(IPopulation population) {
        for (int gene = 0; gene < population.getGenePool().length; gene++) {
            /*test if current gene should be manipulated*/

            if (generator.nextFloat() <= Configuration.INSTANCE.MUTATION_RATIO) {
                boolean validGeneFound = false;
                int countTry = 0;
                do {
                    int start = generator.nextInt(chromosomeString.length() / 2);
                    int end = generator.nextInt(start, chromosomeString.length());
                    while (end == chromosomeString.length() - 1) {
                        end = generator.nextInt(start, chromosomeString.length());
                    }
                    // System.out.println("Displace Chromosome: " +
                    // chromosomeString.toString());

                    String toDisplace = chromosomeString.substring(start, end);
                    chromosomeString.delete(start, end);

                    int displaceIndex = generator.nextInt(chromosomeString.length());
                    chromosomeString.insert(displaceIndex, toDisplace);

                    // System.out.println("New Chromosome: " + chromosomeString.toString());

                    IChromosome newChromosome = new Chromosome(chromosomeString.toString());
                    if (newChromosome.isInPriceBudget()) {
                        return newChromosome;
                    } else {
                        // System.out.println("Chromosome is not Valid. Create random
                        // Chromosome");
                        return gene.generateRandomChromosome();
                    }

                    countTry++;
                    if(countTry >= Configuration.INSTANCE.MUTATION_MAX_TRY_AGAIN){
                        /*give up*/
                        validGeneFound = true;
                    }
                } while (!validGeneFound);
            }
        }

    }
}
