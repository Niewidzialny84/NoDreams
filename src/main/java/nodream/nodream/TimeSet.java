package nodream.nodream;

import org.bukkit.ChatColor;
import org.bukkit.WeatherType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;


public class TimeSet extends BukkitRunnable {

    private Player p;
    private Plugin plugin_;
    private String newDayMsg;
    private boolean doDisplayMsg;

    TimeSet(Player player,Plugin plugin) {
        p = player;
        plugin_ = plugin;
        newDayMsg = plugin.getConfig().getString("newDayMsg", "New day has come!");
        doDisplayMsg = plugin.getConfig().getBoolean("doDisplayMsg",true);
    }

    @Override
    public void run() {
            long time = p.getWorld().getTime() % 24000;
                if (time >= 12000 && time <= 24000 && !p.getWorld().isThundering()) {

                    //System.out.println(p.getWorld().getWeatherDuration());
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
        if(doDisplayMsg) plugin_.getServer().broadcastMessage(ChatColor.DARK_AQUA + newDayMsg);
    }

}

