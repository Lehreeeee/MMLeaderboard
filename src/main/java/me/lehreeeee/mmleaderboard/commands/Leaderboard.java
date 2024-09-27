package me.lehreeeee.mmleaderboard.commands;

import me.lehreeeee.mmleaderboard.MMLeaderboard;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Set;
import java.util.UUID;

public class Leaderboard implements CommandExecutor {
    private MMLeaderboard plugin;

    public Leaderboard(MMLeaderboard plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String [] args){
        if (args.length == 2) {
            String action = args[0].toLowerCase();
            try {
                UUID uuid = UUID.fromString(args[1]);

                if (action.equals("add")){
                    if (plugin.isEntityTracked(uuid)){
                        String msg = "[MMLeaderboard] Entity " + uuid + " is already in tracked list.";
                        Bukkit.getLogger().info(msg);
                        if (sender instanceof Player) sender.sendMessage(msg);
                        return true;
                    }
                    plugin.addTrackedEntity(uuid);
                    String msg = "[MMLeaderboard] Added entity " + uuid + " to tracked list.";
                    Bukkit.getLogger().info(msg);
                    if (sender instanceof Player) sender.sendMessage(msg);
                    return true;
                } else if (action.equals("remove")) {
                    plugin.removeTrackedEntity(uuid);
                    String msg = "[MMLeaderboard] Removed entity "  + uuid + " from tracked list.";
                    Bukkit.getLogger().info(msg);
                    if (sender instanceof Player) sender.sendMessage(msg);
                    return true;
                }
            } catch (IllegalArgumentException e) {
                String msg = "[MMLeaderboard] Invalid UUID format.";
                Bukkit.getLogger().info(msg);
                if (sender instanceof Player) sender.sendMessage(msg);
                return false;
            }
        } else if (args.length == 1) {
             if (args[0].equals("list")){
                Set<UUID> trackedEntityList = plugin.getTrackedEntities();
                if (trackedEntityList.isEmpty()) {
                    String msg = "[MMLeaderboard] No entities are currently tracked.";
                    Bukkit.getLogger().info(msg);
                    if (sender instanceof Player) sender.sendMessage(msg);
                } else {
                    StringBuilder message = new StringBuilder("[MMLeaderboard] Tracked entities: ");
                    for (UUID uuidinlist : trackedEntityList) {
                        message.append(uuidinlist.toString()).append(", ");
                    }
                    // Remove the trailing comma and space
                    if (message.length() > 0) {
                        message.setLength(message.length() - 2);
                    }
                    sender.sendMessage(message.toString());
                }
                return true;
            }
        }
        return false;
    }
}
