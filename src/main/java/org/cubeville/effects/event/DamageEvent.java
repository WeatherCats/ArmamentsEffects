package org.cubeville.effects.event;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class DamageEvent extends Event {

    private final LivingEntity victim;
    private final EntityDamageByEntityEvent event;

    public DamageEvent(Player attacker, LivingEntity victim, EntityDamageByEntityEvent event) {
        super(attacker);

        this.victim = victim;
        this.event = event;
    }

    public LivingEntity getVictim() {
        return victim;
    }

    public EntityDamageByEntityEvent getRawEvent() {
        return event;
    }

}
