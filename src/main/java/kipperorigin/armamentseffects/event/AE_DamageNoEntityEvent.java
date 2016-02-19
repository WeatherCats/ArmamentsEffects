package kipperorigin.armamentseffects.event;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public class AE_DamageNoEntityEvent extends AE_Event {

    private final Player player;
    private final EntityDamageEvent event;

    public AE_DamageNoEntityEvent(Player player, EntityDamageEvent event) {
        super(player);

        this.player = player;
        this.event = event;
    }

    public Player getVictim() {
        return player;
    }

    public EntityDamageEvent getRawEvent() {
        return event;
    }
    
    public DamageCause getDamageType() {
        return event.getCause();
    }
}
