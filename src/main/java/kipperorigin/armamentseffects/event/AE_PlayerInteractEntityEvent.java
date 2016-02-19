package kipperorigin.armamentseffects.event;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class AE_PlayerInteractEntityEvent extends AE_Event {

    private final Entity entity;
    private final PlayerInteractEntityEvent event;
    private final Location location;

    public AE_PlayerInteractEntityEvent(Player player, Entity entity, PlayerInteractEntityEvent event, Location location) {
        super(player);

        this.entity = entity;
        this.event = event;
        this.location = location;
    }

    public Entity getEntity() {
        return entity;
    }

    public PlayerInteractEntityEvent getRawEvent() {
        return event;
    }

    public Location getLocation() {
        return location;
    }
}
