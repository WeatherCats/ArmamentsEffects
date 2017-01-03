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

public class HookPermissionSetCommand extends Command
{
    public HookPermissionSetCommand() {
        super("hook permission set");
        addBaseParameter(new CommandParameterEventClass());
        addBaseParameter(new CommandParameterPermission());
    }

    public CommandResponse execute(Player player, Set<String> flags, Map<String, Object> parameters, List<Object> baseParameters) throws CommandExecutionException {
        String itemName = ItemUtil.safeGetItemInMainHandName(player);
        String eventClass = (String) baseParameters.get(0);
        String permission = (String) baseParameters.get(1);
        Registry.getInstance().setPermission(itemName, eventClass, permission);
        return null;
    }
}
