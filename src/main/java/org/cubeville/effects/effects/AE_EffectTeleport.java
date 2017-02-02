package org.cubeville.effects.effects;

import org.bukkit.Location;
import org.cubeville.effects.event.DamageEvent;
import org.cubeville.effects.event.AE_ProjectileHitEvent;

public class AE_EffectTeleport extends AE_EffectParent {

    @Override
    public void run(AE_ProjectileHitEvent event) {
    	String args[] = event.getArgs();
    	if (args.length != 0)
    		return;
        event.getPlayer().teleport(event.getLocation());
        return;
    }
    
    @Override
    public void run(DamageEvent event) {
    	String args[] = event.getArgs();
    	Location loc = event.getPlayer().getLocation();
        event.getPlayer().teleport(event.getVictim().getLocation());
        
        if (args.length == 1 && args[0].equalsIgnoreCase("swap"))
        	event.getVictim().teleport(loc);
        return;
    }
}
