package gui;

import config.Configuration;
import evolution.Submission;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;

public class SimulationController implements Initializable {
    /*attributes*/
    private int lengthOfCode;
    private int numberOfTries;

    private boolean gameIsRunning = false;
    private boolean gameIsPaused = false;

    private int currentLineToPrint = 0;

    /*default values*/
    private boolean showBlackboxContent = Configuration.INSTANCE.DEFAULT_SHOW_BLACKBOX_CONTENT;
    private boolean runAutomated = Configuration.INSTANCE.DEFAULT_RUN_AUTOMATED;
    private int simulationSpeed = Configuration.INSTANCE.DEFAULT_SIMULATION_SPEED;

    @FXML
    private CheckBox cbShowSecretCode;

    @FXML
    private RadioButton rbRunAutomated;
    @FXML
    private Group gRunAutomatedSettings;
    @FXML
    private Slider spd_slider;
    @FXML
    private Button bContinueSimulation;
    @FXML
    private Button bPauseSimulation;

    @FXML
    private RadioButton rbRunManually;
    @FXML
    private Button bNextStep;

    @FXML
    private Button bStartSimulation;

    @FXML
    private Pane pBlackBox;
    private Circle[] blackBox;

    @FXML
    private AnchorPane apScrollableField;
    @FXML
    private Pane pGameArea;

    private Circle[][] circleMatrix;

    /*functions*/
    private void addNewLine(Submission lineInfo){
        System.out.println("SimulationController - addNewLine");
    }

    private void generateMatrix() {
        System.out.println("SimulationController - generateMatrix");
        int x = lengthOfCode;
        int y = numberOfTries;

        /*circle positions*/
        float[] circleXPositions = new float[lengthOfCode];
        float cXDistance = 500f / lengthOfCode;
        circleXPositions[0] = 100f + (cXDistance / 2f);
        for (int i = 1; i < lengthOfCode; i++) {
            circleXPositions[i] = circleXPositions[i-1]+cXDistance;
        }

        int[] circleYPositions = new int[numberOfTries];
        int cYDistance = 50;
        circleYPositions[0] = cYDistance;
        for (int i = 1; i < numberOfTries; i++) {
            circleYPositions[i] = circleYPositions[i-1]+cYDistance;
        }

        /*gameFieldHeight*/
        apScrollableField.setPrefHeight(circleYPositions[numberOfTries-1]+cYDistance);

        /*place blackBox circles*/
        blackBox = new Circle[lengthOfCode];
        for(int i = 0; i < lengthOfCode; i++){
            blackBox[i] = new Circle(15, Color.BLACK);
            blackBox[i].relocate(circleXPositions[i]-115, 0.0);
        }
        pBlackBox.getChildren().addAll(blackBox);

        /*place game circles*/
        circleMatrix = new Circle[lengthOfCode][numberOfTries];
        for(int row = 0; row < lengthOfCode; row++){
            for(int col = 0; col < numberOfTries; col ++){
                circleMatrix[row][col] = new Circle(15, Color.BLACK);
                circleMatrix[row][col].relocate(circleXPositions[row], circleYPositions[col]);
            }
            pBlackBox.getChildren().addAll(circleMatrix[row]);
        }
    }

    private void onclickChangeSimulationSpeed(int newSpeed) {
        System.out.println("SimulationController - onclickChangeSimulationSpeed from " + simulationSpeed + " to " + newSpeed);
        simulationSpeed = newSpeed;
    }

    private void onclickChangeBlackboxState(boolean newState) {
        System.out.println("SimulationController - onclickChangeBlackboxState from " + showBlackboxContent + " to " + newState);
        showBlackboxContent = newState;
    }

    private void refreshDependencies(){
        System.out.println("SimulationController - refreshDependencies");
        /*show secret code?*/
        pBlackBox.setVisible(cbShowSecretCode.isSelected());

        /*run simulation automated or manually?*/
        runAutomated = rbRunAutomated.isSelected();
        gRunAutomatedSettings.setVisible(runAutomated);
        bNextStep.setVisible(!runAutomated);
        /*before simulation begins, none of the simulation handling buttons are visible*/
        if(!gameIsRunning){
            bPauseSimulation.setVisible(false);
            bContinueSimulation.setVisible(false);
            bNextStep.setVisible(false);
        }
    }

    private void switchSimulationMode() {
        System.out.println("SimulationController - switchSimulationMode");
        rbRunAutomated.setSelected(!runAutomated);
        rbRunManually.setSelected(runAutomated);

        runAutomated = rbRunAutomated.isSelected();

        /*if runAutomated just turned true, "pause simulation" must be visible*/
        if (runAutomated) {
            bPauseSimulation.setVisible(true);
            bContinueSimulation.setVisible(false);
        } else { /*if runAutomated just turned false, pause automated simulation*/
            gameIsPaused = true;
        }

        refreshDependencies();
    }

    private LinearGradient getCircleGradient(int color){
        LinearGradient gradient = new LinearGradient(0f, 1f, 1f, 0f, true, CycleMethod.NO_CYCLE,
                new Stop(0, Configuration.INSTANCE.COLORS[color]),
                new Stop(1, Color.web("#ffffff")));
        /*this.fillProperty().set(gradient);*/
        return gradient;
    }

    @FXML
    private void onclickStartSimulation(){
        System.out.println("SimulationController - onclickStartSimulation");
        gameIsRunning = true;
        bStartSimulation.setVisible(false);
        bPauseSimulation.setVisible(rbRunAutomated.isSelected());
        bNextStep.setVisible(!rbRunAutomated.isSelected());
    }

    @FXML
    private void onclickRunAutomated(){
        switchSimulationMode();
    }

    @FXML
    private void onclickRunManually(){
        switchSimulationMode();
    }

    @FXML
    private void onclickPauseSimulation(){
        System.out.println("SimulationController - onclickPauseSimulation");
        bContinueSimulation.setVisible(true);
        bPauseSimulation.setVisible(false);
        gameIsPaused = true;
    }

    @FXML
    private void onclickContinueSimulation(){
        System.out.println("SimulationController - onclickContinueSimulation");
        bPauseSimulation.setVisible(true);
        bContinueSimulation.setVisible(false);
        gameIsPaused = false;
    }

    @FXML
    private void onclickNextStep(){
        System.out.println("SimulationController - onclickNextStep");
        /*todo*/
    }

    @FXML
    private void onclickReturnToConfigPg(){
        System.out.println("SimulationController - onclickReturnToConfigPg");
        /*todo*/
    }

    @FXML
    private void onclickShowSecretCode(){

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("SimulationController -  initialize");
        /*initialize variables*/
        GUIManager manager = GUIManager.getInstance();
        lengthOfCode = manager.getLengthOfCode();
        numberOfTries = manager.getNumberOfTries();

        /*initialize page settings*/
        generateMatrix();
        cbShowSecretCode.setSelected(showBlackboxContent);
        rbRunAutomated.setSelected(runAutomated);
        rbRunManually.setSelected(!runAutomated);
        spd_slider.setValue(simulationSpeed);


        /*todo: set code colors*/


        refreshDependencies();

    }

    /*getter + setter*/

}
