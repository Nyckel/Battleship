package states;

import common.DisplayHelper;
import common.Player;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import ships.Ship;

import java.util.ArrayList;

import static common.DisplayHelper.*;
import static common.DisplayHelper.CELL_SIZE;

public class ShipPlacementState extends State {

    private Player player;
    private Canvas canvas;

    public ShipPlacementState(Player p) {
        player = p;
    }

    @Override
    public void createScene() {
        ArrayList<Button> shipsButtons = new ArrayList<>();

        Label title = new Label("Player " + player.getId() + " ship placement");
        title.setFont(new Font("Dubai Regular", 30));
        title.setTextFill(Color.DARKBLUE);

        canvas = new Canvas(GRID_SIZE, GRID_SIZE);
        setClickHandler(canvas);
        DisplayHelper.drawGrid(canvas);

        Button validatePlacement = new Button("Validate placement");
        validatePlacement.setOnAction(e -> validatePlacement());

        HBox buttons = new HBox(0);
        buttons.setAlignment(Pos.CENTER);

        for (Ship s : player.getShips())
        {
            Button b = new Button(s.getName() + " (" + s.getLength() + " - " + s.getShootingRange() + ")");
            shipsButtons.add(b);
            b.setOnAction(e -> shipButtonAction(b, s));
            buttons.getChildren().add(b);
        }

        // getMouseClickPosition(canvas);

        VBox layout = new VBox(0 );
        layout.setAlignment(Pos.CENTER);
        layout.setPrefSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        layout.getChildren().addAll(title, canvas, buttons, validatePlacement);

        scene = new Scene(layout);
    }


    private void setClickHandler(Canvas canvas){
        canvas.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                int x = (int) event.getX();
                int y = (int) event.getY();
                if ((x >= CELL_SIZE) && (y >= CELL_SIZE)) {
                    clickOnGrid((x - x%CELL_SIZE)/CELL_SIZE, (y - y%CELL_SIZE)/CELL_SIZE);
                }
            }
        });
    }

    private void clickOnGrid(int x, int y) // TODO: Check if worth moving to DisplayHelper
    {
        if (player.getSelectedShip() != null) {
            if (! player.useCell(x, y)) {
                if (player.getSelectedShip().getCellsPositions().size() == 0)
                {
                    player.getSelectedShip().addCellPosition(x,y);
                    DisplayHelper.colorCells(canvas, x, y);
                }
                else {

                }
            }
        }
    }


    private void shipButtonAction(Button b, Ship s)
    {
        if ((player.getSelectedShip() == null) || (player.getSelectedShip() == s))
        {
            if (player.getSelectedShip() == null) {
                player.setSelectedShip(s);
                b.setText("Annuler");
            }
            else {
                player.setSelectedShip(null);
                b.setText(s.getName() + " (" + s.getLength() + " - " + s.getShootingRange() + ")");
            }
        }
    }

    private void validatePlacement() {
        if (player.areAllPlaced()) {
            goToNextState();
        }
    }
}
