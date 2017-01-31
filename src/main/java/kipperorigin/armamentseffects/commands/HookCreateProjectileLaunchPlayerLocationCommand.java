package kipperorigin.armamentseffects.commands;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.entity.Player;

import org.cubeville.commons.commands.Command;
import org.cubeville.commons.commands.CommandResponse;
import org.cubeville.commons.commands.CommandExecutionException;

import kipperorigin.armamentseffects.hooks.InteractHookPlayerLocation;
import kipperorigin.armamentseffects.hooks.ProjectileLaunchHookPlayerLocation;
import kipperorigin.armamentseffects.managers.Effect;
import kipperorigin.armamentseffects.managers.EffectWithLocation;
import kipperorigin.armamentseffects.registry.Registry;
import kipperorigin.armamentseffects.util.ItemUtil;

public class HookCreateProjectileLaunchPlayerLocationCommand extends Command
{
    public HookCreateProjectileLaunchPlayerLocationCommand() {
        super("hook create projectilelaunch playerlocation");
        addBaseParameter(new CommandParameterEffect(EffectWithLocation.class));
    }

    public CommandResponse execute(Player player, Set<String> flags, Map<String, Object> parameters, List<Object> baseParameters) throws CommandExecutionException {
        String itemName = ItemUtil.getItemInMainHandName(player);
        if(itemName == null) {
            throw new CommandExecutionException("No named item in hand!");
        }

        Effect effect = (Effect) baseParameters.get(0);
        Registry.getInstance().registerEvent(itemName, new ProjectileLaunchHookPlayerLocation((Effect) baseParameters.get(0)));
        CommandUtil.saveConfig();
        return new CommandResponse("Hook created.");
    }
}
