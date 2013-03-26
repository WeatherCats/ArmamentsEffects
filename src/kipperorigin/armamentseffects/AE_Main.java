package kipperorigin.armamentseffects;

import kipperorigin.armamentseffects.effects.AE_EffectDisarm;
import kipperorigin.armamentseffects.effects.AE_EffectDrain;
import kipperorigin.armamentseffects.effects.AE_EffectExplode;
import kipperorigin.armamentseffects.effects.AE_EffectInstakill;
import kipperorigin.armamentseffects.effects.AE_EffectPotions;
import kipperorigin.armamentseffects.effects.AE_EffectSpawn;
import kipperorigin.armamentseffects.effects.AE_EffectVelocity;
import kipperorigin.armamentseffects.effects.AE_EffectWeb;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class AE_Main extends JavaPlugin{
	private final AE_EffectManager listener = new AE_EffectManager();
	private final PluginManager pm = Bukkit.getPluginManager();
	public void onEnable(){
		this.pm.registerEvents(listener, this);
		System.out.println("AE_Main Enabled!");
		
		//drain
		listener.registerEffect("drain", new AE_EffectDrain(this));
		listener.registerEffect("leech", new AE_EffectDrain(this));
		
		//disarm
		listener.registerEffect("disarm", new AE_EffectDisarm());
		listener.registerEffect("unarm", new AE_EffectDisarm());
		
		//explode
		listener.registerEffect("explode", new AE_EffectExplode());
		listener.registerEffect("boom", new AE_EffectExplode());
		
		//instakill
		listener.registerEffect("instakill", new AE_EffectInstakill());
		listener.registerEffect("slay", new AE_EffectInstakill());
		
		//potions
		listener.registerEffect("inflict", new AE_EffectPotions());
		listener.registerEffect("apply", new AE_EffectPotions());
		
		//spawnmob
		listener.registerEffect("spawn", new AE_EffectSpawn());
		listener.registerEffect("summon", new AE_EffectSpawn());
		
		//velocity
		listener.registerEffect("velocity", new AE_EffectVelocity());
		listener.registerEffect("speed", new AE_EffectVelocity());
		
		//web
		//drain
		listener.registerEffect("web", new AE_EffectWeb(this));
		listener.registerEffect("trap", new AE_EffectWeb(this));
	}
	public void onDisable(){
	}
}
