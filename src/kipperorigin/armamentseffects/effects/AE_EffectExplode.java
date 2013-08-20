package kipperorigin.armamentseffects.effects;

import kipperorigin.armamentseffects.AE_RemoveItem;
import kipperorigin.armamentseffects.event.AE_DamageEvent;
import kipperorigin.armamentseffects.event.AE_ProjectileHitEvent;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class AE_EffectExplode extends AE_EffectParent {

	AE_RemoveItem AE_RI = new AE_RemoveItem();

	private void createExplosion(LivingEntity exploded, float power) {
		Location loc = exploded.getLocation();
		exploded.getWorld().createExplosion(loc.getX(), loc.getY() + 1.5, loc.getZ(), power, false, false);
	}

	private void createExplosion(Location loc, float power) {
		loc.getWorld().createExplosion(loc.getX(), loc.getY() + 1.5, loc.getZ(), power, false, false);
	}

	@Override
	public void run(AE_DamageEvent event) {
		LivingEntity target = event.getVictim();
		String[] args = event.getArgs();
		float power = 0;
		if (args.length == 0 || args[0].isEmpty()) {
			power = 1;
		} else if (args.length == 1) {
			try {
				Integer.parseInt(args[0]);
			} catch (NumberFormatException e) {
				return;
			}
			power = Integer.parseInt(args[0]);
		}
		createExplosion(target, power);
		if (power > 25)
			return;
		AE_RI.removeItem(event.getPlayer());
		return;
	}

	@Override
	public void run(AE_ProjectileHitEvent event) {
		Player player = event.getPlayer();
		if (!player.hasPermission("ae.override"))
			return;
		Location loc = event.getProjectile().getLocation();
		String[] args = event.getArgs();

		float power = 0;
		if (args.length == 0 || args[0].isEmpty()) {
			return;
		} else if (args.length == 1) {
			if (args[0].equalsIgnoreCase("impact"))
				power = 1;
			else
				return;
		} else if (args.length == 2) {
			if (!args[0].equalsIgnoreCase("impact"))
				return;
			try {
				Integer.parseInt(args[1]);
			} catch (NumberFormatException e) {
				return;
			}
			power = Integer.parseInt(args[1]);
		}
		if (power < 25)
			return;
		createExplosion(loc, power);
		AE_RI.removeItem(event.getPlayer());
		return;
	}
}
