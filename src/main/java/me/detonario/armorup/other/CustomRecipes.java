package me.detonario.armorup.other;

import me.detonario.armorup.ArmorUp;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ArmorMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public final class CustomRecipes {
    public static ShapedRecipe recipeEmerald1;
    public static ShapedRecipe recipeEmerald2;
    public static ShapedRecipe recipeEmerald3;
    public static ShapedRecipe recipeEmerald4;

    public static ShapedRecipe recipeObsidian1;
    public static ShapedRecipe recipeObsidian2;
    public static ShapedRecipe recipeObsidian3;
    public static ShapedRecipe recipeObsidian4;

    public static ShapedRecipe recipeSponge1;
    public static ShapedRecipe recipeSponge2;
    public static ShapedRecipe recipeSponge3;
    public static ShapedRecipe recipeSponge4;

    public static ShapedRecipe recipeCopper1;
    public static ShapedRecipe recipeCopper2;
    public static ShapedRecipe recipeCopper3;
    public static ShapedRecipe recipeCopper4;

    public static ShapedRecipe recipeSuperGold1;
    public static ShapedRecipe recipeSuperGold2;
    public static ShapedRecipe recipeSuperGold3;
    public static ShapedRecipe recipeSuperGold4;

    public static ShapedRecipe recipeRedstone1;
    public static ShapedRecipe recipeRedstone2;
    public static ShapedRecipe recipeRedstone3;
    public static ShapedRecipe recipeRedstone4;

    public static ShapedRecipe recipeFlint1;
    public static ShapedRecipe recipeFlint2;
    public static ShapedRecipe recipeFlint3;
    public static ShapedRecipe recipeFlint4;

    public static ShapedRecipe recipeAmethyst1;
    public static ShapedRecipe recipeAmethyst2;
    public static ShapedRecipe recipeAmethyst3;
    public static ShapedRecipe recipeAmethyst4;


    public static ShapedRecipe recipeIceBow;
    public static ShapedRecipe recipeFireBow;
    public static ShapedRecipe recipeTNTBow;
    public static ShapedRecipe recipeClusterBow;


    public static ShapedRecipe recipeElectricSword;
    public static ShapedRecipe recipeAirSword;


    public static void registerEmeraldArmor() {
        ItemStack[] armorPieces = {
                new ItemStack(Material.LEATHER_HELMET),
                new ItemStack(Material.LEATHER_CHESTPLATE),
                new ItemStack(Material.LEATHER_LEGGINGS),
                new ItemStack(Material.LEATHER_BOOTS)
        };

        String[] names = {
                "Emerald Helmet", "Emerald Chestplate", "Emerald Leggings", "Emerald Boots"
        };

        int[] armorValues = {2, 6, 5, 2};

        String[][] shapes = {
                {"AAA", "A A"},
                {"A A", "AAA", "AAA"},
                {"AAA", "A A", "A A"},
                {"A A", "A A"}
        };

        List<Component> lore = List.of(
                Component.text("Protects you from damage and gives you", NamedTextColor.BLUE).decoration(TextDecoration.ITALIC, false),
                Component.text("an increased luck effect as a complete set.", NamedTextColor.BLUE).decoration(TextDecoration.ITALIC, false),
                Component.text(" "),
                Component.text("Luck grants you a greater chance", NamedTextColor.BLUE).decoration(TextDecoration.ITALIC, false),
                Component.text("of obtaining more drops from ores.", NamedTextColor.BLUE).decoration(TextDecoration.ITALIC, false),
                Component.text(" "),
                Component.text("Minimum tool strength: ", NamedTextColor.BLUE).decoration(TextDecoration.ITALIC, false)
                        .append(Component.text("Iron pickaxe", NamedTextColor.WHITE)).decoration(TextDecoration.ITALIC, false)
        );

        for (int i = 0; i < armorPieces.length; i++) {
            LeatherArmorMeta meta = (LeatherArmorMeta) armorPieces[i].getItemMeta();
            meta.displayName(Component.text(names[i], TextColor.color(0x6cf549)).decoration(TextDecoration.ITALIC, false));
            meta.addEnchant(Enchantment.PROTECTION, 1, true);
            meta.setColor(Color.fromRGB(108, 245, 73));
            meta.addItemFlags(ItemFlag.HIDE_DYE);
            meta.lore(lore);
            meta.addAttributeModifier(
                    Attribute.ARMOR,
                    new AttributeModifier(
                            new NamespacedKey(ArmorUp.getInstance(), "Armor"),
                            armorValues[i],
                            AttributeModifier.Operation.ADD_NUMBER
                    )
            );

            NamespacedKey key = Keys.getItemKey(names[i].replace(" ", ""));
            meta.getPersistentDataContainer().set(key, PersistentDataType.BOOLEAN, true);

            armorPieces[i].setItemMeta(meta);

            ShapedRecipe recipe = new ShapedRecipe(
                    new NamespacedKey(ArmorUp.getInstance(), names[i].replace(" ", "") + "Recipe"),
                    armorPieces[i]
            );

            recipe.shape(shapes[i]);
            recipe.setIngredient('A', new ItemStack(Material.EMERALD));

            switch (i) {
                case 0 -> recipeEmerald1 = recipe;
                case 1 -> recipeEmerald2 = recipe;
                case 2 -> recipeEmerald3 = recipe;
                case 3 -> recipeEmerald4 = recipe;
            }

            Bukkit.addRecipe(recipe);
        }
    }


    public static void registerObsidianArmor() {
        ItemStack[] armorPieces = {
                new ItemStack(Material.LEATHER_HELMET),
                new ItemStack(Material.LEATHER_CHESTPLATE),
                new ItemStack(Material.LEATHER_LEGGINGS),
                new ItemStack(Material.LEATHER_BOOTS)
        };

        String[] names = {
                "Obsidian Helmet", "Obsidian Chestplate", "Obsidian Leggings", "Obsidian Boots"
        };

        int[] armorValues = {3, 8, 6, 3};

        String[][] shapes = {
                {"AAA", "A A"},
                {"A A", "AAA", "AAA"},
                {"AAA", "A A", "A A"},
                {"A A", "A A"}
        };

        List<Component> lore = List.of(
                Component.text("Protects you from massive damage,", NamedTextColor.BLUE).decoration(TextDecoration.ITALIC, false),
                Component.text("but slows down your movement", NamedTextColor.BLUE).decoration(TextDecoration.ITALIC, false),
                Component.text("with every piece of armor.", NamedTextColor.BLUE).decoration(TextDecoration.ITALIC, false),
                Component.text(" "),
                Component.text("Side effect: ", NamedTextColor.BLUE).decoration(TextDecoration.ITALIC, false).append(Component.text("Slowness", NamedTextColor.DARK_RED)).decoration(TextDecoration.ITALIC, false)
        );

        for (int i = 0; i < armorPieces.length; i++) {
            LeatherArmorMeta meta = (LeatherArmorMeta) armorPieces[i].getItemMeta();
            meta.displayName(Component.text(names[i], TextColor.color(0x8e2da6)).decoration(TextDecoration.ITALIC, false));
            meta.addEnchant(Enchantment.PROTECTION, 2, true);
            meta.setColor(Color.fromRGB(92, 30, 107));
            meta.addItemFlags(ItemFlag.HIDE_DYE);
            meta.lore(lore);
            meta.addAttributeModifier(
                    Attribute.ARMOR,
                    new AttributeModifier(
                            new NamespacedKey(ArmorUp.getInstance(), "Armor"),
                            armorValues[i],
                            AttributeModifier.Operation.ADD_NUMBER
                    )
            );
            meta.addAttributeModifier(
                    Attribute.ARMOR_TOUGHNESS,
                    new AttributeModifier(
                            new NamespacedKey(ArmorUp.getInstance(), "ArmorToughness"),
                            2,
                            AttributeModifier.Operation.ADD_NUMBER));

            NamespacedKey key = Keys.getItemKey(names[i].replace(" ", ""));
            meta.getPersistentDataContainer().set(key, PersistentDataType.BOOLEAN, true);

            armorPieces[i].setItemMeta(meta);

            ShapedRecipe recipe = new ShapedRecipe(
                    new NamespacedKey(ArmorUp.getInstance(), names[i].replace(" ", "") + "Recipe"),
                    armorPieces[i]
            );

            recipe.shape(shapes[i]);
            recipe.setIngredient('A', new ItemStack(Material.OBSIDIAN));

            switch (i) {
                case 0 -> recipeObsidian1 = recipe;
                case 1 -> recipeObsidian2 = recipe;
                case 2 -> recipeObsidian3 = recipe;
                case 3 -> recipeObsidian4 = recipe;
            }

            Bukkit.addRecipe(recipe);
        }
    }


    public static void registerSpongeArmor() {
        ItemStack[] armorPieces = {
                new ItemStack(Material.LEATHER_HELMET),
                new ItemStack(Material.LEATHER_CHESTPLATE),
                new ItemStack(Material.LEATHER_LEGGINGS),
                new ItemStack(Material.LEATHER_BOOTS)
        };

        String[] names = {
                "Sponge Helmet", "Sponge Chestplate", "Sponge Leggings", "Sponge Boots"
        };

        String[][] shapes = {
                {"AAA", "A A"},
                {"A A", "AAA", "AAA"},
                {"AAA", "A A", "A A"},
                {"A A", "A A"}
        };

        List<Component> lore = List.of(
                Component.text("Absorbs all water in the vicinity", NamedTextColor.BLUE).decoration(TextDecoration.ITALIC, false),
                Component.text("as a complete set.", NamedTextColor.BLUE).decoration(TextDecoration.ITALIC, false));

        for (int i = 0; i < armorPieces.length; i++) {
            LeatherArmorMeta meta = (LeatherArmorMeta) armorPieces[i].getItemMeta();
            meta.displayName(Component.text(names[i], TextColor.color(0xdee050)).decoration(TextDecoration.ITALIC, false));
            meta.addEnchant(Enchantment.KNOCKBACK, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            meta.setColor(Color.fromRGB(222, 224, 80));
            meta.addItemFlags(ItemFlag.HIDE_DYE);
            meta.lore(lore);

            NamespacedKey key = Keys.getItemKey(names[i].replace(" ", ""));
            meta.getPersistentDataContainer().set(key, PersistentDataType.BOOLEAN, true);

            armorPieces[i].setItemMeta(meta);

            ShapedRecipe recipe = new ShapedRecipe(
                    new NamespacedKey(ArmorUp.getInstance(), names[i].replace(" ", "") + "Recipe"),
                    armorPieces[i]
            );

            recipe.shape(shapes[i]);
            recipe.setIngredient('A', new ItemStack(Material.SPONGE));

            switch (i) {
                case 0 -> recipeSponge1 = recipe;
                case 1 -> recipeSponge2 = recipe;
                case 2 -> recipeSponge3 = recipe;
                case 3 -> recipeSponge4 = recipe;
            }

            Bukkit.addRecipe(recipe);
        }
    }


    public static void registerCopperArmor() {
        ItemStack[] armorPieces = {
                new ItemStack(Material.LEATHER_HELMET),
                new ItemStack(Material.LEATHER_CHESTPLATE),
                new ItemStack(Material.LEATHER_LEGGINGS),
                new ItemStack(Material.LEATHER_BOOTS)
        };

        String[] names = {
                "Copper Helmet", "Copper Chestplate", "Copper Leggings", "Copper Boots"
        };

        int[] armorValues = {2, 6, 5, 2};

        String[][] shapes = {
                {"AAA", "A A"},
                {"A A", "AAA", "AAA"},
                {"AAA", "A A", "A A"},
                {"A A", "A A"}
        };

        List<Component> lore = List.of(
                Component.text("When worn as a full set, a lightning bolt", NamedTextColor.BLUE).decoration(TextDecoration.ITALIC, false),
                Component.text("is summoned every 5 seconds, killing all", NamedTextColor.BLUE).decoration(TextDecoration.ITALIC, false),
                Component.text("enemies near the player.", NamedTextColor.BLUE).decoration(TextDecoration.ITALIC, false),
                Component.text(" "),
                Component.text("Requirement: ", NamedTextColor.BLUE).decoration(TextDecoration.ITALIC, false).append(Component.text("Thunder", NamedTextColor.WHITE)).decoration(TextDecoration.ITALIC, false)
        );

        for (int i = 0; i < armorPieces.length; i++) {
            LeatherArmorMeta meta = (LeatherArmorMeta) armorPieces[i].getItemMeta();
            meta.displayName(Component.text(names[i], TextColor.color(0xe0806b)).decoration(TextDecoration.ITALIC, false));
            meta.addEnchant(Enchantment.BLAST_PROTECTION, 4, true);
            meta.addEnchant(Enchantment.UNBREAKING, 3, true);
            meta.setColor(Color.fromRGB(224, 128, 107));
            meta.addItemFlags(ItemFlag.HIDE_DYE);
            meta.lore(lore);
            meta.addAttributeModifier(
                    Attribute.ARMOR,
                    new AttributeModifier(
                            new NamespacedKey(ArmorUp.getInstance(), "Armor"),
                            armorValues[i],
                            AttributeModifier.Operation.ADD_NUMBER
                    )
            );

            NamespacedKey key = Keys.getItemKey(names[i].replace(" ", ""));
            meta.getPersistentDataContainer().set(key, PersistentDataType.BOOLEAN, true);

            armorPieces[i].setItemMeta(meta);

            ShapedRecipe recipe = new ShapedRecipe(
                    new NamespacedKey(ArmorUp.getInstance(), names[i].replace(" ", "") + "Recipe"),
                    armorPieces[i]
            );

            recipe.shape(shapes[i]);
            recipe.setIngredient('A', new ItemStack(Material.COPPER_INGOT));

            switch (i) {
                case 0 -> recipeCopper1 = recipe;
                case 1 -> recipeCopper2 = recipe;
                case 2 -> recipeCopper3 = recipe;
                case 3 -> recipeCopper4 = recipe;
            }

            Bukkit.addRecipe(recipe);
        }
    }


    public static void registerSuperGoldArmor() {
        ItemStack[] armorPieces = {
                new ItemStack(Material.GOLDEN_HELMET),
                new ItemStack(Material.GOLDEN_CHESTPLATE),
                new ItemStack(Material.GOLDEN_LEGGINGS),
                new ItemStack(Material.GOLDEN_BOOTS)
        };

        String[] names = {
                "Super Golden Helmet", "Super Golden Chestplate", "Super Golden Leggings", "Super Golden Boots"
        };

        String[][] shapes = {
                {"AAA", "A A"},
                {"A A", "AAA", "AAA"},
                {"AAA", "A A", "A A"},
                {"A A", "A A"}
        };

        List<Component> lore = List.of(
                Component.text("Offers protection and a wide range", NamedTextColor.BLUE).decoration(TextDecoration.ITALIC, false),
                Component.text("of effects as a complete set.", NamedTextColor.BLUE).decoration(TextDecoration.ITALIC, false)
        );

        for (int i = 0; i < armorPieces.length; i++) {
            ArmorMeta meta = (ArmorMeta) armorPieces[i].getItemMeta();
            meta.displayName(Component.text(names[i], TextColor.color(0xFF55FF)).decoration(TextDecoration.ITALIC, false));
            meta.addEnchant(Enchantment.PROTECTION, 4, true);
            meta.addEnchant(Enchantment.UNBREAKING, 3, true);
            meta.lore(lore);

            NamespacedKey key = Keys.getItemKey(names[i].replace(" ", ""));
            meta.getPersistentDataContainer().set(key, PersistentDataType.BOOLEAN, true);

            armorPieces[i].setItemMeta(meta);

            ShapedRecipe recipe = new ShapedRecipe(
                    new NamespacedKey(ArmorUp.getInstance(), names[i].replace(" ", "") + "Recipe"),
                    armorPieces[i]
            );

            recipe.shape(shapes[i]);
            recipe.setIngredient('A', new ItemStack(Material.GOLD_BLOCK));

            switch (i) {
                case 0 -> recipeSuperGold1 = recipe;
                case 1 -> recipeSuperGold2 = recipe;
                case 2 -> recipeSuperGold3 = recipe;
                case 3 -> recipeSuperGold4 = recipe;
            }

            Bukkit.addRecipe(recipe);
        }
    }


    public static void registerRedstoneArmor() {
        ItemStack[] armorPieces = {
                new ItemStack(Material.LEATHER_HELMET),
                new ItemStack(Material.LEATHER_CHESTPLATE),
                new ItemStack(Material.LEATHER_LEGGINGS),
                new ItemStack(Material.LEATHER_BOOTS)
        };

        String[] names = {
                "Redstone Helmet", "Redstone Chestplate", "Redstone Leggings", "Redstone Boots"
        };

        int[] armorValues = {2, 6, 5, 2};

        String[][] shapes = {
                {"AAA", "A A"},
                {"A A", "AAA", "AAA"},
                {"AAA", "A A", "A A"},
                {"A A", "A A"}
        };

        List<Component> lore = List.of(
                Component.text("Creates a protective shield for", NamedTextColor.BLUE).decoration(TextDecoration.ITALIC, false),
                Component.text("a few seconds as a whole set", NamedTextColor.BLUE).decoration(TextDecoration.ITALIC, false),
                Component.text("that repels all attacks.", NamedTextColor.BLUE).decoration(TextDecoration.ITALIC, false),
                Component.text(" "),
                Component.text("Requires ", NamedTextColor.BLUE).decoration(TextDecoration.ITALIC, false).append(Component.text("redstone", NamedTextColor.WHITE)).decoration(TextDecoration.ITALIC, false)
                        .append(Component.text(" in the inventory", NamedTextColor.BLUE)).decoration(TextDecoration.ITALIC, false),
                Component.text("as a power source.", NamedTextColor.BLUE).decoration(TextDecoration.ITALIC, false)
        );

        for (int i = 0; i < armorPieces.length; i++) {
            LeatherArmorMeta meta = (LeatherArmorMeta) armorPieces[i].getItemMeta();
            meta.displayName(Component.text(names[i], TextColor.color(0xff0000)).decoration(TextDecoration.ITALIC, false));
            meta.addEnchant(Enchantment.PROJECTILE_PROTECTION, 2, true);
            meta.addEnchant(Enchantment.UNBREAKING, 1, true);
            meta.setColor(Color.fromRGB(255, 0, 0));
            meta.addItemFlags(ItemFlag.HIDE_DYE);
            meta.lore(lore);
            meta.addAttributeModifier(
                    Attribute.ARMOR,
                    new AttributeModifier(
                            new NamespacedKey(ArmorUp.getInstance(), "Armor"),
                            armorValues[i],
                            AttributeModifier.Operation.ADD_NUMBER
                    )
            );

            NamespacedKey key = Keys.getItemKey(names[i].replace(" ", ""));
            meta.getPersistentDataContainer().set(key, PersistentDataType.BOOLEAN, true);

            armorPieces[i].setItemMeta(meta);

            ShapedRecipe recipe = new ShapedRecipe(
                    new NamespacedKey(ArmorUp.getInstance(), names[i].replace(" ", "") + "Recipe"),
                    armorPieces[i]
            );

            recipe.shape(shapes[i]);
            recipe.setIngredient('A', new ItemStack(Material.REDSTONE));

            switch (i) {
                case 0 -> recipeRedstone1 = recipe;
                case 1 -> recipeRedstone2 = recipe;
                case 2 -> recipeRedstone3 = recipe;
                case 3 -> recipeRedstone4 = recipe;
            }

            Bukkit.addRecipe(recipe);
        }
    }


    public static void registerFlintArmor() {
        ItemStack[] armorPieces = {
                new ItemStack(Material.LEATHER_HELMET),
                new ItemStack(Material.LEATHER_CHESTPLATE),
                new ItemStack(Material.LEATHER_LEGGINGS),
                new ItemStack(Material.LEATHER_BOOTS)
        };

        String[] names = {
                "Flint Helmet", "Flint Chestplate", "Flint Leggings", "Flint Boots"
        };

        String[][] shapes = {
                {"AAA", "A A"},
                {"A A", "AAA", "AAA"},
                {"AAA", "A A", "A A"},
                {"A A", "A A"}
        };

        List<Component> lore = List.of(
                Component.text("If the armor is worn as a complete set,", NamedTextColor.BLUE).decoration(TextDecoration.ITALIC, false),
                Component.text("an area of fire is created around", NamedTextColor.BLUE).decoration(TextDecoration.ITALIC, false),
                Component.text("the player when sneaking.", NamedTextColor.BLUE).decoration(TextDecoration.ITALIC, false)
        );

        for (int i = 0; i < armorPieces.length; i++) {
            LeatherArmorMeta meta = (LeatherArmorMeta) armorPieces[i].getItemMeta();
            meta.displayName(Component.text(names[i], TextColor.color(0x9e9e9e)).decoration(TextDecoration.ITALIC, false));
            meta.setColor(Color.fromRGB(74, 74, 74));
            meta.addItemFlags(ItemFlag.HIDE_DYE);
            meta.lore(lore);

            NamespacedKey key = Keys.getItemKey(names[i].replace(" ", ""));
            meta.getPersistentDataContainer().set(key, PersistentDataType.BOOLEAN, true);

            armorPieces[i].setItemMeta(meta);

            ShapedRecipe recipe = new ShapedRecipe(
                    new NamespacedKey(ArmorUp.getInstance(), names[i].replace(" ", "") + "Recipe"),
                    armorPieces[i]
            );

            recipe.shape(shapes[i]);
            recipe.setIngredient('A', new ItemStack(Material.FLINT));

            switch (i) {
                case 0 -> recipeFlint1 = recipe;
                case 1 -> recipeFlint2 = recipe;
                case 2 -> recipeFlint3 = recipe;
                case 3 -> recipeFlint4 = recipe;
            }

            Bukkit.addRecipe(recipe);
        }
    }


    public static void registerAmethystArmor() {
        ItemStack[] armorPieces = {
                new ItemStack(Material.LEATHER_HELMET),
                new ItemStack(Material.LEATHER_CHESTPLATE),
                new ItemStack(Material.LEATHER_LEGGINGS),
                new ItemStack(Material.LEATHER_BOOTS)
        };

        String[] names = {
                "Amethyst Helmet", "Amethyst Chestplate", "Amethyst Leggings", "Amethyst Boots"
        };

        int[] armorValues = {2, 6, 5, 2};

        String[][] shapes = {
                {"AAA", "A A"},
                {"A A", "AAA", "AAA"},
                {"AAA", "A A", "A A"},
                {"A A", "A A"}
        };

        List<Component> lore = List.of(
                Component.text("Lights up all nearby living", NamedTextColor.BLUE).decoration(TextDecoration.ITALIC, false),
                Component.text("entities as a whole set.", NamedTextColor.BLUE).decoration(TextDecoration.ITALIC, false),
                Component.text(""),
                Component.text("Side effects: ", NamedTextColor.BLUE).decoration(TextDecoration.ITALIC, false).append(Component.text("Weakness, Nausea", NamedTextColor.DARK_RED)).decoration(TextDecoration.ITALIC, false)
        );

        for (int i = 0; i < armorPieces.length; i++) {
            LeatherArmorMeta meta = (LeatherArmorMeta) armorPieces[i].getItemMeta();
            meta.displayName(Component.text(names[i], TextColor.color(0xa376ec)).decoration(TextDecoration.ITALIC, false));
            meta.addEnchant(Enchantment.THORNS, 3, true);
            meta.setColor(Color.fromRGB(163, 118, 236));
            meta.addItemFlags(ItemFlag.HIDE_DYE);
            meta.lore(lore);
            meta.addAttributeModifier(
                    Attribute.ARMOR,
                    new AttributeModifier(
                            new NamespacedKey(ArmorUp.getInstance(), "Armor"),
                            armorValues[i],
                            AttributeModifier.Operation.ADD_NUMBER
                    )
            );

            NamespacedKey key = Keys.getItemKey(names[i].replace(" ", ""));
            meta.getPersistentDataContainer().set(key, PersistentDataType.BOOLEAN, true);

            armorPieces[i].setItemMeta(meta);

            ShapedRecipe recipe = new ShapedRecipe(
                    new NamespacedKey(ArmorUp.getInstance(), names[i].replace(" ", "") + "Recipe"),
                    armorPieces[i]
            );

            recipe.shape(shapes[i]);
            recipe.setIngredient('A', new ItemStack(Material.AMETHYST_SHARD));

            switch (i) {
                case 0 -> recipeAmethyst1 = recipe;
                case 1 -> recipeAmethyst2 = recipe;
                case 2 -> recipeAmethyst3 = recipe;
                case 3 -> recipeAmethyst4 = recipe;
            }

            Bukkit.addRecipe(recipe);
        }
    }


    public static void registerBows() {
        ItemStack iceBow = new ItemStack(Material.BOW);
        ItemMeta metaIceBow = iceBow.getItemMeta();

        metaIceBow.displayName(Component.text("Ice Bow", TextColor.color(0x0dd4ca)).decoration(TextDecoration.ITALIC, false));
        metaIceBow.addEnchant(Enchantment.PROJECTILE_PROTECTION, 1, true);
        metaIceBow.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        metaIceBow.getPersistentDataContainer().set(Keys.ICE_BOW, PersistentDataType.BOOLEAN, true);

        List<Component> lore1 = new ArrayList<>();
        lore1.add(Component.text("Freezes the ground on contact with blocks.", NamedTextColor.DARK_PURPLE).decoration(TextDecoration.ITALIC, false));
        lore1.add(Component.text("Slows down other players and creatures for 2 seconds.", NamedTextColor.DARK_PURPLE).decoration(TextDecoration.ITALIC, false));
        metaIceBow.lore(lore1);

        iceBow.setItemMeta(metaIceBow);

        recipeIceBow = new ShapedRecipe(new NamespacedKey(ArmorUp.getInstance(), "IceBowRecipe"), iceBow);
        recipeIceBow.shape("SSS", "SBS", "SSS");
        recipeIceBow.setIngredient('B', new ItemStack(Material.BOW));
        recipeIceBow.setIngredient('S', new ItemStack(Material.SNOWBALL));
        Bukkit.addRecipe(recipeIceBow);


        ItemStack fireBow = new ItemStack(Material.BOW);
        ItemMeta metaFireBow = fireBow.getItemMeta();

        metaFireBow.displayName(Component.text("Fire Bow", TextColor.color(0xf56c1d)).decoration(TextDecoration.ITALIC, false));
        metaFireBow.addEnchant(Enchantment.PROJECTILE_PROTECTION, 1, true);
        metaFireBow.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        metaFireBow.getPersistentDataContainer().set(Keys.FIRE_BOW, PersistentDataType.BOOLEAN, true);

        List<Component> lore2 = new ArrayList<>();
        lore2.add(Component.text("Creates an area with fire and lava.", NamedTextColor.DARK_PURPLE).decoration(TextDecoration.ITALIC, false));
        lore2.add(Component.text("Sets other players and creatures on fire for 3 seconds.", NamedTextColor.DARK_PURPLE).decoration(TextDecoration.ITALIC, false));
        metaFireBow.lore(lore2);

        fireBow.setItemMeta(metaFireBow);

        recipeFireBow = new ShapedRecipe(new NamespacedKey(ArmorUp.getInstance(), "FireBowRecipe"), fireBow);
        recipeFireBow.shape("LLL", "LBL", "LLL");
        recipeFireBow.setIngredient('B', new ItemStack(Material.BOW));
        recipeFireBow.setIngredient('L', new ItemStack(Material.LAVA_BUCKET));
        Bukkit.addRecipe(recipeFireBow);


        ItemStack tntBow = new ItemStack(Material.BOW);
        ItemMeta metaTNTBow = tntBow.getItemMeta();

        metaTNTBow.displayName(Component.text("TNT Bow", TextColor.color(0xf01111)).decoration(TextDecoration.ITALIC, false));
        metaTNTBow.addEnchant(Enchantment.PROJECTILE_PROTECTION, 1, true);
        metaTNTBow.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        metaTNTBow.getPersistentDataContainer().set(Keys.TNT_BOW, PersistentDataType.BOOLEAN, true);

        List<Component> lore3 = new ArrayList<>();
        lore3.add(Component.text("Shoots ignited TNT, which explodes after 3 seconds.", NamedTextColor.DARK_PURPLE).decoration(TextDecoration.ITALIC, false));
        metaTNTBow.lore(lore3);

        tntBow.setItemMeta(metaTNTBow);

        recipeTNTBow = new ShapedRecipe(new NamespacedKey(ArmorUp.getInstance(), "TNTBowRecipe"), tntBow);
        recipeTNTBow.shape("TTT", "TBT", "TTT");
        recipeTNTBow.setIngredient('B', new ItemStack(Material.BOW));
        recipeTNTBow.setIngredient('T', new ItemStack(Material.TNT));
        Bukkit.addRecipe(recipeTNTBow);


        ItemStack clusterBow = new ItemStack(Material.BOW);
        ItemMeta metaClusterBow = clusterBow.getItemMeta();

        metaClusterBow.displayName(Component.text("Cluster Bow", TextColor.color(0x698226)).decoration(TextDecoration.ITALIC, false));
        metaClusterBow.addEnchant(Enchantment.PROJECTILE_PROTECTION, 1, true);
        metaClusterBow.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        metaClusterBow.getPersistentDataContainer().set(Keys.CLUSTER_BOW, PersistentDataType.BOOLEAN, true);

        List<Component> lore4 = new ArrayList<>();
        lore4.add(Component.text("Shoots cluster ammunition, which causes further", NamedTextColor.DARK_PURPLE).decoration(TextDecoration.ITALIC, false));
        lore4.add(Component.text("projectiles to fall and explode on impact.", NamedTextColor.DARK_PURPLE).decoration(TextDecoration.ITALIC, false));
        metaClusterBow.lore(lore4);

        clusterBow.setItemMeta(metaClusterBow);

        recipeClusterBow = new ShapedRecipe(new NamespacedKey(ArmorUp.getInstance(), "ClusterBowRecipe"), clusterBow);
        recipeClusterBow.shape("GRG", "PBP", "GTG");
        recipeClusterBow.setIngredient('B', new ItemStack(Material.BOW));
        recipeClusterBow.setIngredient('P', new ItemStack(Material.BLAZE_POWDER));
        recipeClusterBow.setIngredient('G', new ItemStack(Material.GUNPOWDER));
        recipeClusterBow.setIngredient('R', new ItemStack(Material.REDSTONE_TORCH));
        recipeClusterBow.setIngredient('T', new ItemStack(Material.TNT));
        Bukkit.addRecipe(recipeClusterBow);
    }


    public static void registerSwords() {
        ItemStack electricSword = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta metaElectricSword = electricSword.getItemMeta();

        metaElectricSword.displayName(Component.text("Electric Sword", TextColor.color(0x11afd6)).decoration(TextDecoration.ITALIC, false));
        metaElectricSword.addEnchant(Enchantment.PROJECTILE_PROTECTION, 1, true);
        metaElectricSword.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        metaElectricSword.getPersistentDataContainer().set(Keys.ELECTRIC_SWORD, PersistentDataType.BOOLEAN, true);

        List<Component> lore1 = new ArrayList<>();
        lore1.add(Component.text("Generates an electric shock when", NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false));
        lore1.add(Component.text("attacking, which is transferred", NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false));
        lore1.add(Component.text("to adjacent opponents.", NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false));
        metaElectricSword.lore(lore1);

        electricSword.setItemMeta(metaElectricSword);

        recipeElectricSword = new ShapedRecipe(new NamespacedKey(ArmorUp.getInstance(), "ElectricSwordRecipe"), electricSword);
        recipeElectricSword.shape("RDR", "RDR", " S ");
        recipeElectricSword.setIngredient('S', new ItemStack(Material.STICK));
        recipeElectricSword.setIngredient('R', new ItemStack(Material.REDSTONE));
        recipeElectricSword.setIngredient('D', new ItemStack(Material.DIAMOND));
        Bukkit.addRecipe(recipeElectricSword);


        ItemStack airSword = new ItemStack(Material.IRON_SWORD);
        ItemMeta metaAirSword = airSword.getItemMeta();

        metaAirSword.displayName(Component.text("Air Sword", TextColor.color(0xFFFDD0)).decoration(TextDecoration.ITALIC, false));
        metaAirSword.addEnchant(Enchantment.PROJECTILE_PROTECTION, 1, true);
        metaAirSword.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        metaAirSword.getPersistentDataContainer().set(Keys.AIR_SWORD, PersistentDataType.BOOLEAN, true);

        List<Component> lore2 = new ArrayList<>();
        lore2.add(Component.text("Pushes all enemies around the player", NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false));
        lore2.add(Component.text("away when attacking. Enables a jump", NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false));
        lore2.add(Component.text("boost by right-clicking.", NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false));
        metaAirSword.lore(lore2);

        airSword.setItemMeta(metaAirSword);

        recipeAirSword = new ShapedRecipe(new NamespacedKey(ArmorUp.getInstance(), "AirSwordRecipe"), airSword);
        recipeAirSword.shape("WIW", "WIW", " S ");
        recipeAirSword.setIngredient('S', new ItemStack(Material.STICK));
        recipeAirSword.setIngredient('W', new ItemStack(Material.WIND_CHARGE));
        recipeAirSword.setIngredient('I', new ItemStack(Material.IRON_INGOT));
        Bukkit.addRecipe(recipeAirSword);
    }


}