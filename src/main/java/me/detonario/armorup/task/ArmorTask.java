package me.detonario.armorup.task;

import me.detonario.armorup.ArmorUp;
import me.detonario.armorup.listener.ArmorListener;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.*;

public final class ArmorTask implements Runnable {

    private static final ArmorTask instance = new ArmorTask();

    private final Set<UUID> copperPlayers = new HashSet<>();

    private final Map<UUID, BukkitTask> redstoneEffects = new HashMap<>();
    private final Map<UUID, BukkitTask> redstoneRemovals = new HashMap<>();

    private static final Collection<LivingEntity> globalEntityGlowList = new HashSet<>();

    private ArmorTask() {
    }

    @Override
    public void run() {
        ArmorListener instance = ArmorListener.getInstance();

        for (Player player : Bukkit.getOnlinePlayers()) {
            UUID uuid = player.getUniqueId();


            if ("sponge".equals(instance.getWornSet(player))) {
                Location playerLocation = player.getLocation();

                for (int x = -4; x <= 4; x++) {
                    for (int z = -4; z <= 4; z++) {
                        for (int y = -4; y <= 4; y++) {

                            Block nearbyBlock = playerLocation.clone().add(x, y, z).getBlock();
                            if (nearbyBlock.getType() == Material.WATER ||
                                    nearbyBlock.getType() == Material.KELP ||
                                    nearbyBlock.getType() == Material.SEAGRASS ||
                                    nearbyBlock.getType() == Material.TALL_SEAGRASS) {
                                nearbyBlock.setType(Material.AIR);
                            }
                        }
                    }
                }


            } else if ("copper".equals(instance.getWornSet(player)) && !copperPlayers.contains(uuid)) {
                if (player.getWorld().isThundering()) {
                    copperPlayers.add(uuid);

                    Bukkit.getScheduler().runTask(ArmorUp.getInstance(), () -> {
                        Location playerLocation = player.getLocation();
                        playerLocation.clone().getWorld().strikeLightningEffect(playerLocation);

                        List<Entity> nearbyEntities = playerLocation.getWorld().getNearbyEntities(playerLocation, 8, 2, 8).stream()
                                .filter(entity -> entity != player && !(entity instanceof EnderDragon) && !(entity instanceof Wither) && entity instanceof LivingEntity)
                                .toList();

                        for (Entity entity : nearbyEntities) {
                            ((LivingEntity) entity).setHealth(0);
                            entity.getWorld().strikeLightningEffect(entity.getLocation());
                        }

                        Bukkit.getScheduler().runTaskLater(ArmorUp.getInstance(), () -> copperPlayers.remove(uuid), 100);
                    });
                }


            } else if ("redstone".equals(instance.getWornSet(player)) && player.getInventory().contains(Material.REDSTONE)
                    && !redstoneEffects.containsKey(uuid) && !redstoneRemovals.containsKey(uuid)) {

                BukkitTask task1 = new BukkitRunnable() {

                    @Override
                    public void run() {
                        if (!"redstone".equals(instance.getWornSet(player))) {
                            redstoneEffects.get(uuid).cancel();
                            redstoneRemovals.get(uuid).cancel();

                            redstoneEffects.remove(uuid);
                            redstoneRemovals.remove(uuid);
                        }

                        Location playerLocation = player.getLocation();

                        for (int i = 0; i < 100; i++) {
                            double angle = Math.toRadians((double) i / 100 * 360);
                            double x = 1.5 * Math.cos(angle);
                            double z = 1.5 * Math.sin(angle);

                            for (double y = 0; y <= 1.5; y += 0.3) {
                                Location particleLocation = playerLocation.clone().add(x, y, z);
                                Particle.DustOptions dustOptions = new Particle.DustOptions(Color.AQUA, 0.3f);

                                player.getWorld().spawnParticle(Particle.DUST, particleLocation, 1, dustOptions);
                            }
                        }

                        List<Entity> nearbyEntities = playerLocation.getWorld().getNearbyEntities(playerLocation, 3, 3, 3).stream()
                                .filter(entity -> entity != player && !(entity instanceof EnderDragon) && !(entity instanceof DragonFireball)
                                        && !(entity instanceof Wither) && !(entity instanceof WitherSkull)
                                        && (entity instanceof LivingEntity || entity instanceof Projectile || entity instanceof TNTPrimed))
                                .filter(entity -> {
                                    if (entity instanceof Projectile projectile) {
                                        ProjectileSource shooter = projectile.getShooter();
                                        return shooter != player && shooter != null;
                                    }
                                    return true;
                                })
                                .toList();

                        for (Entity entity : nearbyEntities) {
                            Vector knockbackDirection = entity.getLocation().toVector().subtract(playerLocation.toVector()).normalize();

                            entity.setVelocity(knockbackDirection.multiply(1));
                            entity.setMetadata("noDamage", new FixedMetadataValue(ArmorUp.getInstance(), true));
                        }
                    }
                }.runTaskTimer(ArmorUp.getInstance(), 0, 1);


                BukkitTask task2 = new BukkitRunnable() {

                    @Override
                    public void run() {
                        if (!player.getInventory().contains(Material.REDSTONE)) {
                            redstoneEffects.get(uuid).cancel();
                            redstoneRemovals.get(uuid).cancel();

                            redstoneEffects.remove(uuid);
                            redstoneRemovals.remove(uuid);
                        } else {
                            player.getInventory().removeItem(new ItemStack(Material.REDSTONE, 1));
                            player.playSound(player.getLocation(), Sound.ITEM_DYE_USE, 1f, 1f);
                        }
                    }
                }.runTaskTimer(ArmorUp.getInstance(), 0, 60);

                redstoneEffects.put(uuid, task1);
                redstoneRemovals.put(uuid, task2);


            } else if ("flint".equals(instance.getWornSet(player)) && player.isSneaking()) {
                Location playerLocation = player.getLocation();
                Block blockBelowPlayer = playerLocation.clone().add(0, -1, 0).getBlock();

                if (blockBelowPlayer.isSolid()) {
                    for (int x = -2; x <= 2; x++) {
                        for (int z = -2; z <= 2; z++) {
                            for (int y = -4; y <= 4; y++) {
                                Block nearbyBlock = playerLocation.clone().add(x, y, z).getBlock();

                                if (x < 2 && x > -2 && z < 2 && z > -2 && y < 4 && y > -4 || nearbyBlock.isEmpty() || !nearbyBlock.isSolid()) {
                                    continue;
                                }

                                Block blockAbove = nearbyBlock.getRelative(0, 1, 0);
                                if ((blockAbove.isEmpty() || !blockAbove.isSolid())) {
                                    blockAbove.setType(Material.FIRE);
                                }

                            }
                        }
                    }
                }


            } else if ("amethyst".equals(instance.getWornSet(player))) {
                final Collection<LivingEntity> nearbyEntities = player.getLocation().clone().getNearbyLivingEntities(30);

                for (LivingEntity entity : nearbyEntities) {
                    if (!globalEntityGlowList.contains(entity)) {
                        globalEntityGlowList.add(entity);
                        entity.setGlowing(true);
                    }
                }

                globalEntityGlowList.removeIf(entity -> {
                    if (!nearbyEntities.contains(entity) && Bukkit.getOnlinePlayers().stream()
                            .noneMatch(p -> p.getLocation().distance(entity.getLocation()) <= 30
                                    && "amethyst".equals(instance.getWornSet(p)))) {
                        entity.setGlowing(false);
                        return true;
                    }
                    return false;
                });
            }
        }
    }


    public static Collection<LivingEntity> getGlowList() {
        return globalEntityGlowList;
    }


    public static ArmorTask getInstance() {
        return instance;
    }

}
