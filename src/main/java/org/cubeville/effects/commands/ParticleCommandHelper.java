package org.cubeville.effects.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.util.Vector;
import org.cubeville.commons.commands.Command;
import org.cubeville.commons.commands.CommandParameterEnum;
import org.cubeville.commons.commands.CommandParameterInteger;
import org.cubeville.commons.commands.CommandParameterList;
import org.cubeville.commons.commands.CommandParameterListDouble;
import org.cubeville.commons.commands.CommandParameterListVector;
import org.cubeville.commons.commands.CommandParameterType;
import org.cubeville.commons.commands.CommandParameterVector;
import org.cubeville.effects.managers.ParticleEffect;
import org.cubeville.effects.managers.ParticleEffectComponent;
import org.cubeville.effects.managers.sources.coordinate.CircleCoordinateSource;
import org.cubeville.effects.managers.sources.coordinate.ConstantCoordinateSource;
import org.cubeville.effects.managers.sources.coordinate.RandomCoordinateSource;
import org.cubeville.effects.managers.sources.value.ValueSource;

import com.comphenix.protocol.wrappers.EnumWrappers.Particle;

public class ParticleCommandHelper
{
    public static void addCommandParameters(Command command) {
        command.addParameter("particle", true, new CommandParameterEnum(Particle.class));
        command.addParameter("constantsource", true, new CommandParameterListVector());
        command.addParameter("circlesource", true, new CommandParameterListDouble(2));
        command.addParameter("circlesourcexz", true, new CommandParameterListDouble(2));
        {
            List<CommandParameterType> pl = new ArrayList<>();
            pl.add(new CommandParameterVector());
            pl.add(new CommandParameterVector());
            pl.add(new CommandParameterInteger(1, 100));
            command.addParameter("randomsource", true, new CommandParameterList(pl));
        }
        command.addParameter("duration", true, new CommandParameterInteger());
        command.addParameter("repeat", true, new CommandParameterInteger());
        command.addParameter("repeatoffset", true, new CommandParameterInteger());        
        command.addParameter("spreadx", true, new CommandParameterValueSource());
        command.addParameter("spready", true, new CommandParameterValueSource());
        command.addParameter("spreadz", true, new CommandParameterValueSource());
        command.addParameter("count", true, new CommandParameterValueSource());
    }
    
    public static void setEffectValues(ParticleEffect effect, Map<String, Object> parameters) {
        if(parameters.containsKey("duration")) effect.setStepsLoop((int) parameters.get("duration"));
        if(parameters.containsKey("repeat")) effect.setRepeatCount((int) parameters.get("repeat"));
        if(parameters.containsKey("repeatoffset")) effect.setRepeatOffset((int) parameters.get("repeatoffset"));
    }
    
    public static void setComponentValues(ParticleEffectComponent component, Map<String, Object> parameters) {
        int numberOfSources = 0;
        if(parameters.containsKey("constantsource")) numberOfSources++;
        if(parameters.containsKey("circlesource")) numberOfSources++;
        if(parameters.containsKey("randomsource")) numberOfSources++;
        if(parameters.containsKey("circlesourcexz")) numberOfSources++;
        if(numberOfSources > 1) throw new IllegalArgumentException("Only one coordinate source is possible.");

        if(parameters.containsKey("constantsource")) {
            List<Vector> coords = (List<Vector>) parameters.get("constantsource");
            component.setCoordinates(new ConstantCoordinateSource(coords));
        }

        if(parameters.containsKey("circlesource") || parameters.containsKey("circlesourcexz")) {
            List<Double> pars = (List<Double>) parameters.get("circlesource");
            if(pars == null) pars = (List<Double>) parameters.get("circlesourcexz");
            int startAngle = 0;
            int endAngle = 360 - pars.get(1).intValue();
            component.setCoordinates(new CircleCoordinateSource(pars.get(0), pars.get(1).intValue(), 0, endAngle, parameters.containsKey("circlesourcexz")));
        }

        if(parameters.containsKey("randomsource")) {
            List<Object> pars = (List<Object>) parameters.get("randomsource");
            Vector vec1 = (Vector) pars.get(0);
            Vector vec2 = (Vector) pars.get(1);
            int count = (Integer) pars.get(2);
            component.setCoordinates(new RandomCoordinateSource(count, vec1, vec2));
        }
        
        if(parameters.containsKey("particle")) component.setParticle((Particle) parameters.get("particle"));

        if(parameters.containsKey("spreadx")) component.setSpreadX((ValueSource) parameters.get("spreadx"));
        if(parameters.containsKey("spready")) component.setSpreadY((ValueSource) parameters.get("spready"));
        if(parameters.containsKey("spreadz")) component.setSpreadZ((ValueSource) parameters.get("spreadz"));
        if(parameters.containsKey("count")) component.setCount((ValueSource) parameters.get("count"));
    }
}
