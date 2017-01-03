package kipperorigin.armamentseffects.managers;

import org.bukkit.entity.Projectile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import kipperorigin.armamentseffects.registry.ProjectileTrackerAction;
import kipperorigin.armamentseffects.registry.Registry;

public class ParticleEffectProjectileRunnable extends BukkitRunnable implements ProjectileTrackerAction
{
    private final Projectile projectile;
    private final ParticleEffect effect;
    private int step;
    
    public ParticleEffectProjectileRunnable(ParticleEffect effect, Projectile projectile) {
        this.projectile = projectile;
        this.effect = effect;
        step = 0;
        Registry.getInstance().addProjectileHitAction(projectile, this);
    }

    private void abort() {
        this.cancel();
        Registry.getInstance().removeProjectileHitAction(projectile, this);
    }

    public void projectileHitEvent() {
        abort();
    }
    
    @Override
    public void run() {
        if(projectile.isDead()) {
            abort();
            return;
        }
        step++;
        if(step == 100) {
            abort();
            return;
        }
        Location loc = projectile.getLocation();
        loc.setYaw(360 - loc.getYaw());
        loc.setPitch(-loc.getPitch());
        if(!effect.play(step, loc)) {
            abort();
            return;
        }
    }
    
}
