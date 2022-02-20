import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import model.Player;
import model.Table;
import model.TableBuilder;
import parser.PlayerList;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

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


        List<Collection<Table>> tableList = new LinkedList<>();
        List<Player> players;
        do {
            // Copy player list and use it to generate unique tables
            players = new ArrayList<>(playerMap.values());
            Collection<Table> tables = generateTables(players, 4);

            // Fill tables with remaining players
            topUpTables(tables, players);
            if (tables.size() > 0) {
                tableList.add(tables);
            }

            //TODO: Ask user if they want to store changes or not
            //TODO: Restore connections if we want to roll back
        } while (players.size() == 0);

        int i = 0;
        for (Collection<Table> tableCollection : tableList) {
            System.out.printf("Table list no.%d (size %d):\n", ++i, tableCollection.size());
            for (Table table : tableCollection) {
                System.out.println(table);
            }
        }
    }

    private static Collection<Table> generateTables(List<Player> players, int maxSize) {
        List<Table> tables = new ArrayList<>();
        // Generate tables until one comes out null, meaning we ran out of players
        Table table = TableBuilder.createTable(players, maxSize);
        while (table != null) {
            tables.add(table);
            table = TableBuilder.createTable(players, maxSize);
        }
        return tables;
    }

    private static void topUpTables(Collection<Table> tables, List<Player> players) {
        for (Table table : tables) {
            // Increase table capacity to fit in leftover players
            table.incrementSize();
            // Remove player from player list if we find a table for them
            players.removeIf(player -> {
                boolean addPlayer = !player.hasPlayedWith(table.getPlayers()) && table.addPlayer(player);
                if (addPlayer) {
                    player.markAsPlayedWith(table.getPlayers());
                }
                return addPlayer;
            });
        }
    }

}
