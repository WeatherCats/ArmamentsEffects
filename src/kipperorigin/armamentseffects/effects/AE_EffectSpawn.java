package kipperorigin.armamentseffects.effects;

import kipperorigin.armamentseffects.AE_RemoveItem;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

public class AE_EffectSpawn extends AE_EffectParent {
	
	AE_RemoveItem AE_RI = new AE_RemoveItem();
	@Override
	public void run(Player player, ItemStack item, Location loc, String[] arg) {
		if (arg.length == 0 || arg[0].isEmpty())
			return;
		else { 
			String mob = arg[0].toString();
			World world = player.getWorld();
			if (EntityType.fromName(mob) == null)
				return;
			if (mob.equalsIgnoreCase("wolf")) {
				Wolf wolf = (Wolf) (world.spawnEntity(loc, EntityType.fromName(mob)));
				wolf.setTamed(true);
				wolf.setOwner(player);
			} else {
				world.spawnEntity(loc, EntityType.fromName(mob));
			}
			AE_RI.removeItem(player);
		}
	}
}
