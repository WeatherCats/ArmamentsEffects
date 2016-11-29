package kipperorigin.armamentseffects.commands;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import com.comphenix.protocol.wrappers.EnumWrappers.Particle;

import org.cubeville.commons.commands.Command;
import org.cubeville.commons.commands.CommandParameterDouble;
import org.cubeville.commons.commands.CommandParameterEnchantment;
import org.cubeville.commons.commands.CommandParameterEnum;
import org.cubeville.commons.commands.CommandParameterEnumeratedStringList;
import org.cubeville.commons.commands.CommandParameterInteger;
import org.cubeville.commons.commands.CommandParameterPotionEffectType;

public class CreateParticleCommand extends Command
{
    public CreateParticleCommand() {
	super("create particle");
	//addParameter("duration", false, new CommandParameterInteger());
	//addParameter("particle", false, new CommandParameterEnum(Particle.class));
	//addTextParameter(new CommandParameterPotionEffectType());
	//addTextParameter(new CommandParameterEnchantment());
        Set<String> poss = new HashSet<>();
        poss.add("eins");
        poss.add("zwei");
        poss.add("drei");
        addBaseParameter(new CommandParameterEnumeratedStringList(poss));
    }

    public void execute(Player player, Set<String> flags, Map<String, Object> parameters, List<Object> baseParameters) {
	//player.sendMessage("Create particle effect with particle = " + (Particle) parameters.get("particle"));
	//PotionEffectType x = (PotionEffectType) textParameters.get(0);
	player.sendMessage("Enchantment is ");
    }
}
