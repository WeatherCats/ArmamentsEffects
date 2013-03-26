package kipperorigin.armamentseffects.effects;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class AE_EffectInstakill extends AE_EffectParent {

	@Override
	public void run(final Player player, ItemStack item, final LivingEntity target, String[] arg) {
		target.setHealth(0);
	}
}
