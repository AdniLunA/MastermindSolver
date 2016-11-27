package engine;

import evolution.NumChromosome;
import evolution.IChromosome;

public class CodeGenerator {
    //attributes
    private IChromosome randomChromosome = new NumChromosome();

    //functions
    public void generateRandomChromosome(){
        System.out.println("CodeGenerator - generateRandomChromosome");
    }

    //getter + setter
    public IChromosome getRandomChromosome() {
        return randomChromosome;
    }

    public void setRandomChromosome(IChromosome randomChromosome) {
        this.randomChromosome = randomChromosome;
    }
}
