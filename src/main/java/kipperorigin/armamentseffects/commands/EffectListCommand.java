package kipperorigin.armamentseffects.commands;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.entity.Player;
import org.cubeville.commons.commands.Command;
import org.cubeville.commons.commands.CommandResponse;
import kipperorigin.armamentseffects.managers.EffectManager;
import kipperorigin.armamentseffects.managers.Effect;

public class EffectListCommand extends Command
{
    public EffectListCommand() {
	super("effect list");
    }

    public CommandResponse execute(Player player, Set<String> flags, Map<String, Object> parameters, List<Object> baseParameters) {
        player.sendMessage("--------------- Effect List ---------------");
        List<String> effects = EffectManager.getInstance().getEffectList();
        Collections.sort(effects);
        for(String e: effects) player.sendMessage(e);
        return null;
    }

}
