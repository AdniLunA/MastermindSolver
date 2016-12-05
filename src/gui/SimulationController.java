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
    CheckBox cbShowSecretCode;
    @FXML
    Group gBlackBox;

    @FXML
    RadioButton rbRunAutomated;
    @FXML
    Group gRunAutomatedSettings;
    @FXML
    Slider spd_slider;
    @FXML
    Button bContinueSimulation;
    @FXML
    Button bPauseSimulation;

    @FXML
    RadioButton rbRunManually;
    @FXML
    Button bNextStep;

    @FXML
    Button bStartSimulation;

    @FXML
    Canvas cGameField;
    @FXML
    AnchorPane apScrollableField;



    /*functions*/
    private void addNewLine(Submission lineInfo){
        System.out.println("SimulationController - addNewLine");
    }

    private void generateMatrix() {
        System.out.println("SimulationController - generateMatrix");
        int x = lengthOfCode;
        int y = numberOfTries;
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
        gBlackBox.setVisible(cbShowSecretCode.isSelected());

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




        refreshDependencies();

    }

    /*getter + setter*/

}
