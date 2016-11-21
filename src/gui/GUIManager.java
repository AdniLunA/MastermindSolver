package gui;

import engine.GameEngine;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class GUIManager{
    //constructor
    public GUIManager(GameEngine gameEngine){
        this.gameEngine = gameEngine;
    }

    //attributes
    private ConfigurationController configPg = new ConfigurationController();
    private SimulationController simulationPg = new SimulationController();
    private GameEngine gameEngine;

    //functions
    public void setStage(Stage primaryStage) throws IOException {
        System.out.println("GUIManager - setStage");

        Pane configPage = (Pane) FXMLLoader.load(getClass().getResource("configurationPage.fxml"));
        Scene scene = new Scene(configPage);
        primaryStage.setTitle("Mastermind Simulation");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public void openConfigurationPage(){
        System.out.println("GUIManager - openConfigurationPage");
    }

    public void openSimulationPage(){
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
