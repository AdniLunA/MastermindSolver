package gui;

import evolution.IChromosome;

public class RGBValues {
    //todo
    //constructor
    RGBValues(IChromosome sequence){
        transformSequenceToRGB();
    }

    //attributes
    private int[] rgb = new int[3];

    //functions
    public void transformSequenceToRGB(){
        //todo set rgb
        System.out.println("RGBValues - transformSequenceToRGB");
    }

    //getter + setter
    public int[] getRgb() {
        return rgb;
    }
}
