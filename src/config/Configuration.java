package config;

import javafx.scene.paint.Color;

public enum Configuration {

	INSTANCE;
	//evolution settings
	public final static CrossoverEnum CROSSOVER_TYPE = CrossoverEnum.UNIFORM;
	public final static int K_FOR_CROSS_OVER = 3; // for kPoint crossover
	public final static float MIXING_RATIO = 0.75f; // for uniform crossover; best results with > 0.5, like 0.75 -> awesome.
	public final static MutationEnum MUTATION_TYPE = MutationEnum.SCRAMBLE; //best results with SCRAMBLE
	public final static SelectionEnum SELECTION_TYPE = SelectionEnum.ROULETTE_WHEEL; //best results with Roulette_wheel
	public final static double MUTATION_RATIO = 0.0005;
	public final int SIZE_OF_POPULATION = 50;

	//game engine settings
	public final int DEFAULT_LENGTH_OF_CODE = 5;
	public final int DEFAULT_NUMBER_OF_COLORS = 10;
	public final int DEFAULT_NUMBER_OF_TRIES = 10;
	public final int MAX_LENGTH_OF_CODE = 10;
	public final int MAX_NUMBER_OF_COLORS = 20;
	public final int MAX_NUMBER_OF_TRIES = 20;

	public final int WEIGHT_OF_WHITE_DIFFERENCE = 1;
	public final int WEIGHT_OF_RED_DIFFERENCE = 2;

	//gui settings
	public final boolean DEFAULT_SHOW_BLACKBOX_CONTENT = true;

	public final Color[] COLORS = new Color[]{
		Color.web("0xFFFFFF"), Color.web("0xC0C0C0"), Color.web("0x000000"), Color.web("0xFFE800"),
		Color.web("0xFF7B00"), Color.web("0xFF0000"), Color.web("0xFF00DC"), Color.web("0x872BFF"),
		Color.web("0x0026FF"), Color.web("0x00FFFF"), Color.web("0x00FF59"), Color.web("0xC7FF47"),
		Color.web("0xE0A674"), Color.web("0x7F0037"), Color.web("0xFF9E9E"), Color.web("0xFFC4F0"),
		Color.web("0xB57FFF"), Color.web("0xB2C4FF"), Color.web("0x3FBDC6"), Color.web("0x007F0E")
	};

	//assumption: sequences set by the solver fulfill the unique color criteria.
}
