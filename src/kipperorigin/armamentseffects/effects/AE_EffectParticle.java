package kipperorigin.armamentseffects.effects;

import kipperorigin.armamentseffects.AE_Main;
import kipperorigin.armamentseffects.event.AE_DamageEvent;
import kipperorigin.armamentseffects.event.AE_ProjectileEvent;
import kipperorigin.armamentseffects.event.AE_ProjectileHitEvent;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Listener;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

public class AE_EffectParticle extends AE_EffectParent implements Listener {
	int running;
	private AE_Main plugin;

	public AE_EffectParticle(AE_Main plugin) {
		this.plugin = plugin;
	}

	public void run(final AE_ProjectileEvent event) {
		Player player = event.getPlayer();
		final Projectile projectile = event.getProjectile();
		String[] args = event.getArgs();

		if ((args.length == 0) || (args[0].isEmpty()))
			return;
		if (args.length != 2) {
			return;
		}

		String particle = args[0].toUpperCase();
		try {
			Effect localEffect1 = Effect.valueOf(particle);
		} catch (IllegalArgumentException e) {
			player.sendMessage("Invalid Particle!");
			return;
		} catch (NullPointerException e) {
			player.sendMessage("Invalid Particle!");
			return;
		}

		final Effect effect = Effect.valueOf(particle);
		try {
			Integer.parseInt(args[1]);
		} catch (NumberFormatException e) {
			return;
		}

		final int data = Integer.parseInt(args[1]);
		if (data == 0) {
			return;
		}
		int taskId = Bukkit.getScheduler()
				.runTaskTimer(this.plugin, new Runnable() {
					public void run() {
						Location loc = event.getProjectile().getLocation();
						projectile.getWorld()
								.playEffect(loc, effect, data, 100);
					}
				}, 0L, 1L).getTaskId();
		MetadataValue x = new FixedMetadataValue(this.plugin,
				Integer.valueOf(taskId));
		projectile.setMetadata("Data", x);
	}

	public void run(AE_ProjectileHitEvent event) {
		Projectile projectile = event.getProjectile();
		String[] args = event.getArgs();
		Location loc = event.getProjectile().getLocation();
		Player player = event.getPlayer();

		if ((args.length == 0) || (args[0].isEmpty()))
			return;
		if (args.length != 2) {
			return;
		}

		String particle = args[0].toUpperCase();
		try {
			Effect localEffect1 = Effect.valueOf(particle);
		} catch (IllegalArgumentException e) {
			return;
		} catch (NullPointerException e) {
			return;
		}

		Effect effect = Effect.valueOf(particle);
		try {
			Integer.parseInt(args[1]);
		} catch (NumberFormatException e) {
			return;
		}

		int data = Integer.parseInt(args[1]);

		if (projectile.hasMetadata("Data")) {
			Bukkit.getScheduler().cancelTasks(this.plugin);
		}

		if (data == 0)
			return;
		projectile.getWorld().playEffect(loc, effect, data, 100);
	}

	public void run(AE_DamageEvent event) {
		LivingEntity victim = event.getVictim();
		String[] args = event.getArgs();
		Location loc = victim.getLocation();
		Player player = event.getPlayer();

		if ((args.length == 0) || (args[0].isEmpty()))
			return;
		if (args.length != 2) {
			return;
		}

		String particle = args[0].toUpperCase();
		try {
			Effect localEffect1 = Effect.valueOf(particle);
		} catch (IllegalArgumentException e) {
			return;
		} catch (NullPointerException e) {
			return;
		}

		Effect effect = Effect.valueOf(particle);
		try {
			Integer.parseInt(args[1]);
		} catch (NumberFormatException e) {
			return;
		}

		int data = Integer.parseInt(args[1]);

		if (data == 0)
			return;
		victim.getWorld().playEffect(loc, effect, data, 100);
	}
}