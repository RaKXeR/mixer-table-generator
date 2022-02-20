package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class Round extends ArrayList<Table> {

    public Round() {
    }

    public Round(List<Table> tables) {
        addAll(tables);
    }

    /** Returns {@code true} if every player in the given list is present in at least one of our tables
     * @param players list of players to search for in the table seats
     * @return {@code true} if every player in the given list is present in at least one of our tables
     */
    public boolean hasAllPlayers(Collection<Player> players) {
        for (Player player : players) {
            boolean hasPlayer = this.stream().anyMatch(table -> table.hasPlayer(player));
            if (!hasPlayer) {
                return false;
            }
        }
        return true;
    }

    public int getPlayerCount() {
        int count = 0;
        for (Table table : this) {
            count += table.getPlayerCount();
        }
        return count;
    }
}
