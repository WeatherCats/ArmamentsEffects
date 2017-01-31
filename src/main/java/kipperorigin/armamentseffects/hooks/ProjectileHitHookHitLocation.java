package kipperorigin.armamentseffects.hooks;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

import kipperorigin.armamentseffects.managers.Effect;
import kipperorigin.armamentseffects.managers.EffectManager;
import kipperorigin.armamentseffects.managers.EffectWithLocation;

@SerializableAs("ProjectileHitHookHitLocation")
public class ProjectileHitHookHitLocation implements ProjectileHitHook
{
    EffectWithLocation effect;

    public ProjectileHitHookHitLocation(Effect effect) {
        this.effect = (EffectWithLocation) effect;
    }

    public ProjectileHitHookHitLocation(Map<String, Object> config) {
        effect = (EffectWithLocation) EffectManager.getInstance().getEffectByName((String) config.get("effect"));
    }

    public Map<String, Object> serialize() {
        Map<String, Object> ret = new HashMap<>();
        ret.put("effect", effect.getName());
        return ret;
    }

    public String getInfo() {
        return "ProjectileHitLocation: " + effect.getName();
    }

    public void process(ProjectileHitEvent event) {
        effect.play(event.getEntity().getLocation());
    }

    public boolean usesEffect(Effect effect) {
        return effect == this.effect;
    }

    public boolean alwaysActive() {
        return false;
    }

    public void projectileHitEvent() {
    }
}
