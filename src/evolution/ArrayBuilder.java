package evolution;

import java.util.LinkedList;
import java.util.Queue;

public class ArrayBuilder {
    //attributes
    Queue<Integer> child1Q = new LinkedList<Integer>();
    Queue<Integer> child2Q = new LinkedList<Integer>();

    //functions
    public void addToQueue(int numberOfChild, int[] numbersToAdd) {
        Queue<Integer> chosenChild = ((numberOfChild == 1) ? child1Q : child2Q);
        for (int number : numbersToAdd) {
            chosenChild.add(number);
        }
    }

    public int[] getChild1Sequence() {
        return transformSequence(child1Q);
    }

    public int[] getChild2Sequence() {
        return transformSequence(child2Q);
    }

    private int[] transformSequence(Queue<Integer> queue) {
        if (queue.isEmpty()) {
            throw new NullPointerException("ArrayBuilder - transformSequence ERROR: Array is empty.");
        } else {
            int size = queue.size();
            int[] array = new int[size];
            for (int i = 0; i < size; i++) {
                array[i] = queue.poll();
            }
            return array;
        }
    }
}