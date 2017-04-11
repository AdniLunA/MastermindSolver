package config;


public class VariableConfig implements IConfigurationFile {

    /*--
     * evolution settings
     */
    private int sizeOfPopulation = 100;
    private int repeatEvolutionNTimes = 5;
    private SelectionEnum selectionType = SelectionEnum.ROULETTE_WHEEL; /*expecting better performance with tournament*/
    private CrossoverEnum crossoverType = CrossoverEnum.ONE_POINT;
    /*Must be greater than length of code (always < 20!):*/
    private final int kForCrossover = 3; /*for kPoint crossover*/
    private int crossoverMaxTryAgain = 10;
    private float mixingRatio = 0.75f; /* for uniform crossover; best results with values > 0.5 as 0.75*/
    private MutationEnum mutationType = MutationEnum.EXCHANGE; /*best results with SCRAMBLE*/
    private double mutationRatio = 0.005;
    private int mutationMaxTryAgain = 10;

    /*--
     * game engine settings
     */
    private int defaultLengthOfCode = 3;
    private int defaultNumberOfColors = 5;
    private int defaultNumberOfTries = 35;

    private final int maxLengthOfCode = 10;
    private final int maxNumberOfTries = 25;

    private int weightOfWhiteDifference = 3;
    private int weightOfRedDifference = 10;

    /*--
     * presentation settings
     */
    private final boolean trackCodeSetting = false;
    private final boolean defaultShowBlackboxContent = true;
    private final boolean defaultRunAutomated = false;
    private final int defaultSimulationSpeedMs = 5000; /*between 100 and 5000*/

	/*assumption: sequences set by the solver fulfill the unique color criteria.
     * -> must be validated by all classes that manipulate chromosomes*/

	/*--
	 * Setter
	 */

    public void setSizeOfPopulation(int sizeOfPopulation) {
        this.sizeOfPopulation = sizeOfPopulation;
    }

    public void setRepeatEvolutionNTimes(int repeatEvolutionNTimes) {
        this.repeatEvolutionNTimes = repeatEvolutionNTimes;
    }

    public void setSelectionType(SelectionEnum selectionType) {
        this.selectionType = selectionType;
    }

    public void setCrossoverType(CrossoverEnum crossoverType) {
        this.crossoverType = crossoverType;
    }

    public void setCrossoverMaxTryAgain(int crossoverMaxTryAgain) {
        this.crossoverMaxTryAgain = crossoverMaxTryAgain;
    }

    public void setMixingRatio(float mixingRatio) {
        this.mixingRatio = mixingRatio;
    }

    public void setMutationType(MutationEnum mutationType) {
        this.mutationType = mutationType;
    }

    public void setMutationRatio(double mutationRatio) {
        this.mutationRatio = mutationRatio;
    }

    public void setMutationMaxTryAgain(int mutationMaxTryAgain) {
        this.mutationMaxTryAgain = mutationMaxTryAgain;
    }

    public void setDefaultLengthOfCode(int defaultLengthOfCode) {
        this.defaultLengthOfCode = defaultLengthOfCode;
    }

    public void setDefaultNumberOfColors(int defaultNumberOfColors) {
        this.defaultNumberOfColors = defaultNumberOfColors;
    }

    public void setDefaultNumberOfTries(int defaultNumberOfTries) {
        this.defaultNumberOfTries = defaultNumberOfTries;
    }

    public void setWeightOfWhiteDifference(int weightOfWhiteDifference) {
        this.weightOfWhiteDifference = weightOfWhiteDifference;
    }

    public void setWeightOfRedDifference(int weightOfRedDifference) {
        this.weightOfRedDifference = weightOfRedDifference;
    }

    /*--
     * Getter
     */

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
