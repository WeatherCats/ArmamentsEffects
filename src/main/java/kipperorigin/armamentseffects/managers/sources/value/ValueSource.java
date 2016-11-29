package kipperorigin.armamentseffects.managers.sources.value;

import kipperorigin.armamentseffects.managers.sources.value.ValueSource;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

public interface ValueSource extends ConfigurationSerializable
{
    public double getValue(int step);
    public String getInfo();
}
