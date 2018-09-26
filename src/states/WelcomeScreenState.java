package states;

import common.DisplayHelper;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class WelcomeScreenState extends State {

//    public WelcomeScreenState() {
////        super();
//        createScene();
//    }

    @Override
    public void createScene() {
        System.out.println("Hey from creator");
        Label title = new Label("Battle of Warships");
        title.setFont(new Font("Dubai Regular", 48));
        title.setTextFill(Color.DARKBLUE);

        Button startButton = new Button("Start");
        startButton.setOnAction(e -> goToNextState());
        startButton.setPrefSize(150, 50);

        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setPrefSize(DisplayHelper.WINDOW_WIDTH, DisplayHelper.WINDOW_HEIGHT);

        layout.getChildren().addAll(title, startButton);

        scene = new Scene(layout);

    }
}