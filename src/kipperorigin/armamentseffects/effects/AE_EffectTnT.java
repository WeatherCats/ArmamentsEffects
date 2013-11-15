package kipperorigin.armamentseffects.effects;

import kipperorigin.armamentseffects.AE_Main;
import kipperorigin.armamentseffects.event.AE_InteractEvent;
import kipperorigin.armamentseffects.resources.AE_RemoveItem;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.util.Vector;

public class AE_EffectTnT extends AE_EffectParent {

	private AE_Main plugin;

	public AE_EffectTnT(AE_Main plugin) {
		this.plugin = plugin;
	}

	AE_RemoveItem AE_RI = new AE_RemoveItem();

	@Override
	public void run(final AE_InteractEvent event) {
		final String args[] = event.getArgs();
		
		if (args.length != 1) 
			return;
		
		try {
			Integer.parseInt(args[0]);
		} catch (NumberFormatException e) {
			return;
		}
		
		final int multiply = Integer.parseInt(args[0]);
		
		final Player player = event.getPlayer();

		if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_AIR) {
			Entity tnt = player.getWorld().spawnEntity(player.getEyeLocation(), EntityType.PRIMED_TNT);
			Vector v = (event.getPlayer().getEyeLocation().getDirection()).multiply(multiply);
			tnt.setVelocity(v);
		}
		
		AE_RI.removeItem(event.getPlayer());
	}
}
