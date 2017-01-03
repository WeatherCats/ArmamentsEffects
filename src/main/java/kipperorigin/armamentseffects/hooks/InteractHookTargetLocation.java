package kipperorigin.armamentseffects.hooks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

import kipperorigin.armamentseffects.managers.Effect;
import kipperorigin.armamentseffects.managers.EffectManager;
import kipperorigin.armamentseffects.managers.EffectWithLocation;
import kipperorigin.armamentseffects.util.Conversions;

@SerializableAs("InteractHookTargetLocation")
public class InteractHookTargetLocation implements InteractHook
{
    List<Effect> effects;
    List<Effect> noTargetEffects;

    public InteractHookTargetLocation(List<Effect> effects, List<Effect> noTargetEffects) {
	this.effects = new ArrayList<>();
	for(Effect effect: effects) {
	    if(effect instanceof EffectWithLocation) this.effects.add((EffectWithLocation) effect);
	}
	this.noTargetEffects = new ArrayList<>();
	for(Effect effect: noTargetEffects) {
	    if(effect instanceof EffectWithLocation) this.noTargetEffects.add((EffectWithLocation) effect);
	}
    }

    public InteractHookTargetLocation(Map<String, Object> config) {
        effects = new ArrayList<>();
        for(String effect: (List<String>) config.get("effects")) {
            effects.add(EffectManager.getInstance().getEffectByName(effect));
        }
        noTargetEffects = new ArrayList<>();
        for(String effect: (List<String>) config.get("noTargetEffects")) {
            noTargetEffects.add(EffectManager.getInstance().getEffectByName(effect));
        }
    }
    
    public Map<String, Object> serialize() {
        Map<String, Object> ret = new HashMap<>();
        ret.put("effects", Conversions.getListOfEffectNames(effects));
        ret.put("noTargetEffects", Conversions.getListOfEffectNames(noTargetEffects));
        return ret;
    }

    public String getInfo() {
        return "TargetLocation: ["
            + Conversions.joinStringList(Conversions.getListOfEffectNames(effects), ",") + "] / ["
            + Conversions.joinStringList(Conversions.getListOfEffectNames(noTargetEffects), ",") + "]";
    }
    
    public void process(PlayerInteractEvent event) {
	Player player = event.getPlayer();
	List<Entity> nbe = player.getNearbyEntities(10, 10, 10);
	Entity target = null;
	double minangle = 1000;
	for(Entity e: nbe) {
	    if(!e.isDead()) {
		double dist = e.getLocation().distance(player.getLocation());
		if(dist < 10) {
		    if(e.getType() == EntityType.PLAYER || e.getType() == EntityType.VILLAGER || e.getType() == EntityType.PIG || e.getType() == EntityType.COW || e.getType() == EntityType.SHEEP || e.getType() == EntityType.CHICKEN || e.getType() == EntityType.HORSE) {
			Vector targetDirection = e.getLocation().subtract(player.getLocation()).toVector();
			Vector playerDirection = player.getLocation().getDirection();
			double angle = playerDirection.angle(targetDirection);
			if(angle < 0.25 && angle < minangle) {
			    target = e;
			    minangle = angle;		    
			}
		    }
		}
	    }
	}
	if(target != null) {
	    for(Effect effect: effects) {
		((EffectWithLocation) effect).play(target.getLocation());
	    }
	}
	else {
	    for(Effect effect: noTargetEffects) {
		((EffectWithLocation) effect).play(player.getLocation());
	    }
	}
    }

    public boolean usesEffect(Effect effect) {
        for(int i = 0; i < effects.size(); i++) if(effects.get(i) == effect) return true;
        for(int i = 0; i < noTargetEffects.size(); i++) if(noTargetEffects.get(i) == effect) return true;
        return false;
    }

    public boolean alwaysActive() {
        return false;
    }

}
