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

}
