package kipperorigin.armamentseffects.effects;

//import org.bukkit.Bukkit;
//import org.bukkit.entity.Projectile;
//import org.bukkit.metadata.FixedMetadataValue;
//import org.bukkit.metadata.MetadataValue;

import kipperorigin.armamentseffects.AE_Main;
import kipperorigin.armamentseffects.event.AE_ProjectileEvent;
import kipperorigin.armamentseffects.event.AE_ProjectileHitEvent;

public class AE_EffectRideable extends AE_EffectParent {

   private AE_Main plugin;

    public AE_EffectRideable(AE_Main plugin) {

        this.plugin = plugin;
    }
	
    @Override
    public void run(final AE_ProjectileEvent event) {
    	/*
    	Projectile projectile = event.getProjectile();
    	
        final int taskId = Bukkit.getScheduler().runTaskTimer(plugin, new Runnable() {
            @Override
            public void run() {
                event.getPlayer().teleport(projectile.getLocation().add(0D, 1D, 0D));
                if (projectile.getLocation().getY() <= 0) {
                	int i = 0;
                	while(projectile.hasMetadata("Data " + String.valueOf(i))) {
                		Bukkit.getScheduler().cancelTask(projectile.getMetadata("Data " + String.valueOf(i)).get(0).asInt());
                		i++;
                	}
                	projectile.eject();
                	projectile.remove();
        		}
            }
        }, 0L, 1L).getTaskId();
        MetadataValue x = new FixedMetadataValue(plugin, taskId);
        projectile.setMetadata("Data", x);
		int i = 0;
		
		while(projectile.hasMetadata("Data " + String.valueOf(i))) 
			i++;
		
		projectile.setMetadata("Data " + String.valueOf(i), x);
        if (event.getRawEvent().isCancelled() && projectile.hasMetadata("Data"))
            Bukkit.getScheduler().cancelTask(projectile.getMetadata("Data").get(0).asInt());
            */
    	event.getProjectile().setPassenger(event.getPlayer());
    }
    
    public void run(final AE_ProjectileHitEvent event) {
    	event.getProjectile().eject();
    	event.getPlayer().remove();
    }
}
