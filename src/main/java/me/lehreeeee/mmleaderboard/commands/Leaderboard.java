package me.lehreeeee.mmleaderboard.commands;

import me.lehreeeee.mmleaderboard.MMLeaderboard;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

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
                        Bukkit.getLogger().info("[MMLeaderboard] Entity " + uuid + " is already in tracked list.");
                        return true;
                    }
                    plugin.addTrackedEntity(uuid);
                    Bukkit.getLogger().info("[MMLeaderboard] Added entity " + uuid + " to tracked list.");
                    return true;
                } else if (action.equals("remove")) {
                    plugin.removeTrackedEntity(uuid);
                    Bukkit.getLogger().info("[MMLeaderboard] Removed entity "  + uuid + " from tracked list.");
                    return true;
                }
            } catch (IllegalArgumentException e) {
                sender.sendMessage("[MMLeaderboard] Invalid UUID format.");
                return false;
            }
        } else if (args.length == 1) {
             if (args[0].equals("list")){
                Set<UUID> trackedEntityList = plugin.getTrackedEntities();
                if (trackedEntityList.isEmpty()) {
                    sender.sendMessage("[MMLeaderboard] No entities are currently tracked.");
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
