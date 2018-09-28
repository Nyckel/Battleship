package states;

import common.DisplayHelper;
import common.Player;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class EndingGameState extends State {

    private Player player;

    public EndingGameState(Player p) {
        player = p;
    }

    @Override
    public void createScene() {

        Label title = new Label("Player " + player.getId() + " has won!");
        title.setFont(new Font("Dubai Regular", 48));
        title.setTextFill(Color.DARKBLUE);

        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setPrefSize(DisplayHelper.WINDOW_WIDTH, DisplayHelper.WINDOW_HEIGHT);

        layout.getChildren().add(title);

        scene = new Scene(layout);

    }
}