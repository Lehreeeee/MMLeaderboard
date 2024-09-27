package me.lehreeeee.mmleaderboard.listeners;


import io.lumine.mythic.bukkit.MythicBukkit;
import io.lumine.mythic.core.mobs.ActiveMob;
import me.lehreeeee.mmleaderboard.MMLeaderboard;
import me.lehreeeee.mmleaderboard.ScoreboardHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.Optional;
import java.util.UUID;

public class EntityDamageListener implements Listener {

    private MMLeaderboard plugin;
    private ScoreboardHandler handler;
    private String debugPrefix;

    public EntityDamageListener(MMLeaderboard plugin, ScoreboardHandler handler, String debugPrefix) {
        this.plugin = plugin;
        this.handler = handler;
        this.debugPrefix = debugPrefix;
        Bukkit.getPluginManager().registerEvents(this,plugin);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        Entity damager = event.getDamager();
        Entity victim = event.getEntity();
        UUID victimId = victim.getUniqueId();

        // Ignore untracked victim
        if(!plugin.isEntityTracked(victimId)){
            return;
        }

        // Handle projectile
        if (damager instanceof Projectile) {
            Projectile proj = (Projectile) damager;
            // Set damager to arrow shooter
            if(proj.getShooter() instanceof Entity) {
                damager = (Entity) proj.getShooter();
            }
        }

        // Ignore not player
        if(!(damager instanceof Player)) {
            return;
        }

        // Ignore not alive victim
        if (!(victim instanceof LivingEntity)) {
            return;
        }

        boolean isMythicMob = MythicBukkit.inst().getMobManager().isMythicMob(victim);

        // Its for MM only
        if(!isMythicMob) {
            return;
        }

        Optional<ActiveMob> activeMob = MythicBukkit.inst().getMobManager().getActiveMob(victimId);
        double finalDamage = event.getFinalDamage();

        // No dmg overflow
        finalDamage = Math.min(finalDamage, getMobHealth(victim));

        if(finalDamage != 0) {
            // Multiply damage by 100 to keep 2 decimal place accuracy
            handler.addScore(String.valueOf(victimId),damager.getUniqueId(), (int) (finalDamage * 100));
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        Entity entity = event.getEntity();

        if ((event.getCause() == EntityDamageEvent.DamageCause.FIRE ||
                event.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK) &&
                entity instanceof LivingEntity) {
            LivingEntity mob = (LivingEntity) entity;
        }
    }

    public double getMobHealth(Entity victim) {

        if (victim != null && victim instanceof LivingEntity) {
            LivingEntity mob = (LivingEntity) victim;
            return mob.getHealth();
        }

        return -1;
    }

    /*
    // For mmostats on mm
    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void onAttack(PlayerAttackEvent event) {

        AttackMetadata attack = event.getAttack();
        LivingEntity victim = attack.getTarget();
        boolean isMythicMob = MythicBukkit.inst().getMobManager().isMythicMob(victim);

        if(!isMythicMob) {
            Bukkit.getLogger().info(debugPrefix+"Victim " + victim.getName() + " is not mythicmobs.");
            return;
        }

        DamageMetadata damage = attack.getDamage();

        Optional<ActiveMob> activeMob = MythicBukkit.inst().getMobManager().getActiveMob(victim.getUniqueId());
        String internalName = activeMob.get().getType().getInternalName();

        //

        if(internalName.equalsIgnoreCase("v_dummy") && damage.hasElement(Element.valueOf("INA"))) {
//            Bukkit.getLogger().info(debugPrefix+"v_dummy detected, adding 10 ina damage.");
//            damage.add(10, Element.valueOf("INA")); // add 10 weapon-physical damage

            Bukkit.getLogger().info(debugPrefix+"v_dummy detected, doing 50% more ina element damage.");
            damage.multiplicativeModifier(1.5, Element.valueOf("INA")); // increase skill damage by 50%
        }
    }
    */

}
