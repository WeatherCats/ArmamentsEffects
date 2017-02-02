package org.cubeville.effects.effects;

import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Horse.Color;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Rabbit;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Slime;
import org.bukkit.entity.Tameable;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Villager.Profession;
import org.bukkit.event.block.Action;
import org.cubeville.effects.event.InteractEvent;

public class AE_EffectSpawn extends AE_EffectParent {

    @Override
    public void run(InteractEvent event) {
        Player player = event.getPlayer();
        Location location = event.getClickedLocation().add(0, 1, 0);
        World world = location.getWorld();
        String[] args = event.getArgs();

        if (args.length == 0 || args.length > 4)
            return;
    	if (args.length > 2 && args[0].equalsIgnoreCase("horse")) {
    		Horse horse = (Horse) world.spawnEntity(location, EntityType.HORSE);
    		if (args.length >= 2) {
    			try {
    				horse.setVariant(Horse.Variant.valueOf(args[1].toUpperCase()));
    			}catch (IllegalArgumentException e) {
    				player.sendMessage("INVALID HORSE VARIANT");
    			}
    		}
    		if (args.length >= 3) {
    			try {
    				horse.setColor(Color.valueOf(args[2].toUpperCase()));
    			} catch (IllegalArgumentException e) {
    				player.sendMessage("INVALID HORSE COLOR");
    			}
    		}
    		if (args.length == 4) {
    			try {
    				horse.setStyle(Horse.Style.valueOf(args[3].toUpperCase()));
    			} catch (IllegalArgumentException e) {
    				player.sendMessage("INVALID HORSE STYLE");
    			}
    		}
    		return;
    	}
    	
    	if (args.length > 2)
    		return;
    			
        if ((event.getAction() == Action.RIGHT_CLICK_AIR) || (event.getAction() == Action.LEFT_CLICK_AIR)) {
            event.getRawEvent().setCancelled(true);
        	return;
        }

        String mob = args[0].toUpperCase();
        if (args[0].equalsIgnoreCase("creeper")) {
                Creeper creeper = (Creeper) world.spawnEntity(location, EntityType.CREEPER);
                if (args[1].equalsIgnoreCase("charged"))
                	creeper.setPowered(true);
                return;
        } else if (args[0].equalsIgnoreCase("rabbit")) {
            Rabbit rabbit = (Rabbit) world.spawnEntity(location, EntityType.RABBIT);
            if (args.length != 2)
            	return;
            try {
            	rabbit.setRabbitType(Rabbit.Type.valueOf(args[1].toUpperCase()));
            } catch (IllegalArgumentException e) {
            	player.sendMessage("INVALID RABBIT TYPE");
            }
        } else if (args[0].equalsIgnoreCase("sheep")) {
        	Sheep sheep = (Sheep) world.spawnEntity(location, EntityType.SHEEP);
            if (args.length != 2)
            	return;
            try {
            	sheep.setColor(DyeColor.valueOf(args[1].toUpperCase()));
            } catch (IllegalArgumentException e) {
            	player.sendMessage("INVALID SHEEP COLOR");
            }
        } else if (args[0].equalsIgnoreCase("villager")){
        	Villager villager = (Villager) world.spawnEntity(location, EntityType.VILLAGER);
        	try {
        		villager.setProfession(Profession.valueOf(args[1].toUpperCase()));
        	} catch (IllegalArgumentException e) {
        		player.sendMessage("INVALID VILLAGER PROFESSION");
        	}
        } else if (args[0].equalsIgnoreCase("slime")) {
        	Slime slime = (Slime) world.spawnEntity(location, EntityType.SLIME);
        	try {
        		int i = Integer.valueOf(args[1]);
        		if (i>20) {
            		player.sendMessage("SLIME CANNOT BE LARGER THAN 20");
        			return;
        		} else
        			slime.setSize(i);
        	} catch (NumberFormatException e) {
        		player.sendMessage("SLIME SIZE NEEDS TO BE A NUMBER");
        	}
        	
        } else {
            
            try {
                EntityType.valueOf(mob);
            } catch (IllegalArgumentException e) {
                return;
            }
            EntityType type = EntityType.valueOf(mob);
    
            if (type == null || !type.isSpawnable())
                return;
    
            if (Tameable.class.isAssignableFrom(type.getEntityClass())) {
                Tameable entity = (Tameable) world.spawnEntity(location, type);
                entity.setOwner(player);
            } else if (LivingEntity.class.isAssignableFrom(type.getEntityClass())) {
                world.spawnEntity(location, type);
            } else return;
    
            return;
        }
    }
}
