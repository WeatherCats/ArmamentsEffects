package kipperorigin.armamentseffects.resources;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class AE_RemoveItem {

	public String stripColors(String line) {
		return line.replaceAll("(\u00A7|&)[0-9A-Fa-fK-Ok-oRr]", "");
	}

	public String color(String message) {
		return ChatColor.translateAlternateColorCodes('&', message);
	}

	@SuppressWarnings("deprecation")
	public void removeItem(Player player) {
		ItemStack item = player.getItemInHand();
		if (!item.hasItemMeta())
			return;
		ItemMeta meta = item.getItemMeta();
		if (!meta.hasLore())
			return;
		List<String> lore = meta.getLore();
		for (int i = 0; i < lore.size(); i++) {
			String linex = lore.get(i);
			String line = stripColors(linex);
			String[] parts = line.split(" +", 2);
			String[] partsx = linex.split(" +", 2);
			String name = parts[0];
			if (name.equalsIgnoreCase("Durability") || name.equalsIgnoreCase("Uses")) {
				System.out.println("debug1");
				if (parts.length != 2) {
					return;
				} else {
					int x;
					try {
						x = Integer.parseInt(parts[1]);
					} catch (NumberFormatException e) {
						return;
					}
					x = Integer.parseInt(parts[1]);
					x = x - 1;
					System.out.println("debug2");
					if (x <= 0) {
						player.setItemInHand(null);
					} else {
						System.out.println("debug3");
						String num = String.valueOf(x);
						lore.set(i, color("&r" + partsx[0] + " " + num));
						meta.setLore(lore);
						item.setItemMeta(meta);
						item.setDurability(item.getType().getMaxDurability());
					}
				}
			}
		}
		player.updateInventory();
	}
}
