package kipperorigin.armamentseffects.event;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class AE_Event {

    private final Player player;
    private final ItemStack item;
    private String[] args;
    private String[] coloredargs;
    private String line;
    private String coloredline;

    public AE_Event(Player player) {
        this.player = player;
        item = player.getInventory().getItemInMainHand();
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
    
    public final void setColoredArgs(String[] coloredargs) {
        this.coloredargs = coloredargs;
    }

    public final String[] getColoredArgs() {
        return coloredargs;
    }
    
    public final void setLine(String line) {
        this.line = line;
    }

    public final String getLine() {
        return line;
    }
    
    public final void setColoredLine(String coloredline) {
        this.coloredline = coloredline;
    }

    public final String getColoredLine() {
        return coloredline;
    }
}
