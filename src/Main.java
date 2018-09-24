import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        // Create ships, grid and players
        Player p1 = new Player();
        Player p2 = new Player();
        ArrayList<Player> players = new ArrayList<>();
        players.add(p1);
        players.add(p2);

        Display display = new Display(args, players);

    }

}

