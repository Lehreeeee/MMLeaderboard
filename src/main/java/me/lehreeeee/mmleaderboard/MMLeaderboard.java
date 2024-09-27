package me.lehreeeee.mmleaderboard;

import me.lehreeeee.mmleaderboard.commands.Leaderboard;
import me.lehreeeee.mmleaderboard.commands.LeaderboardTabCompleter;
import me.lehreeeee.mmleaderboard.listeners.EntityDamageListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.ScoreboardManager;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public final class MMLeaderboard extends JavaPlugin {

    private ScoreboardHandler handler;
    private final Set<UUID> trackedEntities = new HashSet<>();
    private String debugPrefix = "[MMLeaderboard Debug] ";
    @Override
    public void onEnable() {
        Bukkit.getLogger().info("[MMLeaderboard] Enabling MythicMobs Leaderboard...");

        getCommand("mmleaderboard").setExecutor(new Leaderboard(this));
        getCommand("mmleaderboard").setTabCompleter(new LeaderboardTabCompleter());

        ScoreboardManager manager = Bukkit.getScoreboardManager();
        handler = new ScoreboardHandler(manager,manager.getMainScoreboard(),debugPrefix);

        new EntityDamageListener(this,handler, debugPrefix);

        Bukkit.getLogger().info("[MMLeaderboard] Enabled MythicMobs Leaderboard...");
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info("[MMLeaderboard] Disabling MythicMobs Leaderboard...");

        for (UUID uuid : trackedEntities){
            handler.deleteObjective(String.valueOf(uuid));
        }
        Bukkit.getLogger().info("[MMLeaderboard] Removed all scoreboard objectives of tracked entities.");

        Bukkit.getLogger().info("[MMLeaderboard] Disabled MythicMobs Leaderboard...");
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