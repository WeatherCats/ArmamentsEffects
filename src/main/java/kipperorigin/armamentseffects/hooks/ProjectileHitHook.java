package kipperorigin.armamentseffects.hooks;

import org.bukkit.event.entity.ProjectileHitEvent;

import kipperorigin.armamentseffects.registry.ProjectileTrackerAction;

public interface ProjectileHitHook extends Hook, ProjectileTrackerAction
{
    public void process(ProjectileHitEvent event);
}
