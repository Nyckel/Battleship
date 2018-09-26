package common;
import java.util.ArrayList;

import ships.*;


public class Player {

    private static int id_increment = 0;
    private int id;
    private ArrayList<Ship> ships = new ArrayList<>();
    private Ship selectedShip;

    public Player() {
        id = ++id_increment;
        selectedShip = null;

        // Ship initialization
        Ship aircraftCarrier = new AircraftCarrier();
        Ship cruiser = new Cruiser();
        Ship destroyer = new Destroyer();
        Ship submarine = new Submarine();
        Ship torpedoBoat = new TorpedoBoat();

        ships.add(aircraftCarrier);
        ships.add(cruiser);
        ships.add(destroyer);
        ships.add(submarine);
        ships.add(torpedoBoat);
    }

    public Ship getSelectedShip() { return selectedShip; }

    public int getId() {
        return id;
    }

    public void setSelectedShip(Ship s) { selectedShip = s; }

    public boolean areAllPlaced() {
        for(Ship s : ships) {
            if (! s.isPlaced()) {
                return false;
            }
        }
        return true;
    }

    public ArrayList<Ship> getShips() {
        return ships;
    }

    public boolean useCell(int x, int y)
    {
        for (Ship s : ships) {
            if (s.containCell(x, y)) {
                return true;
            }
        }
        return false;
    }
}
