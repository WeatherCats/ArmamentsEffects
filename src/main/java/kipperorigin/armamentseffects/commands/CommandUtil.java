package kipperorigin.armamentseffects.commands;

import kipperorigin.armamentseffects.AE_Main;
import kipperorigin.armamentseffects.registry.Registry;

public class CommandUtil
{
    public static void saveConfig() {
        AE_Main.getInstance().saveEffects();
    }

    public static void clearPermissionCache() {
        Registry.getInstance().clearPermissionCache();
    }
}
