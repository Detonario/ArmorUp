package me.detonario.armorup.listener;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import me.detonario.armorup.other.Keys;
import me.detonario.armorup.task.ArmorTask;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Collection;

public class ArmorListener implements Listener {

    private static final ArmorListener instance = new ArmorListener();

    @EventHandler
    public void onPlayerWearArmor(PlayerArmorChangeEvent event) {
        Player player = event.getPlayer();
        String wornSet = getWornSet(player);

        PotionEffect luckEffect = player.getPotionEffect(PotionEffectType.LUCK);
        PotionEffect slownessEffect = player.getPotionEffect(PotionEffectType.SLOWNESS);
        PotionEffect healthBoostEffect = player.getPotionEffect(PotionEffectType.HEALTH_BOOST);
        PotionEffect nauseaEffect = player.getPotionEffect(PotionEffectType.NAUSEA);


        if ("emerald".equals(wornSet)) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.LUCK, PotionEffect.INFINITE_DURATION, 20, true, true, true));
            return;
        } else if (player.hasPotionEffect(PotionEffectType.LUCK) && luckEffect != null && luckEffect.getDuration() == PotionEffect.INFINITE_DURATION) {
            player.removePotionEffect(PotionEffectType.LUCK);
            return;
        }


        if ("obsidian:1".equals(wornSet) && slownessEffect != null && slownessEffect.getDuration() == PotionEffect.INFINITE_DURATION) {
            player.removePotionEffect(PotionEffectType.SLOWNESS);
            return;
        }

        if ("obsidian:2".equals(wornSet)) {
            player.removePotionEffect(PotionEffectType.SLOWNESS);
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, PotionEffect.INFINITE_DURATION, 0, true, true, true));
            return;
        }

        if ("obsidian:3".equals(wornSet)) {
            player.removePotionEffect(PotionEffectType.SLOWNESS);
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, PotionEffect.INFINITE_DURATION, 1, true, true, true));
            return;
        }

        if ("obsidian:4".equals(wornSet)) {
            player.removePotionEffect(PotionEffectType.SLOWNESS);
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, PotionEffect.INFINITE_DURATION, 2, true, true, true));
            return;
        }


        if ("superGold".equals(wornSet)) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, PotionEffect.INFINITE_DURATION, 3, true, true, false));
            player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, PotionEffect.INFINITE_DURATION, 1, true, true, false));
            player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, PotionEffect.INFINITE_DURATION, 0, true, true, false));
            player.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, PotionEffect.INFINITE_DURATION, 0, true, true, false));
            player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, PotionEffect.INFINITE_DURATION, 0, true, true, false));
            return;

        } else if (player.hasPotionEffect(PotionEffectType.HEALTH_BOOST) && healthBoostEffect != null && healthBoostEffect.getDuration() == PotionEffect.INFINITE_DURATION) {
            player.removePotionEffect(PotionEffectType.ABSORPTION);
            player.removePotionEffect(PotionEffectType.REGENERATION);
            player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
            player.removePotionEffect(PotionEffectType.RESISTANCE);
            player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
            return;
        }


        if ("amethyst".equals(wornSet)) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, PotionEffect.INFINITE_DURATION, 2, true, true, true));
            player.addPotionEffect(new PotionEffect(PotionEffectType.NAUSEA, PotionEffect.INFINITE_DURATION, 0, true, true, true));

        } else if (player.hasPotionEffect(PotionEffectType.NAUSEA) && nauseaEffect != null && nauseaEffect.getDuration() == PotionEffect.INFINITE_DURATION) {
            player.removePotionEffect(PotionEffectType.WEAKNESS);
            player.removePotionEffect(PotionEffectType.NAUSEA);

            Collection<LivingEntity> glowList = ArmorTask.getGlowList();

            final Collection<LivingEntity> nearbyEntities = player.getLocation().clone().getNearbyLivingEntities(35);

            for (LivingEntity entity : nearbyEntities) {
                if (glowList.contains(entity)) {
                    glowList.remove(entity);
                    entity.setGlowing(false);
                }
            }
        }
    }


    public String getWornSet(Player player) {
        ItemStack helmet = player.getInventory().getHelmet();
        ItemStack chestplate = player.getInventory().getChestplate();
        ItemStack leggings = player.getInventory().getLeggings();
        ItemStack boots = player.getInventory().getBoots();


        if ((helmet != null && helmet.getPersistentDataContainer().has(Keys.EMERALD_HELMET)) &&
                (chestplate != null && chestplate.getPersistentDataContainer().has(Keys.EMERALD_CHESTPLATE)) &&
                (leggings != null && leggings.getPersistentDataContainer().has(Keys.EMERALD_LEGGINGS)) &&
                (boots != null && boots.getPersistentDataContainer().has(Keys.EMERALD_BOOTS))) {
            return "emerald";
        }


        int obsidianPieces = 0;

        if (helmet != null && helmet.getPersistentDataContainer().has(Keys.OBSIDIAN_HELMET)) obsidianPieces++;
        if (chestplate != null && chestplate.getPersistentDataContainer().has(Keys.OBSIDIAN_CHESTPLATE))
            obsidianPieces++;
        if (leggings != null && leggings.getPersistentDataContainer().has(Keys.OBSIDIAN_LEGGINGS)) obsidianPieces++;
        if (boots != null && boots.getPersistentDataContainer().has(Keys.OBSIDIAN_BOOTS)) obsidianPieces++;

        if (obsidianPieces > 0) {
            return "obsidian:" + obsidianPieces;
        }


        if ((helmet != null && helmet.getPersistentDataContainer().has(Keys.SPONGE_HELMET)) &&
                (chestplate != null && chestplate.getPersistentDataContainer().has(Keys.SPONGE_CHESTPLATE)) &&
                (leggings != null && leggings.getPersistentDataContainer().has(Keys.SPONGE_LEGGINGS)) &&
                (boots != null && boots.getPersistentDataContainer().has(Keys.SPONGE_BOOTS))) {
            return "sponge";
        }


        if ((helmet != null && helmet.getPersistentDataContainer().has(Keys.COPPER_HELMET)) &&
                (chestplate != null && chestplate.getPersistentDataContainer().has(Keys.COPPER_CHESTPLATE)) &&
                (leggings != null && leggings.getPersistentDataContainer().has(Keys.COPPER_LEGGINGS)) &&
                (boots != null && boots.getPersistentDataContainer().has(Keys.COPPER_BOOTS))) {
            return "copper";
        }


        if ((helmet != null && helmet.getPersistentDataContainer().has(Keys.SUPER_GOLDEN_HELMET)) &&
                (chestplate != null && chestplate.getPersistentDataContainer().has(Keys.SUPER_GOLDEN_CHESTPLATE)) &&
                (leggings != null && leggings.getPersistentDataContainer().has(Keys.SUPER_GOLDEN_LEGGINGS)) &&
                (boots != null && boots.getPersistentDataContainer().has(Keys.SUPER_GOLDEN_BOOTS))) {
            return "superGold";
        }


        if ((helmet != null && helmet.getPersistentDataContainer().has(Keys.REDSTONE_HELMET)) &&
                (chestplate != null && chestplate.getPersistentDataContainer().has(Keys.REDSTONE_CHESTPLATE)) &&
                (leggings != null && leggings.getPersistentDataContainer().has(Keys.REDSTONE_LEGGINGS)) &&
                (boots != null && boots.getPersistentDataContainer().has(Keys.REDSTONE_BOOTS))) {
            return "redstone";
        }


        if ((helmet != null && helmet.getPersistentDataContainer().has(Keys.FLINT_HELMET)) &&
                (chestplate != null && chestplate.getPersistentDataContainer().has(Keys.FLINT_CHESTPLATE)) &&
                (leggings != null && leggings.getPersistentDataContainer().has(Keys.FLINT_LEGGINGS)) &&
                (boots != null && boots.getPersistentDataContainer().has(Keys.FLINT_BOOTS))) {
            return "flint";
        }


        if ((helmet != null && helmet.getPersistentDataContainer().has(Keys.AMETHYST_HELMET)) &&
                (chestplate != null && chestplate.getPersistentDataContainer().has(Keys.AMETHYST_CHESTPLATE)) &&
                (leggings != null && leggings.getPersistentDataContainer().has(Keys.AMETHYST_LEGGINGS)) &&
                (boots != null && boots.getPersistentDataContainer().has(Keys.AMETHYST_BOOTS))) {
            return "amethyst";
        }


        return null;
    }


    public static ArmorListener getInstance() {
        return instance;
    }
}
