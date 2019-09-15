package nodream.nodream.Config;

import nodream.nodream.NoDream;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;


public class NoDreamReload implements CommandExecutor {

    private Plugin plugin_;

    public NoDreamReload(Plugin plugin) {
        plugin_ = plugin;
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
                    plugin_.reloadConfig();
                    NoDreamConfig.loadConfig(plugin_);
                    printReloadMsg(sender);
            } else if (args[0].equalsIgnoreCase("SLEEPING") && args[0] != null) {
                if (args[1].equalsIgnoreCase("TRUE") && args[1] != null) {
                    NoDreamConfig.setEnableSleeping(true);
                    ((NoDream) plugin_).RegisterEvent();
                    printSleepingMsg(sender, "TRUE");
                } else if (args[1].equalsIgnoreCase("FALSE") && args[1] != null) {
                    NoDreamConfig.setEnableSleeping(false);
                    ((NoDream) plugin_).RegisterEvent();
                    printSleepingMsg(sender, "FALSE");
                } else {
                    sender.sendMessage(ChatColor.GRAY +NoDreamConfig.getValuesAre()+" TRUE/FALSE");
                }
            } else if (args[0].equalsIgnoreCase("PHANTOMS") && args[0] != null) {
                if (args[1].equalsIgnoreCase("TRUE") && args[1] != null) {
                    NoDreamConfig.setNoPhantoms(false);
                    ((NoDream) plugin_).RegisterEvent();
                    printPhantomsMsg(sender, "TRUE");
                } else if (args[1].equalsIgnoreCase("FALSE") && args[1] != null) {
                    NoDreamConfig.setNoPhantoms(true);
                    ((NoDream) plugin_).RegisterEvent();
                    printPhantomsMsg(sender, "FALSE");
                } else {
                    sender.sendMessage(ChatColor.GRAY+NoDreamConfig.getValuesAre()+" TRUE/FALSE");
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
        sender.sendMessage(ChatColor.DARK_AQUA+"/nodreams reload"+ChatColor.GRAY+" - "+NoDreamConfig.getReloadHemlMsg());
        sender.sendMessage(ChatColor.DARK_AQUA+"/nodreams sleeping <true/false>"+ChatColor.GRAY+" - "+NoDreamConfig.getSleepingHelpMsg());
        sender.sendMessage(ChatColor.DARK_AQUA+"/nodreams phantoms <true/false>"+ChatColor.GRAY+" - "+NoDreamConfig.getPhantomsHelpMsg());
        sender.sendMessage(ChatColor.GRAY+NoDreamConfig.getWarningHelpMsg());
    }

    private void printReloadMsg(CommandSender sender) {
        sender.sendMessage(ChatColor.GRAY+NoDreamConfig.getReloadMsg());
    }

    private void printSleepingMsg(CommandSender sender, String value) {
        sender.sendMessage(ChatColor.GRAY+NoDreamConfig.getSleepingSetMsg()+" "+value);
    }

    private void printPhantomsMsg(CommandSender sender, String value) {
        sender.sendMessage(ChatColor.GRAY+NoDreamConfig.getPhantomsSetMsg()+" "+value);
    }

}
