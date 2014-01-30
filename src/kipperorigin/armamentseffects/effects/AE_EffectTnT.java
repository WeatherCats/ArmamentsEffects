package kipperorigin.armamentseffects.effects;

import kipperorigin.armamentseffects.AE_Main;
import kipperorigin.armamentseffects.event.AE_InteractEvent;
import kipperorigin.armamentseffects.resources.AE_RemoveItem;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.block.Action;
import org.bukkit.util.Vector;

public class AE_EffectTnT extends AE_EffectParent {

	@SuppressWarnings("unused")
	private AE_Main plugin;

	public AE_EffectTnT(AE_Main plugin) {
		this.plugin = plugin;
	}

	AE_RemoveItem AE_RI = new AE_RemoveItem();

	@Override
	public void run(final AE_InteractEvent event) {
		final String args[] = event.getArgs();
		Player player = event.getPlayer();
		Location loc = player.getLocation();
		World world = loc.getWorld();

		if (args.length != 1)
			return;

		try {
			Integer.parseInt(args[0]);
		} catch (NumberFormatException e) {
			return;
		}

		final int multiply = Integer.parseInt(args[0]);

		if ((event.getAction() == Action.RIGHT_CLICK_AIR) || (event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
			TNTPrimed tnt = world.spawn(loc, TNTPrimed.class);
			Vector v = (event.getPlayer().getEyeLocation().getDirection()).multiply(multiply);
			tnt.setVelocity(v);
			AE_RI.removeItem(event.getPlayer());
		}
	}
}
