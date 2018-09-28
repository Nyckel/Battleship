package ships;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import common.DisplayHelper;
import common.Position;
import javafx.geometry.Orientation;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

import static common.DisplayHelper.GRID_SIZE;

public abstract class Ship {
    private final static Color COLOR_FULL_HP = Color.GREEN;
    private final static Color COLOR_MID_HP = Color.ORANGE;
    private final static Color COLOR_LOW_HP = Color.RED;
    private final static Color COLOR_RANGE = Color.YELLOW;

    protected String name;
    protected int length;
    protected int shootingRange;
    protected String shootingType;
    private int hp = 3;
    private Orientation orientation;
    private boolean placed = false;
    private ArrayList<Position> cellsPositions;
    private ArrayList<Position> cellsInRange;

    public Ship() {
        cellsPositions = new ArrayList<>();
        cellsInRange = new ArrayList<>();
    }

    // Accessors
    public String getName() {
        return this.name;
    }
    public int getLength(){
        return this.length;
    }
    public int getShootingRange(){ return this.shootingRange; }
    public int getHP(){ return hp; }

    public boolean isPlaced(){ return this.placed; }
    public boolean isEmpty() { return cellsPositions.isEmpty(); }
    public boolean isHorizontal() { return orientation == Orientation.HORIZONTAL; }
    public boolean isVertical() { return orientation == Orientation.VERTICAL; }

    public boolean isValidCell(Position pos) {
        // Checks that when we place ship cells, we only select adjacent cells without turns
        if (cellsPositions.isEmpty()) return true;
        if (cellsPositions.size() < 2) return isCellAdjacent(pos);
        if (isHorizontal()) {
            return (pos.y == cellsPositions.get(0).y && isCellAdjacentHorizontal(pos));

        } else { // Vertical
            return (pos.x == cellsPositions.get(0).x && isCellAdjacentVertical(pos));
        }
    }

    private boolean isCellAdjacent(Position pos) {
        return isCellAdjacentVertical(pos) ^ isCellAdjacentHorizontal(pos);
    }

    private boolean isCellAdjacentHorizontal(Position pos) {
        for (Position p : cellsPositions) {
            if (p.x == pos.x -1 || p.x == pos.x + 1) return true;
        }
        return false;
    }

    private boolean isCellAdjacentVertical(Position pos) {
        for (Position p : cellsPositions) {
            if (p.y == pos.y -1 || p.y == pos.y + 1) return true;
        }
        return false;
    }


    public boolean isExtremity(Position pos) {
        if (cellsPositions.size() == 1) return true;
        if (isHorizontal()) {
            int xMin  = pos.x;
            int xMax = pos.x;
            for (Position p : cellsPositions) {
                if (p.x < xMin) xMin = p.x;
                if (p.x > xMax) xMax =  p.x;
            }
            return pos.x == xMin || pos.x == xMax;
        } else {
            int yMin = pos.y;
            int yMax = pos.y;
            for (Position p : cellsPositions) {
                if (p.y < yMin) yMin = p.y;
                if (p.y > yMax) yMax =  p.y;
            }
            return pos.x == yMin || pos.x == yMax;
        }

    }


    // Setters
    public void setHP(int p_hp){ hp = p_hp; }

    public void addCellPosition(Position pos) {
        cellsPositions.add(pos);
        if (cellsPositions.size() == 2) {
            if (cellsPositions.get(0).x == cellsPositions.get(1).x) this.orientation = Orientation.VERTICAL;
            else orientation = Orientation.HORIZONTAL;
        }

        if (cellsPositions.size() == length) {
            placed = true;
            orderCells();
            updateRange();
        }

    }

    public void changePosition(int index, Position positionXY) { this.cellsPositions.set(index, positionXY); }

    // Hit
    public void takeHit(){ hp--; }

    public boolean containsCell(Position pos) {
        for (Position p : cellsPositions) {
            if (p.equals(pos)) {
                return true;
            }
        }
        return false;
    }

    public boolean containsCell(int x ,int y) {
        return containsCell(new Position(x, y));
    }

    public void clearCell(Canvas canvas, Position pos) {
        for (Position p : cellsPositions) {
            if (p.equals(pos)) {
                DisplayHelper.clearCell(canvas, pos);
                cellsPositions.remove(p);
                return;
            }
        }
    }

    public void clearAllCells(Canvas canvas) {
        for (Position cell : cellsPositions)
            DisplayHelper.clearCell(canvas, cell);
        cellsPositions.clear();
        placed = false;
    }

    public void drawAllCells(Canvas canvas, boolean clean) {
        for (Position cell : cellsPositions) {
            Color color;
            if (clean) {
                DisplayHelper.colorCell(canvas, cell, Color.WHITE);
            }
            else {
                switch (getHP()) {
                    case 3 :
                        DisplayHelper.colorCell(canvas, cell, COLOR_FULL_HP);
                        break;
                    case 2 :
                        DisplayHelper.colorCell(canvas, cell, COLOR_MID_HP);
                        break;
                    case 1:
                        DisplayHelper.colorCell(canvas, cell, COLOR_LOW_HP);
                        break;
                }
            }
        }
    }

    private void orderCells() {
        Collections.sort(cellsPositions, new Comparator<Position>() {
            @Override
            public int compare(Position pos1, Position pos2) {
                if (isHorizontal()) return pos1.x - pos2.x;
                else return pos1.y - pos2.y;
            }
        });
    }

    public void updateRange() {
        cellsInRange.clear();
        Orientation shootingOrientation = orientation;
        if (shootingType == "side") {
            if (shootingOrientation == Orientation.HORIZONTAL)
                shootingOrientation = Orientation.VERTICAL;
            else
                shootingOrientation = Orientation.HORIZONTAL;
        }

        for (Position cell : cellsPositions) {
            for (int i = -shootingRange; i <= shootingRange; i++) {
                if (shootingOrientation == Orientation.HORIZONTAL) {
                    if (!cellsInRange.contains(new Position(cell.x + i, cell.y)))
                        cellsInRange.add(new Position(cell.x + i, cell.y));
                } else if (shootingOrientation == Orientation.VERTICAL) {
                    if (!cellsInRange.contains(new Position(cell.x, cell.y + i)))
                        cellsInRange.add(new Position(cell.x, cell.y + i));
                }
            }
        }
    }

    public ArrayList<Position> getCellsInRange() {
        return cellsInRange;
    }

    public void drawRange(Canvas canvas, boolean clean)
    {
        for (Position cell : cellsInRange)
            if (!clean)
                DisplayHelper.colorCell(canvas, cell, COLOR_RANGE);
            else
                DisplayHelper.colorCell(canvas, cell, Color.WHITE);
    }

    public boolean hasInRange(Position pos) {
        for (Position cell : cellsInRange) {
            if (cell.equals(pos)) return true;
        }
        return false;
    }

    public Position getPositionInAlignment(int distance) {
        if (cellsPositions.isEmpty()) return null;

        Position lastCell = distance > 0 ? cellsPositions.get(cellsPositions.size() - 1) : cellsPositions.get(0);
        if (isHorizontal()) {
            int newX = lastCell.x + distance;
            if (newX < 11 && newX >= 0) return new Position(newX, lastCell.y);
        } else {
            int newY = lastCell.y + distance;
            if (newY < 11 && newY >= 0) return new Position(lastCell.x, newY);
        }
        return null;
    }

    public void move(Position p) {
        int[] indices = {-1,1,-2,2};
        int movCell = 0;
        Position pos;

        if (isHorizontal()) {
            for (int i = 0; i < indices.length; i++) {
                if (p.x + indices[i] > 0 && p.x + indices[i] <= 10) {
                    if (containsCell(p.x + indices[i], p.y))
                    {
                        movCell = -indices[i];
                        break;
                    }
                }
            }


            for (int i = 0; i < cellsPositions.size(); i++) {
                pos = cellsPositions.get(i);
                changePosition(i, new Position(pos.x + movCell, pos.y));
            }
        }
        if (isVertical()) {
            for (int i = 0; i < indices.length; i++) {
                if (p.y + indices[i] > 0 && p.y + indices[i] <= 10) {
                    if (containsCell(p.x, p.y + indices[i]))
                    {
                        movCell = -indices[i];
                        break;
                    }
                }
            }

            for (int i = 0; i < cellsPositions.size(); i++) {
                pos = cellsPositions.get(i);
                changePosition(i, new Position(pos.x, pos.y + movCell));
            }
        }

        updateRange();
    }
}
