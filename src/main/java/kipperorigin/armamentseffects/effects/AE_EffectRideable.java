package kipperorigin.armamentseffects.effects;

import kipperorigin.armamentseffects.event.AE_ProjectileEvent;
import kipperorigin.armamentseffects.event.AE_ProjectileHitEvent;

public class AE_EffectRideable extends AE_EffectParent {

    
    @Override
    public void run(final AE_ProjectileEvent event) {
        event.getProjectile().setPassenger(event.getPlayer());
    }
    
    @Override
    public void run(final AE_ProjectileHitEvent event) {
        event.getProjectile().eject();
    }
}
