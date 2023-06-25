package org.cubeville.effects.managers;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Frog;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.List;
import java.util.Map;

public class SpawnFrogWithLivingEntityEffect extends EffectWithLivingEntity {

    private Frog.Variant varient;

    public SpawnFrogWithLivingEntityEffect(String name, Frog.Variant varient) {
        setName(name);
        this.varient = varient;
    }

    public SpawnFrogWithLivingEntityEffect(Map<String, Object> config) {
        String var = (String) config.get("varient");
        if(var.equalsIgnoreCase("cold")) {
            varient = Frog.Variant.COLD;
        } else if(var.equalsIgnoreCase("temperate")) {
            varient = Frog.Variant.TEMPERATE;
        } else if(var.equalsIgnoreCase("warm")) {
            varient = Frog.Variant.WARM;
        }
        setName((String) config.get("name"));
    }

    public Map<String, Object> serialize() {
        Map<String, Object> ret = getSerializationBase();
        ret.put("varient", varient.getKey());
        return ret;
    }

    public void play(LivingEntity entity, Event event) {
        if(event instanceof PlayerInteractEvent) {
            System.out.println("Spawning frog varient " + varient);
            if(entity instanceof Player) {
                Location location = entity.getTargetBlock(null, 5).getLocation().add(0.5, 1, 0.5);
                Frog frog = (Frog) entity.getWorld().spawnEntity(location, EntityType.FROG);
                frog.setVariant(varient);
                if(location.getWorld().getLivingEntities().contains(frog)) {
                    ((PlayerInteractEvent)event).getItem().setAmount(((PlayerInteractEvent)event).getItem().getAmount() - 1);
                } else {
                    entity.sendMessage(ChatColor.RED + "Unable to spawn frog here! Are you allowed spawn things here?");
                }
            }
        }
    }

    public List<String> getInfo(boolean detailed) {
        List<String> ret = getInfoBase();
        ret.add("Varient: " + varient);
        return ret;
    }

    public String getType() {
        return "Varient";
    }
}
