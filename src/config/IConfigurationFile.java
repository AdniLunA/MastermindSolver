package config;

/**
 * Created by Linda on 08.04.2017.
 */
public interface IConfigurationFile {

    /*--
     * evolution settings
     */
    int getSizeOfPopulation();

    int getRepeatEvolutionNTimes();

    SelectionEnum getSelectionType();

    CrossoverEnum getCrossoverType();

    int getKForCrossover();

    int getCrossoverMaxTryAgain();

    float getMixingRatio();

    MutationEnum getMutationType();

    double getMutationRatio();

    int getMutationMaxTryAgain();

    /*--
     * game engine settings
     */
    int getDefaultLengthOfCode();

    int getDefaultNumberOfColors();

    int getDefaultNumberOfTries();

    int getMaxLengthOfCode();

    int getMaxNumberOfTries();

    int getWeightOfWhiteDifference();

    int getWeightOfRedDifference();

    /*--
     * presentation settings
     */
    boolean getTrackCodeSetting();

    boolean getDefaultShowBlackboxContent();

    boolean getDefaultRunAutomated();

    int getDefaultSimulationSpeedMs();
}
