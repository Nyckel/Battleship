package common;

import states.ShipPlacementState;
import states.State;
import states.WelcomeScreenState;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        // Create ships, grid and players
        Player p1 = new Player();
        Player p2 = new Player();

        ArrayList<Player> players = new ArrayList<>();
        players.add(p1);
        players.add(p2);

        State firstState = new WelcomeScreenState();
        State p1ShipPlacementState = new ShipPlacementState(p1);
        State p2ShipPlacementState = new ShipPlacementState(p2);
        //State p1ShipPlayingState = new playingState(p1);
        //State p2ShipPlayingState = new playingState(p2);
        // In playing state A, in the end I do nextState.setNextState(this) to loop between playing states.
        // If in playing state I see that a player has won, I change my nextState to gameEndingState


        firstState.setNextState(p1ShipPlacementState);
        p1ShipPlacementState.setNextState(p2ShipPlacementState);

        Display display = new Display(firstState);

        display.setCurrentState(firstState);
        display.startRunning(args);

        // Make display a new thread, have main Loop here

    }
}

