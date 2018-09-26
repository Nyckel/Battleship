package states;

import javafx.scene.Scene;
import javafx.stage.Stage;

public abstract class State {

    protected Scene scene;
    protected Stage window;
    protected State nextState;

    public abstract void createScene();

    public void setNextState(State next) {
        nextState = next;
    }

    protected void goToNextState() {
        if (nextState != null) {
            nextState.start(window);
        }

    }
    public void start(Stage w) {
        createScene();

        window = w;
        window.setScene(scene);
    }
}
