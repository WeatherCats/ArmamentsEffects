package kipperorigin.armamentseffects.effects;

import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class AE_EffectVelocity extends AE_EffectParent {
	
	@Override
	public void run(Player player, ItemStack item, Projectile projectile, String[] arg) {
		int vec;
		if (arg.length == 0 || arg[0].isEmpty())
			return;
		else {
			try {
				Integer.parseInt(arg[0]);
			} catch (NumberFormatException e) {
				return;
			}
			vec = Integer.parseInt(arg[0]);
		}
		Vector v = projectile.getVelocity();
		Vector z = v.multiply(new Vector(vec, (vec/2), vec));
		projectile.setVelocity(z);
	}
}
