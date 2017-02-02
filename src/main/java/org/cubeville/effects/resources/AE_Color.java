package org.cubeville.effects.resources;

import org.bukkit.ChatColor;

public class AE_Color {

    public String color(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
    
}
