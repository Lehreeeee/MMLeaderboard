package me.lehreeeee.mmleaderboard;

import me.lehreeeee.mmleaderboard.listeners.EntityDamageListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.ScoreboardManager;

public final class MMLeaderboard extends JavaPlugin {

    private ScoreboardHandler handler;
    @Override
    public void onEnable() {
        Bukkit.getLogger().info("[MMLeaderboard] Enabling MythicMobs Leaderboard...");
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        handler = new ScoreboardHandler(manager,manager.getMainScoreboard());

        new EntityDamageListener(this,handler);

        Bukkit.getLogger().info("[MMLeaderboard] Enabled MythicMobs Leaderboard...");
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info("[MMLeaderboard] Disabling MythicMobs Leaderboard...");
        handler.deleteObjective("v_dummy");
        Bukkit.getLogger().info("[MMLeaderboard] Disabled MythicMobs Leaderboard...");
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