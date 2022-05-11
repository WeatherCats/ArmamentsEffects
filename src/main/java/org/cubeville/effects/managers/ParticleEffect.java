package org.cubeville.effects.managers;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import org.cubeville.effects.managers.EventListener;

@SerializableAs("ParticleEffect")
public class ParticleEffect extends EffectWithLocation
{
    private List<ParticleEffectComponent> components;
    private int stepsLoop;
    private int repeatCount; // 0 = indefinitely, not recommended though
    private int repeatOffset;
    
    public ParticleEffect(String name) {
        setName(name);
        stepsLoop = 1;
        repeatCount = 1;
    }

    public ParticleEffect(Map<String, Object> config) {
        setName((String) config.get("name"));
        stepsLoop = (int) config.get("stepsLoop");
        repeatCount = (int) config.get("repeatCount");
        repeatOffset = (int) config.get("repeatOffset");
        components = (List<ParticleEffectComponent>) config.get("components");
    }

    public Map<String, Object> serialize() {
        Map<String, Object> ret = getSerializationBase();
        ret.put("stepsLoop", stepsLoop);
        ret.put("repeatCount", repeatCount);
        ret.put("repeatOffset", repeatOffset);
        ret.put("components", components);
        return ret;
    }

    public int getStepsTotal() {
        return repeatCount * repeatOffset + stepsLoop;
    }

    public boolean hasStep(int Step) {
        if(repeatCount == 0) return true;
        return Step < getStepsTotal();
    }

    public void play(Location location) {
        play(0, location);
    }

    public boolean play(int step, Location location) {
        if(!hasStep(step)) return false;

        int localStep = step % getStepsLoop();
        for(ParticleEffectComponent component: components) {
            if(component.isActive(localStep)) {
                for(Vector vec: component.getModifiedCoordinates(localStep)) {
                    Location nloc = location.clone();
                    Vector nvec = vec.clone();
                    if(component.getDirectionalCoordinates()) {
                        nvec = transform(location.getYaw(), location.getPitch(), nvec);
                    }
                    nloc.add(nvec);

                    double speed = (double) component.getSpeed().getValue(step);

                    if(component.getParticle() == Particle.REDSTONE || component.getParticle() == Particle.DUST_COLOR_TRANSITION) {
                        int red = (int) (Math.round(component.getColourRed().getValue(step) * 255));
                        if(red < 0) red = 0;
                        if(red > 255) red = 255;
                        int green = (int) (Math.round(component.getColourGreen().getValue(step) * 255));
                        if(green < 0) green = 0;
                        if(green > 255) green = 255;
                        int blue = (int) (Math.round(component.getColourBlue().getValue(step) * 255));
                        if(blue < 0) blue = 0;
                        if(blue > 255) blue = 255;
                        float size = (float) component.getSize().getValue(step);

                        Particle.DustOptions dustoptions;
                        if(component.getParticle() == Particle.DUST_COLOR_TRANSITION) {
                            int tored = (int) (Math.round(component.getColourToRed().getValue(step) * 255));
                            if(tored < 0) tored = 0;
                            if(tored > 255) tored = 255;
                            int togreen = (int) (Math.round(component.getColourToGreen().getValue(step) * 255));
                            if(togreen < 0) togreen = 0;
                            if(togreen > 255) togreen = 255;
                            int toblue = (int) (Math.round(component.getColourToBlue().getValue(step) * 255));
                            if(toblue < 0) toblue = 0;
                            if(toblue > 255) toblue = 255;
                            dustoptions = new Particle.DustTransition(Color.fromRGB(red, green, blue), Color.fromRGB(tored, togreen, toblue), size);
                        }
                        else 
                            dustoptions = new Particle.DustOptions(Color.fromRGB(red, green, blue), size);

                        nloc.getWorld().spawnParticle(component.getParticle(), nloc.getX(), nloc.getY(), nloc.getZ(), (int)(component.getCount().getValue(step)),
                                                      component.getSpreadX().getValue(step), component.getSpreadY().getValue(step), component.getSpreadZ().getValue(step),
                                                      speed, dustoptions);
                    }
                    else if(component.getParticle() == Particle.ITEM_CRACK) {
                        nloc.getWorld().spawnParticle(component.getParticle(), nloc.getX(), nloc.getY(), nloc.getZ(), (int)(component.getCount().getValue(step)),
                                                      component.getSpreadX().getValue(step), component.getSpreadY().getValue(step), component.getSpreadZ().getValue(step),
                                                      speed, new ItemStack(component.getMaterial()));
                    }
                    else if(component.getParticle() == Particle.BLOCK_DUST || component.getParticle() == Particle.BLOCK_CRACK || component.getParticle() == Particle.FALLING_DUST) {
                        nloc.getWorld().spawnParticle(component.getParticle(), nloc.getX(), nloc.getY(), nloc.getZ(), (int)(component.getCount().getValue(step)),
                                                      component.getSpreadX().getValue(step), component.getSpreadY().getValue(step), component.getSpreadZ().getValue(step),
                                                      speed, component.getMaterial().createBlockData());
                    }
                    else {
                        nloc.getWorld().spawnParticle(component.getParticle(), nloc.getX(), nloc.getY(), nloc.getZ(), (int)(component.getCount().getValue(step)),
                                                      component.getSpreadX().getValue(step), component.getSpreadY().getValue(step), component.getSpreadZ().getValue(step), speed);
                    }
                }
            }
        }
        
        return hasStep(step + 1);
    }

    private List<Player> getNearbyPlayers(Location location)
    {
        List<Player> ret = new ArrayList<>();
        List<Player> playersInWorld = location.getWorld().getPlayers();
        for(Player player: playersInWorld) {
            if(player.getLocation().distance(location) < 50) { // TODO: Does that value make sense? Should it be configurable?
                ret.add(player);
            }
        }
        return ret;
    }

    private static Vector transform(double yaw, double pitch, Vector vec) {
        final double ryaw = Math.toRadians(yaw);
        final double rpitch = -Math.toRadians(pitch);
        
        final double cosYaw = Math.cos(ryaw);
        final double sinYaw = Math.sin(ryaw);
        final double cosPitch = Math.cos(rpitch);
        final double sinPitch = Math.sin(rpitch);
        
        final Vector left = new Vector(cosYaw, 0, sinYaw);
        final Vector up = new Vector(-sinYaw * -sinPitch, cosPitch, cosYaw * -sinPitch);
        final Vector forward = new Vector(-sinYaw * cosPitch, sinPitch, cosYaw * cosPitch);
        
        return left.multiply(vec.getX())
            .add(up.multiply(vec.getY()))
            .add(forward.multiply(vec.getZ()));
    }

    public List<String> getInfo() {
        List<String> ret = getInfoBase();
        ret.add("Length: " + stepsLoop);
        ret.add("Repeat: " + repeatCount);
        for(ParticleEffectComponent component: components) {
            ret.add("* Component:");
            ret.addAll(component.getInfo());
        }
        return ret;
    }

    public String getType() {
        return "Particle";
    }
    
    public final int getStepsLoop() {
        return this.stepsLoop;
    }

    public final void setStepsLoop(final int argStepsLoop) {
        this.stepsLoop = argStepsLoop;
    }

    public final int getRepeatCount() {
        return this.repeatCount;
    }

    public final void setRepeatCount(final int argRepeatCount) {
        this.repeatCount = argRepeatCount;
    }

    public final List<ParticleEffectComponent> getComponents() {
        if(components == null) components = new ArrayList<>();
        return components;
    }

    public final void addComponent(ParticleEffectComponent component) {
        if(components == null) components = new ArrayList<>();
        components.add(component);
    }

    public final void removeComponent(int index) {
        index--;
        if(index >= components.size() || index < 0) {
            throw new RuntimeException("No component " + index + "!");
        }
        components.remove(index);
    }
    
    public final void setRepeatOffset(final int argRepeatOffset) {
        repeatOffset = argRepeatOffset;
    }

    public final int getRepeatOffset() {
        return repeatOffset;
    }
}
