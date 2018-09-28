package states;

import common.*;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;


import static common.DisplayHelper.GRID_SIZE;
import static common.DisplayHelper.WINDOW_HEIGHT;
import static common.DisplayHelper.WINDOW_WIDTH;

public class PlayingState extends State {

    private VBox labelAndButtonsLayout;
    private Canvas cvUp;
    private Canvas cvDown;
    private Label hitResultLabel;
    private boolean playerHasPlayed;
    private boolean shipMovementPossible;
    private Button refuseMoveButton;
    private Button endTurnButton;

    public PlayingState(Player p) {
        player = p;
    }

    @Override
    public void start(Stage window) {
        super.start(window);

        playerHasPlayed = false;
        shipMovementPossible = player.getLastHit()!= null && !player.wasHit();

        updateScene();

        DisplayHelper.clearAllCells(cvUp);
        DisplayHelper.clearAllCells(cvDown);

        player.drawAllRanges(cvUp, false);
        player.drawAllShips(cvDown, false);
    }

    @Override
    public void createScene() {
        labelAndButtonsLayout = new VBox();

        cvUp = new Canvas(GRID_SIZE, GRID_SIZE);
        GraphicsContext gcUp = cvUp.getGraphicsContext2D();
        setClickHandlerUp();

        cvDown = new Canvas(GRID_SIZE, GRID_SIZE);
        GraphicsContext gcDown = cvDown.getGraphicsContext2D();
        setClickHandlerDown();

        DisplayHelper.drawGrid(cvUp);
        DisplayHelper.drawGrid(cvDown);

        player.drawAllRanges(cvUp, false);
        player.drawAllShips(cvDown, false);

        hitResultLabel = new Label("");
        endTurnButton = new Button("End turn");
        endTurnButton.setOnAction(e -> endTurn());
        //endTurnButton.setVisible(false);
        labelAndButtonsLayout.getChildren().add(endTurnButton);

        VBox canvasLayout = new VBox(0);
        labelAndButtonsLayout = new VBox(0);

        canvasLayout.getChildren().addAll(cvUp, cvDown);
        labelAndButtonsLayout.getChildren().addAll(hitResultLabel);

        HBox layout = new HBox(0);
        layout.setPrefSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        layout.getChildren().addAll(canvasLayout, labelAndButtonsLayout);

        refuseMoveButton = new Button("I don't want to move any ship.");
        refuseMoveButton.setOnAction(e -> refuseMove());

        Label title = new Label("Player " + player.getId() + "'s turn");
        title.setFont(new Font("Dubai Regular", 20));
        title.setTextFill(Color.DARKBLUE);

        labelAndButtonsLayout.getChildren().add(title);

        scene = new Scene(layout);
    }

    public void updateScene() {
        if (player.getLastHit() != null) {
            if (player.wasHit()) {
                player.drawAllShips(cvDown, false);
                player.drawMissiles(cvDown, "received");
            } else DisplayHelper.addCrossToCell(cvDown, player.getLastHit(), Color.BLACK);
        }
        if (shipMovementPossible) {
            System.out.println("Ship movement possible");
            labelAndButtonsLayout.getChildren().add(refuseMoveButton);
        }
    }

    public void refuseMove() {
        shipMovementPossible = false;
        hidePossibleMovesOfSelectedShip();
        labelAndButtonsLayout.getChildren().remove(refuseMoveButton);
    }

    public void endTurn() {
        labelAndButtonsLayout.getChildren().remove(endTurnButton);
        goToNextState();
    }

    private void setClickHandlerUp() {
        cvUp.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    Position pos = DisplayHelper.getClickPosition(event);
                    if (event.getButton() == MouseButton.PRIMARY  && player.hasInRange(pos) && !playerHasPlayed && !shipMovementPossible)
                        fireMissile(pos);
                } catch (Exception e) {
                    // Clicked out of grid
                }

            }
        });
    }

    private void setClickHandlerDown() {
        cvDown.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (shipMovementPossible) {
                    try {
                        Position pos = DisplayHelper.getClickPosition(event);
                        if (event.getButton() == MouseButton.PRIMARY && player.hasShipOnCell(pos)) {
                            hidePossibleMovesOfSelectedShip();
                            player.setSelectedShip(player.getShipOnCell(pos));
                            player.drawPossibleMoves(cvDown, false);
                        }
                        else if (player.getSelectedShip() != null)
                        {
                            for (Position p : player.getPossibleMoves()) {
                                if (p.equals(pos)) {
                                    player.drawPossibleMoves(cvDown, true);
                                    player.drawAllRanges(cvUp, true);
                                    player.drawAllShips(cvDown, true);
                                    player.getSelectedShip().move(pos);
                                    shipMovementPossible = false;
                                    player.drawAllShips(cvDown, false);
                                    player.drawAllRanges(cvUp, false);
                                    labelAndButtonsLayout.getChildren().remove(refuseMoveButton);
                                    break;
                                }
                            }
                        }

                    } catch (Exception e) {
                        // Clicked out of grid
                    }
                }
            }
        });
    }

    private void fireMissile(Position pos) {
        Player adversary = nextState.getPlayer();
        adversary.receiveMissile(new Shot(pos));

        if (adversary.wasHit()) {
            player.addFiredMissile(new Shot(pos, true));
            DisplayHelper.addCrossToCell(cvUp, pos, Color.RED);
            if (adversary.wasSunk()) {
                hitResultLabel.setText("Hit and sunk");
            }
            else hitResultLabel.setText("Hit");
            
        } else {
            player.addFiredMissile(new Shot(pos, false));
            hitResultLabel.setText("Miss");
            DisplayHelper.addCrossToCell(cvUp, pos, Color.BLACK);
        }

        if (nextState.getPlayer().hasLost()) {
            EndingGameState endingGameState = new EndingGameState(player);
            System.out.println("end");
            setNextState(endingGameState);
        }

        labelAndButtonsLayout.getChildren().add(endTurnButton);
        playerHasPlayed = true;
    }

    private void hidePossibleMovesOfSelectedShip() {
        if (player.getSelectedShip() != null)
            player.drawPossibleMoves(cvDown, true);
    }
}
