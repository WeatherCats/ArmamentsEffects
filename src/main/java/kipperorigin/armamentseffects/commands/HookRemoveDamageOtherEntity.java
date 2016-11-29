package kipperorigin.armamentseffects.commands;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.entity.Player;

import org.cubeville.commons.commands.Command;
import org.cubeville.commons.commands.CommandExecutionException;
import org.cubeville.commons.commands.CommandParameterInteger;

import kipperorigin.armamentseffects.registry.Registry;
import kipperorigin.armamentseffects.util.ItemUtil;

public class HookRemoveDamageOtherEntity extends Command
{
    public HookRemoveDamageOtherEntity() {
        super("hook remove damageotherentity");
        addBaseParameter(new CommandParameterInteger());
    }

    public void execute(Player player, Set<String> flags, Map<String, Object> parameters, List<Object> baseParameters) throws CommandExecutionException {
        String itemName = ItemUtil.safeGetItemInMainHandName(player);
        Registry.getInstance().deregisterDamageOtherEntityEvent(itemName, (int) baseParameters.get(0));
    }
    
}
