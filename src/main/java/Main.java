import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import model.Player;
import model.Table;
import parser.PlayerList;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class Main {

    // JSON file with player list on disk
    private static final String PLAYER_LIST = "players.json";

    private static final HashMap<String, Player> playerMap = new HashMap<>();

    public static void main(String[] args) {
        // Get player list from JSON
        Gson gson = new Gson();
        JsonReader reader;
        PlayerList playerList;
        try {
            reader = new JsonReader(new FileReader(PLAYER_LIST));
            playerList = gson.fromJson(reader, PlayerList.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }

        // Generate player objects based on player list
        for (String player : playerList.getPlayers()) {
            playerMap.put(player, new Player(player));
        }

        // Populate player nodes
        for (Player player : playerMap.values()) {
            player.setHasntPlayedWith(playerMap.values());
        }

        // Copy player list and use it to generate unique tables
        List<Player> players = new ArrayList<>(playerMap.values());
        Collection<Table> tables = generateTables(players, 4);

        // Fill tables with remaining players
        topUpTables(tables, players);

        for (Table table : tables) {
            System.out.println(table);
        }
    }

    private static Collection<Table> generateTables(List<Player> players, int maxSize) {
        List<Table> tables = new ArrayList<>();
        // Generate tables until one comes out null, meaning we ran out of players
        Table table = Table.createTable(players, maxSize);
        while (table != null) {
            tables.add(table);
            table = Table.createTable(players, maxSize);
        }
        return tables;
    }

    private static void topUpTables(Collection<Table> tables, List<Player> players) {
        // Increase table capacity to fit in leftover players
        for (Table table : tables) {
            table.incrementSize();
        }
        // Remove player from player list if we find a table for them
        for (Table table : tables) {
            players.removeIf(player -> !table.hasPlayedWith(player) && table.addPlayer(player));
        }
    }

}
