import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import commands.*;
import model.*;
import parser.PlayerList;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    // JSON file with player list on disk
    private static final String PLAYER_LIST = "players.json";

    private static final HashMap<String, Player> joinedMap = new HashMap<>(), leftMap = new HashMap<>();
    private static final List<Round> rounds = new ArrayList<>();
    private static final AtomicInteger preferredTableSize = new AtomicInteger(4);

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
            joinedMap.put(player, new Player(player));
        }

        // Populate player nodes
        for (Player player : joinedMap.values()) {
            player.setHasntPlayedWith(joinedMap.values());
        }

        // Launch CLI for controlling player-related functionality
        Scanner s = new Scanner(System.in);
        while (true) {
            // Present options to user
            System.out.print(
                    "/--------------------------------------COMMANDS--------------------------------------\\\n" +
                            "|1. Generate new round      - creates new tables with all the players                |\n" +
                            "|2. List current players    - shows all available players                            |\n" +
                            "|3. Add player(s)           - adds one or more new players to the collection         |\n" +
                            "|4. Remove player(s)        - removes one or more players from collection            |\n" +
                            "|5. Disconnect player(s)    - prevent player from matchmaking with provided players  |\n" +
                            "|------------------------------------------------------------------------------------|\n" +
                            "|6. List previous rounds    - shows each table of each previously generated round    |\n" +
                            "|7. List player connections - lists players who haven't played with the given player |\n" +
                            "|8. Set target table size   - sets the preferred table size (bypassed if needed)     |\n" +
                            "|q. Quit                    - exits the program                                      |\n" +
                            "\\------------------------------------------------------------------------------------/\n" +
                            ": "
            );

            // Process numeric inputs
            if (s.hasNextInt()) {
                int choice = s.nextInt();
                s.nextLine();
                switch (choice) {
                    case 1: GenerateNewRound.run(s, joinedMap, rounds, preferredTableSize); continue;
                    case 2: ListCurrentPlayers.run(s, joinedMap); continue;
                    case 3: AddNewPlayers.run(s, joinedMap, leftMap); continue;
                    case 4: RemovePlayers.run(s, joinedMap, leftMap); continue;
                    case 8: SetTableSize.run(s, preferredTableSize); continue;
                    default:
                        System.out.println("The number you chose is invalid. Please select one from the given list.");
                }
            }

            // Quit program if requested
            String response = s.nextLine().trim().toLowerCase();
            if (response.equals("q") || response.equals("quit") || response.equals("exit")) {
                return;
            }
        }

    }

}
