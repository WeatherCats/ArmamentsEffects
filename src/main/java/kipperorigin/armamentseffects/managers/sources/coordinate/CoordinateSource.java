package kipperorigin.armamentseffects.managers.sources.coordinate;

import java.util.List;
import org.bukkit.util.Vector;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

public interface CoordinateSource extends ConfigurationSerializable
{
    public List<Vector> getVertices(int step, int nr);
    public String getInfo();
}
