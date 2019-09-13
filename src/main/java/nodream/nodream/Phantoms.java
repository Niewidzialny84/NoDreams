package nodream.nodream;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.Random;

public class Phantoms implements Listener {
    private Plugin plugin_;
    private EntityType mobDrop;

    Phantoms(Plugin plugin) {
        if(plugin.getConfig().getBoolean("noPhantoms",true)) {
            Bukkit.getPluginManager().registerEvents(this,plugin);
            plugin.getLogger().info("Phantoms spawning is Disabled");
            mobDrop =  EntityGet.getEntityTypeConfig(plugin);
        } else {
            plugin.getLogger().info("Phantoms spawning is Enabled");
        }

        plugin_ = plugin;
    }

    @EventHandler
    public void onSpawn(CreatureSpawnEvent e) {
        if(e.getEntityType() == EntityType.PHANTOM) {
            if(e.getEntity().getWorld().getEnvironment() == World.Environment.NORMAL) {
                e.getEntity().remove();
            }
        }
    }

    @EventHandler
    public void onDeath(EntityDeathEvent e) {
        if(e.getEntityType() == mobDrop) {
            if (e.getEntity().getKiller() != null) {
                ItemStack weapon = e.getEntity().getKiller().getItemInHand();

                int a = weapon.getEnchantmentLevel(Enchantment.LOOT_BONUS_MOBS);

                if (a == 0) {
                    e.getDrops().add(new ItemStack(Material.PHANTOM_MEMBRANE, randomInt(0, 2)));
                } else {
                    e.getDrops().add(new ItemStack(Material.PHANTOM_MEMBRANE, randomInt(0 + a, 3 + a)));
                }
            } else {
                //no drop if not player
            }
        }

        if(e.getEntityType() == EntityType.BAT) {
            if (e.getEntity().getKiller() != null) {
                e.getDrops().add(new ItemStack(Material.PHANTOM_MEMBRANE, 1));
            }
        }
    }

    private int randomInt(int min,int max) {
        Random randomGenerator = new Random();
        return randomGenerator.nextInt(max) + min;
    }
}
