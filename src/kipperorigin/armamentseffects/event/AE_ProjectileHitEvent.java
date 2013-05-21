package kipperorigin.armamentseffects.event;

import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.ProjectileHitEvent;

public class AE_ProjectileHitEvent extends AE_Event {
	private final Projectile projectile;
	private final ProjectileHitEvent event;

	public AE_ProjectileHitEvent(Player player, Projectile projectile,
			ProjectileHitEvent event) {
		super(player);

		this.projectile = projectile;
		this.event = event;
	}

	public Projectile getProjectile() {
		return this.projectile;
	}

	public ProjectileHitEvent getRawEvent() {
		return this.event;
	}
}