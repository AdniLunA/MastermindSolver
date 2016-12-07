package test;

import com.sun.deploy.config.Config;
import config.Configuration;
import engine.GameEngine;
import evolution.IChromosome;
import evolution.Population;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class PopulationTest {
    @Test
    public void sortingTest(){
        Population population = new Population();
        IChromosome[] sortedGenePool = population.getPopulationSorted();
        IChromosome expectedFittest = sortedGenePool[sortedGenePool.length-1];
        IChromosome expectedSickest = sortedGenePool[0];
        assertTrue(expectedFittest.getFitness() > expectedSickest.getFitness());
    }
}
