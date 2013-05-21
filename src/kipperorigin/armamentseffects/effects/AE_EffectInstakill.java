package kipperorigin.armamentseffects.effects;

import kipperorigin.armamentseffects.event.AE_DamageEvent;

public class AE_EffectInstakill extends AE_EffectParent {
	public void run(AE_DamageEvent event) {
		event.getRawEvent().setDamage(9001);
	}
}