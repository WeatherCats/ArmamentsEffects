package org.cubeville.effects.effects;

import org.bukkit.entity.Player;
import org.cubeville.effects.Effects;
import org.cubeville.effects.event.DamageEvent;

public class AE_EffectDrain extends AE_EffectParent {

    private Effects plugin;

    public AE_EffectDrain(Effects plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run(DamageEvent event) {
        final Player player = event.getPlayer();
        // final LivingEntity target = event.getVictim();
        String[] args = event.getArgs();
        double health = player.getHealth();
        double maxhealth = player.getMaxHealth();
        int perc = 0;
        
        try {
        	perc = Integer.parseInt(args[0]) * (1/100);
        } catch (NumberFormatException e) {
        	player.sendMessage("Arg 1 must be a number!");
        	return;
        }
        
        health = health + (event.getRawEvent().getFinalDamage() * perc);
        
        if (health > maxhealth)
        	player.setHealth(maxhealth);
        else
        	player.setHealth(health);
        
        /* final double heal = target.getHealth();
        if (args.length == 0 || args[0].isEmpty()) {
            Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                @Override
                public void run() {
                    double healx = target.getHealth();
                    double healy = heal - healx;
                    if (player.getHealth() + healy > 20)
                        player.setHealth(20d);
                    else
                        player.setHealth(player.getHealth() + healy);
                }
            }, 1L);
        } else if (args.length == 1) {
            try {
                Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                return;
            }
            int amp = Integer.parseInt(args[0]);
            if (player.getHealth() + amp > 20)
                player.setHealth(20d);
            else
                player.setHealth(player.getHealth() + amp);
        } */
    }
}
