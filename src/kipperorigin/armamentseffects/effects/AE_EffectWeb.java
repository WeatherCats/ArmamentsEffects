package kipperorigin.armamentseffects.effects;

import kipperorigin.armamentseffects.AE_Main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class AE_EffectWeb extends AE_EffectParent{

	private AE_Main plugin;
	public AE_EffectWeb(AE_Main plugin) {
		this.plugin = plugin;
	}
	
	public String color(String message) {
		return ChatColor.translateAlternateColorCodes('&', message);
	}
	
	@Override
	public void run(final Player player, ItemStack item, LivingEntity target, String[] arg) {
		int timer;
		if (arg.length == 0 || arg[0].isEmpty())
			timer = 5;
		else {
			try {
				Integer.parseInt(arg[0]);
			} catch (NumberFormatException e) {
				return;
			}
			timer = Integer.parseInt(arg[0]);
		}
		Location loc = target.getLocation().add(0, 1, 0);
		Location loc2 = target.getLocation();
		final Block x;
		final Block y;
		final Material matx = loc.getBlock().getType();
		final Material maty = loc2.getBlock().getType();
		
		if (!(matx == null)){
			x = loc.getBlock();
			x.setType(Material.WEB);
			if (target instanceof Player)
				((Player) target).sendMessage(color("&4You've been caught in a web!"));
		} else
			x = null;
		
		if (!(maty == null)){
			y = loc2.getBlock();
			y.setType(Material.WEB);
			if (target instanceof Player)
				((Player) target).sendMessage(color("&4You've been caught in a web!"));
		} else
			y = null;
		
		Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
		    @Override
		    public void run() {
		    	if (!(x == null))
		    		x.setType(matx); 
		    	if (!(y == null))
		    		y.setType(maty);
		    }
		}, timer*20L);	
	}
}
