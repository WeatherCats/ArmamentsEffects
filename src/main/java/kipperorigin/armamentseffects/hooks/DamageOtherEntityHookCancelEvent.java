package kipperorigin.armamentseffects.hooks;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import kipperorigin.armamentseffects.managers.Effect;

@SerializableAs("DamageOtherEntityHookCancelEvent")
public class DamageOtherEntityHookCancelEvent implements DamageOtherEntityHook
{
    public DamageOtherEntityHookCancelEvent() {}

    public DamageOtherEntityHookCancelEvent(Map<String, Object> config) {}
    
    public Map<String, Object> serialize() {
        Map<String, Object> ret = new HashMap<>();
        return ret;
    }

    public String getInfo() {
        return "CancelEvent";
    }

    public void process(EntityDamageByEntityEvent event) {
	event.setCancelled(true);
    }

    public boolean usesEffect(Effect effect) {
        return false;
    }

    public boolean alwaysActive() {
        return true;
    }
    
}

