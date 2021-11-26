package org.cubeville.effects;

import java.lang.reflect.Method;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.SoundCategory;
import org.bukkit.Sound;

import org.cubeville.commons.commands.CommandParser;
import org.cubeville.effects.commands.*;
import org.cubeville.effects.registry.Registry;
import org.cubeville.effects.managers.EventListener;
import org.cubeville.effects.managers.EffectManager;

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

        eventListener = new EventListener();
        pm.registerEvents(eventListener, this);

        loadEffects(null);
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
	    else if((args.length == 2 || args.length == 3) && args[0].equals("playsound")) {
		Sound sound = Sound.valueOf(args[1].toUpperCase());
		if(sound == null) {
		    sender.sendMessage("§cSound not found.");
		    return true;
		}
		float pitch = 1.0f;
		if(args.length == 3) pitch = Float.valueOf(args[2]);
		Player player = (Player) sender;
		player.playSound(player.getLocation(), sound, SoundCategory.MASTER, 1.0f, pitch);
		sender.sendMessage("§aPlaying sound " + sound.toString());
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
        commandParser.addCommand(new EffectModifyPotionEffectCommand());
        commandParser.addCommand(new EffectModifySoundCommand());
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
        commandParser.addCommand(new HookCreateInteractPlayerCommand());
        commandParser.addCommand(new HookCreateInteractPlayerLocationCommand());
        commandParser.addCommand(new HookCreateInteractRemoveItemCommand());
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
