package kipperorigin.armamentseffects.commands;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.entity.Player;

import org.cubeville.commons.commands.Command;
import org.cubeville.commons.commands.CommandExecutionException;
import org.cubeville.commons.commands.CommandResponse;

import kipperorigin.armamentseffects.managers.ParticleEffect;
import kipperorigin.armamentseffects.managers.ParticleEffectComponent;

public class EffectModifyParticleCommand extends Command {

    public EffectModifyParticleCommand() {
        super("effect modify");
        addBaseParameter(new CommandParameterEffect(ParticleEffect.class));
        ParticleCommandHelper.addCommandParameters(this);
    }

    public CommandResponse execute(Player player, Set<String> flags, Map<String, Object> parameters, List<Object> baseParameters)
    throws CommandExecutionException {
        if(parameters.size() == 0) throw new CommandExecutionException("No modification parameters.");
        ParticleEffect effect = (ParticleEffect) baseParameters.get(0);
        ParticleCommandHelper.setEffectValues(effect, parameters);
        ParticleEffectComponent component = effect.getComponents().get(0);
        ParticleCommandHelper.setComponentValues(component, parameters);
        return new CommandResponse("Effect component successfully modified.");
    }
}
