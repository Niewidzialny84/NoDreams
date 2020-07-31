package nodream.nodream.sleep;

import nodream.nodream.config.Config;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;


public class TimeSet extends BukkitRunnable {

    private Player p;
    private Plugin plugin_;

    TimeSet(Player player,Plugin plugin) {
        p = player;
        plugin_ = plugin;
    }

    @Override
    public void run() {
        long time = p.getWorld().getTime() % 24000;
        if (time >= 12000 && time <= 24000 && !p.getWorld().isThundering()) {
            if(p.getWorld().hasStorm()) {
                p.getWorld().setWeatherDuration(1);
            }
            p.getWorld().setTime(p.getWorld().getTime() + (24000 - time));
            newDayMsg();
        }

        if (p.getWorld().isThundering()) {
            if (time >= 12000 && time <= 24000) {
                p.getWorld().setTime(p.getWorld().getTime() + (24000 - time));
            }
            p.getWorld().setThundering(false);
            p.getWorld().setWeatherDuration(1);
            newDayMsg();
        }
    }

    private void newDayMsg() {
        if(Config.isDoDisplayMsg()) plugin_.getServer().broadcastMessage(ChatColor.DARK_AQUA + Config.getNewDayMsg());
    }

}

