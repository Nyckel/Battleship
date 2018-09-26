package states;

import common.DisplayHelper;
import common.Player;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import static common.DisplayHelper.GRID_SIZE;
import static common.DisplayHelper.WINDOW_HEIGHT;
import static common.DisplayHelper.WINDOW_WIDTH;

public class PlayingState extends State {

    private Player player;

    public PlayingState(Player p) {
        player = p;
    }

    @Override
    public void createScene() {

        Canvas cvUp = new Canvas(GRID_SIZE, GRID_SIZE);
        GraphicsContext gcUp = cvUp.getGraphicsContext2D();
        gcUp.setFill(Color.RED);
        gcUp.fillRect(0, 0, cvUp.getWidth(), cvUp.getHeight());

        Canvas cvDown = new Canvas(GRID_SIZE, GRID_SIZE);
        GraphicsContext gcDown = cvDown.getGraphicsContext2D();
        gcDown.setFill(Color.BLUE);
        gcDown.fillRect(0, 0, cvDown.getWidth(), cvDown.getHeight());

        DisplayHelper.drawGrid(cvUp);
        DisplayHelper.drawGrid(cvDown);

        VBox layout = new VBox(0);
        layout.setPrefSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        layout.getChildren().addAll(cvUp, cvDown);

        scene = new Scene(layout);
    }
}
