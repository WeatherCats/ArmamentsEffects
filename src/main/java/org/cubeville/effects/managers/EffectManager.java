package org.cubeville.effects.managers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

@SerializableAs("EffectManager")
public class EffectManager implements ConfigurationSerializable
{
    List<Effect> effects;
    static EffectManager instance;

    public static EffectManager getInstance() {
        return instance;
    }

    public EffectManager(Map<String, Object> config) {
        effects = (List<Effect>) config.get("effects");
        instance = this;
    }

    public Map<String, Object> serialize() {
        Map<String, Object> ret = new HashMap<>();
        ret.put("effects", effects);
        return ret;
    }
    
    public EffectManager() {
        effects = new ArrayList<>();
        instance = this;
        addExampleEffects();
    }

    public void addExampleEffects() {
        // {
        //     ParticleEffect effect = new ParticleEffect("sparkle");
        //     ParticleEffectComponent component = new ParticleEffectComponent();
        //     RandomCoordinateSource coordSource = new RandomCoordinateSource(20, new Vector(-.5, 0, -.5), new Vector(.5, 2, .5));
        //     component.setCoordinates(coordSource);
        //     component.setParticle(Particle.REDSTONE);
        //     component.setCount(0);
        //     component.setSpread(new Vector(0, 0.6, 0));
        //     component.setDirectionalCoordinates(false);
        //     component.getTimeline().add(new ParticleEffectTimelineEntry(0, 0, 5));
        //     effect.getComponents().add(component);
        //     effects.add(effect);
        // }
        // {
        //     SoundEffect effect = new SoundEffect("sparklesound", "ENTITY_PLAYER_LEVELUP");
        //     effects.add(effect);
        // }
        // {
        //     SoundEffect effect = new SoundEffect("sparklenosound", "BLOCK_FIRE_EXTINGUISH");
        //     effects.add(effect);
        // }
        // {
        //     RemoveLivingEntityEffect effect = new RemoveLivingEntityEffect("removeentity");
        //     effects.add(effect);
        // }
        // {
        //     SpawnEntityEffect effect = new SpawnEntityEffect("spawnhorse", EntityType.HORSE);
        //     effects.add(effect);
        // }
        // {
        //     SitEffect effect = new SitEffect("sit");
        //     effects.add(effect);
        // }
        // {
        //     SmoothSnowEffect effect = new SmoothSnowEffect("smoothsnow");
        //     effects.add(effect);
        // }
        // {
        //     ParticleEffect effect = new ParticleEffect("modifiers");
        //     ParticleEffectComponent component = new ParticleEffectComponent();
        //     component.setCoordinates(new CircleCoordinateSource(1.5, 12, 0, 100, false));
        //     component.setParticle(Particle.VILLAGER_HAPPY);
        //     component.setCount(0);
        //     component.setSpread(new Vector(0, 0, 0));
        //     component.setDirectionalCoordinates(true);
        //     component.addModifier(new CoordinateModifierRotate(new LinearValueSource(0, 30)));
        //     component.addModifier(new CoordinateModifierScale(new LinearValueSource(0.2, 0.05)));
        //     component.getTimeline().add(new ParticleEffectTimelineEntry(0, 0, 100));
        //     effect.getComponents().add(component);
        //     effects.add(effect);
        // }
        // {
        //     ParticleEffect effect = new ParticleEffect("tube");
        //     effect.setStepsLoop(40);
        //     ParticleEffectComponent component = new ParticleEffectComponent();
        //     component.setCoordinates(new CircleCoordinateSource(2.5, 36, 0, 359, false));
        //     component.setParticle(Particle.VILLAGER_HAPPY);
        //     component.setCount(0);
        //     component.setSpread(new Vector(0, 0, 0));
        //     component.setDirectionalCoordinates(true);
        //     component.getTimeline().add(new ParticleEffectTimelineEntry(0, 0, 100));
        //     effect.getComponents().add(component);
        //     effects.add(effect);
        // }
        // {
        //     ParticleEffect effect = new ParticleEffect("crystal");
        //     effect.setStepsLoop(100);
        //     ParticleEffectComponent component = new ParticleEffectComponent();
        //     component.setCoordinates(new CircleCoordinateSource(2.5, 36, 0, 359, false));
        //     component.setParticle(Particle.VILLAGER_HAPPY);
        //     component.setCount(0);
        //     component.setSpread(new Vector(0, 0, 0));
        //     component.setDirectionalCoordinates(true);
        //     component.addModifier(new CoordinateModifierRotate(new LinearValueSource(0, 10)));
        //     component.addModifier(new CoordinateModifierScale(new LinearValueSource(0.2, 0.05)));
        //     component.addModifier(new CoordinateModifierMove(new SinewaveValueSource(0, 3, 1, 0, false), false, false, true));
        //     component.getTimeline().add(new ParticleEffectTimelineEntry(0, 0, 100));
        //     effect.getComponents().add(component);
        //     effects.add(effect);
        // }
        // {
        //     ParticleEffect effect = new ParticleEffect("dna");
        //     effect.setRepeatCount(1);
        //     effect.setStepsLoop(300);
        //     {
        //         ParticleEffectComponent component = new ParticleEffectComponent();
        //         List<Vector> vertices = new ArrayList<>();
        //         vertices.add(new Vector(2.5, 0, 0));
        //         vertices.add(new Vector(-2.5, 0, 0));
        //         component.setCoordinates(new ConstantCoordinateSource(vertices));
        //         component.setParticle(Particle.VILLAGER_HAPPY);
        //         component.setCount(0);
        //         component.setSpread(new Vector(0, 0, 0));
        //         component.setDirectionalCoordinates(true);
        //         component.addModifier(new CoordinateModifierRotate(new LinearValueSource(0, 10)));
        //         component.getTimeline().add(new ParticleEffectTimelineEntry(0, 0, 300));
        //         effect.getComponents().add(component);
        //     }
        //     {
        //         ParticleEffectComponent component = new ParticleEffectComponent();
        //         List<Vector> vertices = new ArrayList<>();
        //         vertices.add(new Vector(2.0, 0, 0));
        //         vertices.add(new Vector(1.5, 0, 0));
        //         vertices.add(new Vector(1.0, 0, 0));
        //         vertices.add(new Vector(0.5, 0, 0));
        //         vertices.add(new Vector(0, 0, 0));
        //         vertices.add(new Vector(-0.5, 0, 0));
        //         vertices.add(new Vector(-1, 0, 0));
        //         vertices.add(new Vector(-1.5, 0, 0));
        //         vertices.add(new Vector(-2, 0, 0));
        //         component.setCoordinates(new ConstantCoordinateSource(vertices));
        //         component.setParticle(Particle.SPELL_INSTANT);
        //         component.setCount(0);
        //         component.setSpread(new Vector(0, 0, 0));
        //         component.setDirectionalCoordinates(true);
        //         component.addModifier(new CoordinateModifierRotate(new LinearValueSource(0, 10)));
        //         component.getTimeline().add(new ParticleEffectTimelineEntry(0, 1, 1));
        //         component.getTimeline().add(new ParticleEffectTimelineEntry(10, 1, 1));
        //         component.getTimeline().add(new ParticleEffectTimelineEntry(20, 1, 1));
        //         component.getTimeline().add(new ParticleEffectTimelineEntry(30, 1, 1));
        //         component.getTimeline().add(new ParticleEffectTimelineEntry(40, 1, 1));
        //         component.getTimeline().add(new ParticleEffectTimelineEntry(50, 1, 1));
        //         component.getTimeline().add(new ParticleEffectTimelineEntry(60, 1, 1));
        //         component.getTimeline().add(new ParticleEffectTimelineEntry(70, 1, 1));
        //         component.getTimeline().add(new ParticleEffectTimelineEntry(80, 1, 1));
        //         component.getTimeline().add(new ParticleEffectTimelineEntry(90, 1, 1));
        //         component.getTimeline().add(new ParticleEffectTimelineEntry(100, 1, 1));
        //         component.getTimeline().add(new ParticleEffectTimelineEntry(110, 1, 1));
        //         component.getTimeline().add(new ParticleEffectTimelineEntry(120, 1, 1));
        //         component.getTimeline().add(new ParticleEffectTimelineEntry(130, 1, 1));
        //         component.getTimeline().add(new ParticleEffectTimelineEntry(140, 1, 1));
        //         component.getTimeline().add(new ParticleEffectTimelineEntry(150, 1, 1));
        //         component.getTimeline().add(new ParticleEffectTimelineEntry(160, 1, 1));
        //         component.getTimeline().add(new ParticleEffectTimelineEntry(170, 1, 1));
        //         component.getTimeline().add(new ParticleEffectTimelineEntry(180, 1, 1));
        //         component.getTimeline().add(new ParticleEffectTimelineEntry(190, 1, 1));
        //         component.getTimeline().add(new ParticleEffectTimelineEntry(200, 1, 1));
        //         component.getTimeline().add(new ParticleEffectTimelineEntry(210, 1, 1));
        //         component.getTimeline().add(new ParticleEffectTimelineEntry(220, 1, 1));
        //         component.getTimeline().add(new ParticleEffectTimelineEntry(230, 1, 1));
        //         component.getTimeline().add(new ParticleEffectTimelineEntry(240, 1, 1));
        //         component.getTimeline().add(new ParticleEffectTimelineEntry(250, 1, 1));
        //         component.getTimeline().add(new ParticleEffectTimelineEntry(260, 1, 1));
        //         component.getTimeline().add(new ParticleEffectTimelineEntry(270, 1, 1));
        //         component.getTimeline().add(new ParticleEffectTimelineEntry(280, 1, 1));
        //         component.getTimeline().add(new ParticleEffectTimelineEntry(290, 1, 1));
        //         effect.getComponents().add(component);
        //     }
        //     effects.add(effect);
        // }
        // {
        //     ParticleEffect effect = new ParticleEffect("jumpy");
        //     effect.setStepsLoop(60);
        //     ParticleEffectComponent component = new ParticleEffectComponent();
        //     component.setCoordinates(new ConstantCoordinateSource());
        //     component.setParticle(Particle.CLOUD);
        //     component.setCount(0);
        //     component.setSpread(new Vector(0, 0, 0));
        //     component.setDirectionalCoordinates(true);
        //     component.addModifier(new CoordinateModifierMove(new SinewaveValueSource(0, 12, 3, 0, true), false, true, false));
        //     component.addModifier(new CoordinateModifierScale(new LinearValueSource(1, -0.015)));
        //     component.getTimeline().add(new ParticleEffectTimelineEntry(0, 0, 60));
        //     effect.getComponents().add(component);
        //     effects.add(effect);
        // }
        // {
        //     ParticleEffect effect = new ParticleEffect("wobbly");
        //     ParticleEffectComponent component = new ParticleEffectComponent();
        //     component.setCoordinates(new CircleCoordinateSource(2.5, 18, 0, 359, false));
        //     component.setParticle(Particle.VILLAGER_HAPPY);
        //     component.setCount(0);
        //     component.setSpread(new Vector(0, 0, 0));
        //     component.setDirectionalCoordinates(true);
        //     component.addModifier(new CoordinateModifierScale2d(new SinewaveValueSource(0, 12, 1, 2, false), new SinewaveValueSource(180, 12, 1, 2, false)));
        //     component.addModifier(new CoordinateModifierScale(new LinearValueSource(1, -0.05)));
        //     component.getTimeline().add(new ParticleEffectTimelineEntry(0, 0, 100));
        //     effect.getComponents().add(component);
        //     effects.add(effect);
        // }
    }

    public void addEffect(Effect effect) {
        effects.add(effect);
    }

    public void removeEffect(Effect effect) {
        effects.remove(effect);
    }
    
    public int getEffectIdByName(String name) {
        for(int i = 0; i < effects.size(); i++) {
            if(effects.get(i).getName().equals(name)) return i;
        }
        return -1;
    }

    public List<String> getEffectList() {
        List<String> ret = new ArrayList<>();
        for(Effect effect: effects) {
            ret.add(effect.getName() + ": " + effect.getType());
        }
        return ret;
    }
    
    public Effect getEffectById(int id) {
        return effects.get(id);
    }

    public Effect getEffectByName(String name) {
        int id = getEffectIdByName(name);
        if(id == -1) return null;
        return effects.get(id);
    }

    public List<String> getEffectInfo(String name) {
        Effect effect = getEffectByName(name);
        if(effect == null) return null;
        return effect.getInfo();
    }
}
