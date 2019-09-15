package nodream.nodream.Config;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class NoDreamTabComplete implements TabCompleter {
    private final static List<String> EMPTY = new ArrayList<>();


    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if(command.getLabel().equalsIgnoreCase(alias)) {
            if(args.length == 0) {
                return NoDreamConfig.getSubCommands();
            } else if(args.length == 1) {
                return NoDreamConfig.getSubCommands();
            } else if(args.length == 2) {
                if(args[0].equalsIgnoreCase("SLEEPING") || args[0].equalsIgnoreCase("PHANTOMS")) {
                    return NoDreamConfig.getSubCommandsBool();
                }
            }
            return EMPTY;
        }
        return EMPTY;
    }
}
