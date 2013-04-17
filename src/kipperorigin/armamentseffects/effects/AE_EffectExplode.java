package kipperorigin.armamentseffects.effects;

import kipperorigin.armamentseffects.event.AE_DamageEvent;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;

public class AE_EffectExplode extends AE_EffectParent {

	private void createExplosion(LivingEntity exploded, float power) {
		Location loc = exploded.getLocation();
		exploded.getWorld().createExplosion(loc.getX(), loc.getY()+1.5, loc.getZ(), power, false, false);
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
		return;
	}

}
