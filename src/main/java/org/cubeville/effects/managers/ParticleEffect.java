package org.cubeville.effects.managers;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;

@SerializableAs("ParticleEffect")
public class ParticleEffect extends EffectWithLocation
{
    private static ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();

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
                    PacketContainer particlePacket = protocolManager.createPacket(PacketType.Play.Server.WORLD_PARTICLES);
                    particlePacket.getParticles().write(0, component.getParticle());
                    particlePacket.getIntegers().write(0, new Double(component.getCount().getValue(step)).intValue());
                    particlePacket.getFloat().write(0, (float) nloc.getX());
                    particlePacket.getFloat().write(1, (float) nloc.getY());
                    particlePacket.getFloat().write(2, (float) nloc.getZ());
                    particlePacket.getFloat().write(3, (float) component.getSpreadX().getValue(step));
                    particlePacket.getFloat().write(4, (float) component.getSpreadY().getValue(step));
                    particlePacket.getFloat().write(5, (float) component.getSpreadZ().getValue(step));
                    particlePacket.getFloat().write(6, 1F);
                    int[] il = {0, 0};
                    il[0] = component.getMaterial().getId();
                    particlePacket.getIntegerArrays().write(0, il);
                    //particlePacket.getBooleans().write(0, true);
                    sendParticlePackets(nloc, particlePacket);
                }
            }
        }
        
        return hasStep(step + 1);
    }

    private void sendParticlePackets(Location location, PacketContainer particlePacket)
    {
        try {
            // TODO: Clone the packet?
            List<Player> players = getNearbyPlayers(location);
            if(players != null && players.size() != 0 && !players.isEmpty()) {
                for(Player player: players) {
                    protocolManager.sendServerPacket(player, particlePacket.deepClone());
                }
            }
        }
        catch(InvocationTargetException e) {
            throw new RuntimeException("Cannot send packet " + particlePacket, e);
        }
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

    public final void setRepeatOffset(final int argRepeatOffset) {
        repeatOffset = argRepeatOffset;
    }

    public final int getRepeatOffset() {
        return repeatOffset;
    }
}
