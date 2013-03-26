package kipperorigin.armamentseffects.effects;

import kipperorigin.armamentseffects.AE_Main;

import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class AE_EffectDrain extends AE_EffectParent{
	
	private AE_Main plugin;
	public AE_EffectDrain(AE_Main plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public void run(final Player player, ItemStack item, final LivingEntity target, String[] arg) {
	    final int heal = target.getHealth();
		if (arg.length == 0 || arg[0].isEmpty()) {
			Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
			    @Override
			    public void run() {
			        int healx = target.getHealth();
			        int healy = heal-healx;
					if (player.getHealth()+healy > 20)
						player.setHealth(20);
					else
						player.setHealth(player.getHealth()+healy);
			    }
			}, 1L);	
		} else if (arg.length == 1) {
			try {
				Integer.parseInt(arg[0]);
			} catch (NumberFormatException e) {
				return;
			}
			int amp = Integer.parseInt(arg[0]);
			if (player.getHealth()+amp > 20)
				player.setHealth(20);
			else
				player.setHealth(player.getHealth() + amp);
		} else return;
	}
}
