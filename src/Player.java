import java.util.ArrayList;

public class Player {

    private static int id_increment = 0;
    private int id;
    private ArrayList<Ship> ships = new ArrayList<>();

    public Player() {
        id = ++id_increment;

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

    public int getId() {
        return id;
    }
}
