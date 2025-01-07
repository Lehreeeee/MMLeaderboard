package me.lehreeeee.mmleaderboard;

import me.lehreeeee.mmleaderboard.commands.MMLCommand;
import me.lehreeeee.mmleaderboard.commands.MMLCommandTabCompleter;
import me.lehreeeee.mmleaderboard.listeners.EntityDamageListener;
import me.lehreeeee.mmleaderboard.managers.ScoreboardManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;
import java.util.logging.Logger;

public final class MMLeaderboard extends JavaPlugin {

    private ScoreboardManager handler;
    private final Set<UUID> trackedEntities = new HashSet<>();
    private final Map<UUID,UUID> linkedEntities = new HashMap<>();
    private final Logger logger = getLogger();

    @Override
    public void onEnable() {
        getCommand("mmleaderboard").setExecutor(new MMLCommand(this));
        getCommand("mmleaderboard").setTabCompleter(new MMLCommandTabCompleter());

        handler = new ScoreboardManager(Bukkit.getScoreboardManager().getMainScoreboard(), logger);

        new EntityDamageListener(this,handler);

        logger.info("Enabled MythicMobs MMLCommand...");
    }

    @Override
    public void onDisable() {
        // Clean up server scoreboards as they persist even after restart
        for(UUID uuid : trackedEntities){
            handler.deleteObjective(String.valueOf(uuid));
        }
        logger.info("Removed all scoreboard objectives of tracked entities.");
        logger.info("Disabled MythicMobs MMLCommand...");
    }

    public void addTrackedEntity(UUID uuid) {
        trackedEntities.add(uuid);
        handler.createObjective(String.valueOf(uuid));
    }

    public void removeTrackedEntity(UUID uuid) {
        trackedEntities.remove(uuid);
        handler.deleteObjective(String.valueOf(uuid));

        // Does it have children? Remove all if yes.
        if(linkedEntities.containsValue(uuid)){
            logger.info("Found child for this parent, removing them from linked list.");
            for(UUID key : linkedEntities.keySet()) {
                if(linkedEntities.get(key).equals(uuid)) {
                    linkedEntities.remove(key);
                    logger.info("Removed children " + key + " from linked list.");
                }
            }
        }
    }

    public boolean isEntityTracked(UUID uuid) {
        return trackedEntities.contains(uuid);
    }

    public Set<UUID> getTrackedEntities(){
        return trackedEntities;
    }

    public void addLinkedEntity(UUID childUUID, UUID parentUUID){
        linkedEntities.put(childUUID,parentUUID);
    }

    public UUID removeLinkedEntity(UUID childUUID) {
        UUID parent = linkedEntities.get(childUUID);
        linkedEntities.remove(childUUID);
        return parent;
    }

    public boolean isEntityLinked(UUID childUUID) {
        return linkedEntities.containsKey(childUUID);
    }

    public UUID getLinkedParent(UUID childUUID){
        return linkedEntities.get(childUUID);
    }
}

//
//                       _oo0oo_
//                      o8888888o
//                      88" . "88
//                      (| -_- |)
//                      0\  =  /0
//                    ___/`---'\___
//                  .' \\|     |// '.
//                 / \\|||  :  |||// \
//                / _||||| -:- |||||- \
//               |   | \\\  -  /// |   |
//               | \_|  ''\---/''  |_/ |
//               \  .-\__  '-'  ___/-. /
//             ___'. .'  /--.--\  `. .'___
//          ."" '<  `.___\_<|>_/___.' >' "".
//         | | :  `- \`.;`\ _ /`;.`/ - ` : | |
//         \  \ `_.   \_ __\ /__ _/   .-` /  /
//     =====`-.____`.___ \_____/___.-`___.-'=====
//                       `=---='
//
//
//     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//
//               佛祖保佑         永无BUG
//