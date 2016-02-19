package kipperorigin.armamentseffects.event;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;

public class AE_PlayerMoveEvent extends AE_Event {
    
    private final PlayerMoveEvent event;
    public AE_PlayerMoveEvent(Player player, PlayerMoveEvent event) {
        super (player);
        
        this.event = event;
    }
    
    public PlayerMoveEvent getRawEvent() {
        return event;
    }
}
