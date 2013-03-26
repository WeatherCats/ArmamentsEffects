package kipperorigin.armamentseffects.effects;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class AE_EffectPotions extends AE_EffectParent{
	
	@Override
	public void run( Player player, ItemStack item, LivingEntity target, String[] arg) {
		String potion = null;
		int amp = 1;
		int time = 5;
		if (arg.length == 0)
			return;
		if (arg.length > 0) potion = arg[0];
		if (arg.length > 1) {
			try {
				Integer.parseInt(arg[1]);
			} catch (NumberFormatException e) {
				return;
			}
			amp = Integer.parseInt(arg[1]);
		}
		if (arg.length > 2) {
			try {
				Integer.parseInt(arg[2]);
			} catch (NumberFormatException e) {
				return;
			}
			time = Integer.parseInt(arg[2]);
		}
		if (potion == null)
			return;
		PotionEffectType type = PotionEffectType.getByName(potion);
		if (type == null)
			return;
		if (type.isInstant()) 
			time = 1;
		target.addPotionEffect(new PotionEffect(PotionEffectType.getByName(potion), time*20, amp-1));
	}
}