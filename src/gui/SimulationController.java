package gui;

import config.ConfigurationManager;
import de.bean900.logger.Logger;
import engine.Submission;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.net.URL;
import java.util.InputMismatchException;
import java.util.ResourceBundle;

public class SimulationController implements Initializable {
    /*--
     * debugging
     */
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    /*--
     * attributes
     */
    private int lengthOfCode;
    private int numberOfTries;

    private boolean gameIsRunning = false;
    private boolean gameIsPaused = false;

    private int currentLineToPrint = 0;

    /*default values*/
    private boolean showBlackboxContent = ConfigurationManager.INSTANCE.DEFAULT_SHOW_BLACKBOX_CONTENT;
    private boolean runAutomated = ConfigurationManager.INSTANCE.DEFAULT_RUN_AUTOMATED;
    private int simulationSpeed;

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
    private TextField[] tRedFeedback;
    private TextField[] tWhiteFeedback;

    /*--
     * functions
     */
    private void addNewLine(Submission lineInfo) {
        logger.info("addNewLine", "");
    }

    private void generateGameMatrix() {
        logger.info("generateGameMatrix", "");
        int x = lengthOfCode;
        int y = numberOfTries;

        /*y positions for circles + text fields*/
        int[] yPositions = new int[numberOfTries];
        int yDistance = 60;
        yPositions[0] = yDistance - 25;
        for (int i = 1; i < numberOfTries; i++) {
            yPositions[i] = yPositions[i - 1] + yDistance;
        }

        /*circle positions*/
        float[] circleXPositions = new float[lengthOfCode];
        float cXDistance = 500f / lengthOfCode;
        circleXPositions[0] = 100f + (cXDistance / 2f) - 15f;
        for (int i = 1; i < lengthOfCode; i++) {
            circleXPositions[i] = circleXPositions[i - 1] + cXDistance;
        }

        /*gameFieldHeight*/
        apScrollableField.setPrefHeight(yPositions[numberOfTries - 1] + yDistance);

        /*place blackBox circles*/
        blackBox = new Circle[lengthOfCode];
        for (int i = 0; i < lengthOfCode; i++) {
            blackBox[i] = new Circle(15, Color.BLACK);
            blackBox[i].relocate(circleXPositions[i] - 100, 5.0);
        }
        pBlackBox.getChildren().addAll(blackBox);

        /*place game circles*/
        circleMatrix = new Circle[numberOfTries][lengthOfCode];
        for (int col = 0; col < numberOfTries; col++) {
            for (int row = 0; row < lengthOfCode; row++) {
                circleMatrix[col][row] = new Circle(15, Color.BLACK);
                circleMatrix[col][row].relocate(circleXPositions[row], yPositions[col]);
            }
            pGameArea.getChildren().addAll(circleMatrix[col]);
        }

        /*place TextFields*/
        tRedFeedback = new TextField[numberOfTries];
        tWhiteFeedback = new TextField[numberOfTries];
        Background redBackground = new Background(new BackgroundFill(Color.PINK, CornerRadii.EMPTY, Insets.EMPTY));
        Background whiteBackground = new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY));
        for (int i = 0; i < numberOfTries; i++) {
            /*red*/
            tRedFeedback[i] = getFormattedTextField();
            tRedFeedback[i].setBackground(redBackground);
            tRedFeedback[i].setStyle("-fx-font-size: 18px;-fx-text-fill: darkred;");
            tRedFeedback[i].relocate(20.0, yPositions[i] - 4);
            /*white*/
            tWhiteFeedback[i] = getFormattedTextField();
            tWhiteFeedback[i].setBackground(whiteBackground);
            tWhiteFeedback[i].setStyle("-fx-font-size: 18px;");
            tWhiteFeedback[i].relocate(620.0, yPositions[i] - 4);
        }
        pGameArea.getChildren().addAll(tRedFeedback);
        pGameArea.getChildren().addAll(tWhiteFeedback);

        /*place lines*/
        int[] lineYPositions = new int[numberOfTries - 1];
        lineYPositions[0] = 81;
        int lineYDistance = 60;
        for (int i = 1; i < lineYPositions.length; i++) {
            lineYPositions[i] = lineYPositions[i - 1] + lineYDistance;
        }

        Line[] lines = new Line[numberOfTries - 1];
        for (int i = 0; i < lines.length; i++) {
            lines[i] = new Line(0.0, lineYPositions[i], 700.0, lineYPositions[i]);
            lines[i].setStroke(Color.DARKGRAY);
        }
        pGameArea.getChildren().addAll(lines);
    }

    private TextField getFormattedTextField() {
        TextField textField = new TextField();
        textField.setPrefWidth(60.0);
        textField.setPrefHeight(40.0);
        textField.setDisable(true);
        textField.setOpacity(1.0);
        textField.setAlignment(Pos.CENTER);
        return textField;
    }

    private void onclickChangeSimulationSpeed(int newSpeed) {
        logger.info("onclickChangeSimulationSpeed", "from " + simulationSpeed + " to " + newSpeed);
        simulationSpeed = newSpeed;
    }

    private void onclickChangeBlackBoxState(boolean newState) {
        logger.info("onclickChangeBlackBoxState", "from " + showBlackboxContent + " to " + newState);
        showBlackboxContent = newState;
    }

    private void refreshDependencies() {
        logger.info("refreshDependencies", "");
        /*show secret code?*/
        for (Circle circle : blackBox) {
            circle.setVisible(cbShowSecretCode.isSelected());
            pBlackBox.setVisible(cbShowSecretCode.isSelected());
        }

        /*solve simulation automated or manually?*/
        runAutomated = rbRunAutomated.isSelected();
        gRunAutomatedSettings.setVisible(runAutomated);
        bNextStep.setVisible(!runAutomated);
        /*before simulation begins, none of the simulation handling buttons are visible*/
        if (!gameIsRunning) {
            bPauseSimulation.setVisible(false);
            bContinueSimulation.setVisible(false);
            bNextStep.setVisible(false);
        }
    }

    private void switchSimulationMode() {
        logger.info("switchSimulationMode", "");
        runAutomated = !runAutomated;

        rbRunAutomated.setSelected(runAutomated);
        rbRunManually.setSelected(!runAutomated);

        /*if runAutomated just turned true, "pause simulation" must be visible*/
        if (runAutomated) {
            bPauseSimulation.setVisible(true);
            bContinueSimulation.setVisible(false);
        } else { /*if runAutomated just turned false, pause automated simulation*/
            gameIsPaused = true;
        }

        refreshDependencies();
    }

    private LinearGradient getCircleGradient(int color) {
        //System.out.println("SimulationController - getCircleGradient");
        LinearGradient gradient = new LinearGradient(0f, 1f, 1f, 0f, true, CycleMethod.NO_CYCLE,
                new Stop(0, ConfigurationManager.INSTANCE.COLORS[color]),
                new Stop(1, Color.web("#ffffff")));
        /*this.fillProperty().set(gradient);*/
        return gradient;
    }

    private void fillCircleLine(Circle[] circles, int colors[]) {
        logger.info("fillCircleLine", "");
        for (int i = 0; i < circles.length; i++) {
            logger.info("fillCircleLine", colors[i] + " ");
            circles[i].fillProperty().set(getCircleGradient(colors[i]));
            circles[i].setStroke(Color.DARKGRAY);
        }
        logger.info("","");
    }

    private void requestNewSubmission() {
        if (currentLineToPrint >= numberOfTries) {
            gameIsRunning = false;
        } else {
            logger.info("requestNewSubmission", "");
            GUIManager manager = GUIManager.getInstance();
            manager.handleSubmissionRequest(currentLineToPrint);
            logger.info("","");
        }
    }

    public void setNextSubmission(Submission newLine) {
        logger.info("setNextSubmission", "    Got information:");
        logger.info("setNextSubmission", "    SimulationController: position = " + currentLineToPrint + ", " + newLine.toString());

        /*set line values*/
        fillCircleLine(circleMatrix[currentLineToPrint], newLine.getChromosome().getSequence());
        logger.info("setNextSubmission", "    Set Feedback to GUI:");
        logger.info("setNextSubmission", "    SimulationController: position = " + currentLineToPrint + ", " + newLine.toString());
        tRedFeedback[currentLineToPrint].setText("" + newLine.getRed());
        tWhiteFeedback[currentLineToPrint].setText("" + newLine.getWhite());
        /*increment counter*/
        currentLineToPrint++;
        /*if solution is found, break loop by setting currentLine to max*/
        if (newLine.getRed() == lengthOfCode) {
            logger.info("setNextSubmission", "secret code found!!!");
            currentLineToPrint = numberOfTries;
        }
    }

    private void runSimulationAutomated() {
        logger.info("runSimulationAutomated", "");
        if (runAutomated) {
            while (currentLineToPrint < numberOfTries) {
                try {
                    Thread.sleep(simulationSpeed);
                    requestNewSubmission();
                } catch (InterruptedException e) {
                    logger.error("runSimulationAutomated: Thread Exception", e);
                    e.printStackTrace();
                }
                logger.info("","");
            }
        }
    }

    @FXML
    private void onclickStartSimulation() {
        logger.info("onclickStartSimulation", "");
        gameIsRunning = true;
        gameIsPaused = !gameIsPaused;
        bStartSimulation.setVisible(false);
        bPauseSimulation.setVisible(rbRunAutomated.isSelected());
        bNextStep.setVisible(!rbRunAutomated.isSelected());
    }

    @FXML
    private void onclickRunAutomated() {
        switchSimulationMode();
        runSimulationAutomated();
    }

    @FXML
    private void onclickRunManually() {
        switchSimulationMode();
    }

    @FXML
    private void onclickPauseSimulation() {
        logger.info("onclickPauseSimulation", "");
        bContinueSimulation.setVisible(true);
        bPauseSimulation.setVisible(false);
        gameIsPaused = true;
    }

    @FXML
    private void onclickContinueSimulation() {
        logger.info("onclickContinueSimulation", "");
        bPauseSimulation.setVisible(true);
        bContinueSimulation.setVisible(false);
        gameIsPaused = false;
    }

    @FXML
    private void onclickNextStep() {
        logger.info("onclickNextStep", "");
        if (currentLineToPrint < numberOfTries) {
            requestNewSubmission();
        }
        if (currentLineToPrint == numberOfTries) {
            bNextStep.setVisible(false);
            gameIsRunning = false;
        }
    }

    @FXML
    private void onclickReturnToConfigPg() {
        logger.info("onclickReturnToConfigPg", "");
        GUIManager.getInstance().returnToConfigurationPage();
    }

    @FXML
    private void onclickShowSecretCode() {
        logger.info("onclickShowSecretCode", "");
        refreshDependencies();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logger.info("initialize", "");
        /*--initialize variables*/
        GUIManager manager = GUIManager.getInstance();
        lengthOfCode = manager.getLengthOfCode();
        numberOfTries = manager.getNumberOfTries();

        /*subscribe manager*/
        manager.setSubscriber(this);

        /*initialize speed*/
        int defaultSpeed = ConfigurationManager.INSTANCE.DEFAULT_SIMULATION_SPEED_MS;
        int min = (int) spd_slider.getMin();
        int max = (int) spd_slider.getMax();
        if (defaultSpeed < min || defaultSpeed > max) {
            throw new InputMismatchException("ERROR: The default speed in the configuration must be a value between " + min + " and " + max + ".");
        } else {
            simulationSpeed = defaultSpeed;
        }

        /*--initialize page settings*/
        generateGameMatrix();
        cbShowSecretCode.setSelected(showBlackboxContent);
        rbRunAutomated.setSelected(runAutomated);
        rbRunManually.setSelected(!runAutomated);
        spd_slider.setValue(simulationSpeed);
        /*set blackBox colors*/
        fillCircleLine(blackBox, manager.getSecretCode());

        refreshDependencies();

        /*event listeners*/
        this.spd_slider.valueProperty().addListener(
                new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                        simulationSpeed = newValue.intValue();
                    }
                }
        );

    }


    /*--getter + setter*/

}
