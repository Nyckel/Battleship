package common;
import java.util.ArrayList;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import ships.*;


public class Player {

    private static int id_increment = 0;
    private int id;
    private ArrayList<Ship> ships;
    private Ship selectedShip;
    private boolean wasHit;
    private boolean wasSunk;
    private ArrayList<Shot> missilesReceived;
    private ArrayList<Shot> missilesFired;

    public Player() {
        id = ++id_increment;
        selectedShip = null;

        ships = new ArrayList<>();

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

        missilesReceived = new ArrayList<>();
        missilesFired = new ArrayList<>();
    }

    public Ship getSelectedShip() {
        return selectedShip;
    }

    public int getId() {
        return id;
    }

    public void setSelectedShip(Ship s) {
        selectedShip = s;
    }

    public boolean hasPlacedAllShips() {
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

    public boolean hasShipOnCell(Position pos) {
        for (Ship s : ships) {
            if (s.containsCell(pos)) {
                return true;
            }
        }
        return false;
    }

    public Ship getShipOnCell(Position pos) {
        for (Ship s : ships) {
            if (s.containsCell(pos)) {
                return s;
            }
        }
        return null;
    }

    public void drawAllShips(Canvas canvas, boolean clean) {
        for (Ship s : ships)
            s.drawAllCells(canvas, clean);
        drawMissiles(canvas, "received");
    }

    public void drawAllRanges(Canvas canvas, boolean clean) {
        for (Ship s : ships)
            s.drawRange(canvas, clean);
        drawMissiles(canvas, "fired");
    }

    public boolean hasInRange(Position pos) {
        for (Ship s : ships) {
            if (s.hasInRange(pos)) return true;
        }
        return false;
    }

    public void receiveMissile(Shot p) {
        wasHit = false;
        wasSunk = false;
        Ship s = getShipOnCell(p);

        if (s != null) {
            s.takeHit();
            wasHit = true;
            p.setHit(true);

            if (s.getHP() == 0) {
                ships.remove(s);

                wasSunk = true;
            }
        } else {
            wasHit = false;
        }
        missilesReceived.add(p);
    }

    public void addFiredMissile(Shot s) {
        missilesFired.add(s);
    }

    public boolean hasLost() {
        return ships.isEmpty();
    }

    public Position getLastHit() {
        if (!missilesReceived.isEmpty())
            return missilesReceived.get(missilesReceived.size() - 1);
        return null;
    }

    public boolean wasHit() {
        return wasHit;
    }

    public boolean wasSunk() {
        return wasSunk;
    }


    // return a list of the move that the player can make for a ship if the adversary miss a shot
    public ArrayList<Position> getPossibleMoves() {
        if (selectedShip == null) return null;
        ArrayList<Position> possibleMoves = new ArrayList<>();

        Position front1 = selectedShip.getPositionInAlignment(1);
        if (front1 != null && isCellOkToMoveIn(front1)) {
            possibleMoves.add(front1);
            Position front2 = selectedShip.getPositionInAlignment(2);
            if (front2 != null && isCellOkToMoveIn(front2)) {
                possibleMoves.add(front2);
            }
        }

        Position back1 = selectedShip.getPositionInAlignment(-1);
        if (back1 != null && isCellOkToMoveIn(back1)) {
            possibleMoves.add(back1);
            Position back2 = selectedShip.getPositionInAlignment(-2);
            if (back2 != null && isCellOkToMoveIn(back2)) {
                possibleMoves.add(back2);
            }
        }

        return possibleMoves;
    }

    private boolean isCellOkToMoveIn(Position pos) {
        for (Ship ship : ships) {
            if (ship.containsCell(pos)) {
                return false;
            }
        }
        return true;
    }

    public void drawPossibleMoves(Canvas canvas, boolean clean) {
        ArrayList<Position> possibleMoves = getPossibleMoves();


        if (possibleMoves == null) return;

        if (clean)
            DisplayHelper.colorCells(canvas, possibleMoves, Color.WHITE);
        else
            DisplayHelper.colorCells(canvas, possibleMoves, Color.LIGHTBLUE);

        drawMissiles(canvas, "received");
    }

    public void drawMissiles(Canvas canvas, String type) {
        ArrayList<Shot> missiles;
        if (type == "fired") {
            missiles = missilesFired;
        }
        else
            missiles = missilesReceived;

        for (Shot s : missiles) {
            if (s.isHit())
                DisplayHelper.addCrossToCell(canvas, s, Color.RED);
            else
                DisplayHelper.addCrossToCell(canvas, s, Color.BLACK);
        }
    }
}
