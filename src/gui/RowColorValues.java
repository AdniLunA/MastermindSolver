package gui;

import evolution.IChromosome;

import java.util.ArrayList;

public class RowColorValues {
    //constructor
    public RowColorValues(){

    }

    public RowColorValues(IChromosome sequence){
        rowColors.add(new xRGBValues(sequence));
    }

    //attributes
    private ArrayList<xRGBValues> rowColors = new ArrayList<xRGBValues>();

    //functions

    //getter + setter
    public ArrayList<xRGBValues> getRowColors() {
        return rowColors;
    }

    public void setRowColors(ArrayList<xRGBValues> rowColors) {
        this.rowColors = rowColors;
    }
}
