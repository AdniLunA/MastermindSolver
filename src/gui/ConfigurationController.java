package gui;

import config.Configuration;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
//import evolution.NumChromosome;
//import evolution.IChromosome;

public class ConfigurationController {
    //attributes
    private int lengthOfCode = Configuration.INSTANCE.DEFAULT_LENGTH_OF_CODE;
    private int numberOfColors = Configuration.INSTANCE.DEFAULT_NUMBER_OF_COLORS;
    private int numberOfTries = Configuration.INSTANCE.DEFAULT_NUMBER_OF_TRIES;

    @FXML
    TextField loc_textfield;
    @FXML
    Slider loc_slider;

    //functions
    private int checkBoundaries(String sValue, int maxValue){
        try{
            int value = Integer.parseInt(sValue);
            if (value < 1) {
                return 1;
            }
            if (value > maxValue){
                return maxValue;
            }
            else {
                return value;
            }
        } catch (NumberFormatException e){
            System.out.println("Invalid entry.");
            return 0;
        }
    }

    public void onchangeLengthOfCodeTextfield(){
        System.out.println("ConfigurationController - onchangeLengthOfCodeTextfield");

        int newValue = checkBoundaries(loc_textfield.getText(), Configuration.INSTANCE.MAX_LENGTH_OF_CODE);
        if (newValue != 0) {
            loc_textfield.setStyle("-fx-text-inner-color: black;");
            lengthOfCode = newValue;
            loc_slider.setValue((double) newValue);
            loc_textfield.setText(""+newValue);
        } else {
            loc_textfield.setStyle("-fx-text-inner-color: red;");
        }
    }


    public void saveSettings(){
        System.out.println("Configuration Page - saveSettings");
    }

    public void generateCodeSettingField(){
        System.out.println("Configuration Page - generateCodeSettingField");
    }

    /*
    public IChromosome onclickGetRandomCode(){
        System.out.println("Configuration Page - onclickGetRandomCode");
        return new NumChromosome();
    }
    */

    public void onclickStartSimulation(){
        System.out.println("Configuration Page - onclickStartSimulation");
    }

    //getter + setter
    public int getLengthOfCode() {
        return lengthOfCode;
    }

    public void setLengthOfCode(int lengthOfCode) {
        this.lengthOfCode = lengthOfCode;
    }

    public int getNumberOfColors() {
        return numberOfColors;
    }

    public void setNumberOfColors(int numberOfColors) {
        this.numberOfColors = numberOfColors;
    }

    public int getNumberOfTries() {
        return numberOfTries;
    }

    public void setNumberOfTries(int numberOfTries) {
        this.numberOfTries = numberOfTries;
    }
}
