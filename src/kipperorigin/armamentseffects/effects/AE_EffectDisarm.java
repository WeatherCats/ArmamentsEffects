package kipperorigin.armamentseffects.effects;

import kipperorigin.armamentseffects.AE_RemoveItem;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class AE_EffectDisarm extends AE_EffectParent {
	public String color(String message) {
		return ChatColor.translateAlternateColorCodes('&', message);
	}
	AE_RemoveItem AE_RI = new AE_RemoveItem();
	@Override
	public void run(final Player player, ItemStack item, final LivingEntity target, String[] arg) {
		Player victim = null;
		if (target instanceof Player)
			victim = (Player) target;
		if (victim == null)
			return;
		if (victim.getItemInHand() == null || victim.getItemInHand().getType() == Material.AIR)
			return;
		ItemStack vicitem = victim.getItemInHand();
		PlayerInventory inv = victim.getInventory();
		int oldSlot = inv.getHeldItemSlot();
		int newSlot = inv.firstEmpty();
		inv.setItem(newSlot, vicitem);
		inv.setItem(oldSlot, null);
		victim.sendMessage(color("&4You have been disarmed!"));
		AE_RI.removeItem(player);
	}
}
