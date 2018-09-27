package common;

import javafx.application.Application;
import javafx.stage.Stage;
import states.State;

public class Display extends Application {

    private static Stage window;
    private static State currentState;

    public Display(){} // JavaFX application needs a default constructor where initialized

    public Display(String[] args, State first) {

        currentState = first;
        launch(args);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        window = primaryStage;
        window.setTitle("Battle of Warships");

        currentState.start(window);
        window.show();

    }

}
