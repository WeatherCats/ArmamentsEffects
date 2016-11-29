package kipperorigin.armamentseffects.managers;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class ParticleEffectTimedRunnable extends BukkitRunnable
{
    private final JavaPlugin plugin;
    private final Location location;
    private final int effectId;
    private int step;
    private int ticks;
    private double stepsPerTick;
    private double speed;

    public ParticleEffectTimedRunnable(JavaPlugin plugin, int effectId, double stepsPerTick, double speed, Location location)
    {
	System.out.println("Start timed runnable");
	this.plugin = plugin;
	this.effectId = effectId;
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
            if(step % 20 == 0) {
                System.out.println("Playing step " + step + " at " + nloc);
            }
	    if(!((ParticleEffect)(EffectManager.getInstance().getEffectById(effectId))).play(step++, nloc)) {
		this.cancel();
		System.out.println("Cancel coz steps at " + step);
		return;
	    }
	}
    }
}
