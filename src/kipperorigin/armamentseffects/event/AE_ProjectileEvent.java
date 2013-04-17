package kipperorigin.armamentseffects.event;

import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.ProjectileLaunchEvent;

public class AE_ProjectileEvent extends AE_Event {

	private final Projectile projectile;
	private final ProjectileLaunchEvent event;

	public AE_ProjectileEvent(Player player, Projectile projectile, ProjectileLaunchEvent event) {
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

}
