package me.detonario.armorup;

import me.detonario.armorup.command.ArmorUpCommand;
import me.detonario.armorup.listener.AnyListener;
import me.detonario.armorup.listener.ArmorListener;
import me.detonario.armorup.listener.BowListener;
import me.detonario.armorup.other.CustomRecipes;
import me.detonario.armorup.other.Settings;
import me.detonario.armorup.task.ArmorTask;
import me.detonario.armorup.task.ArrowTask;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.Objects;

public final class ArmorUp extends JavaPlugin {

    private BukkitTask task1;
    private BukkitTask task2;

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(AnyListener.getInstance(), this);
        getServer().getPluginManager().registerEvents(ArmorListener.getInstance(), this);
        getServer().getPluginManager().registerEvents(BowListener.getInstance(), this);

        Bukkit.getScheduler().runTaskLater(this, () -> Objects.requireNonNull(getCommand("au")).setExecutor(new ArmorUpCommand()), 4);

        Settings.getInstance().load();

        CustomRecipes.registerEmeraldArmor();
        CustomRecipes.registerObsidianArmor();
        CustomRecipes.registerSpongeArmor();
        CustomRecipes.registerCopperArmor();
        CustomRecipes.registerSuperGoldArmor();
        CustomRecipes.registerRedstoneArmor();
        CustomRecipes.registerFlintArmor();
        CustomRecipes.registerAmethystArmor();

        CustomRecipes.registerBows();
        CustomRecipes.registerSwords();

        task1 = getServer().getScheduler().runTaskTimer(this, ArmorTask.getInstance(), 0, 1);
        task2 = getServer().getScheduler().runTaskTimer(this, ArrowTask.getInstance(), 0, 1);
    }


    @Override
    public void onDisable() {
        Settings.getInstance().save();

        if (task1 != null && !task1.isCancelled()) {
            task1.cancel();
        }

        if (task2 != null && !task2.isCancelled()) {
            task2.cancel();
        }
    }


    public static ArmorUp getInstance() {
        return getPlugin(ArmorUp.class);
    }

}
