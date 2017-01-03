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
    private String permission;

    protected class PlayerData {
        PlayerData(boolean hasPermission) { this.hasPermission = hasPermission; }
        private boolean hasPermission;
        private long lastUsed = 0;
    }

    public RegistryHook() {
        hooks = new ArrayList<>();
        playerData = new HashMap<>();
        cooldownTime = 0;
    }

    public RegistryHook(Map<String, Object> config) {
        cooldownTime = (int) config.get("cooldownTime");
        permission = (String) config.get("permission");
        hooks = (List<T>) config.get("hooks");
        playerData = new HashMap<>();
    }
    
    public Map<String, Object> serialize() {
        Map<String, Object> ret = new HashMap<>();
        ret.put("cooldownTime", cooldownTime);
        ret.put("permission", permission);
        ret.put("hooks", hooks);
        return ret;
    }
    
    public List<T> getHooks() {
        return hooks;
    }

    public boolean isPermitted(UUID uuid) {
        System.out.println("Check permission for " + uuid);
        if(permission == null && cooldownTime == 0) return true;

        System.out.println("Look at player data");
        PlayerData pd = playerData.get(uuid);
        if(pd == null) {
            System.out.println("No player data yet.");
            if(permission == null) {
                pd = new PlayerData(true);
                System.out.println("Create joker permission for player");
            }
            else {
                System.out.println("Check if player has permission");
                boolean hasPermission = Registry.getInstance().getPermissionList().hasPermission(permission, uuid);
                System.out.println("Check if player has permission: " + hasPermission);
                pd = new PlayerData(hasPermission);
            }
            System.out.println("Newly created playerdata has permissio: " + pd.hasPermission);
            playerData.put(uuid, pd);
        }

        boolean hasPermission = pd.hasPermission;
        System.out.println("Whatever. The player has permission? : " + hasPermission);
        
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

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public void setCooldown(int cooldown) {
        this.cooldownTime = cooldown;
    }
    
}
