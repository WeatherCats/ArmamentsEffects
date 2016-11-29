package kipperorigin.armamentseffects.hooks;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.event.player.PlayerInteractEvent;

import kipperorigin.armamentseffects.managers.Effect;
import kipperorigin.armamentseffects.managers.EffectManager;
import kipperorigin.armamentseffects.managers.EffectWithLocation;

@SerializableAs("InteractHookPlayerLocation")
public class InteractHookPlayerLocation implements InteractHook
{
    EffectWithLocation effect;
    
    public InteractHookPlayerLocation(Effect effect) {
	this.effect = (EffectWithLocation) effect;
    }

    public InteractHookPlayerLocation(Map<String, Object> config) {
        effect = (EffectWithLocation) EffectManager.getInstance().getEffectByName((String) config.get("effect"));
    }
    
    public Map<String, Object> serialize() {
        Map<String, Object> ret = new HashMap<>();
        ret.put("effect", effect.getName());
        return ret;
    }

    public String getInfo() {
        return "PlayerLocation: " + effect.getName();
    }
    
    public void process(PlayerInteractEvent event) {
	effect.play(event.getPlayer().getLocation());
    }

    public boolean usesEffect(Effect effect) {
        return effect == this.effect;
    }

    public boolean alwaysActive() {
        return false;
    }
}
