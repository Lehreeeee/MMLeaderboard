package me.lehreeeee.mmleaderboard.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.List;

public class MMLCommandTabCompleter implements TabCompleter {
    private final List<String> commands = List.of("add", "remove", "list", "link", "unlink");

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {

        if(args.length == 1) {
            return commands;
        }

        if(args.length == 2 && !args[0].equalsIgnoreCase("list")) {
            if(args[0].equalsIgnoreCase("link") || args[0].equalsIgnoreCase("unlink")) return List.of("<ChildUUID>");
            else return List.of("<UUID>");
        }

        if(args.length == 3 && args[0].equalsIgnoreCase("link")){
            return List.of("<ParentUUID>");
        }

        return null;
    }
}
