package me.lehreeeee.mmleaderboard;

import me.lehreeeee.mmleaderboard.commands.Leaderboard;
import me.lehreeeee.mmleaderboard.commands.LeaderboardTabCompleter;
import me.lehreeeee.mmleaderboard.listeners.EntityDamageListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Logger;

public final class MMLeaderboard extends JavaPlugin {

    private ScoreboardHandler handler;
    private final Set<UUID> trackedEntities = new HashSet<>();
    private final Logger logger = getLogger();

    @Override
    public void onEnable() {
        getCommand("mmleaderboard").setExecutor(new Leaderboard(this));
        getCommand("mmleaderboard").setTabCompleter(new LeaderboardTabCompleter());

        handler = new ScoreboardHandler(Bukkit.getScoreboardManager().getMainScoreboard(), logger);

        new EntityDamageListener(this,handler);

        logger.info("Enabled MythicMobs Leaderboard...");
    }

    @Override
    public void onDisable() {
        for (UUID uuid : trackedEntities){
            handler.deleteObjective(String.valueOf(uuid));
        }
        logger.info("Removed all scoreboard objectives of tracked entities.");
        logger.info("Disabled MythicMobs Leaderboard...");
    }

    public void addTrackedEntity(UUID uuid) {
        trackedEntities.add(uuid);
        handler.createObjective(String.valueOf(uuid));
    }

    public void removeTrackedEntity(UUID uuid) {
        trackedEntities.remove(uuid);
        handler.deleteObjective(String.valueOf(uuid));
    }
    public boolean isEntityTracked(UUID uuid) {
        return trackedEntities.contains(uuid);
    }

    public Set<UUID> getTrackedEntities(){
        return trackedEntities;
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