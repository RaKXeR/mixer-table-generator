package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Player {

    private final String name;

    private final List<Player> hasntPlayedWith = new ArrayList<>();

    public Player(String name) {
        this.name = name;
    }

    public void setHasntPlayedWith(Collection<Player> hasntPlayedWith) {
        this.hasntPlayedWith.addAll(hasntPlayedWith);
        this.hasntPlayedWith.remove(this);
    }

    public void markAsPlayedWith(Collection<Player> players) {
        hasntPlayedWith.removeAll(players);
        players.forEach(player -> player.hasntPlayedWith.remove(this));
    }

    public void markAsPlayedWith(Player player) {
        hasntPlayedWith.remove(player);
        player.hasntPlayedWith.remove(this);
    }

    public boolean hasPlayedWith(Collection<Player> player) {
        return !hasntPlayedWith.containsAll(player);
    }

    public boolean hasPlayedWith(Player player) {
        return !hasntPlayedWith.contains(player);
    }

    public String getName() {
        return name;
    }

    // Returns a random player this player hasn't played with
    public Player getPlayer() {
        // Return null if there are no players left
        if (hasntPlayedWith.size() <= 0) {
            return null;
        }
        int rand = (int) Math.floor(Math.random() * hasntPlayedWith.size());
        return hasntPlayedWith.get(rand);
    }

    @Override
    public String toString() {
        return name;
    }
}
