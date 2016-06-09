package kipperorigin.armamentseffects.effects;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Listener;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers.Particle;

import kipperorigin.armamentseffects.AE_Main;
import kipperorigin.armamentseffects.event.AE_ProjectileEvent;
import kipperorigin.armamentseffects.resources.AE_GetPlayersInRadius;

public class AE_EffectPacketParticles extends AE_EffectParent implements Listener {

    int running;

    private AE_Main plugin;

    public AE_EffectPacketParticles(AE_Main plugin) {

        this.plugin = plugin;
    }
    
    private ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
    private AE_GetPlayersInRadius pir = new AE_GetPlayersInRadius();

	@Override
    public void run(final AE_ProjectileEvent event) {
		
		final Player player = event.getPlayer();
		final Projectile projectile = event.getProjectile();
		String[] args = event.getArgs();
		int timer = 1;
		PacketContainer particlePacket = protocolManager.createPacket(PacketType.Play.Server.WORLD_PARTICLES);
		
		if (args.length == 0 || args.length > 7)
			return;
		
		if (args.length >= 1)
			try {
				particlePacket.getParticles().write(0, Particle.valueOf(args[0].toUpperCase()));
			} catch (IllegalArgumentException e) {
				player.sendMessage("INVALID PARTICLE");
			}
		if (args.length >= 2)
			try {
				particlePacket.getIntegers().write(0, Particle.valueOf(args[1].toUpperCase()).getId());
			} catch (NumberFormatException e) {
				player.sendMessage("INVALID PARTICLE SPREAD");
			}
		if (args.length >= 3 && !args[2].equalsIgnoreCase("x"))
			try {
				particlePacket.getFloat().write(3, Float.valueOf(args[2]));
			} catch (NumberFormatException e) {
				player.sendMessage("MUST BE X OR A NUMBER");
			}
		if (args.length >= 4 && !args[3].equalsIgnoreCase("x"))
			try {
				particlePacket.getFloat().write(4, Float.valueOf(args[2]));
			} catch (NumberFormatException e) {
				player.sendMessage("MUST BE X OR A NUMBER");
			}
		if (args.length >= 5 && !args[4].equalsIgnoreCase("x"))
			try {
				particlePacket.getFloat().write(5, Float.valueOf(args[2]));
			} catch (NumberFormatException e) {
				player.sendMessage("MUST BE X OR A NUMBER");
			}
		if (args.length >= 6)
			try {
				timer = Integer.valueOf(args[5]);
			} catch (NumberFormatException e) {
				player.sendMessage("MUST BE AN INTEGER");
			}

    	
		try {
    		protocolManager.sendServerPacket(player, particlePacket);
    	} catch (InvocationTargetException e) {
    		throw new RuntimeException("Cannot send packet " + particlePacket, e);
    	}
		
        final int taskId = Bukkit.getScheduler().runTaskTimer(plugin, new Runnable() {
            @Override
            public void run() {
        		particlePacket.getFloat()
        			.write(0, (float) projectile.getLocation().getX())
        			.write(1, (float) projectile.getLocation().getY())
        			.write(2, (float) projectile.getLocation().getZ())
        			.write(6, 1F);
            	try {
            		List<Player> players = pir.getPlayersInRadius(projectile.getWorld(), 50, projectile);
            		for (int x = 0; x < players.size(); x++)
            			protocolManager.sendServerPacket(players.get(x), particlePacket);
            	} catch (InvocationTargetException e) {
            		throw new RuntimeException("Cannot send packet " + particlePacket, e);
            	}
                if (projectile.getLocation().getY() <= 0) {
                	int i = 0;
                	while(projectile.hasMetadata("Data " + String.valueOf(i))) {
                		Bukkit.getScheduler().cancelTask(projectile.getMetadata("Data " + String.valueOf(i)).get(0).asInt());
                		i++;
                	}
                	projectile.eject();
                	projectile.remove();
        		}
            }
        }, 0L, timer).getTaskId();
        MetadataValue x = new FixedMetadataValue(plugin, taskId);

        if (args.length == 7 && args[6].equalsIgnoreCase("permanent")) {
    		event.getPlayer().sendMessage("taskId = " + String.valueOf(taskId));
        } else {
			int i = 0;
			
			while(projectile.hasMetadata("Data " + String.valueOf(i))) 
				i++;
			
			projectile.setMetadata("Data " + String.valueOf(i), x);
            if (event.getRawEvent().isCancelled() && projectile.hasMetadata("Data"))
                Bukkit.getScheduler().cancelTask(projectile.getMetadata("Data").get(0).asInt());
        }
	}
}

