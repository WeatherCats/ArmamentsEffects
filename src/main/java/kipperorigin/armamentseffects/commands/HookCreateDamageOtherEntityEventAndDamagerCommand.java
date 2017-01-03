package kipperorigin.armamentseffects.commands;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.entity.Player;

import org.cubeville.commons.commands.Command;
import org.cubeville.commons.commands.CommandExecutionException;
import org.cubeville.commons.commands.CommandResponse;

import kipperorigin.armamentseffects.hooks.DamageOtherEntityHookEventAndDamager;
import kipperorigin.armamentseffects.managers.Effect;
import kipperorigin.armamentseffects.managers.PiercingEffect;
import kipperorigin.armamentseffects.registry.Registry;
import kipperorigin.armamentseffects.util.ItemUtil;

public class HookCreateDamageOtherEntityEventAndDamagerCommand extends Command
{
    public HookCreateDamageOtherEntityEventAndDamagerCommand() {
        super("hook create damage eventanddamager");
        addBaseParameter(new CommandParameterEffect(PiercingEffect.class));
    }

    public CommandResponse execute(Player player, Set<String> flags, Map<String, Object> parameters, List<Object> baseParameters) throws CommandExecutionException {
        String itemName = ItemUtil.safeGetItemInMainHandName(player);
        Effect effect = (Effect) baseParameters.get(0);
        Registry.getInstance().registerEvent(itemName, new DamageOtherEntityHookEventAndDamager(effect));
        return null;
    }
}


    
