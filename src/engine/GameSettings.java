package engine;

import config.CrossoverEnum;
import config.MutationEnum;
import config.SelectionEnum;
import javafx.scene.paint.Color;

public enum GameSettings {

    INSTANCE;

    /*--
     * evolution settings
     */
    public int sizeOfPopulation;
    public int repeatEvolutionNTimes;
    public SelectionEnum selectionType;
    public CrossoverEnum crossoverType;
    public int kForCrossover;
    public int crossoverMaxTryAgain;
    public float mixingRatio;
    public MutationEnum mutationType;
    public double mutationRatio;
    public int mutationMaxTryAgain;

    /*--
     * game engine settings
     */
    public int lengthOfCode;
    public int numberOfColors;
    public int numberOfTries;

    public final int MAX_LENGTH_OF_CODE = 10;
    public final int MAX_NUMBER_OF_COLORS = 20;
    public int maxNumberOfTries;

    public int simulationSpeedInMs;

    /*to calculate fitness*/
    public int weightOfWhiteDifference;
    public int weightOfRedDifference;

    public final Color[] COLORS = new Color[]{
            Color.WHITE, Color.BLACK, Color.web("0x00FF59"), Color.web("0xFFE800"),
            Color.web("0xFF7B00"), Color.web("0xFF0000"), Color.web("0xFF00DC"), Color.web("0x872BFF"),
            Color.web("0x0026FF"), Color.web("0x00FFFF"), Color.web("0xC0C0C0"), Color.web("0xC7FF47"),
            Color.web("0xE0A674"), Color.web("0x7F0037"), Color.web("0xFF9E9E"), Color.web("0xFFC4F0"),
            Color.web("0xB57FFF"), Color.web("0xB2C4FF"), Color.web("0x3FBDC6"), Color.web("0x007F0E")
    };

	/*
     * assumption: sequences set by the solver fulfill the unique color criteria.
     * -> must be validated by all classes that manipulate chromosomes
     */

	/*--
	 * functions
	 */
	protected void loadDefaultSettings(){
	    setSizeOfPopulation(100);
	    setRepeatEvolutionNTimes(5);
	    setSelectionType(SelectionEnum.ROULETTE_WHEEL); /*expecting better performance with tournament*/
        setCrossoverType(CrossoverEnum.ONE_POINT);

        setLengthOfCode(8);
        setNumberOfColors(18);
        setNumberOfTries(50);
        setMaxNumberOfTries(50);

        setSimulationSpeedInMs(100);

        setKForCrossover(3);
        setCrossoverMaxTryAgain(10);
        setMixingRatio(0.75f);
        setMutationType(MutationEnum.EXCHANGE); /*best results with SCRAMBLE*/
        setMutationRatio(0.005);
        setMutationMaxTryAgain(10);

        setWeightOfWhiteDifference(3);
        setWeightOfRedDifference(10);
    }

    /*--
     * setter
     */
    protected void setSizeOfPopulation(int sizeOfPopulation) {
        if(sizeOfPopulation < 1) {
            throw new IllegalArgumentException("sizeOfPopulation has to be > 0");
        }
        this.sizeOfPopulation = sizeOfPopulation;
    }

    protected void setRepeatEvolutionNTimes(int repeatEvolutionNTimes) {
        if(repeatEvolutionNTimes < 1) {
            throw new IllegalArgumentException("repeatEvolutionNTimes has to be > 0");
        }
        this.repeatEvolutionNTimes = repeatEvolutionNTimes;
    }

    protected void setSelectionType(SelectionEnum selectionType) {
        this.selectionType = selectionType;
    }

    protected void setCrossoverType(CrossoverEnum crossoverType) {
        this.crossoverType = crossoverType;
    }

    /*Must be smaller than length of code (always < 20!):*/
    protected void setKForCrossover(int kForCrossover) {
        if(kForCrossover < 1 || kForCrossover >= lengthOfCode || kForCrossover >= MAX_LENGTH_OF_CODE){
            throw new IndexOutOfBoundsException("kForCrossover has to be > 0 and < " + MAX_LENGTH_OF_CODE);
        }
        this.kForCrossover = kForCrossover;
    }

    protected void setCrossoverMaxTryAgain(int crossoverMaxTryAgain) {
        if(crossoverMaxTryAgain < 1) {
            throw new IllegalArgumentException("crossoverMaxTryAgain has to be > 0");
        }
        this.crossoverMaxTryAgain = crossoverMaxTryAgain;
    }

    /* for uniform crossover; best results with values > 0.5, like 0.75*/
    protected void setMixingRatio(float mixingRatio) {
        if(mixingRatio <= 0 || mixingRatio >= 1){
            throw new IndexOutOfBoundsException("mixingRatio must be a value between 0 and 1.");
        }
        this.mixingRatio = mixingRatio;
    }

    protected void setMutationType(MutationEnum mutationType) {
        this.mutationType = mutationType;
    }

    protected void setMutationRatio(double mutationRatio) {
        if(mutationRatio <= 0 || mutationRatio >= 1){
            throw new IndexOutOfBoundsException("mutationRatio must be a value between 0 and 1.");
        }
        this.mutationRatio = mutationRatio;
    }

    protected void setMutationMaxTryAgain(int mutationMaxTryAgain) {
        if(mutationMaxTryAgain < 1) {
            throw new IllegalArgumentException("mutationMaxTryAgain has to be > 0");
        }
        this.mutationMaxTryAgain = mutationMaxTryAgain;
    }

    protected void setLengthOfCode(int lengthOfCode) {
        if(lengthOfCode < 1) {
            throw new IllegalArgumentException("lengthOfCode has to be > 0");
        }
        this.lengthOfCode = lengthOfCode;
    }

    protected void setNumberOfColors(int numberOfColors) {
        if(numberOfColors < 1 || numberOfColors > MAX_NUMBER_OF_COLORS) {
            throw new IllegalArgumentException("numberOfColors has to be > 0 and < " + MAX_NUMBER_OF_COLORS);
        }
        this.numberOfColors = numberOfColors;
    }

    protected void setNumberOfTries(int numberOfTries) {
        if(numberOfTries < 1) {
            throw new IllegalArgumentException("numberOfTries has to be > 0");
        }
        this.numberOfTries = numberOfTries;
    }

    protected void setMaxNumberOfTries(int maxNumberOfTries) {
        if(maxNumberOfTries < 1) {
            throw new IllegalArgumentException("maxNumberOfTries has to be > 0");
        }
        this.maxNumberOfTries = maxNumberOfTries;
    }

    /*between 10 and 5000*/
    protected void setSimulationSpeedInMs(int simulationSpeedInMs){
        if(simulationSpeedInMs < 10 || simulationSpeedInMs > 5000){
            throw new IllegalArgumentException("simulationSpeedInMs as to be >= 10ms and <= 5000ms.");
        }
        this.simulationSpeedInMs = simulationSpeedInMs;
    }

    protected void setWeightOfWhiteDifference(int weightOfWhiteDifference) {
        this.weightOfWhiteDifference = weightOfWhiteDifference;
    }

    protected void setWeightOfRedDifference(int weightOfRedDifference) {
        this.weightOfRedDifference = weightOfRedDifference;
    }
}
