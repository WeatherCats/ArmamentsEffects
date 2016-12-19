package kipperorigin.armamentseffects.commands;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.entity.Player;

import org.cubeville.commons.commands.Command;
import org.cubeville.commons.commands.CommandExecutionException;
import org.cubeville.commons.commands.CommandResponse;

import kipperorigin.armamentseffects.managers.Effect;
import kipperorigin.armamentseffects.managers.EffectManager;
import kipperorigin.armamentseffects.registry.Registry;

public class EffectRemove extends Command
{
    public EffectRemove() {
        super("effect remove");
        addBaseParameter(new CommandParameterEffect(Effect.class));
    }

    public CommandResponse execute(Player player, Set<String> flags, Map<String, Object> parameters, List<Object> baseParameters) throws CommandExecutionException {
        Effect effect = (Effect) baseParameters.get(0);
        if(Registry.getInstance().isEffectInUse(effect)) throw new CommandExecutionException("Effect is in use, removal not possible.");
        EffectManager.getInstance().removeEffect(effect);
        return null;
    }
}
