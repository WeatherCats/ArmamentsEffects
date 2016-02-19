package kipperorigin.armamentseffects.effects;

import java.util.ArrayList;
import java.util.List;

import kipperorigin.armamentseffects.event.AE_DamageEvent;
import kipperorigin.armamentseffects.resources.AE_RemoveItem;

import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class AE_EffectStun extends AE_EffectParent {

    AE_RemoveItem AE_RI = new AE_RemoveItem();

    @Override
    public void run(AE_DamageEvent event) {
        LivingEntity target = event.getVictim();
        String[] args = event.getArgs();

        int length = 5;
        if (args.length >= 1) {
            try {
                length = Integer.parseInt(args[0]);
            } catch (NumberFormatException ignore) {
            }
        }

        // 20 ticks per sec
        length *= 20;

        List<PotionEffect> stuns = new ArrayList<PotionEffect>();
        stuns.add(new PotionEffect(PotionEffectType.SLOW, length, 5));
        stuns.add(new PotionEffect(PotionEffectType.JUMP, length, 127));
        stuns.add(new PotionEffect(PotionEffectType.SLOW_DIGGING, length, 127));

        target.addPotionEffects(stuns);
        AE_RI.removeItem(event.getPlayer());
    }

}
