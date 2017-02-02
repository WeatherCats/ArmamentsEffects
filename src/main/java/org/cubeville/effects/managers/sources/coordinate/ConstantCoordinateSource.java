package org.cubeville.effects.managers.sources.coordinate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.util.Vector;
import org.cubeville.effects.util.Conversions;

@SerializableAs("ConstantCoordinateSource")
public class ConstantCoordinateSource implements CoordinateSource
{
    private List<Vector> vertices;

    public ConstantCoordinateSource() {
	vertices = new ArrayList<>();
	vertices.add(new Vector(0, 0, 0));
    }

    public ConstantCoordinateSource(List<Vector> vertices) {
        this.vertices = vertices;
    }
    
    public ConstantCoordinateSource(Map<String, Object> config) {
	List<String> vl = (List<String>) config.get("vertices");
	vertices = new ArrayList<>();
	for(String v: vl) {
	    vertices.add(Conversions.getVectorFromString(v));
	}
    }

    public Map<String, Object> serialize() {
	Map<String, Object> ret = new HashMap<>();
	List<String> vl = new ArrayList<>();
	for(Vector v: vertices) {
	    vl.add(Conversions.getVectorAsString(v));
	}
	ret.put("vertices", vl);
	return ret;
    }
    
    public List<Vector> getVertices(int step, int nr) {
	return vertices;
    }

    public String getInfo() {
	String ret = "Constant (";
	for(int i = 0; i < vertices.size(); i++) {
	    if(i > 0) ret += ", ";
	    ret += vertices.get(i).getX() + "/" + vertices.get(i).getY() + "/" + vertices.get(i).getZ();
	}
        ret += ")";
	return ret;
    }
}
