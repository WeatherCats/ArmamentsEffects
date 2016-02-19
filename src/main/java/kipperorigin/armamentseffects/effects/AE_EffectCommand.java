package kipperorigin.armamentseffects.effects;

import org.bukkit.entity.Player;

import kipperorigin.armamentseffects.event.AE_InteractEvent;

public class AE_EffectCommand extends AE_EffectParent {

    @Override
    public void run(final AE_InteractEvent event) {
        Player player = event.getPlayer();
        StringBuilder builder = new StringBuilder();
        
        for (String string : event.getArgs()) {
            if (builder.length() > 0) {
                builder.append(" ");
            }
            builder.append(string);
        }
        
        String command = builder.toString();
        player.performCommand(command);
    }
}

