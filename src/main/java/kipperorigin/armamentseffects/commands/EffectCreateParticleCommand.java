package kipperorigin.armamentseffects.commands;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.entity.Player;

import com.comphenix.protocol.wrappers.EnumWrappers.Particle;

import org.cubeville.commons.commands.Command;
import org.cubeville.commons.commands.CommandParameterEnum;
import org.cubeville.commons.commands.CommandParameterInteger;
import org.cubeville.commons.commands.CommandParameterString;
import org.cubeville.commons.commands.CommandParameterVector;

import kipperorigin.armamentseffects.managers.ParticleEffect;
import kipperorigin.armamentseffects.managers.EffectManager;
import kipperorigin.armamentseffects.managers.ParticleEffectComponent;

public class EffectCreateParticleCommand extends Command
{
    public EffectCreateParticleCommand() {
        super("effect create particle");
        addBaseParameter(new CommandParameterString());
        addParameter("particle", true, new CommandParameterEnum(Particle.class));
        addParameter("count", true, new CommandParameterInteger());
        addParameter("spread", true, new CommandParameterVector());
    }

    public void execute(Player player, Set<String> flags, Map<String, Object> parameters, List<Object> baseParameters) {
        String name = (String) baseParameters.get(0);
        ParticleEffect effect = new ParticleEffect(name);
        ParticleEffectComponent component = new ParticleEffectComponent();
        effect.addComponent(component);
        ParticleCommandHelper.setComponentValues(component, parameters);
        EffectManager.getInstance().addEffect(effect);
    }
}
