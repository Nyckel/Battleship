import java.util.ArrayList;

abstract class Ship {
    protected String name;
    protected int boxes;
    protected int shootingRange;
    protected String shootingType;
    private int PV = 3;
    private ArrayList<Position> position = new ArrayList<>();


    // Accessors
    public String getName() {
        return this.name;
    }
    public int getBoxes(){
        return this.boxes;
    }
    public int getShootingRange(){ return this.shootingRange; }
    public String getShootingType(){ return this.shootingType;}
    public int getPV(){ return this.PV; }
    public ArrayList<Position> getPosition() { return this.position; }

    // Setters
    public void setPV(int PV){ this.PV = PV; }
    public void setPosition(Position positionXY){ this.position.add(positionXY); }

    public void clearPosition(){this.position.clear(); }
    public void changePosition(int index, Position positionXY){ this.position.set(index, positionXY); }

    // Hit
    public void beHit(){ this.setPV(this.getPV() - 1); }
}
