package config;

/**
 * Created by Linda on 08.04.2017.
 */
public interface IConfigurationFile {

    /*--
     * evolution settings
     */
    public int getSizeOfPopulation();

    public int getRepeatEvolutionNTimes();

    public SelectionEnum getSelectionType();

    public CrossoverEnum getCrossoverType();

    public int getKForCrossover();

    public int getCrossoverMaxTryAgain();

    public float getMixingRatio();

    public MutationEnum getMutationType();

    public double getMutationRatio();

    public int getMutationMaxTryAgain();

    /*--
     * game engine settings
     */
    public int getDefaultLengthOfCode();

    public int getDefaultNumberOfColors();

    public int getDefaultNumberOfTries();

    public int getMaxLengthOfCode();

    public int getMaxNumberOfTries();

    public int getWeightOfWhiteDifference();

    public int getWeightOfRedDifference();

    /*--
     * presentation settings
     */
    public boolean getTrackCodeSetting();

    public boolean getDefaultShowBlackboxContent();

    public boolean getDefaultRunAutomated();

    public int getDefaultSimulationSpeedMs();
}
