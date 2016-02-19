package kipperorigin.armamentseffects.resources;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

public class AE_Explode {
	public void createExplosion(LivingEntity exploded, float power) {
		Location loc = exploded.getLocation();
		exploded.getWorld().createExplosion(loc.getX(), loc.getY() + 1.5, loc.getZ(), power, false, false);
	}
	
	public void createExplosion(Entity exploded, float power) {
		Location loc = exploded.getLocation();
		exploded.getWorld().createExplosion(loc.getX(), loc.getY() + 1.5, loc.getZ(), power, false, false);
	}

	public void createExplosion(Location loc, float power) {
		loc.getWorld().createExplosion(loc.getX(), loc.getY() + 1.5, loc.getZ(), power, false, false);
	}
}
