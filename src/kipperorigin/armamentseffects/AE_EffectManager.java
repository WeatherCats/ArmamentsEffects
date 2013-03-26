package kipperorigin.armamentseffects;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kipperorigin.armamentseffects.effects.AE_EffectParent;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class AE_EffectManager implements Listener {
	
	public String stripColors(String line) {
		return line.replaceAll("(\u00A7|&)[0-9A-Fa-fK-Ok-oRr]", "");
	}
	
	Map<String, AE_EffectParent> effects = new HashMap<String, AE_EffectParent>();
	
	public void registerEffect(String name, AE_EffectParent effect) {
		name = name.toLowerCase();
		effects.put(name, effect);
	}
	private AE_EffectParent getEffect(String name) {
		name = name.toLowerCase();
		return effects.get(name);
	}
	
	@EventHandler
	public void process(EntityDamageByEntityEvent event) {
		Entity assailant = event.getDamager();
		
		if (!(event.getEntity() instanceof LivingEntity))
			return;
		
		if ((assailant instanceof Projectile))
			assailant = ((Projectile)assailant).getShooter();
		
		if (!(assailant instanceof Player))
			return;
		
		Player damager = (Player)assailant;
		
		ItemStack item = damager.getItemInHand();
		if (item == null || item.getType() == Material.AIR)
				return;
		
		if (event.isCancelled() && !damager.hasPermission("ae.admin"))
			return;
		
	    LivingEntity injured = (LivingEntity)event.getEntity();
	    
	    runEffects(damager, damager.getItemInHand(), injured);
	}
	
	@EventHandler
	public void process(PlayerInteractEvent event) {

		Player player = event.getPlayer();
		
		Location loc;
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK) 
				loc = event.getClickedBlock().getLocation().add(0, 1, 0);
		else
			return;
		
		if (event.isCancelled() && !player.hasPermission("ae.admin"))
			return;
		
		runEffects(player, player.getItemInHand(), loc);
	}
	
	@EventHandler
	public void process(ProjectileLaunchEvent event) {
		Projectile projectile = event.getEntity();
		
		Player attacker = (Player)projectile.getShooter();
		
		if (event.isCancelled() && !attacker.hasPermission("ae.admin"))
			return;

		runEffects(attacker, attacker.getItemInHand(), projectile);
	}
	
	private void runEffects(Player player, ItemStack item, Location loc) {
		if (!item.hasItemMeta()) return;
		ItemMeta meta = item.getItemMeta();
		if (!meta.hasLore()) return;
		List<String> lore = meta.getLore();
		for (String line : lore) {
			line = stripColors(line);
			String[] parts = line.split(" ", 2);
			String name = parts[0];
			String args;
			if(parts.length == 2)
				args = parts[1];
			else
				args = "";
			String[] arg = args.split(" ");
			AE_EffectParent effect = this.getEffect(name);
			if (effect == null) continue;
			effect.run(player, item, loc, arg);
		}
	}
	
	private void runEffects(Player player, ItemStack item, Projectile projectile) {
		if (!item.hasItemMeta()) return;
		ItemMeta meta = item.getItemMeta();
		if (!meta.hasLore()) return;
		List<String> lore = meta.getLore();
		for (String line : lore) {
			line = stripColors(line);
			String[] parts = line.split(" ", 2);
			String name = parts[0];
			String args;
			if(parts.length == 2)
				args = parts[1];
			else
				args = "";
			String[] arg = args.split(" ");
			AE_EffectParent effect = this.getEffect(name);
			if (effect == null) continue;
			effect.run(player, item, projectile, arg);
		}
	}
	
	private void runEffects(Player player, ItemStack item, LivingEntity target) {
		if (!item.hasItemMeta()) return;
		ItemMeta meta = item.getItemMeta();
		if (!meta.hasLore()) return;
		List<String> lore = meta.getLore();
		for (String line : lore) {
			line = stripColors(line);
			String[] parts = line.split(" ", 2);
			String name = parts[0];
			String args;
			if(parts.length == 2)
				args = parts[1];
			else
				args = "";
			String[] arg = args.split(" ");
			AE_EffectParent effect = this.getEffect(name);
			if (effect == null) continue;
			effect.run(player, item, target, arg);
		}
	}
}
