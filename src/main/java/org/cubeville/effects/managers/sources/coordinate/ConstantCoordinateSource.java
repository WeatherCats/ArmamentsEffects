package org.cubeville.effects.managers.sources.coordinate;

import java.util.ArrayList;
import java.util.Collections;
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

    public void addVertices(List<Vector> vertices) {
        this.vertices.addAll(vertices);
    }

    public void removeVertices(List<Integer> indices) {
        Collections.sort(indices);
        if(indices.get(0) < 1) throw new IllegalArgumentException("Coordinate index too low!");
        if(indices.get(indices.size() - 1) - 1 >= vertices.size()) throw new IllegalArgumentException("Coordinate index too high!");
        for(int i = indices.size() - 1; i >= 0; i--) {
            vertices.remove((int) (indices.get(i) - 1));
        }
    }

    public void scaleVertices(double factor, boolean includeZ) {
        for(int i = 0; i < vertices.size(); i++) {
            Vector v = vertices.get(i);
            Vector nv = new Vector(v.getX() * factor, v.getY() * factor, v.getZ() * (includeZ ? factor : 1.0));
            vertices.set(i, nv);
        }
    }
        
    public List<Vector> getVertices(int step, int nr) {
	return vertices;
    }

    public String getInfo() {
	String ret = "Constant (";
	for(int i = 0; i < vertices.size(); i++) {
	    if(i > 0) ret += ", ";
	    ret += String.format("%.2f", vertices.get(i).getX()) + "/" + String.format("%.2f", vertices.get(i).getY()) + "/" + String.format("%.2f", vertices.get(i).getZ());
	}
        ret += ")";
	return ret;
    }
}
