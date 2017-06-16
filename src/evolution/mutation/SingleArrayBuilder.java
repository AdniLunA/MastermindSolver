package evolution.mutation;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class SingleArrayBuilder {
    /*attributes*/
    private Queue<Integer> arrayToBuild = new LinkedList<>();

    /*functions*/
    void addToQueue(int[] numbersToAdd) {
        for (int number : numbersToAdd) {
            arrayToBuild.add(number);
        }
    }

    public int getLength() {
        return arrayToBuild.size();
    }

    int[] insert(int position, int[] toInsert) {
        int[] sequence = getSequence();
        if (position < sequence.length && position > -1) {
            addToQueue(Arrays.copyOfRange(sequence, 0, position));
            addToQueue(toInsert);
            addToQueue(Arrays.copyOfRange(sequence, position, sequence.length));
        } else {
            System.out.println("SingleArrayBuilder - insert - ERROR, given position " + position + " must be between 0 and " + sequence.length);
        }
        return getSequence();
    }

    public int[] getSequence() {
        return transformSequence(arrayToBuild);
    }

    private int[] transformSequence(Queue<Integer> queue) {
        if (queue.isEmpty()) {
            throw new NullPointerException("SingleArrayBuilder - transformSequence ERROR: Array is empty.");
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