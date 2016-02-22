package kipperorigin.armamentseffects.effects;

import kipperorigin.armamentseffects.AE_Main;
import kipperorigin.armamentseffects.event.AE_InteractEvent;
import kipperorigin.armamentseffects.resources.AE_LaunchTnT;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class AE_EffectTnT extends AE_EffectParent {

    @SuppressWarnings("unused")
    private AE_Main plugin;

    public AE_EffectTnT(AE_Main plugin) {
        this.plugin = plugin;
    }
    
    AE_LaunchTnT tnt = new AE_LaunchTnT();

    @Override
    public void run(final AE_InteractEvent event) {

        /* -t Trishot
         * -sg Shotgun
         * -mr Mortar
         * -mn Mine
         * -sn Sniper?
         */
        
        String types[] = {"-t","-sg","-mr","-mn","-sn","c"};
        final String args[] = event.getArgs();
        Player player = event.getPlayer();
        Location loc = player.getLocation();
        loc = loc.add(0, 1, 0);
        
        Vector x = new Vector(0, .35, 0);
        Vector sg[] = new Vector[]{new Vector(Math.random(), Math.random(), Math.random()), new Vector(Math.random(), Math.random(), Math.random()), new Vector(Math.random(), Math.random(), Math.random()), new Vector(Math.random(), Math.random(), Math.random()), new Vector(Math.random(), Math.random(), Math.random()), new Vector(Math.random(), Math.random(), Math.random()), new Vector(Math.random(), Math.random(), Math.random()), new Vector(Math.random(), Math.random(), Math.random())};

        if(args.length >= 1 && (args[0].equalsIgnoreCase(types[0]) || args[0].equalsIgnoreCase(types[5]))) {
            
            int multiply = 1;
            int timer = 0;

            try {
                if (args.length >= 2) {
                    multiply = Integer.parseInt(args[1]);
                }
                if (args.length >= 3) {
                    timer = Integer.parseInt(args[2]);
                }
            } catch (NumberFormatException e) {
                return;
            }
            
            if (args.length >= 4) {
                return;
            }

            Vector v = (event.getPlayer().getEyeLocation().getDirection()).multiply(multiply);
            v.add(x);
            if (timer <= 0)
                tnt.fireTnTNoTimer(v, loc);
            else
                tnt.fireTnT(v, loc, timer);

            if (args[0].equalsIgnoreCase(types[0])) { 
                Vector a[] = new Vector[]{v, v, v, v, v, v, v, v};
                
                for(int i = 0; i < a.length; i++) {
                    a[i].add(sg[i]);
                    if (timer <= 0)
                        tnt.fireTnTNoTimer(a[i], loc);
                    else
                        tnt.fireTnT(a[i], loc, timer);
                }
            }
            
        }
    }
}
