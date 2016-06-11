package kipperorigin.armamentseffects.resources;

import java.util.List;
import java.util.ListIterator;

import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class AE_GetPlayersInRadius {

	public List<Player> getPlayersInRadius(World world, int i, Entity e) {
		List<Player> playersInWorld = world.getPlayers();
		ListIterator<Player> litr = null;
		if (playersInWorld.isEmpty())return null;
		else {
			litr=playersInWorld.listIterator();
			while(litr.hasNext()) {
				if (e.getLocation().distance(litr.next().getLocation()) > i)
					litr.remove();
			}
			if (playersInWorld.isEmpty() || playersInWorld == null)
				return null;
			else
				return playersInWorld;
		}
	}
}
