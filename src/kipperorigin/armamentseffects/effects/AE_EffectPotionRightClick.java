package kipperorigin.armamentseffects.effects;

import kipperorigin.armamentseffects.event.AE_InteractEvent;
import net.minecraft.server.v1_5_R2.Material;

import org.bukkit.entity.Player;

public class AE_EffectPotionRightClick extends AE_EffectParent {

	@Override
	public void run(AE_InteractEvent event) {
		Player player;
		String[] args = event.getArgs();
		if (args.length > 1) {

		}
		if (args.length > 2) {

		}
		if ((event.getClickedLocation().getBlock() == Material.AIR) || (event.getClickedLocation().getBlock() == null)) {
			player = event.getPlayer();
		} else if (event.getClickedLocation() instanceof Player) {
			player = (Player) event.getClickedLocation();
		} else {
			player = event.getPlayer();
		}
		System.out.println(player.toString());

	}
}
