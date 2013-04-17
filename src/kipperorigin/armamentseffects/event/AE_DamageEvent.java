package kipperorigin.armamentseffects.event;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class AE_DamageEvent extends AE_Event {

	private final LivingEntity victim;
	private final EntityDamageByEntityEvent event;

	public AE_DamageEvent(Player attacker, LivingEntity victim, EntityDamageByEntityEvent event) {
		super(attacker);

		this.victim = victim;
		this.event = event;
	}

	public LivingEntity getVictim() {
		return victim;
	}

	public EntityDamageByEntityEvent getRawEvent() {
		return event;
	}

}
