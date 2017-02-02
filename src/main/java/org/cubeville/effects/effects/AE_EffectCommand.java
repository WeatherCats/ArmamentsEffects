package org.cubeville.effects.effects;

import org.bukkit.entity.Player;
import org.cubeville.effects.event.InteractEvent;

public class AE_EffectCommand extends AE_EffectParent {

    @Override
    public void run(final InteractEvent event) {
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

