package evolution;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class ArrayBuilder {
    //attributes
    Queue<Integer> child1Q = new LinkedList<Integer>();
    Queue<Integer> child2Q = new LinkedList<Integer>();

    //functions
    public void addToQueue(int numberOfChild, int[] numbersToAdd) {
        Queue<Integer> chosenChild = ((numberOfChild == 1) ? child1Q : child2Q);
        for(int number : numbersToAdd) {
            chosenChild.add(number);
        }
    }

    public void addToQueue(int[] numbersToAdd) {
        addToQueue(1,numbersToAdd);
    }

    public int getLength(){
        return transformSequence(child1Q).length;
    }

    public int[] insert(int position, int[] toInsert){
        int[] sequence = getChild1Sequence();
        if(position < getLength() && position > -1){
            addToQueue(2, Arrays.copyOfRange(sequence, 0, position));
            addToQueue(2, toInsert);
            addToQueue(2, Arrays.copyOfRange(sequence, position, sequence.length));
        } else {
            System.out.println("ArrayBuilder - insert: ERROR, given position "+position+" must be between 0 and "+ sequence.length);
        }
        return sequence;
    }

    public int[] getSequence(){
        return getChild1Sequence();
    }

    public int[] getChild1Sequence(){
        return transformSequence(child1Q);
    }

    public int[] getChild2Sequence(){
        return transformSequence(child2Q);
    }

    private int[] transformSequence(Queue<Integer> queue){
        int size = queue.size();
        int[] array = new int[size];
        for(int i = 0; i < size; i++){
            array[i] = queue.poll();
        }
        return array;
    }
}