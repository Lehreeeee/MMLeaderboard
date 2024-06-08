package me.lehreeeee.mmleaderboard.listeners;


import io.lumine.mythic.api.mobs.MythicMob;
import io.lumine.mythic.bukkit.MythicBukkit;
import io.lumine.mythic.core.mobs.ActiveMob;
import me.lehreeeee.mmleaderboard.MMLeaderboard;
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

    public EntityDamageListener(MMLeaderboard plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this,plugin);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        Entity damager = event.getDamager();
        Entity victim = event.getEntity();
        boolean isMythicMob = MythicBukkit.inst().getMobManager().isMythicMob(victim);

        if(!(damager instanceof Player)) {
            Bukkit.getLogger().info("Damager " + damager.getName() + " is not player.");
            return;
        }

        if(!isMythicMob) {
            Bukkit.getLogger().info("Victim " + victim.getName() + " is not mythicmobs.");
            return;
        }

        Optional<ActiveMob> activeMob = MythicBukkit.inst().getMobManager().getActiveMob(victim.getUniqueId());
        double finalDamage = event.getFinalDamage();
        Bukkit.getLogger().info("Player " + damager.getName() + " is doing " + finalDamage + " to " + victim.getName());
        MythicMob victimType = activeMob.get().getType();
        Bukkit.getLogger().info(victimType.getInternalName());



    }


}
