package states;

import common.DisplayHelper;
import common.Player;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import ships.Ship;

import common.Position;
import static common.DisplayHelper.*;


public class ShipPlacementState extends State {

    private Player player;
    private Canvas canvas;
    private Label currentShipLabel;

    public ShipPlacementState(Player p) {
        player = p;
        player.setSelectedShip(player.getShips().get(0));
    }

    @Override
    public void createScene() {

        Insets margin = new Insets(10,10,10,10);
        BorderPane layout = new BorderPane();
        layout.setPrefSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        createTitle(layout, margin);
        createCanvas(layout, margin);
        createShipSelectionInterface(layout, margin);
        createValidationButton(layout, margin);

        scene = new Scene(layout);
    }

    private void createShipSelectionInterface(BorderPane mainLayout, Insets margin) {
        VBox buttonsLayout = new VBox(0);
        buttonsLayout.setAlignment(Pos.CENTER);

        currentShipLabel = new Label(player.getSelectedShip().getName());
        buttonsLayout.getChildren().add(currentShipLabel);

        for (Ship s : player.getShips())
        {
            Button b = new Button(s.getName() + " (" + s.getLength() + " - " + s.getShootingRange() + ")");
            b.setOnAction(e -> shipButtonAction(b, s));
            buttonsLayout.getChildren().add(b);
        }

        mainLayout.setMargin(buttonsLayout, margin);
        mainLayout.setAlignment(buttonsLayout, Pos.TOP_RIGHT);
        mainLayout.setRight(buttonsLayout);

    }

    private void createValidationButton(BorderPane mainLayout, Insets margin) {
        Button validatePlacementButton = new Button("Validate placement");
        validatePlacementButton.setOnAction(e -> validateButtonAction());

        mainLayout.setMargin(validatePlacementButton, margin);
        mainLayout.setAlignment(validatePlacementButton, Pos.BASELINE_CENTER);
        mainLayout.setBottom(validatePlacementButton);

    }

    private void createTitle(BorderPane mainLayout, Insets margin) {
        Label title = new Label("Player " + player.getId() + " ship placement");
        title.setFont(new Font("Dubai Regular", 30));
        title.setTextFill(Color.DARKBLUE);

        mainLayout.setMargin(title, margin);
        mainLayout.setAlignment(title, Pos.CENTER);
        mainLayout.setTop(title);
    }

    private void createCanvas(BorderPane mainLayout, Insets margin) {
        canvas = new Canvas(GRID_SIZE, GRID_SIZE);
        setClickHandler(canvas);

        DisplayHelper.drawGrid(canvas);

        mainLayout.setMargin(canvas, margin);
        mainLayout.setLeft(canvas);
    }

    private void setClickHandler(Canvas canvas) {
        canvas.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    Position pos = DisplayHelper.getClickPosition(event);
                    if (event.getButton() == MouseButton.PRIMARY) {
                        placeShipCell(pos);
                    } else if (event.getButton() == MouseButton.SECONDARY) {
                        rightClickOnGrid(pos);
                    }
                } catch (Exception e) {
                    // Clicked out of grid
                }

            }
        });
    }

    private void placeShipCell(Position pos) {
        if (player.getSelectedShip() != null) {
            if (!player.hasShipOnCell(pos)) {
                if (player.getSelectedShip().isValidCell(pos)) {
                    player.getSelectedShip().addCellPosition(pos);
                    DisplayHelper.colorCell(canvas, pos, Color.BLACK);
                }
            }
        }
        if (player.getSelectedShip().isPlaced()) player.setSelectedShip(null);
    }

    private void rightClickOnGrid(Position pos) {

        Ship s = player.getShipOnCell(pos);

        if (s == player.getSelectedShip()) {
            s.clearCell(canvas, pos);
        } else if (s != null) {
            s.clearAllCells(canvas);
        }
    }


    private void shipButtonAction(Button b, Ship s) {
        Ship current = player.getSelectedShip();
        if (current == null || current.isPlaced() || current.isEmpty()) {
            player.setSelectedShip(s);
            currentShipLabel.setText(s.getName());
        }
    }

    private void validateButtonAction() {
        //if (player.hasPlacedAllShips()) {
            goToNextState();
        //}
    }

}

