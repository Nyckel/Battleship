package common;

import javafx.application.Application;
import javafx.stage.Stage;
import states.State;

public class Display extends Application {

    private Stage window;
    private State currentState;

    public Display(){} // JavaFX application needs a default constructor where initialized

    public Display(State first) {
        currentState = first;

    }

    public void startRunning(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {

        window = primaryStage;
        window.setTitle("Battle of Warships");

        System.out.println("Hey from before");
        currentState.start(window);
        window.show();

    }

    public void setCurrentState(State s) {
        currentState = s;
    }

}
