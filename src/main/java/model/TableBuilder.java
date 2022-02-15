package model;

import java.util.List;

public class TableBuilder {


    /** Creates a new table with randomly selected players from the given list while ensuring that
     * none of the seated players have been seated together before.
     * @param players list of available players for seating. Players are removed when seated at the table
     * @param tableSize preferred table size, sets the limit for player capacity
     * @return a new table with random unrelated players
     */
    public static Table createTable(List<Player> players, int tableSize) {
        // Return null if player count is lower than table size
        if (players.size() < tableSize) {
            return null;
        }

        // Pick random player in player list to be table pivot
        int rand = (int) Math.floor(Math.random() * players.size());
        Player pivot = players.get(rand);
        // Remove player from list to ensure they don't get picked again
        players.remove(pivot);

        // Create new table of specified max size and add pivot to it
        Table table = new Table(tableSize);
        table.addPlayer(pivot);
        // Fill table with players that haven't played with pivot
        while (!table.isFull()) {
            // Get a random player our pivot hasn't played with yet
            Player player = pivot.getPlayer();
            // Find another player if this one is unavailable
            if (!players.contains(player)) {
                continue;
            }
            // Check if random player has played with anyone on this table before
            boolean hasPlayed = player.hasPlayedWith(table.getPlayers());

            // Add player to table if he hasn't played with any table members yet
            if (!hasPlayed && table.addPlayer(player)) {
                // Mark table members as played with
                for (Player tablePlayer : table.getPlayers()) {
                    tablePlayer.markAsPlayedWith(player);
                }
                // Remove from buffered list of available table pivots
                players.remove(player);
            }
        }

        return table;
    }
}
