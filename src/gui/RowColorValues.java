package gui;

import evolution.IChromosome;

import java.util.ArrayList;

public class RowColorValues {
    //constructor
    public RowColorValues(){

    }

    public RowColorValues(IChromosome sequence){
        rowColors.add(new RGBValues(sequence));
    }

    //attributes
    private ArrayList<RGBValues> rowColors = new ArrayList<RGBValues>();

    //functions

    //getter + setter
    public ArrayList<RGBValues> getRowColors() {
        return rowColors;
    }

    public void setRowColors(ArrayList<RGBValues> rowColors) {
        this.rowColors = rowColors;
    }
}
