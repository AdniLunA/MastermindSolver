package engine;

import config.CrossoverEnum;
import config.MutationEnum;
import config.SelectionEnum;
import engine.helper.CodeSolver;
import engine.helper.CodeValidator;
import engine.helper.Submission;
import engine.helper.SubmissionHandler;
import evolution.SicknessCalculator;
import evolution.IChromosome;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import presentation.GUIManager;

import java.io.IOException;

public class GameEngine {
    /*--
     * debugging
     */
    private final Logger logger = LogManager.getLogger(this);

    /*--
     * constructors
     */
    /* Basic settings */
    public GameEngine() {
        submissionHandler = new SubmissionHandler(this);
        GameSettings.INSTANCE.loadDefaultSettings();
    }

    /*Simulate Mastermind via GUI*/
    public GameEngine(Stage primaryStage) throws IOException {
        this();
        //logger.info("Mastermind with GUI");
        GUIManager gui = new GUIManager(this, submissionHandler);
        gui.openConfigurationPage(primaryStage);
    }

    /*--
     * attributes
     */
    private CodeValidator validator;
    private CodeSolver solver;
    private SubmissionHandler submissionHandler;
    private boolean codeSolved=false;

    /*--
     * settings
     */
    public void settingsSetLocNocNot(int lengthOfCode, int numberOfColors, int numberOfTries){
        GameSettings.INSTANCE.setLengthOfCode(lengthOfCode);
        GameSettings.INSTANCE.setNumberOfColors(numberOfColors);
        GameSettings.INSTANCE.setNumberOfTries(numberOfTries);
    }

    public void settingsSetSimulationSpeedInMs(int speed){
        GameSettings.INSTANCE.setSimulationSpeedInMs(speed);
    }

    public void settingsSetAnalysingMode(boolean analyse){
        GameSettings.INSTANCE.setEfficiencyAnalysisEnabled(analyse);
    }

    public void settingsSetPopulationSizePop(int size){
        GameSettings.INSTANCE.setSizeOfPopulation(size);
    }

    public void settingsSetRepeatEvolution(int number){
        GameSettings.INSTANCE.setRepeatEvolutionNTimes(number);
    }

    public void settingsSetEvolutionTypes(SelectionEnum sType, CrossoverEnum cType, MutationEnum mType) {
        if (sType != null) {
            GameSettings.INSTANCE.setSelectionType(sType);
        }
        if (cType != null) {
            GameSettings.INSTANCE.setCrossoverType(cType);
        }
        if (sType != null) {
            GameSettings.INSTANCE.setMutationType(mType);
        }
    }

   /*--
     * functions
     */

    public void startGame(IChromosome code) {
        //logger.info("");

        SicknessCalculator.INSTANCE.dropForNextGame();
        validator = new CodeValidator(code);
        if(!GameSettings.INSTANCE.efficiencyAnalysisEnabled) {
            System.out.printf("starting simulation with values LOC: "
                    + GameSettings.INSTANCE.lengthOfCode + ", NOC: " + GameSettings.INSTANCE.numberOfColors + ", NOT: "
                    + GameSettings.INSTANCE.numberOfTries + ", secret code: \n* " + code.toString() + " *\n");
        }
        /*initialize solver*/
        solver = new CodeSolver(this);
        calculateNextSubmission();
    }

    public boolean runGameAutomated(IChromosome code) {
        startGame(code);

        while (solver.getRequestCounter() < GameSettings.INSTANCE.numberOfTries) {
            try {
                Thread.sleep(GameSettings.INSTANCE.simulationSpeedInMs);
                calculateNextSubmission();
            } catch (InterruptedException e) {
                System.out.println("GameEngine runGameAutomated: Thread Exception");
                e.printStackTrace();
            }
            //logger.info("");
        }

        return codeSolved;
    }

    public void resolveSubmission(IChromosome chromosome) {
        //logger.info("");
        Submission newSubmission = validator.calculateResponse(chromosome);
        if(GameSettings.INSTANCE.loggingEnabled) {
            this.logger.info("red = " + newSubmission.getRed() + ", white = " + newSubmission.getWhite() + ", code = "
                    + chromosome.toString());
        }
        if(!GameSettings.INSTANCE.efficiencyAnalysisEnabled) {
            System.out.printf("  " + chromosome.toString() + "  => sequence #%02d, red = %2d, white = %2d, sickness = %4d, generation = %3d. @GameEngine - resolveSubmission.\n"
                    , (solver.getRequestCounter() - 1), newSubmission.getRed(), newSubmission.getWhite(), chromosome.getSickness(), chromosome.getGeneration());
        }

        /*if solution is found, break loop by setting currentLine to max*/
        if (newSubmission.getRed() == GameSettings.INSTANCE.lengthOfCode) {
            codeSolved = true;
            solver.setRequestCounter(GameSettings.INSTANCE.numberOfTries);
        }

        SicknessCalculator.INSTANCE.addSubmission(newSubmission);

        submissionHandler.addSubmission(newSubmission);
    }

    public void calculateNextSubmission() {
        //logger.info("");
        solver.solve();
    }

}
