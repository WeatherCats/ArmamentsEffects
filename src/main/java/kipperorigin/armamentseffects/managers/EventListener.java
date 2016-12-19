package kipperorigin.armamentseffects.managers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;

import kipperorigin.armamentseffects.hooks.DamageOtherEntityHookCancelEvent;
import kipperorigin.armamentseffects.hooks.DamageOtherEntityHookTargetEntity;
import kipperorigin.armamentseffects.hooks.InteractHookBlockLocation;
import kipperorigin.armamentseffects.hooks.InteractHookCancelEvent;
import kipperorigin.armamentseffects.hooks.InteractHookParticlePlayer;
import kipperorigin.armamentseffects.hooks.InteractHookPlayer;
import kipperorigin.armamentseffects.hooks.InteractHookPlayerLocation;
import kipperorigin.armamentseffects.hooks.InteractHookTargetLocation;
import kipperorigin.armamentseffects.registry.Registry;

public class EventListener implements Listener
{
    private Registry registry;
    
    public EventListener() {
	EffectManager em = EffectManager.getInstance();
	registry = new Registry();
	// {
	//     List<Effect> sparkleStickEffects = new ArrayList<>();
	//     sparkleStickEffects.add(em.getEffectByName("sparkle"));
	//     sparkleStickEffects.add(em.getEffectByName("sparklesound"));
	//     List<Effect> sparkleStickNoTargetEffects = new ArrayList<>();
	//     sparkleStickNoTargetEffects.add(em.getEffectByName("sparklenosound"));
	//     registry.registerInteractEvent("SparkleStick", new InteractHookTargetLocation(sparkleStickEffects, sparkleStickNoTargetEffects));
	//     registry.registerInteractEvent("SparkleStick", new InteractHookCancelEvent());
	//     registry.registerDamageOtherEntityEvent("SparkleStick", new DamageOtherEntityHookCancelEvent());
	// }
	// {
	//     registry.registerInteractEvent("Superball", new InteractHookParticleDirected("jumpy", 0, 2, .5, true, 0));
	//     registry.registerInteractEvent("Superball", new InteractHookCancelEvent());
	// }
	// {
	//     registry.registerInteractEvent("§2Wand", new InteractHookParticleDirected("modifiers", 1.0, 2, .5, false, 0));
	//     registry.registerInteractEvent("§2Wand", new InteractHookCancelEvent());
	// }
	// {
	//     registry.registerInteractEvent("DNA", new InteractHookParticleDirected("dna", 0, 5, .5, false, 0));
	//     registry.registerInteractEvent("DNA", new InteractHookCancelEvent());
	// }
	// {
	//     registry.registerInteractEvent("Crystal", new InteractHookParticleDirected("crystal", 0, 5, 0, true, 90));
	//     registry.registerInteractEvent("Crystal", new InteractHookCancelEvent());
	// }
        // {
        //     registry.registerInteractEvent("Sit", new InteractHookPlayer(em.getEffectByName("sit")));
        // }
        // {
        //     registry.registerInteractEvent("SmoothSnow", new InteractHookBlockLocation(em.getEffectByName("smoothsnow")));
        // }
        // {
        //     registry.registerDamageOtherEntityEvent("Wiper", new DamageOtherEntityHookTargetEntity(em.getEffectByName("removeentity")));
	//     registry.registerDamageOtherEntityEvent("Wiper", new DamageOtherEntityHookCancelEvent());
        // }
        // {
        //     registry.registerInteractEvent("GimmeHorsey", new InteractHookPlayerLocation(em.getEffectByName("spawnhorse")));
        // }
    }

    public void setRegistry(Registry registry) {
        this.registry = registry;
    }

    public Registry getRegistry() {
        return registry;
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void process(EntityDamageByEntityEvent event) {
        System.out.println("Damage entity by entity" + event.getEntity());
        registry.processEntityDamageByEntityEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void process(PlayerInteractEvent event) {
	registry.processInteractEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void process(ProjectileLaunchEvent event) {
        registry.processProjectileLaunchEvent(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void process(ProjectileHitEvent event) {
    }
    
}
