package gui;

import config.Configuration;
import javafx.scene.paint.*;
import javafx.scene.shape.Circle;

public class ShadedCircle extends Circle {
    public ShadedCircle(int color){
        super(15.0);

        LinearGradient gradient = new LinearGradient(0f, 1f, 1f, 0f, true, CycleMethod.NO_CYCLE,
                new Stop(0, Configuration.INSTANCE.COLORS[color]),
                new Stop(1, Color.web("#ffffff")));
        this.fillProperty().set(gradient);
    }

    public void positionCircle(int x, int xMax, int y, int yMax){

    }
}
