package kipperorigin.armamentseffects.managers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cubeville.commons.utils.Colorize;

import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.entity.Player;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

@SerializableAs("PiercingEffect")
public class PiercingEffect extends Effect
{
    PiercingType type;
    double factor;
    boolean stats;
    
    public PiercingEffect(String name, PiercingType type, double factor, boolean stats) {
        super(name);
        this.type = type;
        this.factor = factor;
        this.stats = stats;
    }

    public PiercingEffect(Map<String, Object> config) {
        factor = (double) config.get("factor");
        type = PiercingType.valueOf((String) config.get("type"));
        stats = (boolean) config.get("stats");
        setName((String) config.get("name"));
    }

    public Map<String, Object> serialize() {
        Map<String, Object> ret = getSerializationBase();
        ret.put("factor", factor);
        ret.put("type", type.toString());
        ret.put("stats", stats);
        return ret;
    }
    
    public void play(EntityDamageByEntityEvent event, Player damager) {
        if(!(event.getEntity() instanceof LivingEntity)) return;
        
        if(stats) {
            damager.sendMessage(Colorize.addColor("&6-----Stats Start-----"));
            damager.sendMessage(Colorize.addColor("&eOriginal Damage = " + event.getDamage()));
            damager.sendMessage(Colorize.addColor("&ePiercing Type = " + type));
            damager.sendMessage(Colorize.addColor("&ePiercing Ammount = " + factor));
            damager.sendMessage(Colorize.addColor("&cPre-Piercing Protection"));
            damager.sendMessage(Colorize.addColor("&cAbsorbtion&f = " + event.getDamage(EntityDamageEvent.DamageModifier.ABSORPTION)));
            damager.sendMessage(Colorize.addColor("&cArmor&f = " + event.getDamage(EntityDamageEvent.DamageModifier.ARMOR)));
            damager.sendMessage(Colorize.addColor("&cMagic&f = " + event.getDamage(EntityDamageEvent.DamageModifier.MAGIC)));
            damager.sendMessage(Colorize.addColor("&cResistance&f = " + event.getDamage(EntityDamageEvent.DamageModifier.RESISTANCE)));
            damager.sendMessage(Colorize.addColor("&c__________"));
            damager.sendMessage(Colorize.addColor("&cFinal Damage = " + event.getFinalDamage()));
        }

        double rfactor = 1 - (factor / 100);
        if(type == PiercingType.TRUE || type == PiercingType.PHYSICAL || type == PiercingType.ABSORPTION)
            changeDamage(EntityDamageEvent.DamageModifier.ABSORPTION, event, rfactor);
        if(type == PiercingType.TRUE || type == PiercingType.PHYSICAL || type == PiercingType.ARMOR)
            changeDamage(EntityDamageEvent.DamageModifier.ARMOR, event, rfactor);
        if(type == PiercingType.TRUE || type == PiercingType.MAGICAL || type == PiercingType.MAGIC)
            changeDamage(EntityDamageEvent.DamageModifier.MAGIC, event, rfactor);
        if(type == PiercingType.TRUE || type == PiercingType.MAGICAL || type == PiercingType.RESISTANCE)
            changeDamage(EntityDamageEvent.DamageModifier.RESISTANCE, event, rfactor);

        if(stats) {
            damager.sendMessage(Colorize.addColor("&aPost-Piercing Protection:"));
            damager.sendMessage(Colorize.addColor("&aAbsorbtion&f = " + event.getDamage(EntityDamageEvent.DamageModifier.ABSORPTION)));
            damager.sendMessage(Colorize.addColor("&aArmor&f = " + event.getDamage(EntityDamageEvent.DamageModifier.ARMOR)));
            damager.sendMessage(Colorize.addColor("&aMagic&f = " + event.getDamage(EntityDamageEvent.DamageModifier.MAGIC)));
            damager.sendMessage(Colorize.addColor("&aResistance&f = " + event.getDamage(EntityDamageEvent.DamageModifier.RESISTANCE)));
            damager.sendMessage(Colorize.addColor("&a__________"));
            damager.sendMessage(Colorize.addColor("&aFinal Damage = " + event.getFinalDamage()));
            damager.sendMessage(Colorize.addColor("&6-----Stats End-----"));
        }
    }

    private void changeDamage(EntityDamageEvent.DamageModifier modifier, EntityDamageByEntityEvent event, double factor) {
        event.setDamage(modifier, event.getDamage(modifier) * factor);
    }
    
    public static enum PiercingType {
        TRUE, MAGICAL, PHYSICAL, ABSORPTION, ARMOR, MAGIC, RESISTANCE;
    }

    public List<String> getInfo() {
        List<String> ret = getInfoBase();
        ret.add("Piercing: " + type + " (" + factor + ")");
        return ret;
    }
    
    public String getType() {
        return "Piercing";
    }
}
