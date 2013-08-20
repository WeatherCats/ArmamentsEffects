package kipperorigin.armamentseffects.effects;

import kipperorigin.armamentseffects.event.AE_DamageEvent;

import org.bukkit.potion.PotionEffectType;

public class AE_EffectRemoveAilment extends AE_EffectParent {

	@Override
	public void run(AE_DamageEvent event) {
		event.getVictim().removePotionEffect(PotionEffectType.BLINDNESS);
		event.getVictim().removePotionEffect(PotionEffectType.CONFUSION);
		event.getVictim().removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
		event.getVictim().removePotionEffect(PotionEffectType.FAST_DIGGING);
		event.getVictim().removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
		event.getVictim().removePotionEffect(PotionEffectType.HARM);
		event.getVictim().removePotionEffect(PotionEffectType.HEAL);
		event.getVictim().removePotionEffect(PotionEffectType.HUNGER);
		event.getVictim().removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
		event.getVictim().removePotionEffect(PotionEffectType.INVISIBILITY);
		event.getVictim().removePotionEffect(PotionEffectType.JUMP);
		event.getVictim().removePotionEffect(PotionEffectType.NIGHT_VISION);
		event.getVictim().removePotionEffect(PotionEffectType.POISON);
		event.getVictim().removePotionEffect(PotionEffectType.REGENERATION);
		event.getVictim().removePotionEffect(PotionEffectType.SLOW);
		event.getVictim().removePotionEffect(PotionEffectType.SLOW_DIGGING);
		event.getVictim().removePotionEffect(PotionEffectType.SPEED);
		event.getVictim().removePotionEffect(PotionEffectType.WATER_BREATHING);
		event.getVictim().removePotionEffect(PotionEffectType.WEAKNESS);
		event.getVictim().removePotionEffect(PotionEffectType.WITHER);
	}
}
