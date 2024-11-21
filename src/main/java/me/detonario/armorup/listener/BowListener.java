package me.detonario.armorup.listener;

import me.detonario.armorup.ArmorUp;
import me.detonario.armorup.other.Keys;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Random;

public class BowListener implements Listener {

    private static final BowListener instance = new BowListener();

    private int bowCooldown = 20;

    @EventHandler
    public void onBowLaunch(ProjectileLaunchEvent event) {
        if (event.getEntity() instanceof Arrow arrow) {

            if (event.getEntity().getShooter() instanceof Player shooter) {
                ItemStack item = shooter.getInventory().getItemInMainHand();
                ItemMeta meta = item.getItemMeta();

                if (item.hasItemMeta() && meta.getPersistentDataContainer().has(Keys.ICE_BOW, PersistentDataType.BOOLEAN)) {
                    shooter.setCooldown(item.getType(), bowCooldown);
                    arrow.getPersistentDataContainer().set(Keys.ICE_ARROW, PersistentDataType.BOOLEAN, true);
                    return;
                }

                if (item.hasItemMeta() && meta.getPersistentDataContainer().has(Keys.FIRE_BOW, PersistentDataType.BOOLEAN)) {
                    shooter.setCooldown(item.getType(), bowCooldown);
                    arrow.getPersistentDataContainer().set(Keys.FIRE_ARROW, PersistentDataType.BOOLEAN, true);
                    return;
                }

                if (item.hasItemMeta() && meta.getPersistentDataContainer().has(Keys.TNT_BOW, PersistentDataType.BOOLEAN)) {
                    Location arrowLocation = arrow.getLocation();
                    Vector arrowVelocity = arrow.getVelocity();

                    TNTPrimed tnt = (TNTPrimed) arrow.getWorld().spawnEntity(arrowLocation, EntityType.TNT);
                    tnt.setVelocity(arrowVelocity);
                    tnt.setSource(shooter);
                    tnt.setFuseTicks(60);

                    shooter.setCooldown(item.getType(), bowCooldown);
                    arrow.remove();
                    return;
                }

                if (item.hasItemMeta() && meta.getPersistentDataContainer().has(Keys.CLUSTER_BOW, PersistentDataType.BOOLEAN)) {
                    shooter.setCooldown(item.getType(), bowCooldown);
                    arrow.getPersistentDataContainer().set(Keys.CLUSTER_ARROW, PersistentDataType.BOOLEAN, true);
                }


            }
        }
    }


    @EventHandler
    public void onBowHit(ProjectileHitEvent event) {
        if (event.getEntity() instanceof Arrow arrow) {

            if (arrow.getPersistentDataContainer().has(Keys.ICE_ARROW, PersistentDataType.BOOLEAN)) {

                if (event.getHitBlock() != null) {
                    Block hitBlock = event.getHitBlock();
                    hitBlock.setType(Material.ICE);

                    Location hitBlockLoc = hitBlock.getLocation();
                    Block[] blocks = new Block[]{
                            hitBlockLoc.clone().add(1, 0, 0).getBlock(),
                            hitBlockLoc.clone().add(0, 0, 1).getBlock(),
                            hitBlockLoc.clone().add(-1, 0, 0).getBlock(),
                            hitBlockLoc.clone().add(0, 0, -1).getBlock()
                    };

                    for (Block block : blocks) {
                        if (!block.isEmpty()) {
                            block.setType(Material.ICE);
                        }
                    }

                    arrow.getWorld().spawnParticle(Particle.SNOWFLAKE, hitBlockLoc, 1000);


                    Random random1 = new Random();
                    Random random2 = new Random();
                    Random random3 = new Random();

                    int randomx = random1.nextInt(3) + 1;
                    int randomz = random2.nextInt(3) + 1;
                    int randomy = random3.nextInt(2) + 1;


                    int snowmanCount = 0;

                    for (int x = -randomx; x <= randomx; x++) {
                        for (int z = -randomz; z <= randomz; z++) {
                            for (int y = -randomy; y <= randomy; y++) {

                                Block nearbyBlock = hitBlockLoc.clone().add(x, y, z).getBlock();
                                if (nearbyBlock.isEmpty() || !nearbyBlock.isSolid()) {
                                    continue;
                                }

                                Material randomBlock = getRandomIceBlock();
                                if (randomBlock != null) {
                                    nearbyBlock.setType(randomBlock);
                                }

                                if (snowmanCount == 0) {
                                    EntityType randomEntity = getRandomSnowman();
                                    if (randomEntity != null) {
                                        Location newLoc = nearbyBlock.getLocation().clone().add(hitBlockLoc.clone(), x, 1, z);
                                        newLoc.getWorld().spawnEntity(newLoc, randomEntity);
                                        snowmanCount++;
                                    }
                                }


                                arrow.setHitSound(Sound.BLOCK_POWDER_SNOW_BREAK);


                            }

                        }

                    }


                }

                if (event.getHitEntity() != null && event.getHitEntity() instanceof LivingEntity livingEntity) {
                    livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 40, 128));
                    arrow.setHitSound(Sound.BLOCK_POWDER_SNOW_BREAK);
                }

                arrow.remove();
                return;

            }


            if (arrow.getPersistentDataContainer().has(Keys.FIRE_ARROW, PersistentDataType.BOOLEAN)) {
                if (event.getHitBlock() != null) {
                    Block hitBlock = event.getHitBlock();
                    hitBlock.setType(Material.NETHERRACK);

                    Location hitBlockLoc = hitBlock.getLocation();
                    Block[] blocks = new Block[]{
                            hitBlockLoc.clone().add(1, 0, 0).getBlock(),
                            hitBlockLoc.clone().add(0, 0, 1).getBlock(),
                            hitBlockLoc.clone().add(-1, 0, 0).getBlock(),
                            hitBlockLoc.clone().add(0, 0, -1).getBlock()
                    };

                    for (Block block : blocks) {
                        if (!block.isEmpty()) {
                            block.setType(Material.NETHERRACK);
                        }
                    }

                    arrow.getWorld().spawnParticle(Particle.DRIPPING_LAVA, hitBlockLoc, 1000);


                    Random random1 = new Random();
                    Random random2 = new Random();
                    Random random3 = new Random();

                    int randomx = random1.nextInt(3) + 1;
                    int randomz = random2.nextInt(3) + 1;
                    int randomy = random3.nextInt(1) + 1;


                    for (int x = -randomx; x <= randomx; x++) {
                        for (int z = -randomz; z <= randomz; z++) {
                            for (int y = -randomy; y <= randomy; y++) {

                                Block nearbyBlock = hitBlockLoc.clone().add(x, y, z).getBlock();
                                if (nearbyBlock.isEmpty() || !nearbyBlock.isSolid()) {
                                    continue;
                                }


                                Material randomBlock = getRandomLavaBlock();
                                if (randomBlock != null) {
                                    nearbyBlock.setType(randomBlock);
                                }


                                Block blockAbove = nearbyBlock.getRelative(0, 1, 0);
                                Material randomFire = getRandomFire();
                                if (blockAbove.isEmpty() && randomFire != null) {
                                    blockAbove.setType(randomFire);
                                }

                                arrow.setHitSound(Sound.BLOCK_FIRE_AMBIENT);


                            }

                        }

                    }

                }

                if (event.getHitEntity() != null && event.getHitEntity() instanceof LivingEntity livingEntity) {
                    livingEntity.setFireTicks(60);
                    arrow.setHitSound(Sound.BLOCK_FIRE_AMBIENT);

                }

                arrow.remove();
                return;

            }


            if (arrow.getPersistentDataContainer().has(Keys.CLUSTER_ARROW, PersistentDataType.BOOLEAN)) {

                Location explosionLocation = null;


                if (event.getHitBlock() != null) {
                    Block hitBlockCluster = event.getHitBlock();
                    explosionLocation = hitBlockCluster.getLocation();

                } else if (event.getHitEntity() instanceof LivingEntity hitEntityCluster && !(event.getHitEntity() instanceof EnderDragonPart)) {
                    explosionLocation = hitEntityCluster.getLocation();

                } else if (event.getHitEntity() instanceof EnderDragonPart hitDragon) {
                    EnderDragon enderDragon = hitDragon.getParent();
                    explosionLocation = enderDragon.getLocation();
                }


                if (explosionLocation != null) {
                    explosionLocation.createExplosion(7F, true, true);
                    arrow.getWorld().spawnParticle(Particle.EXPLOSION, explosionLocation, 1);
                    explodeLater(explosionLocation);
                }


                arrow.remove();
            }
        }
    }


    private void explodeLater(Location explosionLocation) {

        new BukkitRunnable() {

            final Random random = new Random();
            int ticks = 0;

            @Override
            public void run() {
                ticks += 5;

                double[] doublesList = new double[6];
                for (int i = 0; i < doublesList.length; i++) {
                    doublesList[i] = random.nextDouble() * 6 + 1;
                }

                for (int i = 0; i < 3; i++) {
                    explosionLocation.clone().add(doublesList[i * 2], 1, doublesList[i * 2 + 1]).createExplosion(4F, false, true);
                }

                if (ticks >= 35) {
                    this.cancel();
                }
            }
        }.runTaskTimer(ArmorUp.getInstance(), 0, 5);
    }


    private Material getRandomIceBlock() {
        Random random = new Random();
        int randomValue = random.nextInt(100);

        if (randomValue < 5) return Material.WATER;
        if (randomValue < 40) return null;
        if (randomValue < 50) return Material.PACKED_ICE;
        if (randomValue < 60) return Material.BLUE_ICE;
        return Material.ICE;
    }

    private EntityType getRandomSnowman() {
        Random random = new Random();
        double randomValue = random.nextDouble(100);

        if (randomValue < 99.8) return null;
        return EntityType.SNOW_GOLEM;
    }

    private Material getRandomLavaBlock() {
        Random random = new Random();
        int randomValue = random.nextInt(100);

        if (randomValue < 10) return Material.LAVA;
        if (randomValue < 70) return null;
        if (randomValue < 80) return Material.NETHER_WART_BLOCK;
        if (randomValue < 90) return Material.OBSIDIAN;
        return Material.NETHERRACK;
    }

    private Material getRandomFire() {
        Random random = new Random();
        int randomValue = random.nextInt(100);

        if (randomValue < 55) return null;
        return Material.FIRE;
    }


    public void setBowCooldown(int userInput) {
        bowCooldown = userInput;
    }


    public static BowListener getInstance() {
        return instance;
    }

}
