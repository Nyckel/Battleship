abstract class Ship {
    protected String name;
    protected int boxes;
    protected int shootingRange;
    protected String shootingType;
    private int PV = 3;
    // private boxesPosition<>();

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

    // Setters
    /*abstract void setName();
    abstract void setBoxes();
    abstract void setShootingRange();
    abstract void setShootingType();*/
    public void setPV(int PV){ this.PV = PV; }

    // Shoot

    // Hit
    public void beHit(){ this.setPV(this.getPV() - 1); }
}
