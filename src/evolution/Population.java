package evolution;

public class Population {
    //attributes
    private int generationCounter = 0;
    private IChromosome[] generation;

    //functions
    public IChromosome getFittest(){
        System.out.println("Population - getFittest");
        return new NumChromosome(1,1);//todo
    }

    public void evolve(){
        System.out.println("Population - evolve");
    }

    public void sortPopulation(){
        System.out.println("Population - sortPopulation");
    }

    //getter + setter
    public int getGenerationCounter() {
        return generationCounter;
    }

    public void setGenerationCounter(int generationCounter) {
        this.generationCounter = generationCounter;
    }

    public IChromosome[] getGeneration() {
        return generation;
    }

    public void setGeneration(IChromosome[] generation) {
        this.generation = generation;
    }
}
