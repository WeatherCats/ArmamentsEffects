package kipperorigin.armamentseffects.hooks;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.ProjectileLaunchEvent;

import kipperorigin.armamentseffects.AE_Main;
import kipperorigin.armamentseffects.managers.Effect;
import kipperorigin.armamentseffects.managers.EffectManager;
import kipperorigin.armamentseffects.managers.ParticleEffect;
import kipperorigin.armamentseffects.managers.ParticleEffectProjectileRunnable;

@SerializableAs("ProjectileLaunchHookParticlePlayer")
public class ProjectileLaunchHookParticlePlayer implements ProjectileLaunchHook
{
    private ParticleEffect effect;

    public ProjectileLaunchHookParticlePlayer(String effectName) {
        this.effect = (ParticleEffect) EffectManager.getInstance().getEffectByName(effectName);
    }

    public ProjectileLaunchHookParticlePlayer(Map<String, Object> config) {
        effect = (ParticleEffect) EffectManager.getInstance().getEffectByName((String) config.get("effect"));
    }

    public Map<String, Object> serialize() {
        Map<String, Object> ret = new HashMap<>();
        ret.put("effect", effect.getName());
        return ret;
    }

    public String getInfo() {
        return "Projectile Launch Particle Player";
    }

    public void process(ProjectileLaunchEvent event) {
        Projectile projectile = (Projectile) event.getEntity();
        new ParticleEffectProjectileRunnable(effect, projectile).runTaskTimer(AE_Main.getInstance(), 1, 1);
    }

    public boolean alwaysActive() {
        return false;
    }

    public boolean usesEffect(Effect effect) {
        return effect == this.effect;
    }
}
