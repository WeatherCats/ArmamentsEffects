package kipperorigin.armamentseffects.effects;

import kipperorigin.armamentseffects.AE_Main;
import kipperorigin.armamentseffects.event.AE_InteractEvent;
import kipperorigin.armamentseffects.resources.AE_LaunchTnT;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class AE_EffectTnT extends AE_EffectParent {

	@SuppressWarnings("unused")
	private AE_Main plugin;

	public AE_EffectTnT(AE_Main plugin) {
		this.plugin = plugin;
	}
	
	AE_LaunchTnT tnt = new AE_LaunchTnT();

	@Override
	public void run(final AE_InteractEvent event) {
		
		/* -t Trishot
		 * -sg Shotgun
		 * -mr Mortar
		 * -mn Mine
		 * -sn Sniper?
		 */
		
		String types[] = {"-t","-sg","-mr","-mn","-sn","c"};
		final String args[] = event.getArgs();
		Player player = event.getPlayer();
		Location loc = player.getLocation();
		int multiply = 1;
		int timer = 0;
		loc = loc.add(0, 1, 0);
		
		Vector x = new Vector(0, .35, 0);
		Vector sg[] = new Vector[]{new Vector(Math.random(), Math.random(), Math.random()), new Vector(Math.random(), Math.random(), Math.random()), new Vector(Math.random(), Math.random(), Math.random()), new Vector(Math.random(), Math.random(), Math.random()), new Vector(Math.random(), Math.random(), Math.random()), new Vector(Math.random(), Math.random(), Math.random()), new Vector(Math.random(), Math.random(), Math.random()), new Vector(Math.random(), Math.random(), Math.random())};

		if (!(args.length >= 1))
			return;
		
		if (args[1].equalsIgnoreCase(types[0])) { 
			if (args.length >= 2) {
				try {
					multiply = Integer.parseInt(args[1]);
				} catch (NumberFormatException e) {
					return;
				}
			}
	
			if (args.length >= 3) {
				try {
					timer = Integer.parseInt(args[2]);
				} catch (NumberFormatException e) {
					return;
				}
			}
	
			if (args.length >= 4) {
				return;
			}
			Vector v = (event.getPlayer().getEyeLocation().getDirection()).multiply(multiply);
			v.add(x);
			if (timer <= 0)
				tnt.fireTnTNoTimer(v, loc);
			else
				tnt.fireTnT(v, loc, timer);
			
			Vector a[] = new Vector[]{v, v, v, v, v, v, v, v};
			
			a[0].add(sg[0]);
			if (timer <= 0)
				tnt.fireTnTNoTimer(a[0], loc);
			else
				tnt.fireTnT(a[0], loc, timer);
			
			a[1].add(sg[1]);
			if (timer <= 0)
				tnt.fireTnTNoTimer(a[1], loc);
			else
				tnt.fireTnT(a[1], loc, timer);
			
			a[2].add(sg[2]);
			if (timer <= 0)
				tnt.fireTnTNoTimer(a[2], loc);
			else
				tnt.fireTnT(a[2], loc, timer);
			
			a[3].add(sg[3]);
			if (timer <= 0)
				tnt.fireTnTNoTimer(a[3], loc);
			else
				tnt.fireTnT(a[3], loc, timer);
			
			a[4].add(sg[4]);
			if (timer <= 0)
				tnt.fireTnTNoTimer(a[4], loc);
			else
				tnt.fireTnT(a[4], loc, timer);
			
			a[5].add(sg[5]);
			if (timer <= 0)
				tnt.fireTnTNoTimer(a[5], loc);
			else
				tnt.fireTnT(a[5], loc, timer);
			
			a[6].add(sg[6]);
			if (timer <= 0)
				tnt.fireTnTNoTimer(a[6], loc);
			else
				tnt.fireTnT(a[6], loc, timer);
			
			a[7].add(sg[7]);
			if (timer <= 0)
				tnt.fireTnTNoTimer(a[7], loc);
			else
				tnt.fireTnT(a[7], loc, timer);
		}
		
		if (args[1].equalsIgnoreCase(types[5])) { 
			if (args.length >= 2) {
				try {
					multiply = Integer.parseInt(args[1]);
				} catch (NumberFormatException e) {
					return;
				}
			}
	
			if (args.length >= 3) {
				try {
					timer = Integer.parseInt(args[2]);
				} catch (NumberFormatException e) {
					return;
				}
			}
	
			if (args.length >= 4) {
				return;
			}
			Vector v = (event.getPlayer().getEyeLocation().getDirection()).multiply(multiply);
			v.add(x);
			if (timer <= 0)
				tnt.fireTnTNoTimer(v, loc);
			else
				tnt.fireTnT(v, loc, timer);
		}
	}
}
