package states;

import common.DisplayHelper;
import common.Player;
import common.Position;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


import static common.DisplayHelper.GRID_SIZE;
import static common.DisplayHelper.WINDOW_HEIGHT;
import static common.DisplayHelper.WINDOW_WIDTH;

public class PlayingState extends State {

    private VBox layout;
    private Canvas cvUp;
    private Canvas cvDown;
    private Label hitResultLabel;
    private boolean playerHasPlayed;
    private boolean shipMovementPossible;

    public PlayingState(Player p) {
        player = p;
    }

    @Override
    public void start(Stage window) {
        super.start(window);

        playerHasPlayed = false;
        shipMovementPossible = player.getLastHit()!= null && !player.wasHit();

        updateScene();
    }

    @Override
    public void createScene() {

        cvUp = new Canvas(GRID_SIZE, GRID_SIZE);
        GraphicsContext gcUp = cvUp.getGraphicsContext2D();
        setClickHandlerUp(cvUp);

        cvDown = new Canvas(GRID_SIZE, GRID_SIZE);
        GraphicsContext gcDown = cvDown.getGraphicsContext2D();
        setClickHandlerDown(cvDown);

        DisplayHelper.drawGrid(cvUp);
        DisplayHelper.drawGrid(cvDown);

        player.drawAllRanges(cvUp);
        player.drawAllShips(cvDown);

        hitResultLabel = new Label("");

        layout = new VBox(0);
        layout.setPrefSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        layout.getChildren().addAll(cvUp, cvDown, hitResultLabel);

        scene = new Scene(layout);
    }

    public void updateScene() {
        if (player.getLastHit() != null)
            DisplayHelper.addCrossToCell(cvDown, player.getLastHit(), player.wasHit() ? Color.RED : Color.BLACK);
        if (! player.wasHit()) {
            System.out.println("Ship movement possible");
            Button refuseMoveButton = new Button("I don't want to move any ship.");
            refuseMoveButton.setOnAction(e -> refuseMove());
            layout.getChildren().add(refuseMoveButton);
        }
    }

    public void refuseMove()
    {
        shipMovementPossible = false;
        //TODO: Check if button always last inserted element
        layout.getChildren().remove(layout.getChildren().size()-1);
    }

    private void setClickHandlerUp(Canvas canvas) {
        canvas.setOnMouseClicked(new EventHandler<MouseEvent>() {
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

    private void setClickHandlerDown(Canvas canvas) {
        canvas.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (shipMovementPossible) {
                    try {
                        //TODO: Say to user he can move a ship
                        Position pos = DisplayHelper.getClickPosition(event);
                        if (event.getButton() == MouseButton.PRIMARY && player.hasShipOnCell(pos)) {
                            player.setSelectedShip(player.getShipOnCell(pos));
                            player.drawPossibleMoves(canvas);
                        }
                        else if (player.getSelectedShip() != null)
                        {
                            if ( player.getPossibleMoves().contains(pos)) {
                                player.getSelectedShip().move(pos);
                                System.out.println("ok");
                                shipMovementPossible = false;
                                player.drawAllShips(canvas);
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
        player.addFiredMissile(pos);
        System.out.println(nextState);
        System.out.println(nextState.getPlayer());

        Player adversary = nextState.getPlayer();
        adversary.receiveMissile(pos);
        
        if (adversary.wasHit()) {
            DisplayHelper.addCrossToCell(cvUp, pos, Color.RED);
            if (adversary.wasSunk()) hitResultLabel.setText("Hit");
            else hitResultLabel.setText("Hit and sunk");
            
        } else {
            hitResultLabel.setText("Miss");
            DisplayHelper.addCrossToCell(cvUp, pos, Color.BLACK);
        }

        if (nextState.getPlayer().hasLost()) {
            EndingGameState endingGameState = new EndingGameState(player);
            setNextState(endingGameState);
        }

        Button endTurnButton = new Button("End turn");
        endTurnButton.setOnAction(e -> goToNextState());
        layout.getChildren().add(endTurnButton);
        System.out.println("Adding button for next turn");
        playerHasPlayed = true;
//        goToNextState();
    }
}
