package me.lehreeeee.mmleaderboard;

import org.bukkit.Bukkit;
import org.bukkit.scoreboard.Criteria;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import java.util.UUID;

public class ScoreboardHandler {

    static ScoreboardManager manager;
    static Scoreboard scoreboard;

    public ScoreboardHandler(ScoreboardManager scoreboardHandler,Scoreboard scoreboard) {
        this.manager = scoreboardHandler;
        this.scoreboard = scoreboard;
        createObjective("v_dummy");
    }

    public void addScore(String objectiveName, UUID uuid, int damage) {
        Scoreboard board = manager.getMainScoreboard();
        if(board.getObjective(objectiveName) == null)
            createObjective(objectiveName);
        Score playerScore = board.getObjective(objectiveName).getScore(Bukkit.getPlayer(uuid));
        playerScore.setScore(playerScore.getScore() + damage);
    }

    public static void createObjective(String objectiveName) {
        scoreboard.registerNewObjective(objectiveName, Criteria.DUMMY,"V Dummy");
    }

    public void deleteObjective(String objectiveName) {
        scoreboard.getObjective(objectiveName).unregister();
    }
}
