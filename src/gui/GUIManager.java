package gui;

import engine.GameEngine;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class GUIManager{
    //attributes
    private static GUIManager guiManager; //Singleton Pattern

    private ConfigurationController configPg = new ConfigurationController();
    private SimulationController simulationPg = new SimulationController();
    private GameEngine gameEngine = GameEngine.getInstance();

    private int lengthOfCode;
    private int numberOfColors;
    private int numberOfTries;
    private int[] secretCode;

    //functions
    public static final GUIManager getInstance(){ //Singleton Pattern
        if(guiManager == null){
            return new GUIManager();
        } else {
            return guiManager;
        }
    }
    private GUIManager() {
        this.guiManager = this; //Singleton Pattern
    }

    public void openConfigurationPage(Stage primaryStage) throws IOException {
        System.out.println("GUIManager - openConfigurationPage");

        Pane configPage = (Pane) FXMLLoader.load(getClass().getResource("configurationPage.fxml"));
        Scene scene = new Scene(configPage);
        primaryStage.setTitle("Mastermind Simulation");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void startWithPresetCode(int lengthOfCode, int numberOfColors, int numberOfTries, int[] secretCode){
        System.out.println("GUIManager - startWithPresetCode");
        this.lengthOfCode = lengthOfCode;
        this.numberOfColors = numberOfColors;
        this.numberOfTries = numberOfTries;
        this.secretCode = secretCode;

        System.out.println("GUIManager - startWithPresetCode - starting simulation with values LOC: "+lengthOfCode
                +", NOC: "+numberOfColors+", NOT: "+numberOfTries+", secret code: "+secretCode);

        openSimulationPage();
    }

    public void startWithRandomCode(int lengthOfCode, int numberOfColors, int numberOfTries){
        System.out.println("GUIManager - startWithRandomCode");
        this.lengthOfCode = lengthOfCode;
        this.numberOfColors = numberOfColors;
        this.numberOfTries = numberOfTries;
        int[] test = gameEngine.getRandomCode(lengthOfCode, numberOfColors);
        this.secretCode = test;

        System.out.println("GUIManager - startWithRandomCode - starting simulation with values LOC: "+lengthOfCode
                +", NOC: "+numberOfColors+", NOT: "+numberOfTries+", secret code: "+secretCode);

        openSimulationPage();
    }

    private void openSimulationPage(){
        System.out.println("GUIManager - openSimulationPage");
    }

    //getter + setter

    public ConfigurationController getConfigPg() {
        return configPg;
    }

    public void setConfigPg(ConfigurationController configPg) {
        this.configPg = configPg;
    }

    public SimulationController getSimulationPg() {
        return simulationPg;
    }

    public void setSimulationPg(SimulationController simulationPg) {
        this.simulationPg = simulationPg;
    }

    public GameEngine getGameEngine() {
        return gameEngine;
    }

    public void setGameEngine(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
    }
}
