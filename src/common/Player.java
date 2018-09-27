package common;
import java.lang.reflect.Array;
import java.util.ArrayList;

import javafx.geometry.Pos;
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
    private ArrayList<Position> missilesReceived;
    private ArrayList<Position> missilesFired;

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
            if (s.containsCell(pos))
            {
                return s;
            }
        }
        return null;
    }

    public void drawAllShips(Canvas canvas) {
        for (Ship s : ships)
            s.drawAllCells(canvas);
    }

    public void drawAllRanges(Canvas canvas) {
        for (Ship s : ships)
            s.drawRange(canvas);
    }

    public boolean hasInRange(Position pos) {
        for (Ship s : ships) {
            if (s.hasInRange(pos)) return true;
        }
        return false;
    }

    public void receiveMissile(Position p) {
        wasHit = false;
        wasSunk = false;
        Ship s = getShipOnCell(p);

        missilesReceived.add(p);

        if (s != null) {
            s.takeHit();
            wasHit = true;

            if (s.getHP() == 0) {
                ships.remove(s);
                wasSunk = true;
            }
        } else {
            wasHit = false;
        }
    }

    public void addFiredMissile(Position pos) {
        missilesFired.add(pos);
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
        return wasHit;
    }

    public ArrayList<Position> getPossibleMoves() {
        if (selectedShip == null) return null;
        ArrayList<Position> possibleMoves = new ArrayList<>();

        Position front1 = selectedShip.getPositionInAlignment(1);
        if (isCellOkToMove(front1)) {
            possibleMoves.add(front1);
            Position front2 = selectedShip.getPositionInAlignment(2);
            if (isCellOkToMove(front2)) {
                possibleMoves.add(front2);
            }
        }

        Position back1 = selectedShip.getPositionInAlignment(-1);
        if (isCellOkToMove(back1)) {
            possibleMoves.add(back1);
            Position back2 = selectedShip.getPositionInAlignment(-2);
            if (isCellOkToMove(back2)) {
                possibleMoves.add(back2);
            }
        }

        return possibleMoves;
    }

    private boolean isCellOkToMove(Position pos) {
        for (Ship ship : ships) {
            if (ship.containsCell(pos)) return false;
        }
        return true;
    }

    public void drawPossibleMoves(Canvas canvas) {
        ArrayList<Position> possibleMoves = getPossibleMoves();

        if (possibleMoves == null) return;

        DisplayHelper.colorCells(canvas, possibleMoves, Color.LIGHTBLUE);
    }
}
