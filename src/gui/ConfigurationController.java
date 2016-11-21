package gui;

import config.Configuration;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

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

    public void saveSettings(){
        System.out.println("Configuration Page - saveSettings");
    }

    public void generateCodeSettingField(){
        System.out.println("Configuration Page - generateCodeSettingField");
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
    }
}
