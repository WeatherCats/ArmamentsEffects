package kipperorigin.armamentseffects.effects;

import kipperorigin.armamentseffects.event.AE_DamageEvent;
import kipperorigin.armamentseffects.resources.AE_RemoveItem;

public class AE_EffectInstakill extends AE_EffectParent {

	AE_RemoveItem AE_RI = new AE_RemoveItem();

	@Override
	public void run(AE_DamageEvent event) {
		event.getRawEvent().setDamage(9001d);
		AE_RI.removeItem(event.getPlayer());
		return;
	}

}
