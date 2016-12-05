package gui;

import engine.GameEngine;
import evolution.IChromosome;
import evolution.NumChromosome;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class GUIManager {
    /*constructor*/
    private GUIManager() {
        guiManager = this; /*Singleton Pattern*/
    }

    /*attributes*/
    private static GUIManager guiManager; /*Singleton Pattern*/

    private ConfigurationController configPg = new ConfigurationController();
    private SimulationController simulationPg = new SimulationController();
    private GameEngine gameEngine = GameEngine.getInstance();
    private IChromosome code;

    private int lengthOfCode;
    private int numberOfColors;
    private int numberOfTries;

    private Stage primaryStage;

    /*functions*/
    public static final GUIManager getInstance() { /*Singleton Pattern*/
        System.out.println("GUIManager - getInstance");
        if (guiManager == null) {
            return new GUIManager();
        } else {
            return guiManager;
        }
    }

    public void openConfigurationPage(Stage primaryStage) {
        System.out.println("GUIManager - openConfigurationPage");

        this.primaryStage = primaryStage;

        try {
            Pane configPage = (Pane) FXMLLoader.load(getClass().getResource("configurationPage.fxml"));
            Scene scene = new Scene(configPage);
            primaryStage.setTitle("Mastermind Simulation");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            System.out.println("GUIManager - openSimulationPage: can't find \"configurationPage.fxml\"");
            e.printStackTrace();
        }
    }

    public void startWithPresetCode(int lengthOfCode, int numberOfColors, int numberOfTries, int[] secretCode) {
        System.out.println("GUIManager - startWithPresetCode");
        this.lengthOfCode = lengthOfCode;
        this.numberOfColors = numberOfColors;
        this.numberOfTries = numberOfTries;
        this.code = new NumChromosome(secretCode, numberOfColors);

        System.out.println("GUIManager - startWithPresetCode - starting simulation with values LOC: " + lengthOfCode
                + ", NOC: " + numberOfColors + ", NOT: " + numberOfTries + ", secret code: " + code.toString());

        openSimulationPage();
    }

    public void startWithRandomCode(int lengthOfCode, int numberOfColors, int numberOfTries) {
        System.out.println("GUIManager - startWithRandomCode");
        this.lengthOfCode = lengthOfCode;
        this.numberOfColors = numberOfColors;
        this.numberOfTries = numberOfTries;
        this.code = gameEngine.getRandomCode(lengthOfCode, numberOfColors);

        System.out.println("GUIManager - startWithRandomCode - starting simulation with values LOC: " + lengthOfCode
                + ", NOC: " + numberOfColors + ", NOT: " + numberOfTries + ", secret code: " + code.toString());

        openSimulationPage();
    }

    private void openSimulationPage() {
        System.out.println("GUIManager - openSimulationPage");
        //only accept valid code
        if (!code.checkValidity()) {
            /*todo: error message */
        } else {
            /*todo: redirect page*/


            try {
                Pane simulationPage = (Pane) FXMLLoader.load(getClass().getResource("simulationPage.fxml"));
                Scene scene = new Scene(simulationPage);
                primaryStage.setScene(scene);
                primaryStage.show();
            } catch (IOException e) {
                System.out.println("GUIManager - openSimulationPage: can't find \"simulationPage.fxml\"");
                e.printStackTrace();
            }
            gameEngine.startGame(lengthOfCode, numberOfColors, numberOfTries, code);
        }
    }

    /*getter + setter*/
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
