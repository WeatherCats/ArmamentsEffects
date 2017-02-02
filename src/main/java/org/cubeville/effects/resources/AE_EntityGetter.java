package org.cubeville.effects.resources;

import java.util.List;
import java.util.ListIterator;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class AE_EntityGetter {

	public List<Player> getPlayersInRadius(int i, Entity e) {
		List<Player> playersInWorld = e.getWorld().getPlayers();
		ListIterator<Player> litr = null;

		if (playersInWorld.isEmpty())
			return null;
		else {
			litr=playersInWorld.listIterator();
			while(litr.hasNext())
				if (e.getLocation().distance(litr.next().getLocation()) > i)
					litr.remove();
			if (playersInWorld.isEmpty() || playersInWorld == null)
				return null;
			else
				return playersInWorld;
		}
	}
	
	public List<Player> getPlayersInSight(Player player) {
		List<Player> playersInSight = getPlayersInRadius(50, player);
		
		ListIterator<Player> litr = null;
		if (playersInSight.isEmpty()) 
			return null;
		else {
			litr=playersInSight.listIterator();
			while(litr.hasNext())
				if (!player.hasLineOfSight(litr.next()))
					litr.remove();
		}
		return null;
	}
	
	public Player getClosestPlayer(Player player, List<Player> players) {
		if (players.isEmpty())
			return null;
		Player cPlayer = players.get(0);
		for (int i = 1; i < players.size(); i++) {
			if (player.getLocation().distance(cPlayer.getLocation()) > player.getLocation().distance(players.get(i).getLocation()))
				cPlayer = players.get(i);
		}
		return cPlayer;
	}
	
	public Player getClosestPlayerInSight(Player player) {
		Player cPlayer = this.getClosestPlayer(player, this.getPlayersInSight(player));
		if (cPlayer == null)
			return null;
		else
			return cPlayer;
	}
}
