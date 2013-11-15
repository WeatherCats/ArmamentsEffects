package kipperorigin.armamentseffects.effects;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kipperorigin.armamentseffects.event.AE_DamageEvent;
import kipperorigin.armamentseffects.event.AE_DamageNoEntityEvent;
import kipperorigin.armamentseffects.event.AE_InteractEvent;
import kipperorigin.armamentseffects.event.AE_PlayerInteractEntityEvent;
import kipperorigin.armamentseffects.event.AE_PlayerMoveEvent;
import kipperorigin.armamentseffects.event.AE_ProjectileEvent;
import kipperorigin.armamentseffects.event.AE_ProjectileHitEvent;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
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

	@EventHandler(priority = EventPriority.MONITOR)
	public void process(EntityDamageByEntityEvent event) {
		if (!(event.getEntity() instanceof LivingEntity))
			return;

		int y = 1;

		LivingEntity injured = (LivingEntity) event.getEntity();
		Entity assailant = event.getDamager();

		if ((assailant instanceof Projectile)) {
			assailant = ((Projectile) assailant).getShooter();
			y = 0;
		}

		if (!(assailant instanceof Player))
			return;

		Player damager = (Player) assailant;

		ItemStack item = damager.getItemInHand();

		if (!item.hasItemMeta())
			return;
		ItemMeta meta = item.getItemMeta();
		if (!meta.hasLore())
			return;
		List<String> lore = meta.getLore();

		String exempt = "false";

		for (String line : lore) {
			line = stripColors(line);
			if ((line.contains("play")) || (line.contains("playsound"))) {
				exempt = "true";
				break;
			}
		}

		if (item.getType().equals(Material.BOW) && (y == 1))
			return;

		if (event.isCancelled() && !damager.hasPermission("ae.admin")
				&& !exempt.equalsIgnoreCase("false"))
			return;

		if ((assailant instanceof Projectile)) {
			int x = damager.getInventory().first(Material.ARROW);
			item = damager.getInventory().getItem(x);
		}

		if (item == null || item.getType() == Material.AIR)
			return;

		runEvent(new AE_DamageEvent(damager, injured, event));
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void process(PlayerInteractEvent event) {
		Player player = event.getPlayer();

		if (event.isCancelled() && !player.hasPermission("ae.admin"))
			return;

		if (event.getAction() != Action.RIGHT_CLICK_BLOCK)
			return;
		
		if (event.getAction() != Action.LEFT_CLICK_BLOCK)
			return;
		
		if (event.getAction() != Action.RIGHT_CLICK_AIR)
			return;
		
		if (event.getAction() != Action.LEFT_CLICK_AIR)
			return;

		runEvent(new AE_InteractEvent(player, event.getClickedBlock().getLocation(), event));
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void process(ProjectileLaunchEvent event) {
		Projectile projectile = event.getEntity();

		LivingEntity entity = projectile.getShooter();

		if (!(entity instanceof Player))
			return;

		Player attacker = (Player) entity;

		if (event.isCancelled() && !attacker.hasPermission("ae.admin"))
			return;

		runEvent(new AE_ProjectileEvent(attacker, projectile, event));
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void process(ProjectileHitEvent event) {
		Projectile projectile = event.getEntity();

		LivingEntity entity = projectile.getShooter();

		if (!(entity instanceof Player))
			return;

		Player attacker = (Player) entity;

		Location location = event.getEntity().getLocation();

		runEvent(new AE_ProjectileHitEvent(attacker, projectile, event,
				location));
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void process(PlayerInteractEntityEvent event) {
		Player player = event.getPlayer();
		Entity entity = event.getRightClicked();
		Location location = entity.getLocation();

		if (event.isCancelled() && !player.hasPermission("ae.admin"))
			return;

		runEvent(new AE_PlayerInteractEntityEvent(player, entity, event,
				location));
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void process(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		runEvent(new AE_PlayerMoveEvent(player, event));
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void process(EntityDamageEvent event) {
		Player player;
		if (event.getEntity() instanceof Player) 
			player = (Player) event.getEntity();
		else 
			return;
		runEvent(new AE_DamageNoEntityEvent(player, event));
	}

	private void runEvent(AE_DamageEvent data) {
		ItemStack item = data.getItem();

		if (!item.hasItemMeta())
			return;
		ItemMeta meta = item.getItemMeta();
		if (!meta.hasLore())
			return;
		List<String> lore = meta.getLore();

		for (String line : lore) {
			line = stripColors(line);

			String[] parts = line.split(" +", 2);
			String name = parts[0];

			AE_EffectParent effect = getEffect(name);
			if (effect == null)
				continue;

			String[] args;
			if (parts.length == 2)
				args = parts[1].split(" +");
			else
				args = new String[0];

			data.setArgs(args);
			effect.run(data);
		}
	}

	private void runEvent(AE_InteractEvent data) {
		ItemStack item = data.getItem();

		if (!item.hasItemMeta())
			return;
		ItemMeta meta = item.getItemMeta();
		if (!meta.hasLore())
			return;
		List<String> lore = meta.getLore();

		for (String line : lore) {
			line = stripColors(line);

			String[] parts = line.split(" +", 2);
			String name = parts[0];

			AE_EffectParent effect = getEffect(name);
			if (effect == null)
				continue;

			String[] args;
			if (parts.length == 2)
				args = parts[1].split(" +");
			else
				args = new String[0];

			data.setArgs(args);
			effect.run(data);
		}
	}

	private void runEvent(AE_ProjectileEvent data) {
		ItemStack item = data.getItem();

		if (!item.hasItemMeta())
			return;
		ItemMeta meta = item.getItemMeta();
		if (!meta.hasLore())
			return;
		List<String> lore = meta.getLore();

		for (String line : lore) {
			line = stripColors(line);

			String[] parts = line.split(" +", 2);
			String name = parts[0];

			AE_EffectParent effect = getEffect(name);
			if (effect == null)
				continue;

			String[] args;
			if (parts.length == 2)
				args = parts[1].split(" +");
			else
				args = new String[0];

			data.setArgs(args);
			effect.run(data);
		}
	}

	private void runEvent(AE_ProjectileHitEvent data) {
		ItemStack item = data.getItem();

		if (!item.hasItemMeta())
			return;
		ItemMeta meta = item.getItemMeta();
		if (!meta.hasLore())
			return;
		List<String> lore = meta.getLore();

		for (String line : lore) {
			line = stripColors(line);

			String[] parts = line.split(" +", 2);
			String name = parts[0];

			AE_EffectParent effect = getEffect(name);
			if (effect == null)
				continue;

			String[] args;
			if (parts.length == 2)
				args = parts[1].split(" +");
			else
				args = new String[0];

			data.setArgs(args);
			effect.run(data);
		}
	}

	private void runEvent(AE_PlayerInteractEntityEvent data) {
		ItemStack item = data.getItem();

		if (!item.hasItemMeta())
			return;
		ItemMeta meta = item.getItemMeta();
		if (!meta.hasLore())
			return;
		List<String> lore = meta.getLore();

		for (String line : lore) {
			line = stripColors(line);

			String[] parts = line.split(" +", 2);
			String name = parts[0];

			AE_EffectParent effect = getEffect(name);
			if (effect == null)
				continue;

			String[] args;
			if (parts.length == 2)
				args = parts[1].split(" +");
			else
				args = new String[0];

			data.setArgs(args);
			effect.run(data);
		}
	}

	private void runEvent(AE_PlayerMoveEvent data) {
		ItemStack item[] = data.getPlayer().getInventory().getArmorContents();
		for (int i = 0; i < 4;i++) {
			if (!item[i].hasItemMeta())
				continue;
			ItemMeta meta = item[i].getItemMeta();
			if (!meta.hasLore())
				continue;
			List<String> lore = meta.getLore();

			for (String line : lore) {
				line = stripColors(line);

				String[] parts = line.split(" +", 2);
				String name = parts[0];

				AE_EffectParent effect = getEffect(name);
				if (effect == null)
					continue;

				String[] args;
				if (parts.length == 2)
					args = parts[1].split(" +");
				else
					args = new String[0];

				data.setArgs(args);
				effect.run(data);
			}
		}
	}
	
	private void runEvent(AE_DamageNoEntityEvent data) {
		ItemStack item[] = data.getPlayer().getInventory().getArmorContents();
		for (int i = 0; i < 4;i++) {
			if (!item[i].hasItemMeta())
				continue;
			ItemMeta meta = item[i].getItemMeta();
			if (!meta.hasLore())
				continue;
			List<String> lore = meta.getLore();

			for (String line : lore) {
				line = stripColors(line);

				String[] parts = line.split(" +", 2);
				String name = parts[0];

				AE_EffectParent effect = getEffect(name);
				if (effect == null)
					continue;

				String[] args;
				if (parts.length == 2)
					args = parts[1].split(" +");
				else
					args = new String[0];

				data.setArgs(args);
				effect.run(data);
			}
		}
	}
}