package org.cubeville.effects.managers;

import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.scheduler.BukkitRunnable;

import org.cubeville.effects.Effects;

@SerializableAs("SoundEffect")
public class SoundEffect extends EffectWithLocation
{
    private Sound sound;
    private float pitch;
    private int delay;
    
    public SoundEffect(String name, Sound sound, float pitch, int delay) {
	setName(name);
	this.sound = sound;
        this.pitch = pitch;
        this.delay = delay;
    }

    public void modify(Sound sound, float pitch, int delay) {
	this.sound = sound;
	this.pitch = pitch;
        this.delay = delay;
    }
    
    public SoundEffect(Map<String, Object> config) {
	sound = Sound.valueOf((String) config.get("sound"));
	setName((String) config.get("name"));
        if(config.get("pitch") != null) {
            double p = (Double) config.get("pitch");
            pitch = (float) p;
        }
        else {
            pitch = 1F;
        }
        if(config.get("delay") != null) {
            delay = (Integer) config.get("delay");
        }
        else {
            delay = 0;
        }
    }

    public Map<String, Object> serialize() {
	Map<String, Object> ret = getSerializationBase();
	ret.put("sound", sound.toString());
        ret.put("pitch", pitch);
        ret.put("delay", delay);
	return ret;
    }
    
    public void play(Location location) {
        if(delay == 0) {
            location.getWorld().playSound(location, sound, 1F, pitch);
        }
        else {
            new BukkitRunnable() {
                public void run() {
                    location.getWorld().playSound(location, sound, 1F, pitch);
                }
            }.runTaskLater(Effects.getInstance(), delay);
        }
    }

    public List<String> getInfo(boolean detailed) {
	List<String> ret = getInfoBase();
	ret.add("Sound: " + sound);
	return ret;
    }

    public String getType() {
        return "Sound";
    }
}
