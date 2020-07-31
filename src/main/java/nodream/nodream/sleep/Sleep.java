package nodream.nodream.sleep;

import nodream.nodream.config.Config;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.World;
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
    private float currentPlayersPercentage;
    private Plugin plugin;

    private BukkitTask timeTask;

    public Sleep(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void inBed(PlayerBedEnterEvent e) {
        if(e.getBedEnterResult() == PlayerBedEnterEvent.BedEnterResult.OK && e.getPlayer().getWorld().getEnvironment() == World.Environment.NORMAL) {

            countPlayers(e.getPlayer(),1);

            if (currentPlayersPercentage >= Config.getPlayersPercentage()) {
                if(Config.isDoDisplayMsg()) {
                    displayMessage(e.getPlayer(), 2);
                }

                timeTask = new TimeSet(e.getPlayer(), plugin).runTaskLater(plugin, 100);

            } else if(Config.isDoDisplayMsg()) {
                displayMessage(e.getPlayer(),0);
            }
        }
    }

    @EventHandler
    public void outBed(PlayerBedLeaveEvent e) {

        countPlayers(e.getPlayer(),0);

        if (currentPlayersPercentage < Config.getPlayersPercentage()) {
            if(timeTask != null) {
                timeTask.cancel();
            }
        }

        if(Config.isDoDisplayMsg()) {
            displayMessage(e.getPlayer(),1);
        }
    }

    private int playersNeededPercentage() {
        currentPlayersPercentage = ((float) playersSleeping / (float) playersMax * 100);
        return (int)Math.ceil(((Config.getPlayersPercentage() - currentPlayersPercentage) * playersMax)/ 100);
    }

    private void countPlayers(Player p,int startingValue) {
        int playersSleeping_ = startingValue;
        int playersMax_ = 0;

        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            if(player.getWorld().getEnvironment() == World.Environment.NORMAL && (player.getGameMode().equals(GameMode.SURVIVAL) || player.getGameMode().equals(GameMode.ADVENTURE))) {
                if (player.isSleeping() && p != player) playersSleeping_++;
                playersMax_++;
            }
        }

        playersMax = playersMax_;
        playersSleeping = playersSleeping_;

        playersNeededPercentage();
    }

    private void displayMessage(Player p,int msgValue) {

        time = p.getWorld().getTime() % 24000;

        ChatColor c = ChatColor.GRAY;
        ChatColor c2 = ChatColor.DARK_AQUA;

        if ((p.getWorld().isThundering() || time >= 12000 && time <= 24000)) {
            switch (msgValue) {
                case 0:
                    String msg = c2 + p.getDisplayName() + c + " "+ Config.getIsSleepingMsg()+ " " + c2 + playersNeededPercentage() + " ";
                    if(playersNeededPercentage() == 1) {
                        plugin.getServer().broadcastMessage(msg+ Config.getPlayerNeeded());
                    } else {
                        plugin.getServer().broadcastMessage(msg+ Config.getPlayersNeeded());
                    }
                    break;
                case 1:
                    String msgnot = c2 +p.getDisplayName()  + c+ " " + Config.getIsNotSleepingMsg() + " " + c2 + playersNeededPercentage() + " ";
                    if(playersNeededPercentage() == 1) {
                        plugin.getServer().broadcastMessage(msgnot + Config.getPlayerNeeded());
                    } else if (playersNeededPercentage() == 0) {
                        plugin.getServer().broadcastMessage(c2 +p.getDisplayName() + c + " "+ Config.getIsNotSleepingMsg());
                    } else {
                        plugin.getServer().broadcastMessage(msgnot + Config.getPlayersNeeded());
                    }
                    break;
                case 2:
                    plugin.getServer().broadcastMessage(c2 +p.getDisplayName() + c + " "+ Config.getIsSleepingMsg());
                    break;
                default:
                    break;
            }
        }
    }
}
