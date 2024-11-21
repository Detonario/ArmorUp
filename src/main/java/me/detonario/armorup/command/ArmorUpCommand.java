package me.detonario.armorup.command;

import me.detonario.armorup.ArmorUp;
import me.detonario.armorup.listener.BowListener;
import me.detonario.armorup.other.CustomRecipes;
import me.detonario.armorup.other.Settings;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.permissions.PermissionAttachment;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public final class ArmorUpCommand implements CommandExecutor, TabExecutor {

    private final MiniMessage miniMessage = MiniMessage.miniMessage();

    private static final Map<UUID, PermissionAttachment> armorPermissions = new HashMap<>();
    private static final Map<UUID, PermissionAttachment> bowPermissions = new HashMap<>();
    private static final Map<UUID, PermissionAttachment> swordPermissions = new HashMap<>();


    private final ItemStack[] emeraldList = {CustomRecipes.recipeEmerald1.getResult(), CustomRecipes.recipeEmerald2.getResult(),
            CustomRecipes.recipeEmerald3.getResult(), CustomRecipes.recipeEmerald4.getResult()};

    private final ItemStack[] obsidianList = {CustomRecipes.recipeObsidian1.getResult(), CustomRecipes.recipeObsidian2.getResult(),
            CustomRecipes.recipeObsidian3.getResult(), CustomRecipes.recipeObsidian4.getResult()};

    private final ItemStack[] spongeList = {CustomRecipes.recipeSponge1.getResult(), CustomRecipes.recipeSponge2.getResult(),
            CustomRecipes.recipeSponge3.getResult(), CustomRecipes.recipeSponge4.getResult()};

    private final ItemStack[] copperList = {CustomRecipes.recipeCopper1.getResult(), CustomRecipes.recipeCopper2.getResult(),
            CustomRecipes.recipeCopper3.getResult(), CustomRecipes.recipeCopper4.getResult()};

    private final ItemStack[] superGoldList = {CustomRecipes.recipeSuperGold1.getResult(), CustomRecipes.recipeSuperGold2.getResult(),
            CustomRecipes.recipeSuperGold3.getResult(), CustomRecipes.recipeSuperGold4.getResult()};

    private final ItemStack[] redstoneList = {CustomRecipes.recipeRedstone1.getResult(), CustomRecipes.recipeRedstone2.getResult(),
            CustomRecipes.recipeRedstone3.getResult(), CustomRecipes.recipeRedstone4.getResult()};

    private final ItemStack[] flintList = {CustomRecipes.recipeFlint1.getResult(), CustomRecipes.recipeFlint2.getResult(),
            CustomRecipes.recipeFlint3.getResult(), CustomRecipes.recipeFlint4.getResult()};

    private final ItemStack[] amethystList = {CustomRecipes.recipeAmethyst1.getResult(), CustomRecipes.recipeAmethyst2.getResult(),
            CustomRecipes.recipeAmethyst3.getResult(), CustomRecipes.recipeAmethyst4.getResult()};


    private final ItemStack[] bowList = {CustomRecipes.recipeIceBow.getResult(), CustomRecipes.recipeFireBow.getResult(),
            CustomRecipes.recipeTNTBow.getResult(), CustomRecipes.recipeClusterBow.getResult()};


    private final ItemStack[] swordList = {CustomRecipes.recipeElectricSword.getResult(), CustomRecipes.recipeAirSword.getResult()};


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        final YamlConfiguration config = Settings.getInstance().getConfig();

        if (!(sender instanceof Player player)) {
            sender.sendMessage("This command is for players only!");
            return true;
        }

        if (!player.hasPermission("armorup.permission.command")) {
            player.sendMessage("You need to have the 'command' permission.");
            return true;
        }


        if (args.length == 0) {
            Component message = miniMessage.deserialize(
                    "<dark_green>Set the bow cooldown: </dark_green><gold>/au cooldown [seconds]</gold>" +
                            "\n" +
                            "\n<dark_green>Assign permissions: </dark_green><gold>/au permission [armor/bow/sword] give [player]</gold>" +
                            "\n<dark_green>Remove permissions: </dark_green><gold>/au permission [armor/bow/sword] remove [player]</gold>" +
                            "\n" +
                            "\n<dark_green>Give armor: </dark_green><gold>/au item armor [emerald/obsidian/sponge/copper/" +
                            "\nsuperGold/redstone/flint/amethyst] give [player]</gold>" +
                            "\n<dark_green>Give bow: </dark_green><gold>/au item bow [ice/fire/tnt/cluster] give [player]</gold>" +
                            "\n<dark_green>Give sword: </dark_green><gold>/au item sword [electric/air] give [player]</gold>"
            );
            player.sendMessage(message);


        } else if (args.length == 2 && args[0].equalsIgnoreCase("cooldown")) {
            if (player.hasPermission("armorup.permission.admin")) {

                try {
                    int cooldown = Integer.parseInt(args[1]) * 20;

                    if (cooldown >= 0) {
                        BowListener instance = BowListener.getInstance();
                        instance.setBowCooldown(cooldown);

                        player.sendMessage("Bow cooldown set for all players for " + cooldown / 20 + " seconds.");
                    } else {
                        player.sendMessage("Please enter a positive number for the cooldown.");
                    }

                } catch (NumberFormatException e) {
                    player.sendMessage("Enter a valid number for the cooldown.");
                }

            } else {
                player.sendMessage("You need to be an administrator in order to set a cooldown.");
            }


        } else if (args.length == 4 && args[0].equalsIgnoreCase("permission") && args[1].equalsIgnoreCase("armor")
                && args[2].equalsIgnoreCase("give")) {

            if (player.hasPermission("armorup.permission.admin")) {
                String targetName = args[3];
                Player targetPlayer = Bukkit.getPlayerExact(targetName);

                if (targetPlayer != null) {

                    UUID uuid = targetPlayer.getUniqueId();
                    ConfigurationSection sectionPlayers = config.getConfigurationSection("players");

                    if (sectionPlayers != null && !sectionPlayers.contains(uuid.toString())) {
                        sectionPlayers.createSection(uuid.toString());
                    } else if (sectionPlayers == null) {
                        config.createSection("players");
                    }

                    if (!armorPermissions.containsKey(uuid)) {
                        PermissionAttachment permission = targetPlayer.addAttachment(ArmorUp.getInstance(), "armorup.permission.armor", true);
                        armorPermissions.put(uuid, permission);

                        config.set("players." + uuid + ".armor", true);
                        Settings.getInstance().save();

                        player.sendMessage("Permission assigned to " + targetPlayer.getName());
                    } else {
                        player.sendMessage("Player " + targetName + " already has this permission.");
                    }

                } else {
                    player.sendMessage("Player " + targetName + " is not online.");
                }

            } else {
                player.sendMessage("You need to be an administrator in order to assign permissions.");
            }


        } else if (args.length == 4 && args[0].equalsIgnoreCase("permission") && args[1].equalsIgnoreCase("armor")
                && args[2].equalsIgnoreCase("remove")) {

            if (player.hasPermission("armorup.permission.admin")) {
                String targetName = args[3];
                Player targetPlayer = Bukkit.getPlayerExact(targetName);

                if (targetPlayer != null) {

                    UUID uuid = targetPlayer.getUniqueId();

                    if (armorPermissions.containsKey(uuid)) {
                        PermissionAttachment permission = armorPermissions.remove(targetPlayer.getUniqueId());
                        targetPlayer.removeAttachment(permission);

                        config.set("players." + uuid + ".armor", false);
                        Settings.getInstance().save();


                        player.sendMessage("Permission removed from " + targetPlayer.getName());
                    } else {
                        player.sendMessage("Player " + targetName + " doesn't have this permission.");
                    }

                } else {
                    player.sendMessage("Player " + targetName + " is not online.");
                }

            } else {
                player.sendMessage("You need to be an administrator in order to remove permissions.");
            }


        } else if (args.length == 4 && args[0].equalsIgnoreCase("permission") && args[1].equalsIgnoreCase("bow")
                && args[2].equalsIgnoreCase("give")) {

            if (player.hasPermission("armorup.permission.admin")) {
                String targetName = args[3];
                Player targetPlayer = Bukkit.getPlayerExact(targetName);

                if (targetPlayer != null) {

                    UUID uuid = targetPlayer.getUniqueId();
                    ConfigurationSection sectionPlayers = config.getConfigurationSection("players");

                    if (sectionPlayers != null && !sectionPlayers.contains(uuid.toString())) {
                        sectionPlayers.createSection(uuid.toString());
                    } else if (sectionPlayers == null) {
                        config.createSection("players");
                    }

                    if (!bowPermissions.containsKey(uuid)) {
                        PermissionAttachment permission = targetPlayer.addAttachment(ArmorUp.getInstance(), "armorup.permission.bow", true);
                        bowPermissions.put(uuid, permission);

                        config.set("players." + uuid + ".bow", true);
                        Settings.getInstance().save();

                        player.sendMessage("Permission assigned to " + targetPlayer.getName());
                    } else {
                        player.sendMessage("Player " + targetName + " already has this permission.");
                    }

                } else {
                    player.sendMessage("Player " + targetName + " is not online.");
                }

            } else {
                player.sendMessage("You need to be an administrator in order to assign permissions.");
            }


        } else if (args.length == 4 && args[0].equalsIgnoreCase("permission") && args[1].equalsIgnoreCase("bow")
                && args[2].equalsIgnoreCase("remove")) {

            if (player.hasPermission("armorup.permission.admin")) {
                String targetName = args[3];
                Player targetPlayer = Bukkit.getPlayerExact(targetName);

                if (targetPlayer != null) {

                    UUID uuid = targetPlayer.getUniqueId();

                    if (bowPermissions.containsKey(uuid)) {
                        PermissionAttachment permission = bowPermissions.remove(targetPlayer.getUniqueId());
                        targetPlayer.removeAttachment(permission);

                        config.set("players." + uuid + ".bow", false);
                        Settings.getInstance().save();


                        player.sendMessage("Permission removed from " + targetPlayer.getName());
                    } else {
                        player.sendMessage("Player " + targetName + " doesn't have this permission.");
                    }

                } else {
                    player.sendMessage("Player " + targetName + " is not online.");
                }

            } else {
                player.sendMessage("You need to be an administrator in order to remove permissions.");
            }


        } else if (args.length == 4 && args[0].equalsIgnoreCase("permission") && args[1].equalsIgnoreCase("sword")
                && args[2].equalsIgnoreCase("give")) {

            if (player.hasPermission("armorup.permission.admin")) {
                String targetName = args[3];
                Player targetPlayer = Bukkit.getPlayerExact(targetName);

                if (targetPlayer != null) {

                    UUID uuid = targetPlayer.getUniqueId();
                    ConfigurationSection sectionPlayers = config.getConfigurationSection("players");

                    if (sectionPlayers != null && !sectionPlayers.contains(uuid.toString())) {
                        sectionPlayers.createSection(uuid.toString());
                    } else if (sectionPlayers == null) {
                        config.createSection("players");
                    }

                    if (!swordPermissions.containsKey(uuid)) {
                        PermissionAttachment permission = targetPlayer.addAttachment(ArmorUp.getInstance(), "armorup.permission.sword", true);
                        swordPermissions.put(uuid, permission);

                        config.set("players." + uuid + ".sword", true);
                        Settings.getInstance().save();

                        player.sendMessage("Permission assigned to " + targetPlayer.getName());
                    } else {
                        player.sendMessage("Player " + targetName + " already has this permission.");
                    }

                } else {
                    player.sendMessage("Player " + targetName + " is not online.");
                }

            } else {
                player.sendMessage("You need to be an administrator in order to assign permissions.");
            }


        } else if (args.length == 4 && args[0].equalsIgnoreCase("permission") && args[1].equalsIgnoreCase("sword")
                && args[2].equalsIgnoreCase("remove")) {

            if (player.hasPermission("armorup.permission.admin")) {
                String targetName = args[3];
                Player targetPlayer = Bukkit.getPlayerExact(targetName);

                if (targetPlayer != null) {

                    UUID uuid = targetPlayer.getUniqueId();

                    if (swordPermissions.containsKey(uuid)) {
                        PermissionAttachment permission = swordPermissions.remove(targetPlayer.getUniqueId());
                        targetPlayer.removeAttachment(permission);

                        config.set("players." + uuid + ".sword", false);
                        Settings.getInstance().save();

                        player.sendMessage("Permission removed from " + targetPlayer.getName());
                    } else {
                        player.sendMessage("Player " + targetName + " doesn't have this permission.");
                    }

                } else {
                    player.sendMessage("Player " + targetName + " is not online.");
                }

            } else {
                player.sendMessage("You need to be an administrator in order to remove permissions.");
            }


        } else if (args.length == 5 && args[0].equalsIgnoreCase("item") && args[1].equalsIgnoreCase("armor")
                && (args[2].equalsIgnoreCase("emerald") || args[2].equalsIgnoreCase("obsidian")
                || args[2].equalsIgnoreCase("sponge") || args[2].equalsIgnoreCase("copper")
                || args[2].equalsIgnoreCase("superGold") || args[2].equalsIgnoreCase("redstone")
                || args[2].equalsIgnoreCase("flint") || args[2].equalsIgnoreCase("amethyst")) && args[3].equalsIgnoreCase("give")) {

            if (player.hasPermission("armorup.permission.admin")) {
                String targetName = args[4];
                Player targetPlayer = Bukkit.getPlayerExact(targetName);

                String armorType = args[2];

                if (targetPlayer != null) {

                    if (armorType.equalsIgnoreCase("emerald")) {
                        for (int i = 0; i < 4; i++) {
                            ItemStack item = emeraldList[i];
                            targetPlayer.getInventory().addItem(item);
                        }
                    } else if (armorType.equalsIgnoreCase("obsidian")) {
                        for (int i = 0; i < 4; i++) {
                            ItemStack item = obsidianList[i];
                            targetPlayer.getInventory().addItem(item);
                        }
                    } else if (armorType.equalsIgnoreCase("sponge")) {
                        for (int i = 0; i < 4; i++) {
                            ItemStack item = spongeList[i];
                            targetPlayer.getInventory().addItem(item);
                        }
                    } else if (armorType.equalsIgnoreCase("copper")) {
                        for (int i = 0; i < 4; i++) {
                            ItemStack item = copperList[i];
                            targetPlayer.getInventory().addItem(item);
                        }
                    } else if (armorType.equalsIgnoreCase("superGold")) {
                        for (int i = 0; i < 4; i++) {
                            ItemStack item = superGoldList[i];
                            targetPlayer.getInventory().addItem(item);
                        }
                    } else if (armorType.equalsIgnoreCase("redstone")) {
                        for (int i = 0; i < 4; i++) {
                            ItemStack item = redstoneList[i];
                            targetPlayer.getInventory().addItem(item);
                        }
                    } else if (armorType.equalsIgnoreCase("flint")) {
                        for (int i = 0; i < 4; i++) {
                            ItemStack item = flintList[i];
                            targetPlayer.getInventory().addItem(item);
                        }
                    } else if (armorType.equalsIgnoreCase("amethyst")) {
                        for (int i = 0; i < 4; i++) {
                            ItemStack item = amethystList[i];
                            targetPlayer.getInventory().addItem(item);
                        }
                    }


                } else {
                    player.sendMessage("Player " + targetName + " is not online.");
                }

            } else {
                player.sendMessage("You need to be an administrator in order to cheat.");
            }


        } else if (args.length == 5 && args[0].equalsIgnoreCase("item") && args[1].equalsIgnoreCase("bow")
                && (args[2].equalsIgnoreCase("ice") || args[2].equalsIgnoreCase("fire")
                || args[2].equalsIgnoreCase("tnt") || args[2].equalsIgnoreCase("cluster")) && args[3].equalsIgnoreCase("give")) {

            if (player.hasPermission("armorup.permission.admin")) {
                String targetName = args[4];
                Player targetPlayer = Bukkit.getPlayerExact(targetName);

                String bowType = args[2];

                if (targetPlayer != null) {

                    if (bowType.equalsIgnoreCase("ice")) {
                        ItemStack item = bowList[0];
                        targetPlayer.getInventory().addItem(item);

                    } else if (bowType.equalsIgnoreCase("fire")) {
                        ItemStack item = bowList[1];
                        targetPlayer.getInventory().addItem(item);

                    } else if (bowType.equalsIgnoreCase("tnt")) {
                        ItemStack item = bowList[2];
                        targetPlayer.getInventory().addItem(item);

                    } else if (bowType.equalsIgnoreCase("cluster")) {
                        ItemStack item = bowList[3];
                        targetPlayer.getInventory().addItem(item);
                    }

                } else {
                    player.sendMessage("Player " + targetName + " is not online.");
                }

            } else {
                player.sendMessage("You need to be an administrator in order to cheat.");
            }


        } else if (args.length == 5 && args[0].equalsIgnoreCase("item") && args[1].equalsIgnoreCase("sword")
                && (args[2].equalsIgnoreCase("electric") || args[2].equalsIgnoreCase("air")) && args[3].equalsIgnoreCase("give")) {

            if (player.hasPermission("armorup.permission.admin")) {
                String targetName = args[4];
                Player targetPlayer = Bukkit.getPlayerExact(targetName);

                String bowType = args[2];

                if (targetPlayer != null) {

                    if (bowType.equalsIgnoreCase("electric")) {
                        ItemStack item = swordList[0];
                        targetPlayer.getInventory().addItem(item);

                    } else if (bowType.equalsIgnoreCase("air")) {
                        ItemStack item = swordList[1];
                        targetPlayer.getInventory().addItem(item);

                    }

                } else {
                    player.sendMessage("Player " + targetName + " is not online.");
                }

            } else {
                player.sendMessage("You need to be an administrator in order to cheat.");
            }

        } else {
            Component message = miniMessage.deserialize("<red>Unknown command.</red> Please use <gold>/au</gold> or <gold>/armorup</gold> for more information.");
            player.sendMessage(message);
        }


        return true;
    }


    @Override
    public @NotNull List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (args.length == 1) {
            return Arrays.asList("cooldown", "permission", "item");


        } else if (args.length == 2 && (args[0].equalsIgnoreCase("permission") || args[0].equalsIgnoreCase("item"))) {
            return Arrays.asList("armor", "bow", "sword");


        } else if (args.length == 3 && args[0].equalsIgnoreCase("permission")
                && (args[1].equalsIgnoreCase("armor") || args[1].equalsIgnoreCase("bow") || args[1].equalsIgnoreCase("sword"))) {
            return Arrays.asList("give", "remove");

        } else if (args.length == 4 && args[0].equalsIgnoreCase("permission")
                && (args[1].equalsIgnoreCase("armor") || args[1].equalsIgnoreCase("bow") || args[1].equalsIgnoreCase("sword"))
                && (args[2].equalsIgnoreCase("give") || args[2].equalsIgnoreCase("remove"))) {

            List<String> playerNames = new ArrayList<>();
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                playerNames.add(onlinePlayer.getName());
            }

            return playerNames;


        } else if (args.length == 3 && args[0].equalsIgnoreCase("item") && args[1].equalsIgnoreCase("armor")) {
            return Arrays.asList("emerald", "obsidian", "sponge", "copper", "superGold", "redstone", "flint", "amethyst");

        } else if (args.length == 4 && args[0].equalsIgnoreCase("item") && args[1].equalsIgnoreCase("armor")
                && (args[2].equalsIgnoreCase("emerald") || args[2].equalsIgnoreCase("obsidian")
                || args[2].equalsIgnoreCase("sponge") || args[2].equalsIgnoreCase("copper")
                || args[2].equalsIgnoreCase("superGold") || args[2].equalsIgnoreCase("redstone")
                || args[2].equalsIgnoreCase("flint") || args[2].equalsIgnoreCase("amethyst"))) {
            return List.of("give");

        } else if (args.length == 5 && args[0].equalsIgnoreCase("item") && args[1].equalsIgnoreCase("armor")
                && (args[2].equalsIgnoreCase("emerald") || args[2].equalsIgnoreCase("obsidian")
                || args[2].equalsIgnoreCase("sponge") || args[2].equalsIgnoreCase("copper")
                || args[2].equalsIgnoreCase("superGold") || args[2].equalsIgnoreCase("redstone")
                || args[2].equalsIgnoreCase("flint") || args[2].equalsIgnoreCase("amethyst"))
                && args[3].equalsIgnoreCase("give")) {

            List<String> playerNames = new ArrayList<>();
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                playerNames.add(onlinePlayer.getName());
            }

            return playerNames;


        } else if (args.length == 3 && args[0].equalsIgnoreCase("item") && args[1].equalsIgnoreCase("bow")) {
            return Arrays.asList("ice", "fire", "tnt", "cluster");

        } else if (args.length == 4 && args[0].equalsIgnoreCase("item") && args[1].equalsIgnoreCase("bow")
                && (args[2].equalsIgnoreCase("ice") || args[2].equalsIgnoreCase("fire")
                || args[2].equalsIgnoreCase("tnt") || args[2].equalsIgnoreCase("cluster"))) {
            return List.of("give");

        } else if (args.length == 5 && args[0].equalsIgnoreCase("item") && args[1].equalsIgnoreCase("bow")
                && (args[2].equalsIgnoreCase("ice") || args[2].equalsIgnoreCase("fire")
                || args[2].equalsIgnoreCase("tnt") || args[2].equalsIgnoreCase("cluster"))
                && args[3].equalsIgnoreCase("give")) {

            List<String> playerNames = new ArrayList<>();
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                playerNames.add(onlinePlayer.getName());
            }

            return playerNames;


        } else if (args.length == 3 && args[0].equalsIgnoreCase("item") && args[1].equalsIgnoreCase("sword")) {
            return Arrays.asList("electric", "air");

        } else if (args.length == 4 && args[0].equalsIgnoreCase("item") && args[1].equalsIgnoreCase("sword")
                && (args[2].equalsIgnoreCase("electric") || args[2].equalsIgnoreCase("air"))) {
            return List.of("give");

        } else if (args.length == 5 && args[0].equalsIgnoreCase("item") && args[1].equalsIgnoreCase("sword")
                && (args[2].equalsIgnoreCase("electric") || args[2].equalsIgnoreCase("air"))
                && args[3].equalsIgnoreCase("give")) {

            List<String> playerNames = new ArrayList<>();
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                playerNames.add(onlinePlayer.getName());
            }

            return playerNames;
        }


        return new ArrayList<>();
    }


    public static Map<UUID, PermissionAttachment> getArmorPerms() {
        return armorPermissions;
    }

    public static Map<UUID, PermissionAttachment> getBowPerms() {
        return bowPermissions;
    }

    public static Map<UUID, PermissionAttachment> getSwordPerms() {
        return swordPermissions;
    }


}
