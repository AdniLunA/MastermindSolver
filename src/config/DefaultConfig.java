package config;


public class DefaultConfig implements IConfigurationFile {

    /*--
     * evolution settings
     */
    private final int sizeOfPopulation = 100;
    private final int repeatEvolutionNTimes = 5;
    private final SelectionEnum selectionType = SelectionEnum.ROULETTE_WHEEL; /*expecting better performance with tournament*/
    private final CrossoverEnum crossoverType = CrossoverEnum.ONE_POINT;
    /*Must be greater than length of code (always < 20!):*/
    private final int kForCrossover = 3; /*for kPoint crossParents*/
    private final int crossoverMaxTryAgain = 10;
    private final float mixingRatio = 0.75f; /* for uniform crossParents; best results with values > 0.5 as 0.75*/
    private final MutationEnum mutationType = MutationEnum.EXCHANGE; /*best results with SCRAMBLE*/
    private final double mutationRatio = 0.005;
    private final int mutationMaxTryAgain = 10;

    /*--
     * game engine settings
     */
    private final int defaultLengthOfCode = 3;
    private final int defaultNumberOfColors = 5;
    private final int defaultNumberOfTries = 35;

    private final int maxLengthOfCode = 10;
    private final int maxNumberOfTries = 35;

    private final int weightOfWhiteDifference = 3;
    private final int weightOfRedDifference = 10;

    /*--
     * presentation settings
     */
    private final boolean trackCodeSetting = false;
    private final boolean defaultShowBlackboxContent = true;
    private final boolean defaultRunAutomated = false;
    private final int defaultSimulationSpeedMs = 5000; /*between 100 and 5000*/

	/*assumption: sequences set by the solver fulfill the unique color criteria.
     * -> must be validated by all classes that manipulate chromosomes*/

    @Override
    public int getSizeOfPopulation() {
        return sizeOfPopulation;
    }

    @Override
    public int getRepeatEvolutionNTimes() {
        return repeatEvolutionNTimes;
    }

    @Override
    public SelectionEnum getSelectionType() {
        return selectionType;
    }

    @Override
    public CrossoverEnum getCrossoverType() {
        return crossoverType;
    }

    public int getKForCrossover() {
        return kForCrossover;
    }

    @Override
    public int getCrossoverMaxTryAgain() {
        return crossoverMaxTryAgain;
    }

    @Override
    public float getMixingRatio() {
        return mixingRatio;
    }

    public MutationEnum getMutationType() {
        return mutationType;
    }

    @Override
    public double getMutationRatio() {
        return mutationRatio;
    }

    @Override
    public int getMutationMaxTryAgain() {
        return mutationMaxTryAgain;
    }

    @Override
    public int getDefaultLengthOfCode() {
        return defaultLengthOfCode;
    }

    @Override
    public int getDefaultNumberOfColors() {
        return defaultNumberOfColors;
    }

    @Override
    public int getDefaultNumberOfTries() {
        return defaultNumberOfTries;
    }

    @Override
    public int getMaxLengthOfCode() {
        return maxLengthOfCode;
    }

    @Override
    public int getMaxNumberOfTries() {
        return maxNumberOfTries;
    }

    @Override
    public int getWeightOfWhiteDifference() {
        return weightOfWhiteDifference;
    }

    @Override
    public int getWeightOfRedDifference() {
        return weightOfRedDifference;
    }

    public boolean getTrackCodeSetting() {
        return trackCodeSetting;
    }

    public boolean getDefaultShowBlackboxContent() {
        return defaultShowBlackboxContent;
    }

    public boolean getDefaultRunAutomated() {
        return defaultRunAutomated;
    }

    @Override
    public int getDefaultSimulationSpeedMs() {
        return defaultSimulationSpeedMs;
    }


}
