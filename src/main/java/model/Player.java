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

    public void markAsPlayedWith(Player player) {
        hasntPlayedWith.remove(player);
        player.hasntPlayedWith.remove(this);
    }

    public boolean hasPlayedWith(Player player) {
        return !hasntPlayedWith.contains(player);
    }

    public String getName() {
        return name;
    }

    // Returns a random player this player hasn't played with
    public Player getPlayer() {
        int rand = (int) Math.floor(Math.random() * hasntPlayedWith.size());
        return hasntPlayedWith.get(rand);
    }

    @Override
    public String toString() {
        return name;
    }
}