package org.cubeville.effects.managers;

import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.configuration.serialization.SerializableAs;

@SerializableAs("SoundEffect")
public class SoundEffect extends EffectWithLocation
{
    private Sound sound;
    private float pitch;

    public SoundEffect(String name, Sound sound, float pitch) {
	setName(name);
	this.sound = sound;
        this.pitch = pitch;
    }

    public void modify(Sound sound, float pitch) {
	this.sound = sound;
	this.pitch = pitch;
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
    }

    public Map<String, Object> serialize() {
	Map<String, Object> ret = getSerializationBase();
	ret.put("sound", sound.toString());
        ret.put("pitch", pitch);
	return ret;
    }
    
    public void play(Location location) {
	location.getWorld().playSound(location, sound, 1F, pitch);
    }

    public List<String> getInfo() {
	List<String> ret = getInfoBase();
	ret.add("Sound: " + sound);
	return ret;
    }

    public String getType() {
        return "Sound";
    }
}
