package org.cubeville.effects.event;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class PlayerInteractEntityEvent extends Event {

    private final Entity entity;
    private final org.bukkit.event.player.PlayerInteractEntityEvent event;
    private final Location location;

    public PlayerInteractEntityEvent(Player player, Entity entity, org.bukkit.event.player.PlayerInteractEntityEvent event, Location location) {
        super(player);

        this.entity = entity;
        this.event = event;
        this.location = location;
    }

    public Entity getEntity() {
        return entity;
    }

    public org.bukkit.event.player.PlayerInteractEntityEvent getRawEvent() {
        return event;
    }

    public Location getLocation() {
        return location;
    }
}
