package kipperorigin.armamentseffects.commands;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

import org.cubeville.commons.commands.Command;
import org.cubeville.commons.commands.CommandExecutionException;
import org.cubeville.commons.commands.CommandParameterEnum;
import org.cubeville.commons.commands.CommandParameterString;
import org.cubeville.commons.commands.CommandResponse;

import kipperorigin.armamentseffects.managers.Effect;
import kipperorigin.armamentseffects.managers.EffectManager;
import kipperorigin.armamentseffects.managers.SoundEffect;

public class EffectCreateSoundCommand extends Command
{
    public EffectCreateSoundCommand() {
        super("effect create sound");
        addBaseParameter(new CommandParameterString());
        addBaseParameter(new CommandParameterEnum(Sound.class));
    }

    public CommandResponse execute(Player player, Set<String> flags, Map<String, Object> parameters, List<Object> baseParameters) throws CommandExecutionException {
        String name = (String) baseParameters.get(0);
        if(EffectManager.getInstance().getEffectByName(name) != null) {
            throw new CommandExecutionException("Effect with name " + name + " already exists!");
        };
        SoundEffect effect = new SoundEffect(name, (Sound) baseParameters.get(1));
        EffectManager.getInstance().addEffect(effect);
        CommandUtil.saveConfig();
       return null;
    }
}
