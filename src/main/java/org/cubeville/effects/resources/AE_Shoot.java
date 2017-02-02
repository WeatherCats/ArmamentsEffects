package org.cubeville.effects.resources;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Fish;
import org.bukkit.entity.Item;
import org.bukkit.entity.LargeFireball;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.SmallFireball;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.ThrownExpBottle;
import org.bukkit.entity.WitherSkull;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.util.Vector;
import org.cubeville.effects.Effects;

public class AE_Shoot {
	
    private Effects plugin;

    public AE_Shoot(Effects plugin) {
        this.plugin = plugin;
    }
	
	public void shootItem (Vector vec, World world, Location loc, Boolean delay, ItemStack i) {
		Material mat1 = i.getType();
		Item item = world.dropItem(loc, i);
		
		if(item.getItemStack().getType() == Material.STONE && mat1 != Material.STONE) {
			item.remove();
			return;
		}
		
		item.setVelocity(vec);
		
		if (delay)
			item.setPickupDelay(100000000);
	}
	
	public void shootBlock(Vector vec, World world, Location loc, Boolean damage, Material m, Byte b) {
		@SuppressWarnings("deprecation")
		FallingBlock fb = world.spawnFallingBlock(loc, m, b);
		fb.setVelocity(vec);
		fb.setDropItem(false);
		fb.setHurtEntities(damage);
	}
	
	public void shootEntity(Vector vec, World world, Location loc, EntityType type) {
		Entity entity = world.spawnEntity(loc, type);
		entity.setVelocity(vec);
	}
	
	@SuppressWarnings("deprecation")
	public void shootProjectile(Vector vec, Player player, String name) {
		Projectile projectile = null;
        if (name.equalsIgnoreCase("snowball")) {
        	projectile = player.launchProjectile(Snowball.class);
        	projectile.setVelocity(vec);
        } else if (name.equalsIgnoreCase("fireball")) {
        	projectile = player.launchProjectile(Fireball.class);
        	projectile.setVelocity(vec);
        } else if (name.equalsIgnoreCase("fireshot")) {
        	projectile = player.launchProjectile(SmallFireball.class);
        	projectile.setVelocity(vec);
        } else if (name.equalsIgnoreCase("fireblast")) {
        	projectile = player.launchProjectile(LargeFireball.class);
        	projectile.setVelocity(vec);
        } else if (name.equalsIgnoreCase("arrow")) {
        	projectile = player.launchProjectile(Arrow.class);
        	projectile.setVelocity(vec);
        } else if (name.equalsIgnoreCase("withershot")) {
        	projectile = player.launchProjectile(WitherSkull.class);
        	projectile.setVelocity(vec);
        } else if (name.equalsIgnoreCase("egg")) {
        	projectile = player.launchProjectile(Egg.class);
        	projectile.setVelocity(vec);
        } else if (name.equalsIgnoreCase("fish")) {
        	projectile = player.launchProjectile(Fish.class);
        	projectile.setVelocity(vec);
        } else if (name.equalsIgnoreCase("xpbottle")) {
        	projectile = player.launchProjectile(ThrownExpBottle.class);
        	projectile.setVelocity(vec);
        } else {
        	player.sendMessage("Please use a valid projectile!");
        	return;
        }
        
        if (projectile != null) {
        	MetadataValue x = new FixedMetadataValue(plugin, "projectile");	
			projectile.setMetadata("Generated", x);
        }
	}
}
