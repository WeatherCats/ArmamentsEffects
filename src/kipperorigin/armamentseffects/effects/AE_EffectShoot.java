package kipperorigin.armamentseffects.effects;

import kipperorigin.armamentseffects.event.AE_InteractEvent;
import kipperorigin.armamentseffects.event.AE_ProjectileEvent;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.SmallFireball;
import org.bukkit.entity.Snowball;

public class AE_EffectShoot extends AE_EffectParent {

	@Override
	public void run(AE_ProjectileEvent event) {
		Player player = event.getPlayer();
		Projectile projectile = event.getProjectile();
		String args[] = event.getArgs();
		System.out.println(projectile.getVelocity());
		if (projectile instanceof Arrow) {
			event.cancel();
			if (args.length == 1) {
				if (args[0].equalsIgnoreCase("snowball"))
					player.launchProjectile(Snowball.class).setVelocity(projectile.getVelocity());
				else if (args[0].equalsIgnoreCase("fireball"))
					player.launchProjectile(Fireball.class).setVelocity(projectile.getVelocity());
				else if (args[0].equalsIgnoreCase("fireshot"))
					player.launchProjectile(SmallFireball.class).setVelocity(projectile.getVelocity());
				else if (args[0].equalsIgnoreCase("arrow"))
					player.launchProjectile(Arrow.class).setVelocity(projectile.getVelocity());
				else
					return;
			} else
				return;
		} else
			return;
	}

	public void run(AE_InteractEvent event) {
		Player player = event.getPlayer();
	}
}
