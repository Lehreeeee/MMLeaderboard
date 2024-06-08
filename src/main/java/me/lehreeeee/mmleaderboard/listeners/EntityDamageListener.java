package me.lehreeeee.mmleaderboard.listeners;


import me.lehreeeee.mmleaderboard.MMLeaderboard;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityDamageListener implements Listener {

    private MMLeaderboard plugin;

    public EntityDamageListener(MMLeaderboard plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this,plugin);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        Entity Damager = event.getDamager();
        Entity Victim = event.getEntity();

        if(!(Damager instanceof Player)) {
            Bukkit.getLogger().info("Damager " + Damager.getName() + "is not player.");
            return;
        }

        Bukkit.getLogger().info("Player " + Damager.getName() + "is damaging " + Victim.getName());

    }


}
