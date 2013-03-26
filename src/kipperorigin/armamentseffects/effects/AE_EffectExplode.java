package kipperorigin.armamentseffects.effects;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class AE_EffectExplode extends AE_EffectParent {

	private void createExplosion(LivingEntity exploded, float power) {
		Location loc = exploded.getLocation();
		exploded.getWorld().createExplosion(loc.getX(), loc.getY()+1.5, loc.getZ(), power, false, false);
	}
	
	@Override
	public void run(Player player, ItemStack item, LivingEntity target, String[] arg) {
		float power = 0;
		if (arg.length == 0 || arg[0].isEmpty()) {
			power = 1;
		} else if (arg.length == 1) {
			try {
				Integer.parseInt(arg[0]);
			} catch (NumberFormatException e) {
				return;
			}
			power = Integer.parseInt(arg[0]);
		}
		createExplosion(target, power);
		return;
	}
}
