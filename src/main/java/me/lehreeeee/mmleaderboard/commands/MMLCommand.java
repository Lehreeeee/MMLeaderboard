package me.lehreeeee.mmleaderboard.commands;

import me.lehreeeee.mmleaderboard.MMLeaderboard;
import me.lehreeeee.mmleaderboard.utils.MessageHelper;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.UUID;
import java.util.logging.Logger;

public class MMLCommand implements CommandExecutor {
    private final MMLeaderboard plugin;
    private final Logger logger;

    public MMLCommand(MMLeaderboard plugin) {
        this.plugin = plugin;
        this.logger = plugin.getLogger();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender,@NotNull Command command,@NotNull String label, String [] args){

         if(args.length == 1 && args[0].equals("list")) {
             Set<UUID> trackedEntityList = plugin.getTrackedEntities();
             if (trackedEntityList.isEmpty()) {
                 sendFeedbackMessage(sender,"No entity is currently being tracked.");
             } else {
                 StringBuilder message = new StringBuilder("Tracked entities: ");
                 for (UUID uuidinlist : trackedEntityList) {
                     message.append(uuidinlist.toString()).append(", ");
                 }
                 // Remove the trailing comma and space
                 if (!message.isEmpty()) {
                     message.setLength(message.length() - 2);
                 }
                 sender.sendMessage(message.toString());
             }
             return true;
        }

        if(args.length == 2) {
            try {
                UUID uuid = UUID.fromString(args[1]);

                if (args[0].equalsIgnoreCase("add")){
                    if (plugin.isEntityTracked(uuid)){
                        sendFeedbackMessage(sender,"Cannot add the entity " + uuid + ", it's already in tracked list.");
                        return true;
                    }
                    plugin.addTrackedEntity(uuid);
                    sendFeedbackMessage(sender,"Added entity " + uuid + " to tracked list.");
                    return true;
                } else if (args[0].equalsIgnoreCase("remove")) {
                    if (!plugin.isEntityTracked(uuid)){
                        sendFeedbackMessage(sender,"Cannot remove the entity " + uuid + ", it's not in tracked list.");
                        return true;
                    }
                    plugin.removeTrackedEntity(uuid);
                    sendFeedbackMessage(sender,"Removed entity "  + uuid + " from tracked list.");
                    return true;
                } else if (args[0].equalsIgnoreCase("unlink")) {
                    if (!plugin.isEntityLinked(uuid)){
                        sendFeedbackMessage(sender,"Cannot unlink the entity " + uuid + ", it's not linked to any parent.");
                        return true;
                    }
                    UUID parent = plugin.removeLinkedEntity(uuid);
                    sendFeedbackMessage(sender,"Unlinked entity "  + uuid + " from it's parent " + parent + ".");
                    return true;
                }
            } catch (IllegalArgumentException e) {
                sendFeedbackMessage(sender,"Invalid UUID format.");
                return false;
            }
        }

        if(args.length == 3 && args[0].equalsIgnoreCase("link")){
            try {
                UUID childUUID = UUID.fromString(args[1]);
                UUID parentUUID = UUID.fromString(args[2]);

                if(!plugin.isEntityTracked(parentUUID)){
                    sendFeedbackMessage(sender,"Cannot link the entity " + childUUID + " to " + parentUUID
                            + ", parent is not in tracked list.");
                    return true;
                }
                if(plugin.isEntityLinked(childUUID)){
                    sendFeedbackMessage(sender,"Child " + childUUID + " is already linked to " + plugin.getLinkedParent(childUUID)
                            + ", replacing it's parent.");
                    return true;
                }
                plugin.addLinkedEntity(childUUID,parentUUID);
                sendFeedbackMessage(sender,"Linked entity "  + childUUID + " to it's parent " + parentUUID + ".");
                return true;
            } catch (IllegalArgumentException e) {
                sendFeedbackMessage(sender,"Invalid UUID format.");
                return false;
            }
        }
        return false;
    }

    public void sendFeedbackMessage(CommandSender sender, String msg){
        logger.info(MessageHelper.getPlainText(msg));

        if (sender instanceof Player) sender.sendMessage(MessageHelper.process(msg,true));
    }
}
