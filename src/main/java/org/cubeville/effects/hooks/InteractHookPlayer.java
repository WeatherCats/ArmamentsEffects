package org.cubeville.effects.hooks;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.event.player.PlayerInteractEvent;
import org.cubeville.effects.managers.Effect;
import org.cubeville.effects.managers.EffectWithLivingEntity;

public class InteractHookPlayer implements InteractHook
{
    EffectWithLivingEntity effect;

    public InteractHookPlayer(Effect effect) {
        this.effect = (EffectWithLivingEntity) effect;
    }

    public Map<String, Object> serialize() {
        Map<String, Object> ret = new HashMap<>();
        return ret;
    }

    public String getInfo() {
        return "Player: " + effect.getName();
    }

    public void process(PlayerInteractEvent event) {
        effect.play(event.getPlayer());
    }

    public boolean usesEffect(Effect effect) {
        return effect == this.effect;
    }

    public boolean alwaysActive() {
        return false;
    }

}
