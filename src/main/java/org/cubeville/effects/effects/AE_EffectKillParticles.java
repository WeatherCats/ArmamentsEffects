package org.cubeville.effects.effects;

import org.bukkit.Bukkit;
import org.cubeville.effects.Effects;
import org.cubeville.effects.event.InteractEvent;

public class AE_EffectKillParticles extends AE_EffectParent {
	
	private Effects plugin;

	public AE_EffectKillParticles(Effects plugin) {

		this.plugin = plugin;
	}
	
	@Override
	public void run(final InteractEvent event) {
		String[] args = event.getArgs();
		
		if (args.length > 0) {
			for(int i = 0; i <= args.length; i++) {
				try {
					Bukkit.getScheduler().cancelTask(Integer.parseInt(args[i]));
				} catch (ArrayIndexOutOfBoundsException e) {
					
				} catch (NumberFormatException e) {
					
				}
			}
		} else {
			Bukkit.getScheduler().cancelTasks(plugin);
		}
	}
}