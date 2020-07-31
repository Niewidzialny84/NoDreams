package nodream.nodream.sleep;

import nodream.nodream.config.Config;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;


public class TimeSet extends BukkitRunnable {

    private Player player;
    private Plugin plugin;
    private boolean isMoving;

    TimeSet(Plugin plugin,Player player) {
        this.player = player;
        this.plugin = plugin;
        isMoving = false;
    }

    @Override
    public void run() {
        long time = player.getWorld().getTime() % 24000;
        if (time >= 12000 && time <= 24000 && !player.getWorld().isThundering()) {
            if(player.getWorld().hasStorm() || player.getWorld().getWeatherDuration() > 2) {
                player.getWorld().setWeatherDuration(1);
            }

            if(Config.isNewDaySun()) {
                new MovingSun(plugin, player,24000-time);
            } else {
                player.getWorld().setTime(player.getWorld().getTime() + (24000 - time));
            }

            newDayMsg();
        }

        if (player.getWorld().isThundering()) {
            if (time >= 12000 && time <= 24000) {
                if(Config.isNewDaySun()) {
                    new MovingSun(plugin, player,24000-time);
                } else {
                    player.getWorld().setTime(player.getWorld().getTime() + (24000 - time));
                }
            }
            player.getWorld().setThundering(false);
            player.getWorld().setWeatherDuration(1);
            newDayMsg();
        }
    }

    public boolean isSunMoving() {
        return isMoving;
    }

    private void newDayMsg() {
        isMoving = true;
        if(Config.isDoDisplayMsg()) {
            plugin.getServer().broadcastMessage(ChatColor.DARK_AQUA + Config.getNewDayMsg());
        }
    }
}

