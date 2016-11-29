package kipperorigin.armamentseffects.registry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import kipperorigin.armamentseffects.hooks.Hook;

@SerializableAs("RegistryHook")
public class RegistryHook<T extends Hook> implements ConfigurationSerializable
{
    private List<T> hooks;
    private Map<UUID, PlayerData> playerData;
    private long cooldownTime;
    private boolean needsPermission;
    private String permission;
    // TODO: boolean noCooldownTime for admins?

    protected class PlayerData {
        PlayerData(boolean hasPermisson) { this.hasPermission = hasPermission; }
        private boolean hasPermission;
        private long lastUsed = 0;
    }

    public RegistryHook() {
        hooks = new ArrayList<>();
        playerData = new HashMap<>();
        cooldownTime = 0;
    }

    public RegistryHook(Map<String, Object> config) {
        cooldownTime = (Long) config.get("cooldownTime");
        needsPermission = (boolean) config.get("needsPermission");
        if(needsPermission) permission = (String) config.get("permission");
    }
    
    public Map<String, Object> serialize() {
        Map<String, Object> ret = new HashMap<>();
        ret.put("cooldownTime", cooldownTime);
        ret.put("needsPermission", needsPermission);
        if(needsPermission) ret.put("permission", permission);
        return ret;
    }
    
    public List<T> getHooks() {
        return hooks;
    }

    public boolean isPermitted(UUID uuid) {
        if(needsPermission == false && cooldownTime == 0) return true;

        PlayerData pd = playerData.get(uuid);
        if(pd == null) {
            if(needsPermission == false) {
                pd = new PlayerData(true);
            }
            else {
                boolean hasPermission = Registry.getInstance().getPermissionList().hasPermission(permission, uuid);
                pd = new PlayerData(hasPermission);
            }
            playerData.put(uuid, pd);
        }

        boolean hasPermission = pd.hasPermission;

        boolean needCooldown;
        if(cooldownTime == 0) {
            needCooldown = false;
        }
        else {
            long ts = System.currentTimeMillis();
            if(ts - pd.lastUsed > cooldownTime) {
                needCooldown = false;
                pd.lastUsed = ts;
            }
            else {
                needCooldown = true;
            }
        }
        return needCooldown == false && hasPermission == true;
    }
}
