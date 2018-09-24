import java.util.ArrayList;

public class Player {

    private static int id_increment = 0;
    private int id;
    private ArrayList<Ship> ships;

    public Player() {
        id = ++id_increment;
//        ship = new Ship();
    }

    public int getId() {
        return id;
    }
}
