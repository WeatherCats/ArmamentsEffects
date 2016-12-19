package kipperorigin.armamentseffects.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.entity.Player;

import org.cubeville.commons.commands.Command;
import org.cubeville.commons.commands.CommandExecutionException;
import org.cubeville.commons.commands.CommandParameterListString;
import org.cubeville.commons.commands.CommandParameterString;
import org.cubeville.commons.commands.CommandResponse;

import kipperorigin.armamentseffects.hooks.InteractHookTargetLocation;
import kipperorigin.armamentseffects.managers.Effect;
import kipperorigin.armamentseffects.managers.EffectWithLocation;
import kipperorigin.armamentseffects.registry.Registry;
import kipperorigin.armamentseffects.util.ItemUtil;

public class HookCreateInteractTargetLocationCommand extends Command
{
    public HookCreateInteractTargetLocationCommand() {
        super("hook create interact targetlocation");
        addBaseParameter(new CommandParameterListEffect(EffectWithLocation.class));
        addOptionalBaseParameter(new CommandParameterListEffect(EffectWithLocation.class));
    }

    public CommandResponse execute(Player player, Set<String> flags, Map<String, Object> parameters, List<Object> baseParameters) throws CommandExecutionException {
        List<Effect> noTargetParameters;
        if(baseParameters.size() < 2) {
            noTargetParameters = new ArrayList<>();
        }
        else {
            noTargetParameters = (List<Effect>) baseParameters.get(1);
        }

        String itemName = ItemUtil.safeGetItemInMainHandName(player);        
        Registry.getInstance().registerEvent(itemName, new InteractHookTargetLocation((List<Effect>) baseParameters.get(0), noTargetParameters));
        return null;
    }
}
