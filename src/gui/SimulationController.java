package gui;

import config.Configuration;

import java.util.ArrayList;

public class SimulationController {
    //constructors
    public SimulationController(int lengthOfCode, int numberOfColors, int numberOfTries) {
        this.lengthOfCode = lengthOfCode;
        this.numberOfColors = numberOfColors;
        this.numberOfTries = numberOfTries;

        generateMatrix(lengthOfCode, numberOfColors, numberOfTries);
    }
    public SimulationController() {
        this.lengthOfCode = Configuration.INSTANCE.DEFAULT_LENGTH_OF_CODE;
        this.numberOfColors = Configuration.INSTANCE.DEFAULT_NUMBER_OF_COLORS;
        this.numberOfTries = Configuration.INSTANCE.DEFAULT_NUMBER_OF_TRIES;

        generateMatrix(lengthOfCode, numberOfColors, numberOfTries);
    }

    //attributes
    private int lengthOfCode;
    private int numberOfColors;
    private int numberOfTries;

    private int simulationSpeed = 100;
    private boolean showBlackboxContent = Configuration.INSTANCE.DEFAULT_SHOW_BLACKBOX_CONTENT;
    private int[] redResponse = new int[numberOfTries];
    private int[] whiteResponse = new int[numberOfTries];
    private ArrayList<RowColorValues> solverSequences = new ArrayList<RowColorValues>();

    //functions

    private void generateMatrix(int code, int colors, int tries){
        System.out.println("SimulationController - generateMatrix");
    }

    private void onclickChangeSimulationSpeed(int newSpeed){
        System.out.println("SimulationController - onclickChangeSimulationSpeed from "+ simulationSpeed +" to " + newSpeed);
        simulationSpeed = newSpeed;
    }

    private void onclickChangeBlackboxState(boolean newState){
        System.out.println("SimulationController - onclickChangeBlackboxState from "+ showBlackboxContent +" to " + newState);
        showBlackboxContent = newState;
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

    public int getSimulationSpeed() {
        return simulationSpeed;
    }

    public void setSimulationSpeed(int simulationSpeed) {
        this.simulationSpeed = simulationSpeed;
    }

    public boolean isShowBlackboxContent() {
        return showBlackboxContent;
    }

    public void setShowBlackboxContent(boolean showBlackboxContent) {
        this.showBlackboxContent = showBlackboxContent;
    }

    public int[] getRedResponse() {
        return redResponse;
    }

    public void setRedResponse(int[] redResponse) {
        this.redResponse = redResponse;
    }

    public int[] getWhiteResponse() {
        return whiteResponse;
    }

    public void setWhiteResponse(int[] whiteResponse) {
        this.whiteResponse = whiteResponse;
    }

    public ArrayList<RowColorValues> getSolverSequences() {
        return solverSequences;
    }

    public void setSolverSequences(ArrayList<RowColorValues> solverSequences) {
        this.solverSequences = solverSequences;
    }
}
