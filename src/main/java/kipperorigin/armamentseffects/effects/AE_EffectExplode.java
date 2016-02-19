package kipperorigin.armamentseffects.effects;

import kipperorigin.armamentseffects.event.AE_DamageEvent;
import kipperorigin.armamentseffects.event.AE_ProjectileHitEvent;
import kipperorigin.armamentseffects.resources.AE_Explode;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

public class AE_EffectExplode extends AE_EffectParent {
    
    AE_Explode explode = new AE_Explode();

    @Override
    public void run(AE_DamageEvent event) {

        Entity target = event.getRawEvent().getDamager();

        String[] args = event.getArgs();

        float power = 0;

        if (args.length == 0 || args[0].isEmpty()) {
            power = 1;
        } else if (args.length == 1) {
            try {
                power = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                return;
            }
        }

        if (power > 25)
            return;

        explode.createExplosion(target, power);
        return;
    }

    @Override
    public void run(AE_ProjectileHitEvent event) {
        
        String[] args = event.getArgs();

        float power = 0;

        if (args.length != 2 || !args[0].equalsIgnoreCase("impact")) {
            return;
        }

        try {
            power = Integer.parseInt(args[1]);
        }
        catch (NumberFormatException e) {
            return;
        }
        
        if (power > 25)
            return;
            
        Location loc = event.getProjectile().getLocation();
        explode.createExplosion(loc, power);

        return;
    }
    
}
