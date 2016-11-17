package gui;

import config.Configuration;
import evolution.IChromosome;
import evolution.Chromosome;

public class ConfigurationPage {
    //attributes
    private int lengthOfCode = Configuration.INSTANCE.DEFAULT_LENGTH_OF_CODE;
    private int numberOfColors = Configuration.INSTANCE.DEFAULT_NUMBER_OF_COLORS;
    private int numberOfTries = Configuration.INSTANCE.DEFAULT_NUMBER_OF_TRIES;

    //functions
    private void saveSettings(){
        System.out.println("Configuration Page - saveSettings");
    }

    public void generateCodeSettingField(){
        System.out.println("Configuration Page - generateCodeSettingField");
    }

    public IChromosome onclickGetRandomCode(){
        System.out.println("Configuration Page - onclickGetRandomCode");
        return new Chromosome();
    }

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
