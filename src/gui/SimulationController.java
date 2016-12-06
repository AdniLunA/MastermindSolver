package gui;

import config.Configuration;
import evolution.Submission;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
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
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

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
    private Rectangle[] rRedFeedback;
    private Rectangle[] rWhiteFeedback;

    /*functions*/
    private void addNewLine(Submission lineInfo){
        System.out.println("SimulationController - addNewLine");
    }

    private void generateGameMatrix() {
        System.out.println("SimulationController - generateGameMatrix");
        int x = lengthOfCode;
        int y = numberOfTries;

        /*y positions for circles + rectangles*/
        int[] yPositions = new int[numberOfTries];
        int yDistance = 60;
        yPositions[0] = yDistance - 25;
        for (int i = 1; i < numberOfTries; i++) {
            yPositions[i] = yPositions[i-1]+yDistance;
        }

        /*circle positions*/
        float[] circleXPositions = new float[lengthOfCode];
        float cXDistance = 500f / lengthOfCode;
        circleXPositions[0] = 100f + (cXDistance / 2f) - 15f;
        for (int i = 1; i < lengthOfCode; i++) {
            circleXPositions[i] = circleXPositions[i-1]+cXDistance;
        }

        /*gameFieldHeight*/
        apScrollableField.setPrefHeight(yPositions[numberOfTries-1]+yDistance);

        /*place blackBox circles*/
        blackBox = new Circle[lengthOfCode];
        for(int i = 0; i < lengthOfCode; i++){
            blackBox[i] = new Circle(15, Color.BLACK);
            blackBox[i].relocate(circleXPositions[i]-100, 0.0);
        }
        pBlackBox.getChildren().addAll(blackBox);

        /*place game circles*/
        circleMatrix = new Circle[lengthOfCode][numberOfTries];
        for(int row = 0; row < lengthOfCode; row++){
            for(int col = 0; col < numberOfTries; col ++){
                circleMatrix[row][col] = new Circle(15, Color.BLACK);
                circleMatrix[row][col].relocate(circleXPositions[row], yPositions[col]);
            }
            pGameArea.getChildren().addAll(circleMatrix[row]);
        }

        /*place rectangles*/
        rRedFeedback = new Rectangle[numberOfTries];
        rWhiteFeedback = new Rectangle[numberOfTries];
        for(int i = 0; i < numberOfTries; i++) {
            /*red*/
            rRedFeedback[i] = new Rectangle(60, 40, Color.PINK);
            rRedFeedback[i].relocate(20.0, yPositions[i]-4);
            /*white*/
            rWhiteFeedback[i] = new Rectangle(60,40, Color.WHITE);
            rWhiteFeedback[i].relocate(620.0, yPositions[i]-4);
        }
        pGameArea.getChildren().addAll(rRedFeedback);
        pGameArea.getChildren().addAll(rWhiteFeedback);

        /*place lines*/
        int[] lineYPositions = new int[numberOfTries-1];
        lineYPositions[0] = 81;
        int lineYDistance = 60;
        for (int i = 1; i < lineYPositions.length; i++) {
            lineYPositions[i] = lineYPositions[i-1] + lineYDistance;
        }

        Line[] lines = new Line[numberOfTries-1];
        for (int i = 0; i < lines.length; i++) {
            lines[i] = new Line(0.0, lineYPositions[i], 700.0, lineYPositions[i]);
            lines[i].setStroke(Color.DARKGRAY);
        }
        pGameArea.getChildren().addAll(lines);
    }

    private void onclickChangeSimulationSpeed(int newSpeed) {
        System.out.println("SimulationController - onclickChangeSimulationSpeed from " + simulationSpeed + " to " + newSpeed);
        simulationSpeed = newSpeed;
    }

    private void onclickChangeBlackBoxState(boolean newState) {
        System.out.println("SimulationController - onclickChangeBlackBoxState from " + showBlackboxContent + " to " + newState);
        showBlackboxContent = newState;
    }

    private void refreshDependencies(){
        System.out.println("SimulationController - refreshDependencies");
        /*show secret code?*/
        for (Circle circle : blackBox) {
            circle.setVisible(cbShowSecretCode.isSelected());
        }

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
        //System.out.println("SimulationController - getCircleGradient");
        LinearGradient gradient = new LinearGradient(0f, 1f, 1f, 0f, true, CycleMethod.NO_CYCLE,
                new Stop(0, Configuration.INSTANCE.COLORS[color]),
                new Stop(1, Color.web("#ffffff")));
        /*this.fillProperty().set(gradient);*/
        return gradient;
    }

    private void fillCircleLine(Circle[] circles, int colors[]){
        System.out.printf("SimulationController - fillCircleLine: ");
        for (int i = 0; i < circles.length; i++) {
            System.out.printf(colors[i] +" ");
            circles[i].fillProperty().set(getCircleGradient(colors[i]));
        }
        System.out.println();
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
        GUIManager.getInstance().returnToConfigurationPage();
    }

    @FXML
    private void onclickShowSecretCode(){
        System.out.println("SimulationController - onclickShowSecretCode");
        refreshDependencies();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("SimulationController -  initialize");
        /*initialize variables*/
        GUIManager manager = GUIManager.getInstance();
        lengthOfCode = manager.getLengthOfCode();
        numberOfTries = manager.getNumberOfTries();

        /*initialize page settings*/
        generateGameMatrix();
        cbShowSecretCode.setSelected(showBlackboxContent);
        rbRunAutomated.setSelected(runAutomated);
        rbRunManually.setSelected(!runAutomated);
        spd_slider.setValue(simulationSpeed);
        fillCircleLine(blackBox, manager.getSecretCode());

        /*todo: set code colors*/


        refreshDependencies();

    }

    /*getter + setter*/

}
