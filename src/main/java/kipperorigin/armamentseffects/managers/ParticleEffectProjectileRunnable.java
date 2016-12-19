package kipperorigin.armamentseffects.managers;

import org.bukkit.entity.Projectile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class ParticleEffectProjectileRunnable extends BukkitRunnable
{
    private final Projectile projectile;
    private final ParticleEffect effect;
    private int step;
    
    public ParticleEffectProjectileRunnable(ParticleEffect effect, Projectile projectile) {
        this.projectile = projectile;
        this.effect = effect;
        step = 0;
    }

    @Override
    public void run() {
        if(projectile.isDead()) {
            this.cancel();
            return;
        }
        step++;
        if(step == 100) {
            this.cancel();
            return;
        }
        Location loc = projectile.getLocation();
        loc.setYaw(360 - loc.getYaw());
        loc.setPitch(-loc.getPitch());
        if(!effect.play(step, loc)) {
            //System.out.println("Effect is over");
            //this.cancel();
            // TODO!!!
            return;
        }
    }
    
}
