package kipperorigin.armamentseffects.commands;

import java.util.Map;

import org.bukkit.util.Vector;

import com.comphenix.protocol.wrappers.EnumWrappers.Particle;

import kipperorigin.armamentseffects.managers.ParticleEffectComponent;

public class ParticleCommandHelper
{
    public static void setComponentValues(ParticleEffectComponent component, Map<String, Object> parameters) {
        if(parameters.containsKey("particle")) component.setParticle((Particle) parameters.get("particle"));
        if(parameters.containsKey("count")) component.setCount((int) parameters.get("count"));
        if(parameters.containsKey("spread")) component.setSpread((Vector) parameters.get("spread"));
    }
}
