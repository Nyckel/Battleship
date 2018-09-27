package common;

import states.PlayingState;
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
        State p1ShipPlayingState = new PlayingState(p1);
        State p2ShipPlayingState = new PlayingState(p2);

        firstState.setNextState(p1ShipPlacementState);
        p1ShipPlacementState.setNextState(p2ShipPlacementState);
        p2ShipPlacementState.setNextState(p1ShipPlayingState);
        p1ShipPlayingState.setNextState(p2ShipPlayingState);
        p2ShipPlayingState.setNextState(p1ShipPlayingState);


        Display display = new Display(args, firstState);

        // Make display a new thread, have main Loop here

    }
}

