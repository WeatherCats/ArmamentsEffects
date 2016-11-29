package kipperorigin.armamentseffects.managers;

import org.bukkit.Location;

public abstract class EffectWithLocationAndTimeline extends EffectWithLocation
{
    public abstract boolean play(int step, Location location);
}
