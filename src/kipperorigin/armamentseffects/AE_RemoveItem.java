package kipperorigin.armamentseffects;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class AE_RemoveItem {

	@SuppressWarnings("deprecation")
	public void removeItem(Player player) {
		ItemStack inhand = player.getItemInHand();
		Material mat = inhand.getType();
		short max = mat.getMaxDurability();
		if (max == 0) {
			player.getInventory().remove(inhand);
			return;
		}
		if (max >= 1) {
			short dur = (short) (inhand.getDurability() + 1);
			if (inhand.getDurability() >= mat.getMaxDurability())
				player.setItemInHand(null);
			else
				inhand.setDurability(dur);
			player.updateInventory();
			return;
		}
	}
}
