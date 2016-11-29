package kipperorigin.armamentseffects.managers.modifier;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import org.bukkit.util.Vector;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

public interface CoordinateModifier extends ConfigurationSerializable
{
    public List<Vector> modify(List<Vector> coordinates, int step);
    public String getInfo();
}
