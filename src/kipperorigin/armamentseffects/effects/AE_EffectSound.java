package kipperorigin.armamentseffects.effects;

import kipperorigin.armamentseffects.event.AE_DamageEvent;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class AE_EffectSound extends AE_EffectParent {
	public void run(AE_DamageEvent event) {
		Player player = event.getPlayer();
		String[] args = event.getArgs();
		World world = player.getWorld();

		String playsound = "player";
		Location loc = player.getLocation();
		String soundstring;
		if ((args.length == 0) || (args[0].isEmpty())) {
			soundstring = null;
		} else {
			if (args.length == 1) {
				soundstring = args[0];
			} else if (args.length == 2) {
				soundstring = args[0];
				if ((args[1].equalsIgnoreCase("player"))
						|| (args[1].equalsIgnoreCase("local")))
					playsound = args[1];
			} else {
				soundstring = null;
			}
		}
		if (soundstring == null)
			return;
		soundstring = soundstring.toUpperCase();
		try {
			Sound.valueOf(soundstring);
		} catch (IllegalArgumentException e) {
			return;
		}
		Sound sound = Sound.valueOf(soundstring);

		if (playsound.equalsIgnoreCase("player")) {
			player.playSound(loc, sound, 1.0F, 1.0F);
		}

		if (playsound.equalsIgnoreCase("local"))
			world.playSound(loc, sound, 1.0F, 1.0F);
	}
}