package kipperorigin.armamentseffects.effects;

import kipperorigin.armamentseffects.AE_RemoveItem;
import kipperorigin.armamentseffects.event.AE_DamageEvent;

public class AE_EffectInstakill extends AE_EffectParent {

	AE_RemoveItem AE_RI = new AE_RemoveItem();

	@Override
	public void run(AE_DamageEvent event) {
		event.getRawEvent().setDamage(9001);
		AE_RI.removeItem(event.getPlayer());
		return;
	}

}
