package me.lehreeeee.mmleaderboard.commands;

import me.lehreeeee.mmleaderboard.MMLeaderboard;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.UUID;

public class Leaderboard implements CommandExecutor {
    private final MMLeaderboard plugin;

    public Leaderboard(MMLeaderboard plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender,@NotNull Command command,@NotNull String label, String [] args){
        if (args.length == 2) {
            String action = args[0].toLowerCase();
            try {
                UUID uuid = UUID.fromString(args[1]);

                if (action.equals("add")){
                    if (plugin.isEntityTracked(uuid)){
                        sendFeedbackMessage(sender,"[MMLeaderboard] Cannot add the entity " + uuid + ", it's already in tracked list.");
                        return true;
                    }
                    plugin.addTrackedEntity(uuid);
                    sendFeedbackMessage(sender,"[MMLeaderboard] Added entity " + uuid + " to tracked list.");
                    return true;
                } else if (action.equals("remove")) {
                    if (!plugin.isEntityTracked(uuid)){
                        String msg = "[MMLeaderboard] Cannot remove the entity " + uuid + ", it's not in tracked list.";
                        Bukkit.getLogger().info(msg);
                        if (sender instanceof Player) sender.sendMessage(msg);
                        return true;
                    }
                    plugin.removeTrackedEntity(uuid);
                    sendFeedbackMessage(sender,"[MMLeaderboard] Removed entity "  + uuid + " from tracked list.");
                    return true;
                }
            } catch (IllegalArgumentException e) {
                sendFeedbackMessage(sender,"[MMLeaderboard] Invalid UUID format.");
                return false;
            }
        } else if (args.length == 1) {
             if (args[0].equals("list")){
                Set<UUID> trackedEntityList = plugin.getTrackedEntities();
                if (trackedEntityList.isEmpty()) {
                    sendFeedbackMessage(sender,"[MMLeaderboard] No entities are currently tracked.");
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

    public void sendFeedbackMessage(CommandSender sender, String msg){
        Bukkit.getLogger().info(msg);
        if (sender instanceof Player) sender.sendMessage(msg);
    }
}
