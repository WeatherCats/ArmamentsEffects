package kipperorigin.armamentseffects.effects;

import kipperorigin.armamentseffects.event.AE_ProjectileHitEvent;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class AE_EffectTeleport extends AE_EffectParent {

    @Override
    public void run(AE_ProjectileHitEvent event) {
        Player player = event.getPlayer();
        Location loc = event.getLocation();
        player.teleport(loc);
        return;
    }
}
