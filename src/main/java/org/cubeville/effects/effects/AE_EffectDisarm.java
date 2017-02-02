package org.cubeville.effects.effects;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.cubeville.effects.event.DamageEvent;
import org.cubeville.effects.resources.AE_Color;

public class AE_EffectDisarm extends AE_EffectParent {

    AE_Color color = new AE_Color();

    @Override
    public void run(DamageEvent event) {
        if (!(event.getVictim() instanceof Player))
            return;

        Player victim = (Player)event.getVictim();

        ItemStack item = victim.getInventory().getItemInMainHand();
        if (item == null || item.getType() == Material.AIR)
            return;

        PlayerInventory inv = victim.getInventory();
        int oldSlot = inv.getHeldItemSlot();
        int newSlot = inv.firstEmpty();
        inv.setItem(newSlot, item);
        inv.setItem(oldSlot, null);
        victim.sendMessage(color.color("&4You have been disarmed!"));
    }
}
