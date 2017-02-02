package org.cubeville.effects.effects;

import org.cubeville.effects.Effects;
import org.cubeville.effects.event.ProjectileEvent;
import org.cubeville.effects.event.AE_ProjectileHitEvent;

public class AE_EffectRideable extends AE_EffectParent {

   private Effects plugin;

    public AE_EffectRideable(Effects plugin) {

        this.plugin = plugin;
    }
	
    @Override
    public void run(final ProjectileEvent event) {
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
