package evolution;

public class NumChromosome implements IChromosome {
    private int[] sequence;

    public int[] getSequence(){
        return sequence;
    }

    @Override
    public void generateRandom(int loc, int noc) {
        System.out.println("NumChromosome - generateRandom");
        sequence = new int[loc];
        for(int i = 0; i < sequence.length; i++){
            sequence[i] = (int) Math.round(Math.random() * noc);
        }
    }

    @Override
    public int getFitness() {
        System.out.println("NumChromosome - getFitness");
        //todo
        return 0;
    }
}
