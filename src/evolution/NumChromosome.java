package evolution;

public class NumChromosome implements IChromosome {
    public NumChromosome() {
    }

    public NumChromosome(int[] sequence) {
        this.sequence = sequence;
    }

    private int lengthOfCode;
    private int numberOfColors;
    private int[] sequence;

    public NumChromosome(int lengthOfCode, int numberOfColors) {
        this.lengthOfCode = lengthOfCode;
        this.numberOfColors = numberOfColors;
    }

    @Override
    public int[] getSequence(){
        return sequence;
    }

    @Override
    public void generateRandom() {
        System.out.println("NumChromosome - generateRandom");
        sequence = new int[lengthOfCode];
        for(int i = 0; i < sequence.length; i++){
            sequence[i] = (int) Math.floor((Math.random() * numberOfColors) + 1);
        }
    }

    @Override
    public int getFitness() {
        System.out.println("NumChromosome - getFitness");
        //todo
        return 0;
    }

    public String toString(){
        String array = "";
        for (int elem: sequence) {
            array += elem+", ";
        }
        return array;
    }
}
