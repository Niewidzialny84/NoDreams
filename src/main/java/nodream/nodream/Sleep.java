package nodream.nodream;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.WorldType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

public class Sleep implements Listener {
    private int playersSleeping = 0;
    private int playersMax = 0;
    private long time = 0;
    float currentPlayersPercentage = 50;
    private Plugin plugin_;

    //CONFIG VALUES
    private String isSleepingMsg;
    private String isNotSleepingMsg;
    private String playersNeeded;
    private String playerNeeded;
    private int playersPercentage;
    private boolean doDisplayMsg;

    private BukkitTask timeTask;

    public Sleep(Plugin plugin) {
        Bukkit.getPluginManager().registerEvents(this,plugin);
        isSleepingMsg = plugin.getConfig().getString("isSleepingMsg","is sleeping.");
        isNotSleepingMsg = plugin.getConfig().getString("isNotSleepingMsg","is not sleeping.");
        playersNeeded = plugin.getConfig().getString("playersNeeded", "players needed");
        playerNeeded = plugin.getConfig().getString("playerNeeded", "player needed");
        doDisplayMsg = plugin.getConfig().getBoolean("doDisplayMsg",true);

        playersPercentage = plugin.getConfig().getInt("playersPercentage",50);

        if(playersPercentage>100 || playersPercentage < 0) {
            playersPercentage = 50;
        }

        plugin_ = plugin;
    }

    @EventHandler
    public void inBed(PlayerBedEnterEvent e) {
        if(e.getBedEnterResult() == PlayerBedEnterEvent.BedEnterResult.OK && e.getPlayer().getWorld().getEnvironment() == World.Environment.NORMAL) {

            countPlayers(e.getPlayer(),1);

            if (currentPlayersPercentage >= playersPercentage) {
                if(doDisplayMsg) {
                    displayMessage(e.getPlayer(), 2);
                }

                timeTask = new TimeSet(e.getPlayer(),plugin_).runTaskLater(plugin_, 100);

            } else if(doDisplayMsg) {
                displayMessage(e.getPlayer(),0);
            }
        }
    }

    @EventHandler
    public void outBed(PlayerBedLeaveEvent e) {

        countPlayers(e.getPlayer(),0);

        if (currentPlayersPercentage >= playersPercentage) {
            timeTask.cancel();

        }

        if(doDisplayMsg) {
            displayMessage(e.getPlayer(),1);
        }
    }

    private int playersNeededPercentage() {

        currentPlayersPercentage = ((float) playersSleeping / (float) playersMax * 100);
        return (int)Math.ceil((currentPlayersPercentage - playersPercentage) / 100);
    }

    private void countPlayers(Player p,int startingValue) {
        int playersSleeping_ = startingValue;
        int playersMax_ = 0;

        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            if(player.getWorld().getEnvironment() == World.Environment.NORMAL) {
                if (player.isSleeping() && p != player) playersSleeping_++;
                playersMax_++;
            }
        }

        playersMax = playersMax_;
        playersSleeping = playersSleeping_;
    }

    private void displayMessage(Player p,int msgValue) {

        time = p.getWorld().getTime() % 24000;

        ChatColor c = ChatColor.GRAY;
        ChatColor c2 = ChatColor.DARK_AQUA;

        if ((p.getWorld().isThundering() || time >= 12000 && time <= 24000)) {
            switch (msgValue) {
                case 0:
                    if(playersNeededPercentage() == 1) {
                        plugin_.getServer().broadcastMessage(c2 + p.getDisplayName() + c + " "+ isSleepingMsg+ " " + c2 + playersNeededPercentage() + " "+playerNeeded);
                    } else {
                        plugin_.getServer().broadcastMessage(c2 +p.getDisplayName()  + c+ " "+ isSleepingMsg+ " " + c2 + playersNeededPercentage() + " "+playersNeeded);
                    }
                    break;
                case 1:
                    if(playersNeededPercentage() == 1) {
                        plugin_.getServer().broadcastMessage(c2 +p.getDisplayName()  + c+ " " + isNotSleepingMsg + " " + c2 + playersNeededPercentage() + " " + playerNeeded);
                    } else  {
                        plugin_.getServer().broadcastMessage(c2 +p.getDisplayName()  + c+ " " + isNotSleepingMsg + " " + c2 + playersNeededPercentage() + " " + playersNeeded);
                    }
                    break;
                case 2:
                    plugin_.getServer().broadcastMessage(c2 +p.getDisplayName() + c + " "+ isSleepingMsg);
                    break;
                default:
                    break;
            }
        }
    }
}
