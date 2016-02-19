package kipperorigin.armamentseffects.effects;

import kipperorigin.armamentseffects.event.AE_ProjectileEvent;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Fish;
import org.bukkit.entity.LargeFireball;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.SmallFireball;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.ThrownExpBottle;
import org.bukkit.entity.WitherSkull;

@SuppressWarnings("deprecation")
public class AE_EffectShoot extends AE_EffectParent {

    @Override
    public void run(AE_ProjectileEvent event) {
        Player player = event.getPlayer();
        Projectile projectile = event.getProjectile();
        String args[] = event.getArgs();
        Location location = event.getProjectile().getLocation();
        World world = location.getWorld();
        if (projectile instanceof Arrow) {
            event.cancel();
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("snowball"))
                    player.launchProjectile(Snowball.class).setVelocity(projectile.getVelocity());
                else if (args[0].equalsIgnoreCase("fireball"))
                    player.launchProjectile(Fireball.class).setVelocity(projectile.getVelocity());
                else if (args[0].equalsIgnoreCase("fireshot"))
                    player.launchProjectile(SmallFireball.class).setVelocity(projectile.getVelocity());
                else if (args[0].equalsIgnoreCase("fireblast"))
                    player.launchProjectile(LargeFireball.class).setVelocity(projectile.getVelocity());
                else if (args[0].equalsIgnoreCase("arrow"))
                    player.launchProjectile(Arrow.class).setVelocity(projectile.getVelocity());
                else if (args[0].equalsIgnoreCase("withershot"))
                    player.launchProjectile(WitherSkull.class).setVelocity(projectile.getVelocity());
                else if (args[0].equalsIgnoreCase("egg"))
                    player.launchProjectile(Egg.class).setVelocity(projectile.getVelocity());
                else if (args[0].equalsIgnoreCase("fish"))
                    player.launchProjectile(Fish.class).setVelocity(projectile.getVelocity());
                else if (args[0].equalsIgnoreCase("xpbottle"))
                    player.launchProjectile(ThrownExpBottle.class).setVelocity(projectile.getVelocity());
                else if (args[0].equalsIgnoreCase("charged_creeper")) {
                    Creeper creeper = (Creeper) world.spawnEntity(location, EntityType.CREEPER);
                    creeper.setPowered(true);
                    creeper.setVelocity(projectile.getVelocity());
                } else {
                    
                    try {
                        EntityType.valueOf(args[0].toUpperCase());
                    } catch  (IllegalArgumentException e) {
                        return;
                    }
                    
                    EntityType type = EntityType.valueOf(args[0].toUpperCase());

                    if (type == null || !type.isSpawnable())
                        return;
                    
                    Entity entity = world.spawnEntity(location, type);
                    entity.setVelocity(projectile.getVelocity());
                    
                    return;
                } 
            } else
                return;
        } else
            return;
    }
}
