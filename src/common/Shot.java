package common;

public class Shot extends Position {
    private boolean hit;

    public Shot(Position pos) {
        super(pos.x, pos.y);
        this.hit = false;
    }

    public Shot(Position pos, boolean hit){
        super(pos.x, pos.y);
        this.hit = hit;
    }

    public void setHit(boolean hit){
        this.hit = hit;
    }

    public boolean isHit() {
        return this.hit;
    }
}
