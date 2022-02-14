package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Table {

    private static final int MAX_TABLE_SIZE = 5;

    private final Collection<Player> players;
    private int maxSize;

    public Table(int maxSize) {
        this(new ArrayList<>(maxSize), maxSize);
    }

    public Table(Collection<Player> players, int maxSize) {
        this.players = players;
        this.maxSize = maxSize;
    }

    public int getSize() {
        return maxSize;
    }

    public void setSize(int maxSize) {
        this.maxSize = Math.min(MAX_TABLE_SIZE, maxSize);
    }

    public void incrementSize() {
        if (maxSize < MAX_TABLE_SIZE) {
            maxSize++;
        }
    }

    public Collection<Player> getPlayers() {
        return players;
    }

    public boolean addPlayer(Player player) {
        // Return if table is full or player is already in the table
        if (isFull() || players.contains(player)) {
            return false;
        }
        players.add(player);
        return true;
    }

    public boolean removePlayer(Player player) {
        return players.remove(player);
    }

    // Returns true if no more players fit in the table
    public boolean isFull() {
        return players.size() >= maxSize;
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        for (Player player : players) {
            b.append(player).append(" ");
        }
        return b.toString();
    }

    public boolean hasPlayedWith(Player player) {
        boolean hasPlayedWith = false;
        for (Player tablePlayer : players) {
            if (tablePlayer.hasPlayedWith(player)) {
                hasPlayedWith = true;
                break;
            }
        }
        return hasPlayedWith;
    }

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
            boolean hasPlayed = table.hasPlayedWith(player);

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
