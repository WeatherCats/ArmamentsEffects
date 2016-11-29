package kipperorigin.armamentseffects;

import kipperorigin.armamentseffects.effects.AE_EffectArrow;
import kipperorigin.armamentseffects.effects.AE_EffectCommand;
import kipperorigin.armamentseffects.effects.AE_EffectDisarm;
import kipperorigin.armamentseffects.effects.AE_EffectDrain;
//import kipperorigin.armamentseffects.effects.AE_EffectDurability;
import kipperorigin.armamentseffects.effects.AE_EffectExplode;
import kipperorigin.armamentseffects.effects.AE_EffectFireworkParticle;
//import kipperorigin.armamentseffects.effects.AE_EffectHomingArrow;
import kipperorigin.armamentseffects.effects.AE_EffectInstakill;
import kipperorigin.armamentseffects.effects.AE_EffectKillParticles;
import kipperorigin.armamentseffects.effects.AE_EffectLightning;
import kipperorigin.armamentseffects.effects.AE_EffectManager;
import kipperorigin.armamentseffects.effects.AE_EffectPacketParticles;
import kipperorigin.armamentseffects.effects.AE_EffectParticle;
import kipperorigin.armamentseffects.effects.AE_EffectPiercing;
import kipperorigin.armamentseffects.effects.AE_EffectPotionRightClick;
import kipperorigin.armamentseffects.effects.AE_EffectPotions;
import kipperorigin.armamentseffects.effects.AE_EffectRemoveAilment;
import kipperorigin.armamentseffects.effects.AE_EffectRideable;
import kipperorigin.armamentseffects.effects.AE_EffectShoot;
import kipperorigin.armamentseffects.effects.AE_EffectSound;
import kipperorigin.armamentseffects.effects.AE_EffectSpawn;
import kipperorigin.armamentseffects.effects.AE_EffectStats;
import kipperorigin.armamentseffects.effects.AE_EffectStun;
import kipperorigin.armamentseffects.effects.AE_EffectTeleport;
import kipperorigin.armamentseffects.effects.AE_EffectTnT;
import kipperorigin.armamentseffects.effects.AE_EffectVelocity;
import kipperorigin.armamentseffects.effects.AE_EffectWeb;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;


import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import kipperorigin.armamentseffects.managers.EffectManager;
import org.cubeville.commons.commands.CommandParser;
import org.bukkit.configuration.file.FileConfiguration;
import kipperorigin.armamentseffects.managers.EventListener;
import kipperorigin.armamentseffects.registry.Registry;
import kipperorigin.armamentseffects.commands.*;

public class AE_Main extends JavaPlugin {

    private static AE_Main instance; // TODO: Unload -> delete!
    private CommandParser commandParser;
    private final PluginManager pm = Bukkit.getPluginManager();
    private EventListener eventListener;
    
    public static AE_Main getInstance() {
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
	if(!(sender instanceof Player)) return false;
	Player player = (Player)sender;
	
	if(command.getName().equals("fx")) {
            if(args.length == 1 && args[0].equals("save")) {
                FileConfiguration config = getConfig();
                config.set("EffectManager", EffectManager.getInstance());
                config.set("Registry", eventListener.getRegistry());
                saveConfig();
                return true;
            }
            else if(args.length == 1 && args[0].equals("load")) {
                EffectManager e = (EffectManager) getConfig().get("EffectManager");
                player.sendMessage("Effects loaded");
                Registry r = (Registry) getConfig().get("Registry");
                eventListener.setRegistry(r);
                player.sendMessage("Registry loaded");
                return true;
            }
            else if(args.length == 1 && args[0].equals("test")) {
                player.sendMessage("UUID: " + player.getUniqueId());
                return true;
            }
            else {
                commandParser.execute(player, args);
                return true;
            }
	}
	else {
	    return false;
	}
    }

    private void initializeCommands() {
	commandParser = new CommandParser();
	commandParser.addCommand(new CreateParticleCommand());
	commandParser.addCommand(new EffectListCommand());
	commandParser.addCommand(new HookListCommand());
	commandParser.addCommand(new InfoCommand());
        commandParser.addCommand(new EffectCreateParticleCommand());
        commandParser.addCommand(new EffectCreateSoundCommand());
        commandParser.addCommand(new HookCreateDamageOtherEntityCancelEventCommand());
        commandParser.addCommand(new HookCreateInteractCancelEventCommand());
        commandParser.addCommand(new HookCreateInteractPlayerLocationCommand());
        commandParser.addCommand(new HookCreateInteractTargetLocationCommand());
        commandParser.addCommand(new HookRemoveDamageOtherEntity());
        commandParser.addCommand(new HookRemoveInteract());
        commandParser.addCommand(new EffectRemove());
        commandParser.addCommand(new PermissionAddCommand());
        commandParser.addCommand(new PermissionListCommand());
        commandParser.addCommand(new PermissionRemoveCommand());
    }
}
