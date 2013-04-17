package kipperorigin.armamentseffects.effects;

import kipperorigin.armamentseffects.AE_Main;
import kipperorigin.armamentseffects.event.AE_DamageEvent;

import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class AE_EffectDrain extends AE_EffectParent{

	private AE_Main plugin;
	public AE_EffectDrain(AE_Main plugin) {
		this.plugin = plugin;
	}

	@Override
	public void run(AE_DamageEvent event) {
		final Player player = event.getPlayer();
		final LivingEntity target = event.getVictim();
		String[] args = event.getArgs();

		final int heal = target.getHealth();
		if (args.length == 0 || args[0].isEmpty()) {
			Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
				@Override
				public void run() {
					int healx = target.getHealth();
					int healy = heal-healx;
					if (player.getHealth()+healy > 20)
						player.setHealth(20);
					else
						player.setHealth(player.getHealth()+healy);
				}
			}, 1L);
		} else if (args.length == 1) {
			try {
				Integer.parseInt(args[0]);
			} catch (NumberFormatException e) {
				return;
			}
			int amp = Integer.parseInt(args[0]);
			if (player.getHealth()+amp > 20)
				player.setHealth(20);
			else
				player.setHealth(player.getHealth() + amp);
		}
	}
}
