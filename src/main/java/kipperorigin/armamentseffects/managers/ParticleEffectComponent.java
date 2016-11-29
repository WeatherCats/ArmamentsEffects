package kipperorigin.armamentseffects.managers;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.util.Vector;

import com.comphenix.protocol.wrappers.EnumWrappers.Particle;

import kipperorigin.armamentseffects.managers.modifier.CoordinateModifier;
import kipperorigin.armamentseffects.managers.sources.coordinate.ConstantCoordinateSource;
import kipperorigin.armamentseffects.managers.sources.coordinate.CoordinateSource;
import kipperorigin.armamentseffects.util.Conversions;

@SerializableAs("ParticleEffectComponent")
public class ParticleEffectComponent implements ConfigurationSerializable
{
    private CoordinateSource coordinates;
    private Particle particle;
    private int count;
    private Vector spread;
    private Material material;
    private boolean directionalCoordinates;
    private List<CoordinateModifier> modifiers;
    private List<ParticleEffectTimelineEntry> timeline;

    public ParticleEffectComponent() {
	coordinates = new ConstantCoordinateSource();
	particle = Particle.VILLAGER_HAPPY;
	count = 0;
	spread = new Vector(0, 0, 0);
	material = Material.AIR;
	directionalCoordinates = true;
	modifiers = new ArrayList<>();
	timeline = new ArrayList<>();
    }
    
    public ParticleEffectComponent(Map<String, Object> config) {
	coordinates = (CoordinateSource) config.get("coordinates");
	particle = Particle.valueOf((String) config.get("particle"));
	count = (int) config.get("count");
	spread = Conversions.getVectorFromString((String) config.get("spread"));
	material = Material.valueOf((String) config.get("material"));
	directionalCoordinates = (boolean) config.get("directionalCoordinates");
	modifiers = (List<CoordinateModifier>) config.get("modifiers");
	timeline = (List<ParticleEffectTimelineEntry>) config.get("timeline");
    }

    public Map<String, Object> serialize() {
	Map<String, Object> ret = new HashMap<>();
	ret.put("coordinates", coordinates);
	ret.put("particle", particle.toString());
	ret.put("count", count);
	ret.put("spread", Conversions.getVectorAsString(spread));
	ret.put("material", material.toString());
	ret.put("directionalCoordinates", directionalCoordinates);
	ret.put("modifiers", modifiers);
	ret.put("timeline", timeline);
	return ret;
    }

    public boolean isActive(int step) {
        if(timeline.size() == 0) return true;
	for(ParticleEffectTimelineEntry tle: timeline) {
	    if(step >= tle.getStepStart() && step < tle.getStepStart() + tle.getStepCount()) {
		return true;
	    }
	}
	return false;
    }

    public List<String> getInfo() {
	List<String> ret = new ArrayList<>();
	ret.add("  Source: " + coordinates.getInfo());
	ret.add("  Particle: " + particle);
	ret.add("  Count: " + count);
	ret.add("  Spread: " + spread);
	ret.add("  Material: " + material);
	if(modifiers.size() > 0) {
	    ret.add("  Modifiers:");
	    for(CoordinateModifier modifier: modifiers) {
		ret.add("    " + modifier.getInfo());
	    }
	}
	if(timeline.size() > 0) {
	    ret.add("  Timeline:");
	    for(ParticleEffectTimelineEntry e: timeline) {
		ret.add("    " + e.getStepStart() + "-" + (e.getStepStart() + e.getStepCount()) + "x" + e.getStepRepeat());
	    }
	}
	return ret;
    }
    
    public final List<CoordinateModifier> getModifiers() {
	return modifiers;
    }
    
    public final void addModifier(CoordinateModifier modifier) {
	getModifiers().add(modifier);
    }
    
    public final CoordinateSource getCoordinates() {
	return coordinates;
    }

    public final void setCoordinates(CoordinateSource coordinates) {
	this.coordinates = coordinates;
    }
    
    public final List<Vector> getModifiedCoordinates(int step) {
	List<Vector> ret = coordinates.getVertices(step, 0);
	for(CoordinateModifier modifier: getModifiers()) {
	    ret = modifier.modify(ret, step);
	}
	return ret;
    }
    
    public final Particle getParticle() {
	return this.particle;
    }

    public final void setParticle(final Particle argParticle) {
	this.particle = argParticle;
    }

    public final int getCount() {
	return this.count;
    }

    public final void setCount(final int argCount) {
	this.count = argCount;
    }

    public final Vector getSpread() {
	return this.spread;
    }

    public final void setSpread(Vector spread) {
	this.spread = spread;
    }
    
    public final Material getMaterial() {
	return this.material;
    }

    public final void setMaterial(final Material argMaterial) {
	this.material = argMaterial;
    }

    public final boolean isDirectionalCoordinates() {
	return this.directionalCoordinates;
    }

    public final void setDirectionalCoordinates(final boolean argDirectionalCoordinates) {
	this.directionalCoordinates = argDirectionalCoordinates;
    }

    public final boolean getDirectionalCoordinates() {
	return directionalCoordinates;
    }

    public final List<ParticleEffectTimelineEntry> getTimeline() {
	return timeline;
    }
}
