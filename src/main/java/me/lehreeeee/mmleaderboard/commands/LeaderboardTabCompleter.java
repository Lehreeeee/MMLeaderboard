package me.lehreeeee.mmleaderboard.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class LeaderboardTabCompleter implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> suggestions = new ArrayList<>();

        if (args.length == 1) {
            suggestions.add("add");
            suggestions.add("remove");
            suggestions.add("list");
        } else if ((args[0].equals("add") || args[0].equals("remove")) && args.length == 2) {
            suggestions.add("<UUID>");
        }

        return suggestions;
    }
}
