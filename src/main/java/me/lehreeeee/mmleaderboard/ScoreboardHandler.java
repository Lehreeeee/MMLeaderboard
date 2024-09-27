package me.lehreeeee.mmleaderboard;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.scoreboard.Criteria;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import java.util.UUID;

public class ScoreboardHandler {

    static ScoreboardManager manager;
    static Scoreboard scoreboard;
//    private String debugPrefix;

    public ScoreboardHandler(ScoreboardManager scoreboardHandler,Scoreboard scoreboard, String debugPrefix) {
        this.manager = scoreboardHandler;
        this.scoreboard = scoreboard;
//        this.debugPrefix = debugPrefix;
    }

    public void addScore(String objectiveName, UUID damagerId, int damage) {
        Scoreboard board = manager.getMainScoreboard();
        if(board.getObjective(objectiveName) == null)
            createObjective(objectiveName);
        Score playerScore = board.getObjective(objectiveName).getScore(Bukkit.getPlayer(damagerId));
        playerScore.setScore(playerScore.getScore() + damage);
    }

    public void createObjective(String objectiveName) {
        Bukkit.getLogger().info("[MMLeaderboard] Creating objective - " + objectiveName);
        scoreboard.registerNewObjective(objectiveName, Criteria.DUMMY, Component.text(objectiveName));
    }

    public void deleteObjective(String objectiveName) {
        Bukkit.getLogger().info("[MMLeaderboard] Deleting objective - " + objectiveName);
        scoreboard.getObjective(objectiveName).unregister();
    }
}
