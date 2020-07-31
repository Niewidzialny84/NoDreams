package nodream.nodream.config;

import nodream.nodream.NoDreams;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;


public class Commands implements CommandExecutor {

    private Plugin plugin;

    public Commands(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender != null && cmd != null && label != null) {
            if ((sender instanceof Player || sender instanceof CommandSender) && (sender.hasPermission("nodreams") || sender.isOp())) {

                if(args.length == 0 || args == null) {
                    printHelpMsg(sender);
                    return true;
                }

                if (args[0].equalsIgnoreCase("RELOAD") && args[0] != null) {
                    plugin.reloadConfig();
                    Config.loadConfig(plugin);
                    printReloadMsg(sender);
            } else if (args[0].equalsIgnoreCase("SLEEPING") && args[0] != null) {
                if (args[1].equalsIgnoreCase("TRUE") && args[1] != null) {
                    Config.setEnableSleeping(true);
                    ((NoDreams) plugin).RegisterEvent();
                    printSleepingMsg(sender, "TRUE");
                } else if (args[1].equalsIgnoreCase("FALSE") && args[1] != null) {
                    Config.setEnableSleeping(false);
                    ((NoDreams) plugin).RegisterEvent();
                    printSleepingMsg(sender, "FALSE");
                } else {
                    sender.sendMessage(ChatColor.GRAY + Config.getValuesAre()+" TRUE/FALSE");
                }
            } else if (args[0].equalsIgnoreCase("PHANTOMS") && args[0] != null) {
                if (args[1].equalsIgnoreCase("TRUE") && args[1] != null) {
                    Config.setNoPhantoms(false);
                    ((NoDreams) plugin).RegisterEvent();
                    printPhantomsMsg(sender, "TRUE");
                } else if (args[1].equalsIgnoreCase("FALSE") && args[1] != null) {
                    Config.setNoPhantoms(true);
                    ((NoDreams) plugin).RegisterEvent();
                    printPhantomsMsg(sender, "FALSE");
                } else {
                    sender.sendMessage(ChatColor.GRAY+ Config.getValuesAre()+" TRUE/FALSE");
                }
            } else if(args[0].equalsIgnoreCase("HELP")) {
                    printHelpMsg(sender);
            }
        }
    }
        return true;
    }

    private void printHelpMsg(CommandSender sender) {
        sender.sendMessage(ChatColor.DARK_AQUA+"NoDreams Help:");
        sender.sendMessage(ChatColor.DARK_AQUA+"/nodreams reload"+ChatColor.GRAY+" - "+ Config.getReloadHemlMsg());
        sender.sendMessage(ChatColor.DARK_AQUA+"/nodreams sleeping <true/false>"+ChatColor.GRAY+" - "+ Config.getSleepingHelpMsg());
        sender.sendMessage(ChatColor.DARK_AQUA+"/nodreams phantoms <true/false>"+ChatColor.GRAY+" - "+ Config.getPhantomsHelpMsg());
        sender.sendMessage(ChatColor.GRAY+ Config.getWarningHelpMsg());
    }

    private void printReloadMsg(CommandSender sender) {
        sender.sendMessage(ChatColor.GRAY+ Config.getReloadMsg());
    }

    private void printSleepingMsg(CommandSender sender, String value) {
        sender.sendMessage(ChatColor.GRAY+ Config.getSleepingSetMsg()+" "+value);
    }

    private void printPhantomsMsg(CommandSender sender, String value) {
        sender.sendMessage(ChatColor.GRAY+ Config.getPhantomsSetMsg()+" "+value);
    }

}
