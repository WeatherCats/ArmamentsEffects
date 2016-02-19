package kipperorigin.armamentseffects.effects;

import kipperorigin.armamentseffects.event.AE_DamageEvent;

import org.bukkit.entity.Player;

public class AE_EffectUnbreakable extends AE_EffectParent{

    @Override
    public void run(AE_DamageEvent event) {
        Player player = event.getPlayer();
        if (!(event.getVictim() instanceof Player)) 
            return;
        short x = (short) (0 - player.getItemInHand().getType().getMaxDurability());
        player.getItemInHand().setDurability(x);
    }
}
