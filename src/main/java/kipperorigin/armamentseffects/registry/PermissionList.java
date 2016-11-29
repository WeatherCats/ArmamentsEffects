package kipperorigin.armamentseffects.registry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

@SerializableAs("PermissionList")
public class PermissionList implements ConfigurationSerializable
{
    private Map<String, Set<UUID>> permissions;

    public PermissionList() {
        permissions = new HashMap<>();
    }

    public PermissionList(Map<String, Object> config) {
        permissions = (Map<String, Set<UUID>>) config.get("permissions");
    }

    public Map<String, Object> serialize() {
        Map<String, List<String>> wp = new HashMap<>();;
        for(String s: permissions.keySet()) {
            List<String> sl = new ArrayList<>();
            for(UUID uuid: permissions.get(s)) {
                sl.add(uuid.toString());
            }
            wp.put(s, sl);
        }
        Map<String, Object> ret = new HashMap<>();
        ret.put("permissions", wp);
        return ret;
    }
    
    public boolean hasPermission(String permission, UUID player) {
        if(!permissions.containsKey(permission)) return false;
        return permissions.get(permission).contains(player);
    }

    public void addPermission(String permission, UUID player) {
        if(!permissions.containsKey(permission)) {
            permissions.put(permission, new HashSet<>());
        }
        permissions.get(permission).add(player);
    }

    public void removePermission(String permission, UUID player) {
        if(!permissions.containsKey(permission)) throw new IllegalArgumentException("Permission " + permission + " does not exist.");
        if(!permissions.get(permission).contains(player)) throw new IllegalArgumentException("Player does not have permission.");
        permissions.get(permission).remove(player);
        if(permissions.get(permission).size() == 0) permissions.remove(permission);
    }

    public Set<String> getPermissions() {
        return permissions.keySet();
    }

    public Set<UUID> getPlayersByPermission(String permission) {
        if(!permissions.containsKey(permission)) return new HashSet<>();
        return permissions.get(permission);
    }
}
