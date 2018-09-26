package ships;

import java.util.ArrayList;
import common.Position;

public abstract class Ship {
    protected String name;
    protected int length;
    protected int shootingRange;
    protected String shootingType;
    private int PV = 3;
    private boolean Placed = false;
    private ArrayList<Position> cellsPositions = new ArrayList<>();


    // Accessors
    public String getName() {
        return this.name;
    }
    public int getLength(){
        return this.length;
    }
    public int getShootingRange(){ return this.shootingRange; }
    public String getShootingType(){ return this.shootingType;}
    public int getPV(){ return this.PV; }
    public boolean isPlaced(){ return this.Placed; }
    public ArrayList<Position> getCellsPositions() { return this.cellsPositions; }

    // Setters
    public void setPV(int PV){ this.PV = PV; }
    public void addCellPosition(Position positionXY){ this.cellsPositions.add(positionXY); }
    public void addCellPosition(int x, int y) {this.cellsPositions.add((new Position(x, y)));System.out.println(x + " ; " + y); }
    public void setPlaced(boolean isPlaced){ this.Placed = isPlaced; }

    public void clearPosition(){this.cellsPositions.clear(); }
    public void changePosition(int index, Position positionXY){ this.cellsPositions.set(index, positionXY); }

    // Hit
    public void beHit(){ this.setPV(this.getPV() - 1); }

    public boolean containCell(int x, int y) {
        for (Position p : cellsPositions) {
            if ((p.positionX == x) && (p.positionY == y)) {
                return true;
            }
        }
        return false;
    }
}
