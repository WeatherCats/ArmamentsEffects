package kipperorigin.armamentseffects.event;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class AE_Event {

	private final Player player;
	private final ItemStack item;
	private String[] args;

	public AE_Event(Player player) {
		this.player = player;
		item = player.getItemInHand();
	}

	public final Player getPlayer() {
		return player;
	}

	public final ItemStack getItem() {
		return item;
	}

	public final void setArgs(String[] args) {
		this.args = args;
	}

	public final String[] getArgs() {
		return args;
	}

}
