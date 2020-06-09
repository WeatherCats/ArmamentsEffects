package org.cubeville.effects;

import java.lang.reflect.Method;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import org.cubeville.commons.commands.CommandParser;
import org.cubeville.effects.commands.*;
import org.cubeville.effects.effects.AE_EffectArrow;
import org.cubeville.effects.effects.AE_EffectCommand;
import org.cubeville.effects.effects.AE_EffectDisarm;
import org.cubeville.effects.effects.AE_EffectDrain;
import org.cubeville.effects.effects.AE_EffectExplode;
import org.cubeville.effects.effects.AE_EffectFireworkParticle;
import org.cubeville.effects.effects.AE_EffectInstakill;
import org.cubeville.effects.effects.AE_EffectKillParticles;
import org.cubeville.effects.effects.AE_EffectLightning;
import org.cubeville.effects.effects.AE_EffectManager;
import org.cubeville.effects.effects.AE_EffectPacketParticles;
import org.cubeville.effects.effects.AE_EffectParticle;
import org.cubeville.effects.effects.AE_EffectPiercing;
import org.cubeville.effects.effects.AE_EffectPotionRightClick;
import org.cubeville.effects.effects.AE_EffectPotions;
import org.cubeville.effects.effects.AE_EffectRemoveAilment;
import org.cubeville.effects.effects.AE_EffectRideable;
import org.cubeville.effects.effects.AE_EffectShoot;
import org.cubeville.effects.effects.AE_EffectSound;
import org.cubeville.effects.effects.AE_EffectSpawn;
import org.cubeville.effects.effects.AE_EffectStats;
import org.cubeville.effects.effects.AE_EffectStun;
import org.cubeville.effects.effects.AE_EffectTeleport;
import org.cubeville.effects.effects.AE_EffectTnT;
import org.cubeville.effects.effects.AE_EffectVelocity;
import org.cubeville.effects.effects.AE_EffectWeb;
import org.cubeville.effects.managers.EffectManager;
import org.cubeville.effects.managers.EventListener;
import org.cubeville.effects.registry.Registry;

import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;

public class Effects extends JavaPlugin {

    private static Effects instance; // TODO: Unload -> delete!
    private CommandParser commandParser;
    private final PluginManager pm = Bukkit.getPluginManager();
    private EventListener eventListener;
    
    public static Effects getInstance() {
	return instance;
    }
    
    @Override
    public void onEnable() {
	instance = this;

	SerializationRegistration.init();
        EffectManager e = new EffectManager();
        e.addExampleEffects();
        initializeCommands();
        
        final AE_EffectManager listener = new AE_EffectManager(this);
        
        pm.registerEvents(listener, this);

        eventListener = new EventListener();
        pm.registerEvents(eventListener, this);

        loadEffects(null);

        // arrow
        AE_EffectArrow arrow = new AE_EffectArrow(this);
        listener.registerEffect("arrow", arrow);
        listener.registerEffect("fire", arrow);

        // command
        AE_EffectCommand command = new AE_EffectCommand();
        listener.registerEffect("command", command);
        listener.registerEffect("send", command);
        
        // drain
        AE_EffectDrain drain = new AE_EffectDrain(this);
        listener.registerEffect("drain", drain);
        listener.registerEffect("leech", drain);
        
        // disarm
        AE_EffectDisarm disarm = new AE_EffectDisarm();
        listener.registerEffect("disarm", disarm);
        listener.registerEffect("unarm", disarm);
        
        // durability
        // AE_EffectDurability durability = new AE_EffectDurability();
        // listener.registerEffect("durability", durability);

        // explode
        AE_EffectExplode explode = new AE_EffectExplode();
        listener.registerEffect("explode", explode);
        listener.registerEffect("boom", explode);
        
        // firework particle
        AE_EffectFireworkParticle firework = new AE_EffectFireworkParticle(this);
        listener.registerEffect("firework", firework);
        listener.registerEffect("fireworkparticle", firework);
        
        // homing arrow
        // AE_EffectHomingArrow ha = new AE_EffectHomingArrow(this);
        // listener.registerEffect("Homing", ha);
        // listener.registerEffect("Seeking", ha);

        // instakill
        AE_EffectInstakill instakill = new AE_EffectInstakill();
        listener.registerEffect("instakill", instakill);
        listener.registerEffect("slay", instakill);

        // instapotion
        AE_EffectPotionRightClick potright = new AE_EffectPotionRightClick();
        listener.registerEffect("instapot", potright);
        listener.registerEffect("poteffect", potright);
        
        // kill particles
        AE_EffectKillParticles killparticles = new AE_EffectKillParticles(this);
        listener.registerEffect("killallparticles", killparticles);
        listener.registerEffect("killparticles", killparticles);
        
        // lightning
        AE_EffectLightning lightning = new AE_EffectLightning(this);
        listener.registerEffect("lightning", lightning);
        listener.registerEffect("thunder", lightning);
        
        // piercing
        AE_EffectPiercing piercing = new AE_EffectPiercing();
        listener.registerEffect("piercing", piercing);
        listener.registerEffect("penetration", piercing);
        
        // potions
        AE_EffectPotions potions = new AE_EffectPotions();
        listener.registerEffect("inflict", potions);
        listener.registerEffect("apply", potions);
        
        // rideable
        AE_EffectRideable rideable = new AE_EffectRideable(this);
        listener.registerEffect("rideable", rideable);
        listener.registerEffect("ride", rideable);

        // shoot
        AE_EffectShoot shoot = new AE_EffectShoot();
        listener.registerEffect("shoot", shoot);
        listener.registerEffect("launch", shoot);
        listener.registerEffect("cast", shoot);

        // sound
        AE_EffectSound sound = new AE_EffectSound();
        listener.registerEffect("playsound", sound);
        listener.registerEffect("play", sound);

        // spawnmob
        AE_EffectSpawn spawn = new AE_EffectSpawn();
        listener.registerEffect("spawn", spawn);
        listener.registerEffect("summon", spawn);
        
        // stats
        AE_EffectStats stats = new AE_EffectStats();
        listener.registerEffect("stats", stats);
        listener.registerEffect("numbers", stats);

        // stun
        AE_EffectStun stun = new AE_EffectStun();
        listener.registerEffect("stun", stun);
        listener.registerEffect("stop", stun);
        
        // tnt
        AE_EffectTnT tnt = new AE_EffectTnT(this);
        listener.registerEffect("tnt", tnt);
        listener.registerEffect("primedtnt", tnt);

        // velocity
        AE_EffectVelocity velocity = new AE_EffectVelocity();
        listener.registerEffect("velocity", velocity);
        listener.registerEffect("speed", velocity);

        // web
        AE_EffectWeb web = new AE_EffectWeb(this);
        listener.registerEffect("web", web);
        listener.registerEffect("trap", web);

        // particle
        AE_EffectParticle particle = new AE_EffectParticle(this);
        listener.registerEffect("particle", particle);
        listener.registerEffect("display", particle);
        
        // packet particles
        AE_EffectPacketParticles packetParticles = new AE_EffectPacketParticles(this);
        listener.registerEffect("packetparticle", packetParticles);
        listener.registerEffect("packetparticles", packetParticles);

        // remove ailment
        AE_EffectRemoveAilment heal = new AE_EffectRemoveAilment();
        listener.registerEffect("heal", heal);
        listener.registerEffect("cure", heal);

        // teleport
        AE_EffectTeleport teleport = new AE_EffectTeleport();
        listener.registerEffect("teleport", teleport);
        listener.registerEffect("move", teleport);

    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
	//if(!(sender instanceof Player)) return false;
	//Player player = (Player)sender;
	
	if(command.getName().equals("fx")) {
            if(args.length == 1 && args[0].equals("testx")) {
                
                // Location loc = player.getLocation();
                // loc = loc.add(0, 3, 0);
                // Firework firework = player.getWorld().spawn(loc, Firework.class);
                
                // Method world_getHandle = getMethod(player.getWorld().getClass(), "getHandle");
                // Method firework_getHandle = getMethod(firework.getClass(), "getHandle");
                // Object nms_world = null;
                // Object nms_firework = null;
                // try {
                //     nms_world = world_getHandle.invoke(player.getWorld(), (Object[]) null);
                //     nms_firework = firework_getHandle.invoke(firework, (Object[]) null);
                // }
                // catch(Exception e) { System.out.println("NMS Exception"); }
                // Method nms_world_broadcastEntityEffect = getMethod(nms_world.getClass(), "broadcastEntityEffect");

                // FireworkMeta data = (FireworkMeta) firework.getFireworkMeta();
                // data.addEffects(FireworkEffect.builder().withColor(org.bukkit.Color.BLUE).build());
                // data.setPower(3);
                // firework.setFireworkMeta(data);

                // try {
                //     nms_world_broadcastEntityEffect.invoke(nms_world, new Object[] { nms_firework, (byte) 17});
                // }
                // catch(Exception e) { System.out.println("NMS Exception"); }
                
                // firework.remove();
                
                // // Bukkit.getScheduler().runTaskLater(this, new Runnable() {
                // //         public void run() {
                // //             firework.detonate();
                // //         }
                // //     }, 0);
                return true;
            }
            else if(args.length == 1 && args[0].equals("save")) { // TODO: deprecated
                saveEffects();
                return true;
            }
            else if(args.length == 1 && args[0].equals("load")) { // TODO: deprecated
                loadEffects(null);
                return true;
            }
            else {
                commandParser.execute(sender, args);
                return true;
            }
        }
	else {
	    return false;
	}
    }

    private static Method getMethod(Class<?> aClass, String method) {
        for(Method thisMethod: aClass.getMethods()) {
            if(thisMethod.getName().equals(method)) {
                return thisMethod;
            }
        }
        return null;
    }
    
    public void loadEffects(Player player) {
        EffectManager e = (EffectManager) getConfig().get("EffectManager");
        if(player != null) player.sendMessage("Effects loaded");
        Registry r = (Registry) getConfig().get("Registry");
        if(r == null) r = new Registry();
        eventListener.setRegistry(r);
        if(player != null) player.sendMessage("Registry loaded");
    }

    public void saveEffects() {
        FileConfiguration config = getConfig();
        config.set("EffectManager", EffectManager.getInstance());
        config.set("Registry", eventListener.getRegistry());
        saveConfig();
    }
    
    private void initializeCommands() {
        commandParser = new CommandParser();
        commandParser.addCommand(new EffectListCommand());
        commandParser.addCommand(new HookListCommand());
        commandParser.addCommand(new InfoCommand());
        commandParser.addCommand(new EffectCreateBrokenWandEffectCommand());
        commandParser.addCommand(new EffectCreateCommandWithLocationCommand());
        commandParser.addCommand(new EffectCreateParticleCommand());
        commandParser.addCommand(new EffectCreatePiercingCommand());
        commandParser.addCommand(new EffectCreatePotionEffectCommand());
        commandParser.addCommand(new EffectCreateSoundCommand());
        commandParser.addCommand(new EffectModifyParticleCommand());
        commandParser.addCommand(new EffectRemove());
        commandParser.addCommand(new EffectRemovecomponent());
        commandParser.addCommand(new HookCooldownCommand());
        commandParser.addCommand(new HookCreateBlockBreakBlockLocationCommand());
        commandParser.addCommand(new HookCreateBlockBreakParticlePlayerCommand());
        commandParser.addCommand(new HookCreateDamageOtherEntityCancelEventCommand());
        commandParser.addCommand(new HookCreateDamageOtherEntityEventAndDamagerCommand());
        commandParser.addCommand(new HookCreateDamageOtherEntityLivingEntityCommand());
        commandParser.addCommand(new HookCreateInteractCancelEventCommand());
        commandParser.addCommand(new HookCreateInteractParticlePlayerCommand());
        commandParser.addCommand(new HookCreateInteractPlayerLocationCommand());
        commandParser.addCommand(new HookCreateInteractTargetEntityCommand());
        commandParser.addCommand(new HookCreateInteractTargetLocationCommand()); 
        commandParser.addCommand(new HookCreateInteractTargetLocationParticlePlayerCommand()); 
        commandParser.addCommand(new HookCreateMovePlayerLocationCommand());
        commandParser.addCommand(new HookCreateProjectileLaunchParticlePlayerCommand());
        commandParser.addCommand(new HookCreateProjectileLaunchPlayerLocationCommand());
        commandParser.addCommand(new HookPermissionSetCommand());
        commandParser.addCommand(new HookRemoveBlockBreak());
        commandParser.addCommand(new HookRemoveDamageOtherEntity());
        commandParser.addCommand(new HookRemoveInteract());
        commandParser.addCommand(new HookRemoveProjectileLaunch());
        commandParser.addCommand(new ParticlePlayerCommand());
        commandParser.addCommand(new PermissionAddCommand());
        commandParser.addCommand(new PermissionListCommand());
        commandParser.addCommand(new PermissionRemoveCommand());
    }
}
