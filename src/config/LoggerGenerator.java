package config;

import engine.GameEngine;
import engine.helper.CodeSolver;
import engine.helper.CodeValidator;
import evolution.SicknessCalculator;
import evolution.SingleArrayBuilder;
import evolution.crossover.KPointCrossover;
import evolution.crossover.OnePointCrossover;
import evolution.crossover.TwoPointCrossover;
import evolution.crossover.UniformCrossover;
import evolution.mutation.*;
import evolution.population.Population;
import evolution.selection.RouletteWheelSelection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import presentation.ConfigurationController;
import presentation.GUIManager;
import presentation.SimulationController;

public enum LoggerGenerator {

    INSTANCE;

    public static final Logger codeSolver = LogManager.getLogger(CodeSolver.class);
    public static final Logger codeValidator = LogManager.getLogger(CodeValidator.class);
    public static final Logger submissionHandler = LogManager.getLogger(engine.helper.SubmissionHandler.class);
    public static final Logger gameEngine = LogManager.getLogger(GameEngine.class);
    public static final Logger kPointCrossover = LogManager.getLogger(KPointCrossover.class);
    public static final Logger onePointCrossover = LogManager.getLogger(OnePointCrossover.class);
    public static final Logger twoPointCrossover = LogManager.getLogger(TwoPointCrossover.class);
    public static final Logger uniformCrossover = LogManager.getLogger(UniformCrossover.class);
    public static final Logger displacementMutation = LogManager.getLogger(DisplacementMutation.class);
    public static final Logger exchangeMutation = LogManager.getLogger(ExchangeMutation.class);
    public static final Logger insertionMutation = LogManager.getLogger(InsertionMutation.class);
    public static final Logger inversionMutation = LogManager.getLogger(InversionMutation.class);
    public static final Logger mutatorBasics = LogManager.getLogger(MutatorBasics.class);
    public static final Logger scrambleMutation = LogManager.getLogger(ScrambleMutation.class);
    public static final Logger rouletteWheelSelection = LogManager.getLogger(RouletteWheelSelection.class);
    public static final Logger population = LogManager.getLogger(Population.class);
    public static final Logger sicknessCalculator = LogManager.getLogger(SicknessCalculator.class);
    public static final Logger singleArrayBuilder = LogManager.getLogger(SingleArrayBuilder.class);
    public static final Logger configurationController = LogManager.getLogger(ConfigurationController.class);
    public static final Logger guiManager = LogManager.getLogger(GUIManager.class);
    public static final Logger simulationController = LogManager.getLogger(SimulationController.class);
}
