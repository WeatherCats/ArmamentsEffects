package kipperorigin.armamentseffects.event;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class AE_InteractEvent extends AE_Event {

	private final Location location;
	private final PlayerInteractEvent event;

	public AE_InteractEvent(Player player, Location location, PlayerInteractEvent event) {
		super(player);

		this.location = location;
		this.event = event;
	}

	public Location getClickedLocation() {
		return location;
	}

	public PlayerInteractEvent getRawEvent() {
		return event;
	}
	
	public Action getAction() {
		return event.getAction();
	}

}
