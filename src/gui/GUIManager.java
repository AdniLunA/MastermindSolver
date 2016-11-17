package gui;

import engine.GameEngine;

public class GUIManager {
    //attributes
    private ConfigurationPage configPg = new ConfigurationPage();
    private SimulationPage simulationPg = new SimulationPage();
    private GameEngine gameEngine;

    //functions
    public GUIManager(GameEngine gameEngine){
        this.gameEngine = gameEngine;
    }

    public void openConfigurationPage(){
        System.out.println("GUIManager - openConfigurationPage");
    }

    public void openSimulationPage(){
        System.out.println("GUIManager - openSimulationPage");
    }

    //getter + setter
    public ConfigurationPage getConfigPg() {
        return configPg;
    }

    public void setConfigPg(ConfigurationPage configPg) {
        this.configPg = configPg;
    }

    public SimulationPage getSimulationPg() {
        return simulationPg;
    }

    public void setSimulationPg(SimulationPage simulationPg) {
        this.simulationPg = simulationPg;
    }

    public GameEngine getGameEngine() {
        return gameEngine;
    }

    public void setGameEngine(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
    }
}
