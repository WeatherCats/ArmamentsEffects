package kipperorigin.armamentseffects.commands;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.entity.Player;

import org.cubeville.commons.commands.Command;
import org.cubeville.commons.commands.CommandExecutionException;
import org.cubeville.commons.commands.CommandResponse;

import kipperorigin.armamentseffects.hooks.InteractHookParticlePlayer;
import kipperorigin.armamentseffects.managers.Effect;
import kipperorigin.armamentseffects.managers.ParticleEffect;
import kipperorigin.armamentseffects.registry.Registry;
import kipperorigin.armamentseffects.util.ItemUtil;

public class HookCreateInteractParticlePlayerCommand extends Command
{
    public HookCreateInteractParticlePlayerCommand() {
        super("hook create interact particleplayer");
        addBaseParameter(new CommandParameterEffect(ParticleEffect.class));
    }

    public CommandResponse execute(Player player, Set<String> flags, Map<String, Object> parameters, List<Object> baseParameters) throws CommandExecutionException {
        String itemName = ItemUtil.getItemInMainHandName(player);
        if(itemName == null) throw new CommandExecutionException("No named item in hand!");
        Effect effect = (Effect) baseParameters.get(0);
        Registry.getInstance().registerEvent(itemName, new InteractHookParticlePlayer(effect.getName(), 0, 1, 1, false, 0));
        return new CommandResponse("Hook created.");
    }
}
