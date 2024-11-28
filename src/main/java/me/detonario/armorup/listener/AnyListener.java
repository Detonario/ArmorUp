package me.detonario.armorup.listener;

import me.detonario.armorup.ArmorUp;
import me.detonario.armorup.command.ArmorUpCommand;
import me.detonario.armorup.other.CustomRecipes;
import me.detonario.armorup.other.Keys;
import me.detonario.armorup.other.Settings;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;

import java.util.*;

public final class AnyListener implements Listener {

    private static final AnyListener instance = new AnyListener();

    private final Map<UUID, Long> airCooldowns = new HashMap<>();

    private final List<Material> blockList = Arrays.asList(
            Material.COPPER_ORE,
            Material.COAL_ORE,
            Material.IRON_ORE,
            Material.GOLD_ORE,
            Material.REDSTONE_ORE,
            Material.LAPIS_ORE,
            Material.DIAMOND_ORE,
            Material.EMERALD_ORE,

            Material.DEEPSLATE_COPPER_ORE,
            Material.DEEPSLATE_COAL_ORE,
            Material.DEEPSLATE_IRON_ORE,
            Material.DEEPSLATE_GOLD_ORE,
            Material.DEEPSLATE_REDSTONE_ORE,
            Material.DEEPSLATE_LAPIS_ORE,
            Material.DEEPSLATE_DIAMOND_ORE,
            Material.DEEPSLATE_EMERALD_ORE,

            Material.NETHER_QUARTZ_ORE,
            Material.NETHER_GOLD_ORE
    );

    private AnyListener() {
    }
    

    @EventHandler
    public void onPlayerBreakBlock(BlockBreakEvent event) {
        Player player = event.getPlayer();

        if ("emerald".equals(ArmorListener.getInstance().getWornSet(player))) {
            Material mat = player.getInventory().getItemInMainHand().getType();

            Block block = event.getBlock();
            Collection<ItemStack> blockDrop = block.getDrops();
            Material blockType = block.getType();

            if (blockList.contains(blockType) && (mat == Material.NETHERITE_PICKAXE || mat == Material.DIAMOND_PICKAXE || mat == Material.IRON_PICKAXE)) {
                if (!blockDrop.isEmpty()) {
                    ItemStack dropItem = blockDrop.iterator().next();
                    dropItem.setAmount(2 + new Random().nextInt(4));
                    block.getWorld().dropItemNaturally(block.getLocation(), dropItem);
                }
            }
        }
    }


    @EventHandler
    public void onFallDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (event.getCause() == EntityDamageEvent.DamageCause.FALL && airCooldowns.containsKey(player.getUniqueId())) {
                event.setCancelled(true);
            }
        }
    }


    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getEntity().isGlowing()) {
            event.setCancelled(false);
            return;
        }

        if (event.getDamager().getType() == EntityType.LIGHTNING_BOLT && event.getEntity() instanceof Player player && "copper".equals(ArmorListener.getInstance().getWornSet(player))) {
            event.setCancelled(true);
            return;
        }

        if (event.getDamager().hasMetadata("noDamage") && event.getEntity() instanceof Player player && "redstone".equals(ArmorListener.getInstance().getWornSet(player))
                && player.getInventory().contains(Material.REDSTONE)) {
            event.setCancelled(true);
            return;
        }

        if (event.getDamager() instanceof Player player) {
            Entity livingEntity1 = event.getEntity();
            Location initialLoc = event.getEntity().getLocation();

            Location playerLoc = player.getLocation();
            ItemStack item = player.getInventory().getItemInMainHand();


            if (item.getPersistentDataContainer().has(Keys.ELECTRIC_SWORD, PersistentDataType.BOOLEAN)) {
                Entity entity2 = initialLoc.getNearbyLivingEntities(3).stream()
                        .filter(entity -> entity != null && entity != player && entity != livingEntity1)
                        .findFirst().orElse(null);
                player.playSound(playerLoc, Sound.BLOCK_REDSTONE_TORCH_BURNOUT, 0.5F, 1);


                if (entity2 instanceof LivingEntity livingEntity2) {
                    Bukkit.getScheduler().runTaskLater(ArmorUp.getInstance(), () -> {
                        livingEntity2.damage(7);
                        Location loc1 = livingEntity2.getLocation();
                        createElectricSparkEffect(initialLoc, loc1);

                        Entity entity3 = loc1.getNearbyLivingEntities(3).stream()
                                .filter(entity -> entity != null && entity != player && entity != livingEntity2 && entity != livingEntity1)
                                .findFirst().orElse(null);
                        player.playSound(playerLoc, Sound.BLOCK_REDSTONE_TORCH_BURNOUT, 0.5f, 1);


                        if (entity3 instanceof LivingEntity livingEntity3) {
                            Bukkit.getScheduler().runTaskLater(ArmorUp.getInstance(), () -> {
                                livingEntity3.damage(5);
                                Location loc2 = livingEntity3.getLocation();
                                createElectricSparkEffect(loc1, loc2);

                                Entity entity4 = loc2.getNearbyLivingEntities(3).stream()
                                        .filter(entity -> entity != null && entity != player && entity != livingEntity3 && entity != livingEntity2 && entity != livingEntity1)
                                        .findFirst().orElse(null);
                                player.playSound(playerLoc, Sound.BLOCK_REDSTONE_TORCH_BURNOUT, 0.5F, 1);


                                if (entity4 instanceof LivingEntity livingEntity4) {
                                    Bukkit.getScheduler().runTaskLater(ArmorUp.getInstance(), () -> {
                                        livingEntity4.damage(2);
                                        Location loc3 = livingEntity4.getLocation();
                                        createElectricSparkEffect(loc2, loc3);
                                        player.playSound(playerLoc, Sound.BLOCK_REDSTONE_TORCH_BURNOUT, 0.5F, 1);
                                    }, 4);
                                }
                            }, 4);
                        }
                    }, 4);
                }
            }


            if (item.getPersistentDataContainer().has(Keys.AIR_SWORD, PersistentDataType.BOOLEAN)) {
                Collection<LivingEntity> collection = initialLoc.getNearbyLivingEntities(5).stream()
                        .filter(entity -> entity != null && entity != player && !entity.getLocation().equals(playerLoc)).toList();

                for (LivingEntity entity : collection) {
                    Vector entityVector = entity.getLocation().toVector();
                    Vector playerVector = playerLoc.toVector();

                    if (!entityVector.equals(playerVector)) {
                        Vector knockbackDirection = entityVector.subtract(playerVector).normalize();
                        entity.setVelocity(knockbackDirection.multiply(3));
                    }

                }

                player.playSound(playerLoc, Sound.ENTITY_WIND_CHARGE_WIND_BURST, 1, 1);
            }
        }
    }


    private void createElectricSparkEffect(Location start, Location end) {
        Vector direction = end.toVector().subtract(start.toVector()).normalize();
        double distance = start.distance(end);

        for (double i = 0; i < distance; i += 0.2) {
            Location particleLoc = start.clone().add(direction.clone().multiply(i));
            start.getWorld().spawnParticle(Particle.ELECTRIC_SPARK, particleLoc, 50);
        }
    }


    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        ItemStack item = event.getItem();

        if (item != null && item.getPersistentDataContainer().has(Keys.AIR_SWORD, PersistentDataType.BOOLEAN)
                && event.getAction().isRightClick() && event.getHand() == EquipmentSlot.HAND) {

            ItemMeta meta = item.getItemMeta();
            if (meta == null) return;

            if (airCooldowns.containsKey(uuid)) {
                long remainingMillis = airCooldowns.get(uuid) - System.currentTimeMillis();
                double remainingSeconds = remainingMillis / 1000.0;

                String formattedTime = String.format("%.1f", remainingSeconds);

                Component msg = Component.text("Air Sword is on cooldown. Remaining time: ", NamedTextColor.RED).decoration(TextDecoration.ITALIC, false)
                        .append(Component.text(formattedTime + " seconds", NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false));

                player.sendMessage(msg);

            } else {
                airCooldowns.put(uuid, System.currentTimeMillis() + 3000);

                Vector knockbackDirection = player.getLocation().getDirection().normalize();
                player.setVelocity(knockbackDirection.multiply(1.5));

                player.playSound(player.getLocation(), Sound.ENTITY_BREEZE_WIND_BURST, 1, 1);
                player.getWorld().spawnParticle(Particle.EXPLOSION, player.getLocation(), 10);

                Bukkit.getScheduler().runTaskLater(ArmorUp.getInstance(), () -> airCooldowns.remove(uuid), 60);
            }
        }
    }


    @EventHandler
    public void onPrepareCraft(PrepareItemCraftEvent event) {
        if (!(event.getView().getPlayer() instanceof Player player)) {
            return;
        }

        Recipe eventRecipe = event.getRecipe();
        Inventory inventory = event.getInventory();


        if (eventRecipe != null) {

            final List<Recipe> armorList = Arrays.asList(
                    CustomRecipes.recipeEmerald1, CustomRecipes.recipeEmerald2, CustomRecipes.recipeEmerald3, CustomRecipes.recipeEmerald4,
                    CustomRecipes.recipeObsidian1, CustomRecipes.recipeObsidian2, CustomRecipes.recipeObsidian3, CustomRecipes.recipeObsidian4,
                    CustomRecipes.recipeSponge1, CustomRecipes.recipeSponge2, CustomRecipes.recipeSponge3, CustomRecipes.recipeSponge4,
                    CustomRecipes.recipeCopper1, CustomRecipes.recipeCopper2, CustomRecipes.recipeCopper3, CustomRecipes.recipeCopper4,
                    CustomRecipes.recipeSuperGold1, CustomRecipes.recipeSuperGold2, CustomRecipes.recipeSuperGold3, CustomRecipes.recipeSuperGold4,
                    CustomRecipes.recipeRedstone1, CustomRecipes.recipeRedstone2, CustomRecipes.recipeRedstone3, CustomRecipes.recipeRedstone4,
                    CustomRecipes.recipeFlint1, CustomRecipes.recipeFlint2, CustomRecipes.recipeFlint3, CustomRecipes.recipeFlint4,
                    CustomRecipes.recipeAmethyst1, CustomRecipes.recipeAmethyst2, CustomRecipes.recipeAmethyst3, CustomRecipes.recipeAmethyst4);

            final List<Recipe> bowList = Arrays.asList(
                    CustomRecipes.recipeIceBow, CustomRecipes.recipeFireBow, CustomRecipes.recipeTNTBow, CustomRecipes.recipeClusterBow);

            final List<Recipe> swordList = Arrays.asList(
                    CustomRecipes.recipeElectricSword, CustomRecipes.recipeAirSword);

            for (Recipe recipe : armorList) {
                if (recipe != null && recipe.getResult().isSimilar(eventRecipe.getResult()) && !player.hasPermission("armorup.permission.armor")) {
                    inventory.setItem(0, null);
                    return;
                }
            }

            for (Recipe recipe : bowList) {
                if (recipe != null && recipe.getResult().isSimilar(eventRecipe.getResult()) && !player.hasPermission("armorup.permission.bow")) {
                    inventory.setItem(0, null);
                    return;
                }
            }

            for (Recipe recipe : swordList) {
                if (recipe != null && recipe.getResult().isSimilar(eventRecipe.getResult()) && !player.hasPermission("armorup.permission.sword")) {
                    inventory.setItem(0, null);
                    return;
                }
            }
        }
    }


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        final YamlConfiguration config = Settings.getInstance().getConfig();
        ConfigurationSection section = config.getConfigurationSection("players");

        if (section != null && section.contains(uuid.toString())) {
            if (config.getBoolean("players." + uuid + ".armor")) {
                PermissionAttachment attachment = player.addAttachment(ArmorUp.getInstance(), "armorup.permission.armor", true);
                ArmorUpCommand.getArmorPerms().put(uuid, attachment);
            }
            if (config.getBoolean("players." + uuid + ".bow")) {
                PermissionAttachment attachment = player.addAttachment(ArmorUp.getInstance(), "armorup.permission.bow", true);
                ArmorUpCommand.getBowPerms().put(uuid, attachment);
            }
            if (config.getBoolean("players." + uuid + ".sword")) {
                PermissionAttachment attachment = player.addAttachment(ArmorUp.getInstance(), "armorup.permission.sword", true);
                ArmorUpCommand.getSwordPerms().put(uuid, attachment);
            }
        }
    }


    public static AnyListener getInstance() {
        return instance;
    }

}
