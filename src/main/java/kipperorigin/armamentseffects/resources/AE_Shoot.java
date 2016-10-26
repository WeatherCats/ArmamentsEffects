package kipperorigin.armamentseffects.resources;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Fish;
import org.bukkit.entity.Item;
import org.bukkit.entity.LargeFireball;
import org.bukkit.entity.Player;
import org.bukkit.entity.SmallFireball;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.ThrownExpBottle;
import org.bukkit.entity.WitherSkull;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class AE_Shoot {
	
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
	
	public void shootProjectile(Vector vec, Player player, String name) {
        if (name.equalsIgnoreCase("snowball")) {
        	player.launchProjectile(Snowball.class).setVelocity(vec);
        } else if (name.equalsIgnoreCase("fireball")) {
        	player.launchProjectile(Fireball.class).setVelocity(vec);
        } else if (name.equalsIgnoreCase("fireshot")) {
        	player.launchProjectile(SmallFireball.class).setVelocity(vec);
        } else if (name.equalsIgnoreCase("fireblast")) {
        	player.launchProjectile(LargeFireball.class).setVelocity(vec);
        } else if (name.equalsIgnoreCase("arrow")) {
        	player.launchProjectile(Arrow.class).setVelocity(vec);
        } else if (name.equalsIgnoreCase("withershot")) {
        	player.launchProjectile(WitherSkull.class).setVelocity(vec);
        } else if (name.equalsIgnoreCase("egg")) {
        	player.launchProjectile(Egg.class).setVelocity(vec);
        } else if (name.equalsIgnoreCase("fish")) {
        	player.launchProjectile(Fish.class).setVelocity(vec);
        } else if (name.equalsIgnoreCase("xpbottle")) {
        	player.launchProjectile(ThrownExpBottle.class).setVelocity(vec);
        } else {
        	player.sendMessage("Please use a valid projectile!");
        }
	}
}
