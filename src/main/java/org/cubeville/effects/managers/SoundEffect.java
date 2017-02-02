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

    public SoundEffect(String name, Sound sound) {
	setName(name);
	this.sound = sound;
    }

    public SoundEffect(Map<String, Object> config) {
	sound = Sound.valueOf((String) config.get("sound"));
	setName((String) config.get("name"));
    }

    public Map<String, Object> serialize() {
	Map<String, Object> ret = getSerializationBase();
	ret.put("sound", sound.toString());
	return ret;
    }
    
    public void play(Location location) {
	location.getWorld().playSound(location, sound, 1F, 1F);
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
