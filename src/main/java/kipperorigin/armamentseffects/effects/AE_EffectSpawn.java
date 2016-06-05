package kipperorigin.armamentseffects.effects;

import kipperorigin.armamentseffects.event.AE_InteractEvent;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Tameable;
import org.bukkit.event.block.Action;

public class AE_EffectSpawn extends AE_EffectParent {

    @Override
    public void run(AE_InteractEvent event) {
        Player player = event.getPlayer();
        Location location = event.getClickedLocation().add(0, 1, 0);
        World world = location.getWorld();
        String[] args = event.getArgs();

        if (args.length == 0)
            return;

        
        if ((event.getAction() == Action.RIGHT_CLICK_AIR) || (event.getAction() == Action.LEFT_CLICK_AIR)) {
            event.getRawEvent().setCancelled(true);
        	return;
        }

        String mob = args[0].toUpperCase();
        if (args[0].equalsIgnoreCase("charged_creeper")) {
                Creeper creeper = (Creeper) world.spawnEntity(location, EntityType.CREEPER);
                creeper.setPowered(true);
                return;
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
