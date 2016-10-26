package kipperorigin.armamentseffects.effects;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.util.Vector;

import kipperorigin.armamentseffects.AE_Main;
import kipperorigin.armamentseffects.event.AE_InteractEvent;
import kipperorigin.armamentseffects.event.AE_ProjectileCalculation;
import kipperorigin.armamentseffects.resources.AE_Shoot;

public class AE_EffectArrow extends AE_EffectParent {
	
	AE_ProjectileCalculation calc = new AE_ProjectileCalculation();
	AE_Shoot shoot = new AE_Shoot();
	
    private AE_Main plugin;

    public AE_EffectArrow(AE_Main plugin) {

        this.plugin = plugin;
    }
	
    @Override
    public void run(final AE_InteractEvent event) {
		final String args[] = event.getArgs();
		Player player = event.getPlayer();
		Location loc = player.getLocation();
		int multiply = 1;
		loc.add(0, 1, 0);

		// Vector adjustY = new Vector(0, .35, 0);
		// Vector dir = event.getPlayer().getEyeLocation().getDirection().add(adjustY);

		if (args.length < 1 || args.length > 6)
			return;

		if (args.length >= 2) {
			try {
				multiply = Integer.parseInt(args[1]);
			} catch (NumberFormatException e) {
				return;
			}
		}
		
		List<Vector> shots = new ArrayList<Vector>();

		if (args[0].equalsIgnoreCase("s")) {
			// Single shot
			shots.add(calc.transform(loc, new Vector(0, 0.3, 1)));
		} else if (args[0].equalsIgnoreCase("t")) {
			// Trishot
			// Shot 1 slightly upward
			shots.add(calc.transform(loc, new Vector(0, 0.4, 1)));
			// Shot 2 slightly to the left
			shots.add(calc.transform(loc, new Vector(0.25, 0.1, 1)));
			// Shot 3 slightly to the right
			shots.add(calc.transform(loc, new Vector(-0.25, 0.1, 1)));
		} else if (args[0].equalsIgnoreCase("sg")) {
			// Shotgun
			for (int i = 0; i < 8; i++) {
				shots.add(calc.transform(loc, calc.randomOffset()));
			}
		} else if (args[0].equalsIgnoreCase("mr")) {
			// Mortar
			// TODO
		} else if (args[0].equalsIgnoreCase("mn")) {
			// Mine
			// TODO
		} else if (args[0].equalsIgnoreCase("sn")) {
			// Sniper
			shots.add(calc.transform(loc, new Vector(0, 0, 1)));
			multiply *= 5;
		}
		
		if (args[2].equalsIgnoreCase("projectile")) {
			if (!shots.isEmpty()) {
				for (Vector shot : shots) {
					shot.normalize().multiply(multiply);
					shoot.shootProjectile(shot, player, args[3]);
				}
			}
			
		} else if (args[2].equalsIgnoreCase("dropped_item") || args[2].equalsIgnoreCase("item")) {
    		ItemStack i;
    		Boolean b = false;
    		if (Material.valueOf(args[3].toUpperCase()) == null)
    			return;
    		try {
    			i = new ItemStack(Material.valueOf(args[3].toUpperCase()));
    		} catch (IllegalArgumentException e) {
    			player.sendMessage("Invalid Item Type; Type used = " + args[3]);
    			return;
    		}

    		Material mat1 = i.getType();
    		
    		if (args.length == 5) {
    			ItemMeta itemMeta = i.getItemMeta();
    			itemMeta.setDisplayName(args[4]);
    			i.setItemMeta(itemMeta);
    		}
    		
    		if (args.length == 6 && args[5].equalsIgnoreCase("nope"))
    			b = true;
    		if (!shots.isEmpty()) {
    			for (Vector shot : shots) {
    				shot.normalize().multiply(multiply);
    				shoot.shootItem(shot, player.getWorld(), loc, b, i);
    			}
    		}
    	} else if (args[2].equalsIgnoreCase("Falling_Block") || args[2].equalsIgnoreCase("block")) {
    		Material m;
    		byte b = 0;
    		Boolean boo = false;
    		
    		try {
    			m = Material.valueOf(args[3].toUpperCase());
    		} catch (IllegalArgumentException e) {
    			player.sendMessage("Please use a valid block");
    			return;
    		}
    		
    		if (!m.isBlock()) {
    			player.sendMessage("Please use a valid block");
    			return;
    		}
    		
    		if (args.length == 5)
    			try {
    				b = Byte.valueOf(args[4]);
    			} catch (NumberFormatException e) {
    				player.sendMessage("Please use a valid number");
    			}

    		if (args.length == 6 && args[5].equalsIgnoreCase("Damage"))
    			boo = true;
    		
    		if (!shots.isEmpty()) {
    			for (Vector shot : shots) {
    				shot.normalize().multiply(multiply);
    				shoot.shootBlock(shot, player.getWorld(), loc, boo, m, b);
    			}
    		}
    	} else if (args[2].equalsIgnoreCase("spawn") || args[2].equalsIgnoreCase("mob")) {
    		EntityType type;
    			
    		try {
    			type = EntityType.valueOf(args[3].toUpperCase());
    		} catch  (IllegalArgumentException e) {
    			return;
    		}
            
    		if (type == null || !type.isSpawnable())
    			return;
    		if (!shots.isEmpty()) {
    			for (Vector shot : shots) {
    				shot.normalize().multiply(multiply);
    				shoot.shootEntity(shot, player.getWorld(), loc, type);
    			}
    		}
    	} 
    }
}
