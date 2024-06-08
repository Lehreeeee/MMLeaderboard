package me.lehreeeee.mmleaderboard.listeners;


import io.lumine.mythic.bukkit.MythicBukkit;
import io.lumine.mythic.core.mobs.ActiveMob;
import me.lehreeeee.mmleaderboard.MMLeaderboard;
import me.lehreeeee.mmleaderboard.ScoreboardHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.Optional;

public class EntityDamageListener implements Listener {

    private MMLeaderboard plugin;
    private ScoreboardHandler handler;
    private String debugPrefix = "[MMLeaderboard Debug] ";

    public EntityDamageListener(MMLeaderboard plugin, ScoreboardHandler handler) {
        this.plugin = plugin;
        this.handler = handler;
        Bukkit.getPluginManager().registerEvents(this,plugin);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        Entity damager = event.getDamager();
        Entity victim = event.getEntity();
        boolean isMythicMob = MythicBukkit.inst().getMobManager().isMythicMob(victim);

        if(!(damager instanceof Player)) {
            Bukkit.getLogger().info(debugPrefix+"Damager " + damager.getName() + " is not player.");
            return;
        }

        if(!isMythicMob) {
            Bukkit.getLogger().info(debugPrefix+"Victim " + victim.getName() + " is not mythicmobs.");
            return;
        }

        Optional<ActiveMob> activeMob = MythicBukkit.inst().getMobManager().getActiveMob(victim.getUniqueId());
        String internalName = activeMob.get().getType().getInternalName();

        double finalDamage = event.getFinalDamage();
        Bukkit.getLogger().info(debugPrefix+"Player " + damager.getName() + " is doing " + finalDamage + " to " + victim.getName());
        Bukkit.getLogger().info(debugPrefix+"Internal name = " + internalName);
        if(internalName.equalsIgnoreCase("v_dummy")) {
            Bukkit.getLogger().info(debugPrefix+"v_dummy detected.");
            handler.addScore(internalName,damager.getUniqueId(), (int) finalDamage);
        }

    }


}
