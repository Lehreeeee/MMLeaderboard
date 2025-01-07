package me.lehreeeee.mmleaderboard.listeners;


import io.lumine.mythic.bukkit.MythicBukkit;
import io.lumine.mythic.core.mobs.MobExecutor;
import me.lehreeeee.mmleaderboard.MMLeaderboard;
import me.lehreeeee.mmleaderboard.managers.ScoreboardManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.UUID;

public class EntityDamageListener implements Listener {

    private final MMLeaderboard plugin;
    private final ScoreboardManager handler;
    private final MobExecutor MythicMobsManager;

    public EntityDamageListener(MMLeaderboard plugin, ScoreboardManager handler) {
        this.plugin = plugin;
        this.handler = handler;
        this.MythicMobsManager = MythicBukkit.inst().getMobManager();
        Bukkit.getPluginManager().registerEvents(this,plugin);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        Entity damager = event.getDamager();
        Entity victim = event.getEntity();
        UUID victimUUID = victim.getUniqueId();
        boolean isChildren = plugin.isEntityLinked(victimUUID);

        // Ignore untracked and unlinked victim
        if(!plugin.isEntityTracked(victimUUID) && !isChildren){
            return;
        }

        // Handle projectile, this is what MythicMobs built in leaderboard is lacking
        if(damager instanceof Projectile) {
            Projectile proj = (Projectile) damager;
            // Set damager to arrow shooter instead of arrow
            if(proj.getShooter() instanceof Entity) {
                damager = (Entity) proj.getShooter();
            }
        }

        // Ignore not player
        if(!(damager instanceof Player)) {
            return;
        }

        // Ignore not alive victim
        if(!(victim instanceof LivingEntity)) {
            return;
        }

        // Its for MM only
        if(!MythicMobsManager.isMythicMob(victim)) {
            return;
        }

        double finalDamage = event.getFinalDamage();
        String scoreboardName = String.valueOf(victimUUID);

        // is it a child? If yes then use parent's leaderboard.
        if(isChildren) scoreboardName = String.valueOf(plugin.getLinkedParent(victimUUID));

        // No dmg overflow
        finalDamage = Math.min(finalDamage, getMobHealth(victim));

        if(finalDamage != 0) {
            // Multiply damage by 100 to keep 2 decimal place accuracy
            handler.addScore(scoreboardName ,damager.getUniqueId(), (int) (finalDamage * 100));
        }
    }

    public double getMobHealth(Entity victim) {
        if (victim instanceof LivingEntity) {
            LivingEntity mob = (LivingEntity) victim;
            return mob.getHealth();
        }
        return -1;
    }
}
