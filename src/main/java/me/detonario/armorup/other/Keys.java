package me.detonario.armorup.other;

import me.detonario.armorup.ArmorUp;
import org.bukkit.NamespacedKey;

public final class Keys {
    public static final NamespacedKey EMERALD_HELMET = new NamespacedKey(ArmorUp.getInstance(), "EmeraldHelmet");
    public static final NamespacedKey EMERALD_CHESTPLATE = new NamespacedKey(ArmorUp.getInstance(), "EmeraldChestplate");
    public static final NamespacedKey EMERALD_LEGGINGS = new NamespacedKey(ArmorUp.getInstance(), "EmeraldLeggings");
    public static final NamespacedKey EMERALD_BOOTS = new NamespacedKey(ArmorUp.getInstance(), "EmeraldBoots");

    public static final NamespacedKey OBSIDIAN_HELMET = new NamespacedKey(ArmorUp.getInstance(), "ObsidianHelmet");
    public static final NamespacedKey OBSIDIAN_CHESTPLATE = new NamespacedKey(ArmorUp.getInstance(), "ObsidianChestplate");
    public static final NamespacedKey OBSIDIAN_LEGGINGS = new NamespacedKey(ArmorUp.getInstance(), "ObsidianLeggings");
    public static final NamespacedKey OBSIDIAN_BOOTS = new NamespacedKey(ArmorUp.getInstance(), "ObsidianBoots");

    public static final NamespacedKey SPONGE_HELMET = new NamespacedKey(ArmorUp.getInstance(), "SpongeHelmet");
    public static final NamespacedKey SPONGE_CHESTPLATE = new NamespacedKey(ArmorUp.getInstance(), "SpongeChestplate");
    public static final NamespacedKey SPONGE_LEGGINGS = new NamespacedKey(ArmorUp.getInstance(), "SpongeLeggings");
    public static final NamespacedKey SPONGE_BOOTS = new NamespacedKey(ArmorUp.getInstance(), "SpongeBoots");

    public static final NamespacedKey COPPER_HELMET = new NamespacedKey(ArmorUp.getInstance(), "CopperHelmet");
    public static final NamespacedKey COPPER_CHESTPLATE = new NamespacedKey(ArmorUp.getInstance(), "CopperChestplate");
    public static final NamespacedKey COPPER_LEGGINGS = new NamespacedKey(ArmorUp.getInstance(), "CopperLeggings");
    public static final NamespacedKey COPPER_BOOTS = new NamespacedKey(ArmorUp.getInstance(), "CopperBoots");

    public static final NamespacedKey SUPER_GOLDEN_HELMET = new NamespacedKey(ArmorUp.getInstance(), "SuperGoldenHelmet");
    public static final NamespacedKey SUPER_GOLDEN_CHESTPLATE = new NamespacedKey(ArmorUp.getInstance(), "SuperGoldenChestplate");
    public static final NamespacedKey SUPER_GOLDEN_LEGGINGS = new NamespacedKey(ArmorUp.getInstance(), "SuperGoldenLeggings");
    public static final NamespacedKey SUPER_GOLDEN_BOOTS = new NamespacedKey(ArmorUp.getInstance(), "SuperGoldenBoots");

    public static final NamespacedKey REDSTONE_HELMET = new NamespacedKey(ArmorUp.getInstance(), "RedstoneHelmet");
    public static final NamespacedKey REDSTONE_CHESTPLATE = new NamespacedKey(ArmorUp.getInstance(), "RedstoneChestplate");
    public static final NamespacedKey REDSTONE_LEGGINGS = new NamespacedKey(ArmorUp.getInstance(), "RedstoneLeggings");
    public static final NamespacedKey REDSTONE_BOOTS = new NamespacedKey(ArmorUp.getInstance(), "RedstoneBoots");

    public static final NamespacedKey FLINT_HELMET = new NamespacedKey(ArmorUp.getInstance(), "FlintHelmet");
    public static final NamespacedKey FLINT_CHESTPLATE = new NamespacedKey(ArmorUp.getInstance(), "FlintChestplate");
    public static final NamespacedKey FLINT_LEGGINGS = new NamespacedKey(ArmorUp.getInstance(), "FlintLeggings");
    public static final NamespacedKey FLINT_BOOTS = new NamespacedKey(ArmorUp.getInstance(), "FlintBoots");

    public static final NamespacedKey AMETHYST_HELMET = new NamespacedKey(ArmorUp.getInstance(), "AmethystHelmet");
    public static final NamespacedKey AMETHYST_CHESTPLATE = new NamespacedKey(ArmorUp.getInstance(), "AmethystChestplate");
    public static final NamespacedKey AMETHYST_LEGGINGS = new NamespacedKey(ArmorUp.getInstance(), "AmethystLeggings");
    public static final NamespacedKey AMETHYST_BOOTS = new NamespacedKey(ArmorUp.getInstance(), "AmethystBoots");


    public static final NamespacedKey ICE_BOW = new NamespacedKey(ArmorUp.getInstance(), "IceBow");
    public static final NamespacedKey ICE_ARROW = new NamespacedKey(ArmorUp.getInstance(), "IceArrow");

    public static final NamespacedKey FIRE_BOW = new NamespacedKey(ArmorUp.getInstance(), "FireBow");
    public static final NamespacedKey FIRE_ARROW = new NamespacedKey(ArmorUp.getInstance(), "FireArrow");

    public static final NamespacedKey TNT_BOW = new NamespacedKey(ArmorUp.getInstance(), "TNTBow");

    public static final NamespacedKey CLUSTER_BOW = new NamespacedKey(ArmorUp.getInstance(), "ClusterBow");
    public static final NamespacedKey CLUSTER_ARROW = new NamespacedKey(ArmorUp.getInstance(), "ClusterArrow");


    public static final NamespacedKey ELECTRIC_SWORD = new NamespacedKey(ArmorUp.getInstance(), "ElectricSword");
    public static final NamespacedKey AIR_SWORD = new NamespacedKey(ArmorUp.getInstance(), "AirSword");


    public static NamespacedKey getItemKey(String name) {
        return switch (name) {
            case "EmeraldHelmet" -> EMERALD_HELMET;
            case "EmeraldChestplate" -> EMERALD_CHESTPLATE;
            case "EmeraldLeggings" -> EMERALD_LEGGINGS;
            case "EmeraldBoots" -> EMERALD_BOOTS;

            case "ObsidianHelmet" -> OBSIDIAN_HELMET;
            case "ObsidianChestplate" -> OBSIDIAN_CHESTPLATE;
            case "ObsidianLeggings" -> OBSIDIAN_LEGGINGS;
            case "ObsidianBoots" -> OBSIDIAN_BOOTS;

            case "SpongeHelmet" -> SPONGE_HELMET;
            case "SpongeChestplate" -> SPONGE_CHESTPLATE;
            case "SpongeLeggings" -> SPONGE_LEGGINGS;
            case "SpongeBoots" -> SPONGE_BOOTS;

            case "CopperHelmet" -> COPPER_HELMET;
            case "CopperChestplate" -> COPPER_CHESTPLATE;
            case "CopperLeggings" -> COPPER_LEGGINGS;
            case "CopperBoots" -> COPPER_BOOTS;

            case "SuperGoldenHelmet" -> SUPER_GOLDEN_HELMET;
            case "SuperGoldenChestplate" -> SUPER_GOLDEN_CHESTPLATE;
            case "SuperGoldenLeggings" -> SUPER_GOLDEN_LEGGINGS;
            case "SuperGoldenBoots" -> SUPER_GOLDEN_BOOTS;

            case "RedstoneHelmet" -> REDSTONE_HELMET;
            case "RedstoneChestplate" -> REDSTONE_CHESTPLATE;
            case "RedstoneLeggings" -> REDSTONE_LEGGINGS;
            case "RedstoneBoots" -> REDSTONE_BOOTS;

            case "FlintHelmet" -> FLINT_HELMET;
            case "FlintChestplate" -> FLINT_CHESTPLATE;
            case "FlintLeggings" -> FLINT_LEGGINGS;
            case "FlintBoots" -> FLINT_BOOTS;

            case "AmethystHelmet" -> AMETHYST_HELMET;
            case "AmethystChestplate" -> AMETHYST_CHESTPLATE;
            case "AmethystLeggings" -> AMETHYST_LEGGINGS;
            case "AmethystBoots" -> AMETHYST_BOOTS;


            case "IceBow" -> ICE_BOW;
            case "IceArrow" -> ICE_ARROW;

            case "FireBow" -> FIRE_BOW;
            case "FireArrow" -> FIRE_ARROW;

            case "TNTBow" -> TNT_BOW;

            case "ClusterBow" -> CLUSTER_BOW;
            case "ClusterArrow" -> CLUSTER_ARROW;


            case "ElectricSword" -> ELECTRIC_SWORD;
            case "AirSword" -> AIR_SWORD;

            default -> throw new IllegalArgumentException("Unknown item: " + name);
        };
    }


}
