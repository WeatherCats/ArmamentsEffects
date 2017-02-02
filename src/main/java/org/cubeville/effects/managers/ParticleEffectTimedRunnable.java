package org.cubeville.effects.managers;

import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class ParticleEffectTimedRunnable extends BukkitRunnable
{
    private final JavaPlugin plugin;
    private final Location location;
    private final ParticleEffect effect;
    private int step;
    private int ticks;
    private double stepsPerTick;
    private double speed;

    public ParticleEffectTimedRunnable(JavaPlugin plugin, ParticleEffect effect, double stepsPerTick, double speed, Location location)
    {
	this.plugin = plugin;
	this.effect = effect;
	this.location = location;
	this.stepsPerTick = stepsPerTick;
	this.speed = speed;
	ticks = 0;
	step = 0;
    }

    @Override
    public void run() {
	ticks++;
	while(step + 1 < ticks * stepsPerTick) {
	    Vector dist = location.getDirection().clone();
	    Location nloc = location.clone();
	    dist.multiply(step * speed);
	    nloc.add(dist);
	    if(!effect.play(step++, nloc)) {
		this.cancel();
		return;
	    }
	}
    }
}
