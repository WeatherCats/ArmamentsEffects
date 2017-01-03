package kipperorigin.armamentseffects.registry;

// TODO: Somehow remove projectiles from the projectiledamageactions with timeout

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.projectiles.ProjectileSource;

import kipperorigin.armamentseffects.AE_Main;
import kipperorigin.armamentseffects.managers.Effect;
import kipperorigin.armamentseffects.managers.EffectManager;
import kipperorigin.armamentseffects.hooks.DamageOtherEntityHook;
import kipperorigin.armamentseffects.hooks.Hook;
import kipperorigin.armamentseffects.hooks.InteractHook;
import kipperorigin.armamentseffects.hooks.ProjectileHitHook;
import kipperorigin.armamentseffects.hooks.ProjectileLaunchHook;
import kipperorigin.armamentseffects.hooks.DamageOtherEntityWithProjectileHook;
import kipperorigin.armamentseffects.util.ItemUtil;

@SerializableAs("Registry")
public class Registry implements ConfigurationSerializable
{
    private Map<String, RegistryHook<InteractHook>> interactEvents;
    private Map<String, RegistryHook<DamageOtherEntityHook>> damageOtherEntityEvents;
    private Map<String, RegistryHook<ProjectileLaunchHook>> projectileLaunchEvents;
    private Map<String, RegistryHook<ProjectileHitHook>> projectileHitEvents;
    
    private Map<String, Map<String, RegistryHook<Hook>>> eventMaps;

    static Registry instance;

    private PermissionList permissionList;

    Map<UUID, Set<ProjectileTrackerAction>> projectileHitActions;
    Map<UUID, ProjectileDamageTracker> projectileDamageActions;

    public Registry() {
	interactEvents = new HashMap<>();
	damageOtherEntityEvents = new HashMap<>();
	projectileLaunchEvents = new HashMap<>();
        projectileHitEvents = new HashMap<>();
        
        initializeEventMaps();
        
        instance = this;
        permissionList = new PermissionList();

        projectileHitActions = new HashMap<>();
        projectileDamageActions = new HashMap<>();
    }

    public Registry(Map<String, Object> config) {
        // TODO!!! : wha?
        interactEvents = (Map<String, RegistryHook<InteractHook>>) config.get("interact");
        damageOtherEntityEvents = (Map<String, RegistryHook<DamageOtherEntityHook>>) config.get("damage");
        projectileLaunchEvents = (Map<String, RegistryHook<ProjectileLaunchHook>>) config.get("projectilelaunch");
        projectileHitEvents = (Map<String, RegistryHook<ProjectileHitHook>>) config.get("projectilehit");
        initializeEventMaps();
        permissionList = (PermissionList) config.get("permissionList");
        instance = this;
        projectileHitActions = new HashMap<>();
        projectileDamageActions = new HashMap<>();
    }

    private void initializeEventMaps() {
        eventMaps = new HashMap<>();
        eventMaps.put("interact", (Map) interactEvents);
        eventMaps.put("damage", (Map) damageOtherEntityEvents);
        eventMaps.put("projectilelaunch", (Map) projectileLaunchEvents);
        eventMaps.put("projectilehit", (Map) projectileHitEvents);
    }
    
    public static Registry getInstance() {
        return instance;
    }

    public PermissionList getPermissionList() {
        return permissionList;
    }
    
    public Map<String, Object> serialize() {
        Map<String, Object> ret = new HashMap<>();
        for(String key: eventMaps.keySet()) ret.put(key, eventMaps.get(key));
        ret.put("permissionList", permissionList);
        return ret;
    }

    public List<String> getHookList() {
        List<String> ret = new ArrayList<>();
        for(String key: eventMaps.keySet()) {
            ret.add(key + ":");
            for(String hk: eventMaps.get(key).keySet()) {
                ret.add("  " + hk + ":");
                int cnt = 1;
                for(Hook h: eventMaps.get(key).get(hk).getHooks()) {
                    ret.add("    " + (cnt++) + ") " + ((Hook) h).getInfo());
                }
            }
        }
        return ret;
    }

    public List<String> getHookList(String itemName) {
        List<String> ret = new ArrayList<>();
        for(String key: eventMaps.keySet()) {
            if(eventMaps.get(key).containsKey(itemName)) {
                ret.add(key + ":");
                int cnt = 1;
                for(Hook h: eventMaps.get(key).get(itemName).getHooks()) {
                    ret.add("  " + (cnt++) + ") " + ((Hook) h).getInfo());
                }
            }
        }
        return ret;
    }

    private Map<String, RegistryHook<Hook>> getMapByHookType(Hook hook) {
        Map<String, RegistryHook<Hook>> ret = null;
        if(hook instanceof InteractHook) {
            ret = (Map)interactEvents;
        }
        else if(hook instanceof DamageOtherEntityHook) {
            ret = (Map)damageOtherEntityEvents;
        }
        else if(hook instanceof ProjectileLaunchHook) {
            ret = (Map)projectileLaunchEvents;
        }
        return ret;
    }
    
    public void registerEvent(String name, Hook hook) {
        Map<String, RegistryHook<Hook>> map = getMapByHookType(hook);
        if(map.get(name) == null) map.put(name, new RegistryHook<>());
        map.get(name).getHooks().add(hook);
    }
    
    public void processInteractEvent(PlayerInteractEvent event) {
	if(event.getHand() != EquipmentSlot.HAND) return;

        String itemName = ItemUtil.getItemName(event.getItem());
        System.out.println("Itemname: " + itemName);
        if(itemName == null) return;
        if(!interactEvents.containsKey(itemName)) return;

        RegistryHook<InteractHook> rh = interactEvents.get(itemName);
        boolean isPermitted = rh.isPermitted(event.getPlayer().getUniqueId());
        for(InteractHook hook: rh.getHooks()) {
            if(isPermitted || hook.alwaysActive()) {
                hook.process(event);
            }
        }
    }

    public void deregisterInteractEvent(String name, int index) {
        deregisterEvent(interactEvents, name, index);
    }
    
    public void processEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
        if(event.getDamager() instanceof Player) {
            Player damager = (Player) event.getDamager();
            String itemName = ItemUtil.getItemName(damager.getInventory().getItemInMainHand());
            if(itemName != null && damageOtherEntityEvents.containsKey(itemName)) {
                RegistryHook<DamageOtherEntityHook> rh = damageOtherEntityEvents.get(itemName);
                boolean isPermitted = rh.isPermitted(damager.getUniqueId());
                for(DamageOtherEntityHook hook: rh.getHooks()) {
                    if(isPermitted || hook.alwaysActive()) {
                        hook.process(event);
                    }
                }
            }
        }
        else if(event.getDamager() instanceof Projectile) {
            Projectile damager = (Projectile) event.getDamager();
            if(damager.getShooter() instanceof Player) {
                UUID uuid = damager.getUniqueId();
                if(projectileDamageActions.containsKey(uuid)) {
                    RegistryHook<DamageOtherEntityHook> rh = projectileDamageActions.get(uuid).getRegistryHook();
                    boolean isPermitted = rh.isPermitted(((Player) damager.getShooter()).getUniqueId());
                    for(DamageOtherEntityHook hook: rh.getHooks()) {
                        if(isPermitted || hook.alwaysActive()) {
                            hook.process(event);
                        }
                    }
                }
            }
        }
    }

    public void deregisterDamageOtherEntityEvent(String name, int index) {
        deregisterEvent(damageOtherEntityEvents, name, index);
    }

    public void processProjectileLaunchEvent(ProjectileLaunchEvent event) {
        if(!(event.getEntity() instanceof Projectile)) return;
        ProjectileSource shooter = ((Projectile) event.getEntity()).getShooter();
        if(!(shooter instanceof Player)) return;
        Player player = (Player) shooter;

        // TODO: Will the last snowball also trigger?
        String itemName = ItemUtil.getItemName(player.getInventory().getItemInMainHand());
        if(itemName == null) return;

        if(projectileLaunchEvents.containsKey(itemName)) {
            RegistryHook<ProjectileLaunchHook> rh = projectileLaunchEvents.get(itemName);
            boolean isPermitted = rh.isPermitted(player.getUniqueId());
            for(ProjectileLaunchHook hook: rh.getHooks()) {
                if(isPermitted || hook.alwaysActive()) {
                    hook.process(event);
                }
            }
        }

        // TODO!
        // if(projectileHitEvents.containsKey(itemName)) {
        //     addProjectileHitAction((Projectile) event.getEntity(), new ProjectileTrackerHookProcessor(projectileHitEvents.get(itemName)));
        // }
        if(damageOtherEntityEvents.containsKey(itemName)) {
            addProjectileDamageAction((Projectile) event.getEntity(), damageOtherEntityEvents.get(itemName));
        }
    }

    private void deregisterEvent(Map<String, ?> map, String name, int index) {
        RegistryHook rh = (RegistryHook) map.get(name);
        List<?> list = rh.getHooks();
        if(list == null) throw new IllegalArgumentException("No hooks available for item " + name + ".");
        if(index < 1 || index > list.size()) throw new IllegalArgumentException("No hook nr " + index + " available.");
        list.remove(index - 1);
        if(list.size() == 0) map.remove(name);
    }

    public boolean isEffectInUse(Effect effect) {
        for(String s: interactEvents.keySet()) {
            if(hookListUsesEffect(effect, interactEvents.get(s).getHooks())) return true;
        }
        for(String s: damageOtherEntityEvents.keySet()) {
            if(hookListUsesEffect(effect, damageOtherEntityEvents.get(s).getHooks())) return true;
        }
        return false;
    }

    private boolean hookListUsesEffect(Effect effect, List<?> list) {
        List<Hook> hooklist = (List<Hook>) list;
        for(Hook h: hooklist) {
            if(h.usesEffect(effect)) return true;
        }
        return false;
    }

    public void addProjectileHitAction(Projectile projectile, ProjectileTrackerAction action) {
        UUID uuid = projectile.getUniqueId();
        if(!projectileHitActions.containsKey(uuid)) {
            projectileHitActions.put(uuid, new HashSet<ProjectileTrackerAction>());
        }
        projectileHitActions.get(uuid).add(action);
    }

    public void processProjectileHitEvent(ProjectileHitEvent event) {
        Projectile projectile = event.getEntity();
        UUID uuid = projectile.getUniqueId();
        if(projectileHitActions.containsKey(uuid)) {
            for(ProjectileTrackerAction action: projectileHitActions.get(uuid)) {
                action.projectileHitEvent();
            }
        }
        if(projectileDamageActions.containsKey(uuid)) {
            Bukkit.getScheduler().runTask(AE_Main.getInstance(), new Runnable() {
                    public void run() {
                        projectileDamageActions.remove(uuid);
                    }
                });
        }
    }

    public void removeProjectileHitAction(Projectile projectile, ProjectileTrackerAction action) {
        UUID uuid = projectile.getUniqueId();
        Set<ProjectileTrackerAction> set = projectileHitActions.get(uuid);
        if(set != null) {
            set.remove(action);
            if(set.size() == 0) projectileHitActions.remove(uuid);
        }
    }
    
    public void addProjectileDamageAction(Projectile projectile, RegistryHook<DamageOtherEntityHook> registryHook) {
        UUID uuid = projectile.getUniqueId();
        projectileDamageActions.put(uuid, new ProjectileDamageTracker(registryHook));
    }

    public void setPermission(String itemName, String eventClass, String permission) {
        Map<String, RegistryHook<Hook>> eventMap = (Map<String, RegistryHook<Hook>>) eventMaps.get(eventClass);
        if(!eventMap.containsKey(itemName)) throw new IllegalArgumentException("Item not defined in this event class.");
        eventMap.get(itemName).setPermission(permission);
    }

    public void setCooldown(String itemName, String eventClass, double cooldown) {
        Map<String, RegistryHook<Hook>> eventMap = (Map<String, RegistryHook<Hook>>) eventMaps.get(eventClass);
        if(!eventMap.containsKey(itemName)) throw new IllegalArgumentException("Item not defined in this event class.");
        eventMap.get(itemName).setCooldown(new Double(cooldown * 1000).intValue());
    }
    
    public Set<String> getEventClasses() {
        return eventMaps.keySet();
    }
    
}
