package kipperorigin.armamentseffects.commands;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.entity.Player;

import org.cubeville.commons.commands.Command;
import org.cubeville.commons.commands.CommandExecutionException;
import org.cubeville.commons.commands.CommandResponse;

import kipperorigin.armamentseffects.hooks.InteractHookPlayerLocation;
import kipperorigin.armamentseffects.managers.Effect;
import kipperorigin.armamentseffects.managers.EffectWithLocation;
import kipperorigin.armamentseffects.registry.Registry;
import kipperorigin.armamentseffects.util.ItemUtil;

public class HookCreateInteractPlayerLocationCommand extends Command
{
    public  HookCreateInteractPlayerLocationCommand() {
        super("hook create interact playerlocation");
        addBaseParameter(new CommandParameterEffect(EffectWithLocation.class));
        addFlag("fixedpitch");
    }

    public CommandResponse execute(Player player, Set<String> flags, Map<String, Object> parameters, List<Object> baseParameters) throws CommandExecutionException {
        String itemName = ItemUtil.safeGetItemInMainHandName(player);
        Effect effect = (Effect) baseParameters.get(0);
        boolean fixedPitch = flags.contains("fixedpitch");
        Registry.getInstance().registerEvent(itemName, new InteractHookPlayerLocation(effect, fixedPitch));
        CommandUtil.saveConfig();
        return new CommandResponse("Hook created.");
    }
}

    
