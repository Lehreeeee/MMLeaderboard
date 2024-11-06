package me.lehreeeee.mmleaderboard;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.scoreboard.*;

import java.util.UUID;
import java.util.logging.Logger;

public class ScoreboardHandler {
    private final Scoreboard scoreboard;
    private final Logger logger;

    public ScoreboardHandler(Scoreboard scoreboard, Logger logger) {
        this.scoreboard = scoreboard;
        this.logger = logger;
    }

    public void addScore(String objectiveName, UUID damagerId, int damage) {
        Objective objective = scoreboard.getObjective(objectiveName);

        // Just in case objective is deleted after adding to the list
        if(objective == null)
            objective = createObjective(objectiveName);
        Score playerScore = objective.getScore(Bukkit.getPlayer(damagerId));
        playerScore.setScore(playerScore.getScore() + damage);
    }

    public Objective createObjective(String objectiveName) {
        Objective objective = scoreboard.registerNewObjective(objectiveName, Criteria.DUMMY, Component.text(objectiveName));
        logger.info("Created objective - " + objectiveName);
        return objective;
    }

    public void deleteObjective(String objectiveName) {
        Objective objective = scoreboard.getObjective(objectiveName);
        if(objective != null){
            objective.unregister();
            logger.info("Deleted objective - " + objectiveName);
        }
        else
            logger.info("Failed to delete objective - " + objectiveName + ", objective not found.");
    }
}
