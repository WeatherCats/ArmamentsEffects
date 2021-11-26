package org.cubeville.effects.managers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.util.Vector;
import org.cubeville.effects.managers.modifier.CoordinateModifier;
import org.cubeville.effects.managers.sources.coordinate.ConstantCoordinateSource;
import org.cubeville.effects.managers.sources.coordinate.CoordinateSource;
import org.cubeville.effects.managers.sources.value.ConstantValueSource;
import org.cubeville.effects.managers.sources.value.ValueSource;

@SerializableAs("ParticleEffectComponent")
public class ParticleEffectComponent implements ConfigurationSerializable
{
    private CoordinateSource coordinates;
    private Particle particle;
    private ValueSource count;
    private ValueSource spreadX;
    private ValueSource spreadY;
    private ValueSource spreadZ;
    private ValueSource size;
    private ValueSource colourRed;
    private ValueSource colourGreen;
    private ValueSource colourBlue;
    private Material material;
    private boolean directionalCoordinates;
    private List<CoordinateModifier> modifiers;
    private List<ParticleEffectTimelineEntry> timeline;

    public ParticleEffectComponent() {
	coordinates = new ConstantCoordinateSource();
	particle = Particle.VILLAGER_HAPPY;
	count = new ConstantValueSource(0);
	spreadX = new ConstantValueSource(0);
        spreadY = new ConstantValueSource(0);
        spreadZ = new ConstantValueSource(0);
	material = Material.AIR;
	directionalCoordinates = true;
	modifiers = new ArrayList<>();
	timeline = new ArrayList<>();
    }
    
    public ParticleEffectComponent(Map<String, Object> config) {
	coordinates = (CoordinateSource) config.get("coordinates");
	particle = Particle.valueOf((String) config.get("particle"));
	count = (ValueSource) config.get("count");
        spreadX = (ValueSource) config.get("spreadX");
        spreadY = (ValueSource) config.get("spreadY");
        spreadZ = (ValueSource) config.get("spreadZ");
	material = Material.valueOf((String) config.get("material"));
	directionalCoordinates = (boolean) config.get("directionalCoordinates");
	modifiers = (List<CoordinateModifier>) config.get("modifiers");
	timeline = (List<ParticleEffectTimelineEntry>) config.get("timeline");
        if(config.get("colourRed") != null)
            colourRed = (ValueSource) config.get("colourRed");
        else
            colourRed = new ConstantValueSource(0);
        if(config.get("colourGreen") != null)
            colourGreen = (ValueSource) config.get("colourGreen");
        else
            colourGreen = new ConstantValueSource(0);
        if(config.get("colourBlue") != null)
            colourBlue = (ValueSource) config.get("colourBlue");
        else
            colourBlue = new ConstantValueSource(0);
        if(config.get("size") != null)
            size = (ValueSource) config.get("size");
        else
            size = new ConstantValueSource(1);
    }

    public Map<String, Object> serialize() {
	Map<String, Object> ret = new HashMap<>();
	ret.put("coordinates", coordinates);
	ret.put("particle", particle.toString());
	ret.put("count", count);
        ret.put("spreadX", spreadX);
        ret.put("spreadY", spreadY);
        ret.put("spreadZ", spreadZ);
	ret.put("material", material.toString());
	ret.put("directionalCoordinates", directionalCoordinates);
	ret.put("modifiers", modifiers);
	ret.put("timeline", timeline);
        ret.put("colourRed", colourRed);
        ret.put("colourGreen", colourGreen);
        ret.put("colourBlue", colourBlue);
	ret.put("size", size);
	return ret;
    }

    public void addModifier(CoordinateModifier coordinateModifier) {
        modifiers.add(coordinateModifier);
    }

    public void deleteModifiers() {
        modifiers = new ArrayList<>();
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
	ret.add("  Count: " + count.getInfo());
	ret.add("  SpreadX: " + spreadX.getInfo());
	ret.add("  SpreadY: " + spreadY.getInfo());
	ret.add("  SpreadZ: " + spreadZ.getInfo());
	ret.add("  Material: " + material);
        if(particle == Particle.REDSTONE) {
            ret.add("  Red: " + colourRed.getInfo());
            ret.add("  Green: " + colourGreen.getInfo());
            ret.add("  Blue: " + colourBlue.getInfo());
            ret.add("  Size: " + size.getInfo());
        }
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
        // TODO: Add colour and size for redstone particle, could remove material where it's not relevant
    }
    
    public final List<CoordinateModifier> getModifiers() {
	return modifiers;
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

    public final ValueSource getCount() {
	return this.count;
    }

    public final void setCount(final ValueSource argCount) {
	this.count = argCount;
    }

    public final ValueSource getSpreadX() {
	return this.spreadX;
    }

    public final void setSpreadX(ValueSource spreadX) {
	this.spreadX = spreadX;
    }
    
    public final ValueSource getSpreadY() {
	return this.spreadY;
    }

    public final void setSpreadY(ValueSource spreadY) {
	this.spreadY = spreadY;
    }
    
    public final ValueSource getSpreadZ() {
	return this.spreadZ;
    }

    public final void setSpreadZ(ValueSource spreadZ) {
	this.spreadZ = spreadZ;
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

    public final ValueSource getColourRed() {
        return colourRed;
    }

    public final void setColourRed(ValueSource colourRed) {
        this.colourRed = colourRed;
    }
    
    public final ValueSource getColourGreen() {
        return colourGreen;
    }

    public final void setColourGreen(ValueSource colourGreen) {
        this.colourGreen = colourGreen;
    }
    
    public final ValueSource getColourBlue() {
        return colourBlue;
    }

    public final void setColourBlue(ValueSource colourBlue) {
        this.colourBlue = colourBlue;
    }
    
    public final ValueSource getSize() {
        return size;
    }

    public final void setSize(ValueSource size) {
        this.size = size;
    }
    
}
