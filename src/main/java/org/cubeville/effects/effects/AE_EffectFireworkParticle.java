package org.cubeville.effects.effects;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Listener;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.cubeville.effects.Effects;
import org.cubeville.effects.event.DamageEvent;
import org.cubeville.effects.event.ProjectileEvent;
import org.cubeville.effects.event.AE_ProjectileHitEvent;
import org.cubeville.effects.resources.AE_CheckFireworkColor;
import org.cubeville.effects.resources.AE_FireworkEffectPlayer;
import org.cubeville.effects.resources.AE_RemoveItem;

public class AE_EffectFireworkParticle extends AE_EffectParent implements Listener {

    private Effects plugin;

    public AE_EffectFireworkParticle(Effects plugin) {

        this.plugin = plugin;
    }

    AE_RemoveItem AE_RI = new AE_RemoveItem();
    AE_FireworkEffectPlayer fplayer = new AE_FireworkEffectPlayer();
    AE_CheckFireworkColor colorCheck = new AE_CheckFireworkColor();
    
    private FireworkEffect getEffect(Type type, Color finalColor) {
        return FireworkEffect.builder().with(type).withColor(finalColor).build();
    }
    
    @Override
    public void run(final ProjectileEvent event) {
        try {
            final Projectile projectile = event.getProjectile();
            String[] args = event.getArgs();
            int timer = 1;
            long delay = 0;
        
            if (args.length > 4 || args[0].isEmpty())
                return;
        
            final Type type = Type.valueOf(args[0].toUpperCase());
            final Color color = colorCheck.getFireworkColorByString(args[1]);
        
            if (args.length >= 3) {
                try {
                    timer = Integer.parseInt(args[2]);
                } catch (NumberFormatException e) {
                    return;
                }
            }
            if (args.length == 4) {
            	try {
            		delay = Long.parseLong(args[3]);
            	} catch (NumberFormatException e) {
            		return;
            	}
            }
            final int taskId = Bukkit.getScheduler().runTaskTimer(plugin, new Runnable() {
                    @Override
                    public void run() {
                        if (projectile.getLocation().getY() <= 0) {
                        	int i = 0;
                        	while(projectile.hasMetadata("Data " + String.valueOf(i))) {
                        		Bukkit.getScheduler().cancelTask(projectile.getMetadata("Data " + String.valueOf(i)).get(0).asInt());
                        		i++;
                        	}
                        	projectile.eject();
                        	projectile.remove();
                		}
                        try {
                            fplayer.playFirework(event.getPlayer().getWorld(), projectile.getLocation(), getEffect(type, color));
                        } catch (Exception e) {
                            return;
                        }
                    }
                }, delay, timer).getTaskId();
            MetadataValue x = new FixedMetadataValue(plugin, taskId);
			int i = 0;
			
			while(projectile.hasMetadata("Data " + String.valueOf(i))) 
				i++;
			
			projectile.setMetadata("Data " + String.valueOf(i), x);
            if (event.getRawEvent().isCancelled() && projectile.hasMetadata("Data"))
                Bukkit.getScheduler().cancelTask(projectile.getMetadata("Data").get(0).asInt());
        }
        catch(RuntimeException e) {
            return;
        }
    }

    @Override
    public void run(AE_ProjectileHitEvent event) {
        String[] args = event.getArgs();
    
        if (args.length != 2 || args[0].isEmpty())
            return;

        try {
        } catch  (IllegalArgumentException e) {
            return;
        }

        try {
        } catch (NullPointerException e) {
            return;
        }

        //try {
        // fplayer.playFirework(event.getPlayer().getWorld(), projectile.getLocation(), getEffect(type, color));
        //} catch (Exception e) {
        //    return;
        //}
        AE_RI.removeItem(event.getPlayer());
        return;
    }

    @Override
    public void run(DamageEvent event) {
        final LivingEntity victim = event.getVictim();
        String[] args = event.getArgs();

        if (args.length != 2 || args[0].isEmpty())
            return;

        final Type type;
        try {
            type = Type.valueOf(args[0].toUpperCase());
        } catch  (IllegalArgumentException e) {
            return;
        }
        
        final Color color;
        try {
            color = colorCheck.getFireworkColorByString(args[1]);
        } catch (NullPointerException e) {
            return;
        }
        
        try {
            fplayer.playFirework(event.getPlayer().getWorld(), victim.getLocation(), getEffect(type, color));
        } catch (Exception e) {
            return;
        }
        AE_RI.removeItem(event.getPlayer());
        return;
    }
}
