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
        for(int number : numbersToAdd) {
            chosenChild.add(number);
        }
    }

    public void addToQueue(int[] numbersToAdd) {
        addToQueue(1,numbersToAdd);
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
        int[] array = new int[queue.size()];
        for(int i = 0; i < queue.size(); i++){
            array[i] = queue.poll();
        }
        return array;
    }
}