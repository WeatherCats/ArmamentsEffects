package org.cubeville.effects.hooks;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.cubeville.effects.Effects;
import org.cubeville.effects.managers.Effect;
import org.cubeville.effects.managers.EffectManager;
import org.cubeville.effects.managers.ParticleEffect;
import org.cubeville.effects.managers.ParticleEffectTimedRunnable;
import org.cubeville.effects.util.PlayerUtil;

@SerializableAs("InteractHookTargetLocationParticlePlayer")
public class InteractHookTargetLocationParticlePlayer implements InteractHook
{
    private ParticleEffect effect;
    private double yOffset;
    private double stepsPerTick;
    private double speed;
    private boolean fixedPitch;
    private double pitch;

    public InteractHookTargetLocationParticlePlayer(String effectName, double yOffset, double stepsPerTick, double speed, boolean fixedPitch, double pitch) {
	this.effect = (ParticleEffect) EffectManager.getInstance().getEffectByName(effectName);
	this.yOffset = yOffset;
	this.stepsPerTick = stepsPerTick;
	this.speed = speed;
	this.fixedPitch = fixedPitch;
	this.pitch = pitch;
    }

    public InteractHookTargetLocationParticlePlayer(Map<String, Object> config) {
        String effectName = (String) config.get("effect");
        this.effect = (ParticleEffect) EffectManager.getInstance().getEffectByName(effectName);
        this.yOffset = (double) config.get("yOffset");
        this.stepsPerTick = (double) config.get("stepsPerTick");
        this.speed = (double) config.get("speed");
        this.fixedPitch = (boolean) config.get("fixedPitch");
        this.pitch = (double) config.get("pitch");
    }
    
    public Map<String, Object> serialize() {
        Map<String, Object> ret = new HashMap<>();
        ret.put("effect", effect.getName());
        ret.put("yOffset", yOffset);
        ret.put("stepsPerTick", stepsPerTick);
        ret.put("speed", speed);
        ret.put("pitch", pitch);
        ret.put("fixedPitch", fixedPitch);
        return ret;
    }

    public String getInfo() {
        return "TargetLocationParticlePlayer: " + effect.getName();
    }
    
    public void process(PlayerInteractEvent event) {
	Player player = event.getPlayer();
        Entity target = PlayerUtil.findTargetEntity(player, player.getNearbyEntities(10, 10, 10), 1000);
        if(target == null) return;
        Location loc = target.getLocation().clone();
        loc.setY(loc.getY() + yOffset);
        if(fixedPitch) loc.setPitch((float)pitch);
        new ParticleEffectTimedRunnable(Effects.getInstance(), effect, stepsPerTick, speed, loc).runTaskTimer(Effects.getInstance(), 1, 1);
    }

    public boolean usesEffect(Effect effect) {
        return (effect == this.effect);
    }

    public boolean alwaysActive() {
        return false;
    }

}
