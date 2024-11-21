package me.detonario.armorup.task;

import me.detonario.armorup.other.Keys;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

public final class ArrowTask implements Runnable {

    private static final ArrowTask instance = new ArrowTask();

    private ArrowTask() {
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            for (Entity entity : player.getNearbyEntities(50, 50, 50)) {
                if (entity instanceof Arrow arrow && arrow.getShooter() instanceof Player) {

                    if (arrow.getPersistentDataContainer().has(Keys.ICE_ARROW, PersistentDataType.BOOLEAN)) {
                        final Particle.DustOptions dust = new Particle.DustOptions(Color.fromRGB(107, 250, 236), 0.6F);

                        arrow.getWorld().spawnParticle(Particle.DUST, arrow.getLocation(), 20, dust);
                        arrow.getWorld().spawnParticle(Particle.SOUL_FIRE_FLAME, arrow.getLocation(), 7);
                        arrow.setColor(Color.AQUA);

                    } else if (arrow.getPersistentDataContainer().has(Keys.FIRE_ARROW, PersistentDataType.BOOLEAN)) {
                        final Particle.DustOptions dust = new Particle.DustOptions(Color.fromRGB(237, 122, 33), 0.6F);

                        arrow.getWorld().spawnParticle(Particle.DUST, arrow.getLocation(), 25, dust);
                        arrow.getWorld().spawnParticle(Particle.FALLING_LAVA, arrow.getLocation(), 7);
                        arrow.setColor(Color.ORANGE);

                    } else if (arrow.getPersistentDataContainer().has(Keys.CLUSTER_ARROW, PersistentDataType.BOOLEAN)) {
                        arrow.getWorld().spawnParticle(Particle.ASH, arrow.getLocation(), 50);
                        arrow.getWorld().spawnParticle(Particle.FLASH, arrow.getLocation(), 1);
                        arrow.setColor(Color.fromRGB(0x698226));
                    }
                }
            }
        }
    }


    public static ArrowTask getInstance() {
        return instance;
    }

}
