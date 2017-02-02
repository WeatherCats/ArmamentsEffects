package org.cubeville.effects.event;

import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.ProjectileLaunchEvent;

public class ProjectileEvent extends Event {

    private final Projectile projectile;
    private final ProjectileLaunchEvent event;

    public ProjectileEvent(Player player, Projectile projectile, ProjectileLaunchEvent event) {
        super(player);

        this.projectile = projectile;
        this.event = event;
    }

    public Projectile getProjectile() {
        return projectile;
    }

    public ProjectileLaunchEvent getRawEvent() {
        return event;
    }

    public void cancel() {
        event.setCancelled(true);
    }
}
