package kipperorigin.armamentseffects.effects;

import org.bukkit.Bukkit;
import kipperorigin.armamentseffects.AE_Main;
import kipperorigin.armamentseffects.event.AE_InteractEvent;

public class AE_EffectKillParticles extends AE_EffectParent {
    
    private AE_Main plugin;

    public AE_EffectKillParticles(AE_Main plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public void run(final AE_InteractEvent event) {
        Bukkit.getScheduler().cancelTasks(plugin);
    }
}
