package kipperorigin.armamentseffects.effects;

import kipperorigin.armamentseffects.AE_RemoveItem;
import kipperorigin.armamentseffects.event.AE_DamageEvent;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class AE_EffectDisarm extends AE_EffectParent {

	private String color(String message) {
		return ChatColor.translateAlternateColorCodes('&', message);
	}

	private AE_RemoveItem AE_RI = new AE_RemoveItem();

	@Override
	public void run(AE_DamageEvent event) {
		if (!(event.getVictim() instanceof Player))
			return;

		Player victim = (Player)event.getVictim();

		ItemStack item = victim.getItemInHand();
		if (item == null || item.getType() == Material.AIR)
			return;

		PlayerInventory inv = victim.getInventory();
		int oldSlot = inv.getHeldItemSlot();
		int newSlot = inv.firstEmpty();
		inv.setItem(newSlot, item);
		inv.setItem(oldSlot, null);
		victim.sendMessage(color("&4You have been disarmed!"));
		AE_RI.removeItem(event.getPlayer());
	}
}
