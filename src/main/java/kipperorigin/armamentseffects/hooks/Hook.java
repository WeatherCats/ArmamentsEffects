package kipperorigin.armamentseffects.hooks;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

import kipperorigin.armamentseffects.managers.Effect;

public interface Hook extends ConfigurationSerializable
{
    public String getInfo();
    public boolean usesEffect(Effect effect);
    public boolean alwaysActive();
}
