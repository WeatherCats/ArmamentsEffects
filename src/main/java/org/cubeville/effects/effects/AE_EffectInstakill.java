package org.cubeville.effects.effects;

import org.cubeville.effects.event.DamageEvent;

public class AE_EffectInstakill extends AE_EffectParent {

    @Override
    public void run(DamageEvent event) {
        event.getRawEvent().setDamage(9001d);
        return;
    }

}
