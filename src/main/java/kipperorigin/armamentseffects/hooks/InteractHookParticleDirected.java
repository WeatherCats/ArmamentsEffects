package kipperorigin.armamentseffects.hooks;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerInteractEvent;

import kipperorigin.armamentseffects.AE_Main;
import kipperorigin.armamentseffects.managers.Effect;
import kipperorigin.armamentseffects.managers.EffectManager;
import kipperorigin.armamentseffects.managers.ParticleEffectTimedRunnable;

public class InteractHookParticleDirected implements InteractHook
{
    private int effectId;
    private double yOffset;
    private double stepsPerTick;
    private double speed;
    private boolean fixedPitch;
    private double pitch;
    
    public InteractHookParticleDirected(String effectName, double yOffset, double stepsPerTick, double speed, boolean fixedPitch, double pitch) {
	this.effectId = EffectManager.getInstance().getEffectIdByName(effectName);
	this.yOffset = yOffset;
	this.stepsPerTick = stepsPerTick;
	this.speed = speed;
	this.fixedPitch = fixedPitch;
	this.pitch = pitch;
    }

    public Map<String, Object> serialize() {
        Map<String, Object> ret = new HashMap<>();
        return ret;
    }

    public String getInfo() {
        return "DEPRECATED";
    }
    
    public void process(PlayerInteractEvent rawEvent) {
        PlayerInteractEvent event = (PlayerInteractEvent) rawEvent;
	Location loc = event.getPlayer().getLocation().clone();
	loc.setY(loc.getY() + yOffset);
	if(fixedPitch) loc.setPitch((float)pitch);
	new ParticleEffectTimedRunnable(AE_Main.getInstance(), effectId, stepsPerTick, speed, loc).runTaskTimer(AE_Main.getInstance(), 1, 1);
    }

    public boolean usesEffect(Effect effect) {
        return false; // TODO, this needs an overhaul anyways
    }

    public boolean alwaysActive() {
        return false;
    }
}
