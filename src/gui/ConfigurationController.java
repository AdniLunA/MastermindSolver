package gui;

import config.Configuration;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;
//import evolution.NumChromosome;
//import evolution.IChromosome;

public class ConfigurationController implements Initializable{
    //attributes
    private int lengthOfCode = Configuration.INSTANCE.DEFAULT_LENGTH_OF_CODE;
    private int numberOfColors = Configuration.INSTANCE.DEFAULT_NUMBER_OF_COLORS;
    private int numberOfTries = Configuration.INSTANCE.DEFAULT_NUMBER_OF_TRIES;

    @FXML
    TextField loc_textfield;
    @FXML
    Slider loc_slider;
    @FXML
    TextField noc_textfield;
    @FXML
    Slider noc_slider;
    @FXML
    TextField not_textfield;
    @FXML
    Slider not_slider;
    @FXML
    Button buttonNextStep;
    @FXML
    Button buttonLastStep;
    @FXML
    Button buttonCreateRandom;
    @FXML
    Button buttonStartSimulation;
    @FXML
    Group parameterSettingArea;
    @FXML
    Group codeSettingArea;
    @FXML
    Circle c0;
    @FXML
    Circle c1;
    @FXML
    Circle c2;
    @FXML
    Circle c3;
    @FXML
    Circle c4;
    @FXML
    Circle c5;
    @FXML
    Circle c6;
    @FXML
    Circle c7;
    @FXML
    Circle c8;
    @FXML
    Circle c9;

    private Circle[] circles = {c0, c1, c2, c3, c4, c5, c6, c7, c8, c9};
    private Color[] colors;

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

    @FXML
    private void onchangeLOCTextfield(){
        System.out.println("ConfigurationController - onchangeLOCTextfield");

        int newValue = checkBoundaries(loc_textfield.getText(), Configuration.INSTANCE.MAX_LENGTH_OF_CODE);
        if (newValue != 0) {
            loc_textfield.setStyle("-fx-text-inner-color: black;");
            lengthOfCode = newValue;
            loc_slider.setValue((double) newValue);
            loc_textfield.setText("" + newValue);
        } else {
            loc_textfield.setStyle("-fx-text-inner-color: red;");
        }
    }

    @FXML
    private void onchangeNOCTextfield(){
        System.out.println("ConfigurationController - onchangNOCTextfield");

        int newValue = checkBoundaries(noc_textfield.getText(), Configuration.INSTANCE.MAX_NUMBER_OF_COLORS);
        if (newValue != 0) {
            noc_textfield.setStyle("-fx-text-inner-color: black;");
            numberOfColors = newValue;
            noc_slider.setValue((double) newValue);
            noc_textfield.setText("" + newValue);
        } else {
            noc_textfield.setStyle("-fx-text-inner-color: red;");
        }
    }

    @FXML
    private void onchangeNOTTextfield(){
        System.out.println("ConfigurationController - onchangeNOTTextfield");

        int newValue = checkBoundaries(not_textfield.getText(), Configuration.INSTANCE.MAX_NUMBER_OF_TRIES);
        if (newValue != 0) {
            not_textfield.setStyle("-fx-text-inner-color: black;");
            numberOfTries = newValue;
            not_slider.setValue((double) newValue);
            not_textfield.setText("" + newValue);
        } else {
            not_textfield.setStyle("-fx-text-inner-color: red;");
        }
    }

    @FXML
    private void onclickNextStep(){
        System.out.println("ConfigurationController - onclickNextStep");

        parameterSettingArea.setDisable(true);
        codeSettingArea.setDisable(false);

        initializeCodeSettingField();
    }

    @FXML
    private void onclickGenerateRandom(){
        System.out.println("ConfigurationController - onclickGenerateRandom");

    }

    @FXML
    private void onclickLastStep(){
        System.out.println("ConfigurationController - onclickLastStep");

        parameterSettingArea.setDisable(false);
        codeSettingArea.setDisable(true);

    }

    @FXML
    private void onclickStartSimulation(){
        System.out.println("ConfigurationController - onclickStartSimulation");

    }

    private void incrColor(Circle circle){
        System.out.println("ConfigurationController - incrColor");
        LinearGradient gradient = new LinearGradient(0f, 1f, 1f, 0f, true, CycleMethod.NO_CYCLE,
                new Stop(0, Color.web("#000000")),
                new Stop(1, Color.web("#ffffff")));
        circle.fillProperty().set(gradient);

    }

    private void initializeCodeSettingField(){
        System.out.println("Configuration Page - initializeCodeSettingField");
        //initialize colors
        String[] hexCodes = {"#000000", "#199999", "#333333", "#4CCCCC", "#666666",
                            "#7FFFFF", "#999999", "#B33332", "#CCCCCC", "#E66665"};

        for(int i = 0; i<lengthOfCode; i++){
            colors[i] = Color.web(hexCodes[i], 1.0);
        }

        //todo show only needed number of holes

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.loc_slider.valueProperty().addListener(
                new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                        loc_textfield.setText(String.valueOf(newValue.intValue()));
                        loc_textfield.setStyle("-fx-text-inner-color: black;");
                    }
                }
        );
        this.noc_slider.valueProperty().addListener(
                new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                        noc_textfield.setText(String.valueOf(newValue.intValue()));
                        noc_textfield.setStyle("-fx-text-inner-color: black;");
                    }
                }
        );
        this.not_slider.valueProperty().addListener(
                new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                        not_textfield.setText(String.valueOf(newValue.intValue()));
                        not_textfield.setStyle("-fx-text-inner-color: black;");
                    }
                }
        );
        c0.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent t) {
                System.out.println("ConfigurationController - onclickToggleColor");
                incrColor(c0);
            }
        });

    }
}
