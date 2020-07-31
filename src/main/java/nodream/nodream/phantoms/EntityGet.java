package nodream.nodream.phantoms;

import nodream.nodream.config.Config;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.Plugin;

public class EntityGet {

    public static EntityType getEntityTypeConfig(Plugin plugin) {
        String name = Config.getMobName();
        switch (name) {
            case "ENDERMAN":
                mobInfo(plugin,name);
                return EntityType.ENDERMAN;
            case "ZOMBIE":
                mobInfo(plugin,name);
                return EntityType.ZOMBIE;
            case"SKELETON":
                mobInfo(plugin,name);
                return EntityType.SKELETON;
            case"CREEPER":
                mobInfo(plugin,name);
                return EntityType.CREEPER;
            case"SPIDER":
                mobInfo(plugin,name);
                return EntityType.SPIDER;
            case"GHAST":
                mobInfo(plugin,name);
                return EntityType.GHAST;
            case"GUARDIAN":
                mobInfo(plugin,name);
                return EntityType.GUARDIAN;
            case"PILLAGER":
                mobInfo(plugin,name);
                return EntityType.PILLAGER;
            case"BLAZE":
                mobInfo(plugin,name);
                return EntityType.BLAZE;
            case"DROWNED":
                mobInfo(plugin,name);
                return EntityType.DROWNED;
            default:
                mobInfo(plugin,"ENDERMAN - set by default due an error");
                return EntityType.ENDERMAN;
        }
    }

    private static void mobInfo(Plugin plugin, String name) {
        plugin.getLogger().info("Phantom membrane will now drop from "+name);
    }
}
