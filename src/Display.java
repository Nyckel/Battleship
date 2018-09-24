import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;


public class Display extends Application {

    private final int GRID_SIZE = 331;
    private final int WINDOW_WIDTH = 800;
    private final int WINDOW_HEIGHT = 600;

    private Stage window;
    private static ArrayList<Player> players;
    private static Queue<Scene> sceneQueue;


    public Display(){} // JavaFX application needs a default constructor where initialized

    public Display(String[] args, ArrayList<Player> p_players) {
        players = p_players;
        sceneQueue = new LinkedList<>();

        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {

        window = primaryStage;
        window.setTitle("Battle of Warships");

        createWelcomeScreen();
        window.show();

    }

    private void createWelcomeScreen() {
        Label title = new Label("Battle of Warships");
        title.setFont(new Font("Dubai Regular", 48));
        title.setTextFill(Color.DARKBLUE);

        Button startButton = new Button("Start");
        startButton.setOnAction(e -> placePlayersShips());
        startButton.setPrefSize(150, 50);

        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setPrefSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        layout.getChildren().addAll(title, startButton);

        Scene welcomeScene = new Scene(layout);

        window.setScene(welcomeScene);
    }

    private void placePlayersShips() {

        for (Player p : players) {
            sceneQueue.add(createShipPlacementViewForPlayer(p.getId()));
        }

        sceneQueue.add(createPlayingView());
        goToNextView();
    }


    private Scene createShipPlacementViewForPlayer(int playerId) {
        Label title = new Label("Player " + playerId + " ship placement");
        title.setFont(new Font("Dubai Regular", 30));
        title.setTextFill(Color.DARKBLUE);

        Canvas canvas = new Canvas(GRID_SIZE, GRID_SIZE);
        drawGrid(canvas);

        Button validatePlacement = new Button("Validate placement");
        validatePlacement.setOnAction(e -> goToNextView());

        VBox layout = new VBox(0 );
        layout.setAlignment(Pos.CENTER);
        layout.setPrefSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        layout.getChildren().addAll(title, canvas, validatePlacement);

        return new Scene(layout);

    }

    private Scene createPlayingView() {

        Canvas cvUp = new Canvas(GRID_SIZE, GRID_SIZE);
        GraphicsContext gcUp = cvUp.getGraphicsContext2D();
        gcUp.setFill(Color.RED);
        gcUp.fillRect(0, 0, cvUp.getWidth(), cvUp.getHeight());

        Canvas cvDown = new Canvas(GRID_SIZE, GRID_SIZE);
        GraphicsContext gcDown = cvDown.getGraphicsContext2D();
        gcDown.setFill(Color.BLUE);
        gcDown.fillRect(0, 0, cvDown.getWidth(), cvDown.getHeight());

        drawGrid(cvUp);
        drawGrid(cvDown);

        VBox layout = new VBox(0);
        layout.setPrefSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        layout.getChildren().addAll(cvUp, cvDown);

        return new Scene(layout);
    }

    private void drawGrid(Canvas cv) {
        GraphicsContext gc = cv.getGraphicsContext2D();

        gc.setFill(Color.BLACK);
        gc.setFont(new Font(15));
        gc.setStroke(Color.BLACK);

        int xPos = 40;
        for (char letter = 'A'; letter <= 'J'; letter++) {
            gc.fillText(String.valueOf(letter), xPos, 20, 30);
            xPos+= 30;
        }
        int yPos = 50;
        for (int i = 1; i < 11; i++) {
            gc.fillText(String.valueOf(i), 10, yPos, 30);
            yPos+= 30;
        }

        // Vertical lines
        for(int i = 0 ; i < cv.getWidth() ; i+=30){
            gc.strokeLine(i, 0, i, cv.getHeight() - (cv.getHeight()%30));
        }

        // Horizontal lines
        for(int i = 0 ; i < cv.getHeight() ; i+=30){
            gc.strokeLine(0, i, cv.getWidth(), i);
        }
    }

    private void goToNextView() {
        System.out.println("Going to next scene");
        Scene next = sceneQueue.poll();
        if (next != null) window.setScene(next);

    }
}
