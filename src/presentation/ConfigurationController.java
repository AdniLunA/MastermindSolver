package presentation;

import config.CrossoverEnum;
import engine.GameSettings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class ConfigurationController implements Initializable {
    /*--
     * debugging
     */
    private final Logger logger = LogManager.getLogger(this);

    /*--
     * constructor
     */
    public ConfigurationController(GUIManager guiManager) {
        this.gui = guiManager;
    }

    /*--attributes*/
    private GUIManager gui;

    private int lengthOfCode;
    private int numberOfColors;
    private int numberOfTries;
    private GUIManager guiManager;

    @FXML
    private TextField loc_textfield;
    @FXML
    private Slider loc_slider;
    @FXML
    private TextField noc_textfield;
    @FXML
    private Slider noc_slider;
    @FXML
    private TextField not_textfield;
    @FXML
    private Slider not_slider;
    @FXML
    private Group paramSettingArea;
    @FXML
    private Group codeSettingArea;
    @FXML
    private Circle c0;
    @FXML
    private Circle c1;
    @FXML
    private Circle c2;
    @FXML
    private Circle c3;
    @FXML
    private Circle c4;
    @FXML
    private Circle c5;
    @FXML
    private Circle c6;
    @FXML
    private Circle c7;
    @FXML
    private Circle c8;
    @FXML
    private Circle c9;
    @FXML
    private Rectangle r0;
    @FXML
    private Rectangle r1;
    @FXML
    private Rectangle r2;
    @FXML
    private Rectangle r3;
    @FXML
    private Rectangle r4;
    @FXML
    private Rectangle r5;
    @FXML
    private Rectangle r6;
    @FXML
    private Rectangle r7;
    @FXML
    private Rectangle r8;
    @FXML
    private Rectangle r9;
    @FXML
    private Rectangle r10;
    @FXML
    private Rectangle r11;
    @FXML
    private Rectangle r12;
    @FXML
    private Rectangle r13;
    @FXML
    private Rectangle r14;
    @FXML
    private Rectangle r15;
    @FXML
    private Rectangle r16;
    @FXML
    private Rectangle r17;
    @FXML
    private Rectangle r18;
    @FXML
    private Rectangle r19;

    private Rectangle[] rectangles;
    private Circle[] circles;
    private int[] circleState = new int[GameSettings.INSTANCE.MAX_LENGTH_OF_CODE];
    private Color[] colors;

    /*--functions*/
    private int checkBoundaries(String sValue, int maxValue) {
        //logger.info("");
        try {
            int value = Integer.parseInt(sValue);
            if (value < 1) {
                return 1;
            }
            if (value > maxValue) {
                return maxValue;
            } else {
                return value;
            }
        } catch (NumberFormatException e) {
            System.out.println("ConfigurationController - checkBoundaries: Invalid entry.");
            e.printStackTrace();
            return 0;
        }
    }

    @FXML
    private void onchangeLOCTextfield() {
//        logger.info("");

        int newValue = checkBoundaries(loc_textfield.getText(), GameSettings.INSTANCE.MAX_LENGTH_OF_CODE);
        if (newValue != 0) {
            loc_textfield.setStyle("-fx-text-inner-color: black;");
            lengthOfCode = newValue;
            loc_slider.setValue((double) newValue);
            loc_textfield.setText("" + newValue);
        } else {
            loc_textfield.setStyle("-fx-text-inner-color: red;");
        }
    }

    @FXML
    private void onchangeNOCTextfield() {
//        logger.info("");

        int newValue = checkBoundaries(noc_textfield.getText(), GameSettings.INSTANCE.MAX_NUMBER_OF_COLORS);
        if (newValue != 0) {
            noc_textfield.setStyle("-fx-text-inner-color: black;");
            numberOfColors = newValue;
            noc_slider.setValue((double) newValue);
            noc_textfield.setText("" + newValue);
        } else {
            noc_textfield.setStyle("-fx-text-inner-color: red;");
        }
    }

    @FXML
    private void onchangeNOTTextfield() {
//        logger.info("");

        int newValue = checkBoundaries(not_textfield.getText(), GameSettings.INSTANCE.maxNumberOfTries);
        if (newValue != 0) {
            not_textfield.setStyle("-fx-text-inner-color: black;");
            numberOfTries = newValue;
            not_slider.setValue((double) newValue);
            not_textfield.setText("" + newValue);
        } else {
            not_textfield.setStyle("-fx-text-inner-color: red;");
        }
    }

    @FXML
    private void onclickNextStep() {
//        logger.info("");

        paramSettingArea.setDisable(true);
        initializeCodeSettingArea();
        codeSettingArea.setDisable(false);

    }

    @FXML
    private void onclickGenerateRandom() {
//        logger.info("");
        guiManager.setRandomSecretCode(lengthOfCode, numberOfColors, numberOfTries);
    }

    @FXML
    private void onclickLastStep() {
//        logger.info("");

        paramSettingArea.setDisable(false);
        codeSettingArea.setDisable(true);

    }

    @FXML
    private void onclickStartSimulation() {
//        logger.info("");

        boolean[] accepted = checkCodeAcceptance();

        if (accepted[0] && accepted[1]) {
            int[] secretCode = Arrays.copyOf(circleState, lengthOfCode);
            guiManager.setSelectedSecretCode(lengthOfCode, numberOfColors, numberOfTries, secretCode);
        }
        if (!accepted[0]) {
            /*todo presentation show empty hole message*/
            System.out.println("ConfigurationController - onclickStartSimulation - code not accepted due to empty hole(s)");
        }
        if (!accepted[1]) {
            /*todo presentation show duplicate color message*/
            System.out.println("ConfigurationController - onclickStartSimulation - code not accepted due to duplicate colors");
        }
    }

    private boolean[] checkCodeAcceptance() {
        //logger.info("");

        /*test if a hole is not filled*/
        int i = 0;
        boolean emptyHoleFound = false;
        while (i < lengthOfCode && !emptyHoleFound) {
            if (circleState[i] >= numberOfColors) {
                emptyHoleFound = true;
                System.out.println("ConfigurationController - checkCodeAccepptance - found empty hole at pos " + i);
            }
            i++;
        }

        /*test if there are duplicates*/
        int[] sortedCode = Arrays.copyOf(circleState, lengthOfCode);
        Arrays.sort(sortedCode);
        int j = 1;
        boolean duplicateFound = false;
        while (j < lengthOfCode && !duplicateFound) {
            if (sortedCode[j] == sortedCode[j - 1]) {
                duplicateFound = true;
                System.out.println("ConfigurationController - checkCodeAccepptance - found duplicate color number " + sortedCode[j]);
            }
            j++;
        }

        boolean[] accepted = {!emptyHoleFound, !duplicateFound};
        return accepted;
    }

    private void incrColor(int pos) {
        if (gui.TRACK_CODE_SETTING) {
            logger.info("");
        }
        Circle circle = circles[pos];
        int nextState = circleState[pos] + 1;
        if (nextState >= numberOfColors) {
            nextState = 0;
        }
        circleState[pos] = nextState;
        Color nextColor = colors[nextState];
        LinearGradient gradient = new LinearGradient(0f, 1f, 1f, 0f, true, CycleMethod.NO_CYCLE,
                new Stop(0, nextColor),
                new Stop(1, Color.web("#ffffff")));
        if (gui.TRACK_CODE_SETTING) {
            logger.info("new color: " + nextColor + ", state: " + nextState + ", circle position: " + pos);
        }
        circle.fillProperty().set(gradient);
    }

    private void initializeColors() {
//        logger.info("");
        for (int i = 0; i < GameSettings.INSTANCE.MAX_NUMBER_OF_COLORS; i++) {
            try {
                LinearGradient gradient = new LinearGradient(0f, 1f, 1f, 0f, true, CycleMethod.NO_CYCLE,
                        new Stop(0, colors[i]),
                        new Stop(1, Color.web("#ffffff")));
                rectangles[i].fillProperty().set(gradient);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
    }

    private void initializeParamSettingArea(int locMinLength) {
//        logger.info("");

        lengthOfCode = GameSettings.INSTANCE.lengthOfCode;
        /*override Config setting if its below minimum)*/
        lengthOfCode = (lengthOfCode < locMinLength) ? locMinLength : lengthOfCode;
        numberOfColors = GameSettings.INSTANCE.numberOfColors;
        numberOfTries = GameSettings.INSTANCE.numberOfTries;
        colors = GameSettings.INSTANCE.COLORS;

        not_slider.setMax(GameSettings.INSTANCE.maxNumberOfTries);

        loc_slider.setMin(locMinLength);
        noc_slider.setMin((double) lengthOfCode);

        loc_slider.setValue(lengthOfCode);
        loc_textfield.setText("" + lengthOfCode);
        noc_slider.setValue(numberOfColors);
        noc_textfield.setText("" + numberOfColors);
        not_slider.setValue(numberOfTries);
        not_textfield.setText("" + numberOfTries);
    }

    private void initializeCodeSettingArea() {
//        logger.info("");

        for (int i = 0; i < GameSettings.INSTANCE.MAX_LENGTH_OF_CODE; i++) {
            circles[i].setVisible(true);
        }
        for (int i = GameSettings.INSTANCE.MAX_LENGTH_OF_CODE - 1; i >= lengthOfCode; i--) {
            circles[i].setVisible(false);
        }

        for (int i = 0; i < GameSettings.INSTANCE.MAX_NUMBER_OF_COLORS; i++) {
            rectangles[i].setVisible(true);
        }
        for (int i = GameSettings.INSTANCE.MAX_NUMBER_OF_COLORS - 1; i >= numberOfColors; i--) {
            rectangles[i].setVisible(false);
        }
    }

    /*--getter + setter*/
    public int getLengthOfCode() {
        return lengthOfCode;
    }

    public void setLengthOfCode(int lengthOfCode) {
        this.lengthOfCode = lengthOfCode;
    }

    public int getNumberOfColors() {
        return numberOfColors;
    }

    public void setNumberOfColors(int numberOfColors) {
        this.numberOfColors = numberOfColors;
    }

    public int getNumberOfTries() {
        return numberOfTries;
    }

    public void setNumberOfTries(int numberOfTries) {
        this.numberOfTries = numberOfTries;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        logger.info("");
        /*--attributes*/
        int k = GameSettings.INSTANCE.kForCrossover;
        int minValue;
        if (GameSettings.INSTANCE.crossoverType == CrossoverEnum.K_POINT) {
            minValue = k + 1;
        } else {
            minValue = 1;
        }
        final int LOC_MIN_VALUE = minValue;

        rectangles = new Rectangle[]{r0, r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18, r19};
        circles = new Circle[]{c0, c1, c2, c3, c4, c5, c6, c7, c8, c9};
        for (int i = 0; i < circleState.length; i++) {
            circleState[i] = GameSettings.INSTANCE.MAX_NUMBER_OF_COLORS;
        }

        /*--functions*/
        initializeParamSettingArea(LOC_MIN_VALUE);
        initializeColors();
        initializeCodeSettingArea();

        /*event listeners*/
        this.loc_slider.valueProperty().addListener(
                new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                        loc_textfield.setText(String.valueOf(newValue.intValue()));
                        lengthOfCode = newValue.intValue();
                        loc_textfield.setStyle("-fx-text-inner-color: black;");
                        noc_slider.setMin((double) newValue);
                    }
                }
        );
        this.noc_slider.valueProperty().addListener(
                new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                        noc_textfield.setText(String.valueOf(newValue.intValue()));
                        numberOfColors = newValue.intValue();
                        noc_textfield.setStyle("-fx-text-inner-color: black;");
                    }
                }
        );
        this.not_slider.valueProperty().addListener(
                new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                        not_textfield.setText(String.valueOf(newValue.intValue()));
                        numberOfTries = newValue.intValue();
                        not_textfield.setStyle("-fx-text-inner-color: black;");
                    }
                }
        );
        c0.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                if (gui.TRACK_CODE_SETTING) {
                    logger.info("");
                }
                incrColor(0);
            }
        });
        c1.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                if (gui.TRACK_CODE_SETTING) {
                    logger.info("");
                }
                incrColor(1);
            }
        });
        c2.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                if (gui.TRACK_CODE_SETTING) {
                    logger.info("");
                }
                incrColor(2);
            }
        });
        c3.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                if (gui.TRACK_CODE_SETTING) {
                    logger.info("");
                }
                incrColor(3);
            }
        });
        c4.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                if (gui.TRACK_CODE_SETTING) {
                    logger.info("");
                }
                incrColor(4);
            }
        });
        c5.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                if (gui.TRACK_CODE_SETTING) {
                    logger.info("");
                }
                incrColor(5);
            }
        });
        c6.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                if (gui.TRACK_CODE_SETTING) {
                    logger.info("");
                }
                incrColor(6);
            }
        });
        c7.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                if (gui.TRACK_CODE_SETTING) {
                    logger.info("");
                }
                incrColor(7);
            }
        });
        c8.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                if (gui.TRACK_CODE_SETTING) {
                    logger.info("");
                }
                incrColor(8);
            }
        });
        c9.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                if (gui.TRACK_CODE_SETTING) {
                    logger.info("");
                }
                incrColor(9);
            }
        });
    }
}
