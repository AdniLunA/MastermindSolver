package config;

import javafx.scene.paint.Color;

import static config.ConfigType.*;

public enum ConfigurationManager {

    INSTANCE;

    private IConfigurationFile configFile = new DefaultConfig();

    public void setConfigType(ConfigType type) {
        switch (type) {
            /*
            case TESTCASE1:
                configFile = new TestCase1Config();
                break; */
            default:
                configFile = new DefaultConfig();
        }
    }

    /*--
     * evolution settings
     */
    public final int SIZE_OF_POPULATION = configFile.getSizeOfPopulation();
    public final int REPEAT_EVOLUTION_N_TIMES = configFile.getRepeatEvolutionNTimes();
    public final SelectionEnum SELECTION_TYPE = configFile.getSelectionType();
    public final CrossoverEnum CROSSOVER_TYPE = configFile.getCrossoverType();

    public final int K_FOR_CROSS_OVER = configFile.getKForCrossover();
    public final int CROSSOVER_MAX_TRY_AGAIN = configFile.getCrossoverMaxTryAgain();
    public final float MIXING_RATIO = configFile.getMixingRatio();
    public final MutationEnum MUTATION_TYPE = configFile.getMutationType();
    public final double MUTATION_RATIO = configFile.getMutationRatio();
    public final int MUTATION_MAX_TRY_AGAIN = configFile.getMutationMaxTryAgain();

    /*--
     * game engine settings
     */
    public final int DEFAULT_LENGTH_OF_CODE = configFile.getDefaultLengthOfCode();
    public final int DEFAULT_NUMBER_OF_COLORS = configFile.getDefaultNumberOfColors();
    public final int DEFAULT_NUMBER_OF_TRIES = configFile.getDefaultNumberOfTries();

    public final int MAX_LENGTH_OF_CODE = configFile.getMaxLengthOfCode();
    public final int MAX_NUMBER_OF_COLORS = 20;
    public final int MAX_NUMBER_OF_TRIES = configFile.getMaxNumberOfTries();

    public final int WEIGHT_OF_WHITE_DIFFERENCE = configFile.getWeightOfWhiteDifference();
    public final int WEIGHT_OF_RED_DIFFERENCE = configFile.getWeightOfRedDifference();

    /*--
     * gui settings
     */
    public final boolean TRACK_CODE_SETTING = configFile.getTrackCodeSetting();
    public final boolean DEFAULT_SHOW_BLACKBOX_CONTENT = configFile.getDefaultShowBlackboxContent();
    public final boolean DEFAULT_RUN_AUTOMATED = configFile.getDefaultRunAutomated();
    public final int DEFAULT_SIMULATION_SPEED_MS = configFile.getDefaultSimulationSpeedMs();


    public final Color[] COLORS = new Color[]{
            Color.WHITE, Color.BLACK, Color.web("0x00FF59"), Color.web("0xFFE800"),
            Color.web("0xFF7B00"), Color.web("0xFF0000"), Color.web("0xFF00DC"), Color.web("0x872BFF"),
            Color.web("0x0026FF"), Color.web("0x00FFFF"), Color.web("0xC0C0C0"), Color.web("0xC7FF47"),
            Color.web("0xE0A674"), Color.web("0x7F0037"), Color.web("0xFF9E9E"), Color.web("0xFFC4F0"),
            Color.web("0xB57FFF"), Color.web("0xB2C4FF"), Color.web("0x3FBDC6"), Color.web("0x007F0E")
    };

	/*assumption: sequences set by the solver fulfill the unique color criteria.
     * -> must be validated by all classes that manipulate chromosomes*/
}
