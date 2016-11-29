package kipperorigin.armamentseffects.registry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import kipperorigin.armamentseffects.managers.Effect;
import kipperorigin.armamentseffects.managers.EffectManager;
import kipperorigin.armamentseffects.hooks.DamageOtherEntityHook;
import kipperorigin.armamentseffects.hooks.Hook;
import kipperorigin.armamentseffects.hooks.InteractHook;
import kipperorigin.armamentseffects.util.ItemUtil;

@SerializableAs("Registry")
public class Registry implements ConfigurationSerializable
{
    private Map<String, RegistryHook<InteractHook>> interactEvents;
    private Map<String, RegistryHook<DamageOtherEntityHook>> damageOtherEntityEvents;

    static Registry instance;

    private PermissionList permissionList;
    
    public Registry() {
	interactEvents = new HashMap<>();
	damageOtherEntityEvents = new HashMap<>();
        instance = this;
        permissionList = new PermissionList();
    }

    public Registry(Map<String, Object> config) {
        // TODO!!! :
        interactEvents = (Map<String, RegistryHook<InteractHook>>) config.get("interactEvents");
        damageOtherEntityEvents = (Map<String, RegistryHook<DamageOtherEntityHook>>) config.get("damageOtherEntityEvents");
        permissionList = (PermissionList) config.get("permissionList");
        instance = this;
    }

    public static Registry getInstance() {
        return instance;
    }

    public PermissionList getPermissionList() {
        return permissionList;
    }
    
    public Map<String, Object> serialize() {
        Map<String, Object> ret = new HashMap<>();
        ret.put("interactEvents", interactEvents);
        ret.put("damageOtherEntityEvents", damageOtherEntityEvents);
        ret.put("permissionList", permissionList);
        return ret;
    }

    public List<String> getHookList() {
        List<String> ret = new ArrayList<>();
        ret.add("Interact:");
        for(String hk: interactEvents.keySet()) {
            System.out.println("Get interact info for " + hk);
            ret.add("  " + hk + ":");
            int cnt = 1;
            for(InteractHook h: interactEvents.get(hk).getHooks()) {
                ret.add("    " + (cnt++) + ") " + ((Hook) h).getInfo());
            }
        }
        ret.add("Damage Other Entity:");
        for(String hk: damageOtherEntityEvents.keySet()) {
            ret.add("  " + hk + ":");
            int cnt = 1;
            for(DamageOtherEntityHook h: damageOtherEntityEvents.get(hk).getHooks()) {
                ret.add("    " + (cnt++) + ") " + ((Hook) h).getInfo());
            }
        }
        return ret;
    }

    public List<String> getHookList(String itemName) {
        List<String> ret = new ArrayList<>();
        if(interactEvents.containsKey(itemName)) {
            ret.add("Interact:");
            int cnt = 1;
            for(InteractHook h: interactEvents.get(itemName).getHooks()) {
                ret.add("  " + (cnt++) + ") " + ((Hook) h).getInfo());
            }
        }
        if(damageOtherEntityEvents.containsKey(itemName)) {
            ret.add("Damage Other Entity:");
            int cnt = 1;
            for(DamageOtherEntityHook h: damageOtherEntityEvents.get(itemName).getHooks()) {
                ret.add("  " + (cnt++) + ") " + ((Hook) h).getInfo());
            }            
        }
        return ret;
    }
    
    public void registerInteractEvent(String name, InteractHook hook) {
	if(interactEvents.get(name) == null) interactEvents.put(name, new RegistryHook<>());
	interactEvents.get(name).getHooks().add(hook);
    }

    public void processInteractEvent(PlayerInteractEvent event) {
	if(event.getHand() != EquipmentSlot.HAND) return;

        String itemName = ItemUtil.getItemName(event.getItem());
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
    
    public void registerDamageOtherEntityEvent(String name, DamageOtherEntityHook hook) {
	if(damageOtherEntityEvents.get(name) == null) damageOtherEntityEvents.put(name, new RegistryHook<>());
	damageOtherEntityEvents.get(name).getHooks().add(hook);
    }

    public void processEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
	if(event.getDamager() instanceof Player) {
	    Player damager = (Player) event.getDamager();

            String itemName = ItemUtil.getItemName(damager.getInventory().getItemInMainHand());
            if(itemName == null) return;
            if(!damageOtherEntityEvents.containsKey(itemName)) return;
            
            RegistryHook<DamageOtherEntityHook> rh = damageOtherEntityEvents.get(itemName);
            boolean isPermitted = rh.isPermitted(damager.getUniqueId());
            for(DamageOtherEntityHook hook: rh.getHooks()) {
                if(isPermitted || hook.alwaysActive()) {
                    hook.process(event);
                }
            }
        }
    }

    public void deregisterDamageOtherEntityEvent(String name, int index) {
        deregisterEvent(damageOtherEntityEvents, name, index);
    }
    
    private void deregisterEvent(Map<String, ?> map, String name, int index) {
        List<?> list = (List<Hook>)map.get(name);
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
}
