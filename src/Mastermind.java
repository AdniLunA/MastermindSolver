import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Mastermind extends Application{
    //attributes


    //functions
    @Override
    public void start (Stage primaryStage) throws Exception {
        System.out.println("Mastermind - start");
        primaryStage.setTitle("Mastermind Solver");

        primaryStage.show();
    }

    //getter + setter

    //MAIN
    public static void main (String ... args) {
        System.out.println("Mastermind - main");
        launch(args);
    }
}


