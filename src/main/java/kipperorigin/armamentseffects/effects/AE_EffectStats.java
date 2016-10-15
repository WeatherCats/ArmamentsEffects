package kipperorigin.armamentseffects.effects;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

import kipperorigin.armamentseffects.event.AE_DamageEvent;
import kipperorigin.armamentseffects.resources.AE_Color;

public class AE_EffectStats extends AE_EffectParent {
	
	AE_Color color = new AE_Color();
	
    @Override
    public void run(AE_DamageEvent event) {
    	Player player = event.getPlayer(); 
    	player.sendMessage(color.color("&6-----Stats Start-----"));
    	player.sendMessage(color.color("&eOriginal Damage = " + event.getRawEvent().getDamage()));
    	player.sendMessage(color.color("&e__________"));
    	player.sendMessage(color.color("&eAbsorbtion&f = " + event.getRawEvent().getDamage(EntityDamageEvent.DamageModifier.ABSORPTION)));
    	player.sendMessage(color.color("&eArmor&f = " + event.getRawEvent().getDamage(EntityDamageEvent.DamageModifier.ARMOR)));
    	player.sendMessage(color.color("&eMagic&f = " + event.getRawEvent().getDamage(EntityDamageEvent.DamageModifier.MAGIC)));
    	player.sendMessage(color.color("&eResistance&f = " + event.getRawEvent().getDamage(EntityDamageEvent.DamageModifier.RESISTANCE)));
    	player.sendMessage(color.color("&e__________"));
    	player.sendMessage(color.color("&eFinal Damage = " + event.getRawEvent().getFinalDamage()));
    	player.sendMessage(color.color("&6-----Stats End-----"));
    }
}
