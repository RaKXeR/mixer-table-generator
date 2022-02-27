package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RoundBuilder {

    /** Creates a new round with randomly selected players from the given list while ensuring that
     * none of the seated players have been seated together before. Does not mark each player as played with.
     * @param playerList list of available players for seating. Players are removed when seated at a table
     * @param maxTableSize preferred table size, sets the limit for player capacity for each table
     * @return a new round with random unrelated players on each table, or null if not all players can be seated
     */
    public static Round createRound(Collection<Player> playerList, int maxTableSize) {
        List<Player> players;
        // Keep track of how many times we generated a round that had leftover players to prevent infinite loops
        int retryCounter = 0;
        do {
            // Copy player list and use it to generate unique tables
            players = new ArrayList<>(playerList);

            // Generate a round of tables with players who haven't played with each other
            Round round = createUncheckedRound(players, maxTableSize);

            // Try again if not all players fit in
            if (players.size() > 0 || !round.hasAllPlayers(playerList)) {
                // Break out of loop if finding a set of tables is taking too many attempts
                if (++retryCounter >= TableBuilder.TIMEOUT) {
                    break;
                }
                players.clear();
                continue;
            }
            if (round.size() > 0) {
                return round;
            }

        } while (players.size() == 0);

        return null;
    }

    /** Creates a new round with randomly selected players from the given list while ensuring that
     * none of the seated players have been seated together before. Does not mark each player as played with.
     * @param players list of available players for seating. Players are removed when seated at a table
     * @param maxTableSize preferred table size, sets the limit for player capacity for each table
     * @return a new round with random unrelated players on each table. Doesn't ensure all players are seated
     */
    private static Round createUncheckedRound(List<Player> players, int maxTableSize) {
        Round round = new Round();
        // Generate tables until one comes out null, meaning we ran out of players
        Table table = TableBuilder.createTable(players, maxTableSize);
        while (table != null) {
            round.add(table);
            table = TableBuilder.createTable(players, maxTableSize);
        }

        // Fill tables with remaining players
        topUpTables(round, players);

        return round;
    }

    private static void topUpTables(Round round, List<Player> players) {
        for (Table table : round) {
            // Increase table capacity to fit in leftover players
            table.incrementSize();
            // Remove player from player list if we find a table for them
            players.removeIf(player -> !player.hasPlayedWith(table.getPlayers()) && table.addPlayer(player));
        }
    }
}
