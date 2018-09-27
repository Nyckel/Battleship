package states;

import common.Player;
import javafx.scene.Scene;
import javafx.stage.Stage;

public abstract class State {

    protected Scene scene;
    protected Stage window;
    protected State nextState;
    protected Player player;

    private boolean isSceneCreated;

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
        if (!isSceneCreated) {
            createScene();
            isSceneCreated = true;
        }

        window = w;
        window.setScene(scene);
    }

    public Player getPlayer() {
        return player;
    }
}
