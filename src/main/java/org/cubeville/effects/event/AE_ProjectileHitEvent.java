package org.cubeville.effects.event;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;

public class AE_ProjectileHitEvent extends Event {

    private final Projectile projectile;
    private final org.bukkit.event.entity.ProjectileHitEvent event;
    private final Location location;

    public AE_ProjectileHitEvent(Player player, Projectile projectile, org.bukkit.event.entity.ProjectileHitEvent event, Location location) {
        super(player);

        this.projectile = projectile;
        this.event = event;
        this.location = location;
    }

    public Projectile getProjectile() {
        return projectile;
    }

    public org.bukkit.event.entity.ProjectileHitEvent getRawEvent() {
        return event;
    }

    public Location getLocation() {
        return location;
    }
}
