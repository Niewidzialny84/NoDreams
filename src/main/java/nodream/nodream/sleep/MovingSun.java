package nodream.nodream.sleep;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class MovingSun extends BukkitRunnable {
    private Plugin plugin;
    private Player player;
    private int repeat;
    private int max;

    public MovingSun (Plugin plugin, Player player, long time) {
        this.plugin = plugin;
        this.player = player;
        repeat = 0;
        max = (int)Math.ceil( ((float)time)/200);
        runTaskTimer(plugin, 0 ,0);
    }

    @Override
    public void run() {
        if(repeat != max) {
            player.getWorld().setTime(player.getWorld().getTime() + 200);
            repeat++;
        } else {
            cancel();
            repeat = 0;
        }
    }
}
