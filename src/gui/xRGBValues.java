package gui;

import evolution.IChromosome;

public class xRGBValues {
    //todo
    //constructor
    xRGBValues(IChromosome sequence){
        transformSequenceToRGB();
    }

    //attributes
    private int[] rgb = new int[3];

    //functions
    public void transformSequenceToRGB(){
        //todo set rgb
        System.out.println("xRGBValues - transformSequenceToRGB");
    }

    //getter + setter
    public int[] getRgb() {
        return rgb;
    }
}
