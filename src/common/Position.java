package common;

public class Position {
    public int x;
    public int y;

    public Position(int xPos, int yPos)
    {
        this.x = xPos;
        this.y = yPos;
    }

    public boolean equals(Position p) {
        return p.x == x && p.y == y;
    }
}
