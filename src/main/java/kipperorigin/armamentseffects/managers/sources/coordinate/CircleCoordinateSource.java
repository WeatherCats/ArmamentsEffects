package kipperorigin.armamentseffects.managers.sources.coordinate;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import org.bukkit.util.Vector;

import org.bukkit.configuration.serialization.SerializableAs;

@SerializableAs("CircleCoordinateSource")
public class CircleCoordinateSource implements CoordinateSource
{
    private double radius;
    private int startAngle;
    private int endAngle;
    private int stepAngle;
    private boolean xz;
    
    public CircleCoordinateSource(double radius, int stepAngle, int startAngle, int endAngle, boolean xz) {
	this.radius = radius;
	this.startAngle = startAngle;
	this.endAngle = endAngle;
	this.stepAngle = stepAngle;
	this.xz = xz;
    }

    public CircleCoordinateSource(Map<String, Object> config) {
	radius = (double) config.get("radius");
	startAngle = (int) config.get("startAngle");
	endAngle = (int) config.get("endAngle");
	stepAngle = (int) config.get("stepAngle");
	xz = (boolean) config.get("xz");
    }

    public Map<String, Object> serialize() {
	Map<String, Object> ret = new HashMap<>();
	ret.put("radius", radius);
	ret.put("startAngle", startAngle);
	ret.put("endAngle", endAngle);
	ret.put("stepAngle", stepAngle);
	ret.put("xz", xz);
	return ret;
    }
    
    public List<Vector> getVertices(int step, int nr) {
	List<Vector> ret = new ArrayList<>();
	for(double r = startAngle; r <= endAngle; r += stepAngle) {
	    double x = Math.cos(Math.toRadians(r)) * radius;
	    double y = Math.sin(Math.toRadians(r)) * radius;
	    if(xz) {
		ret.add(new Vector(x, 0, y));
	    }
	    else {
		ret.add(new Vector(x, y, 0));
	    }
	}
	return ret;
    }

    public String getInfo() {
	return "Circle " + startAngle + "-" + endAngle + "," + stepAngle + "; r=" + radius + (xz ? " xz" : "xy");
    }
}
