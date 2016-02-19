package kipperorigin.armamentseffects.event;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.ProjectileHitEvent;

public class AE_ProjectileHitEvent extends AE_Event {

    private final Projectile projectile;
    private final ProjectileHitEvent event;
    private final Location location;

    public AE_ProjectileHitEvent(Player player, Projectile projectile, ProjectileHitEvent event, Location location) {
        super(player);

        this.projectile = projectile;
        this.event = event;
        this.location = location;
    }

    public Projectile getProjectile() {
        return projectile;
    }

    public ProjectileHitEvent getRawEvent() {
        return event;
    }

    public Location getLocation() {
        return location;
    }
}
