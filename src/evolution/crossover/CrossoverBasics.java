package evolution.crossover;

import config.MersenneTwisterFast;
import engine.GameSettings;
import evolution.IChromosome;
import evolution.NumChromosome;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

abstract class CrossoverBasics implements ICrossover {
    /*--
     * attributes
     */
    MersenneTwisterFast randomGenerator = new MersenneTwisterFast(System.nanoTime());
    IChromosome parent1;
    IChromosome parent2;
    int sequenceLength = GameSettings.INSTANCE.lengthOfCode;
    ArrayList<Integer> splitPos;
    IChromosome[] children = new IChromosome[2];
    int[] child1Sequence = new int[GameSettings.INSTANCE.lengthOfCode];
    int[] child2Sequence = new int[GameSettings.INSTANCE.lengthOfCode];

    private Queue<Integer> in1ToMuch = new LinkedList<>();
    private Queue<Integer> in2ToMuch = new LinkedList<>();
    private Queue<Integer> c1Copy = new LinkedList<>();
    private Queue<Integer> c2Copy = new LinkedList<>();
    private Queue<Integer> alt1 = new LinkedList<>();
    private Queue<Integer> alt2 = new LinkedList<>();
    private HashSet<Integer> overlap = new HashSet<>();

    /*--
     * functions
     */
    void setChildrenHealthy() {
        boolean childrenAreSick = !(new NumChromosome(child1Sequence).checkValidity() && new NumChromosome(child2Sequence).checkValidity());
        c1Copy.clear();
        c2Copy.clear();
        alt1.clear();
        alt2.clear();
        overlap.clear();
        if (childrenAreSick) {
            initializeQueues();

            while (!in1ToMuch.isEmpty() && !in2ToMuch.isEmpty()) {
                match(in1ToMuch.poll(), in2ToMuch.poll());
            }
            while (!in1ToMuch.isEmpty()) {
                match(in1ToMuch.poll(), alt2.poll());
            }
            while (!in2ToMuch.isEmpty()) {
                match(alt1.poll(), in2ToMuch.poll());
            }
        }
        children[0] = new NumChromosome(child1Sequence);
        children[1] = new NumChromosome(child2Sequence);
        children[0].setGeneration(parent1.getGeneration() + 1);
        children[1].setGeneration(parent2.getGeneration());
        if (!(children[0].checkValidity() && children[1].checkValidity())) {
            setChildrenHealthy();
        }
    }

    private void initializeQueues() {
        /*find duplicate genes*/
        for (int i = 0; i < child1Sequence.length; i++) {
            int gene1 = child1Sequence[i];
            int gene2 = child2Sequence[i];
            if (c1Copy.contains(gene1)) {
                in1ToMuch.add(gene1);
            } else {
                c1Copy.add(gene1);
            }
            if (c2Copy.contains(gene2)) {
                in2ToMuch.add(gene2);
            } else {
                c2Copy.add(gene2);
            }

            if (alt1.contains(gene1)) {
                alt1.remove(gene1);
            } else {
                alt1.add(gene1);
            }
            if (alt2.contains(gene2)) {
                alt2.remove(gene2);
            } else {
                alt2.add(gene2);
            }
        }
        for (Integer candidate : (alt1.size() > alt2.size() ? alt1 : alt2)) {
            if (((alt1.size() < alt2.size() ? alt1 : alt2)).contains(candidate)) {
                overlap.add(candidate);
            }
        }
        alt1.removeAll(overlap);
        alt2.removeAll(overlap);
    }

    private void match(int gene1ToFind, int gene2ToFind) {
        for (int i = 0; i < child1Sequence.length; i++) {
            if (child1Sequence[i] == gene1ToFind) {
                child1Sequence[i] = gene2ToFind;
                break;
            }
        }
        for (int i = 0; i < child2Sequence.length; i++) {
            if (child2Sequence[i] == gene2ToFind) {
                child2Sequence[i] = gene1ToFind;
                break;
            }
        }
    }

    /**
     * calculate splitting position.
     * search if new position is duplicate.
     * if that is the case, search new position.
     */
    int createValidRandomSplitPos(int currentPos) {
        int pos = 0;
        ArrayList<Integer> possibleSplitPos = new ArrayList<>();
        for (int i = 1; i < sequenceLength - 1; i++) {
            if (!splitPos.contains(i)) {
                possibleSplitPos.add(i);
            }
        }
        try {
            /*nextInt: incl. minimum, incl. maximum*/
            if (!possibleSplitPos.isEmpty()) {
                pos = possibleSplitPos.get(randomGenerator.nextInt(0, possibleSplitPos.size() - 1));
            }
        } catch (RuntimeException r) {
            System.out.println("Crossover - createValidRandomSplitPos: - cannot resolve new position, last value: " + pos);
            r.printStackTrace();
        }
        return pos;
    }
}
