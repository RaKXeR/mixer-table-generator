package model;

import java.util.List;

public class RoundBuilder {

    /** Creates a new round with randomly selected players from the given list while ensuring that
     * none of the seated players have been seated together before. Does not mark each player as played with.
     * @param players list of available players for seating. Players are removed when seated at a table
     * @param maxTableSize preferred table size, sets the limit for player capacity for each table
     * @return a new round with random unrelated players on each table
     */
    public static Round createRound(List<Player> players, int maxTableSize) {
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
