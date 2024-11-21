package me.detonario.armorup.other;

import me.detonario.armorup.ArmorUp;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public final class Settings {

    private static final Settings instance = new Settings();

    private File file;
    private YamlConfiguration config;

    private Settings() {
    }


    public void load() {
        file = new File(ArmorUp.getInstance().getDataFolder(), "settings.yml");

        if (!file.exists()) {
            ArmorUp.getInstance().saveResource("settings.yml", false);
        }

        config = new YamlConfiguration();

        try {
            config.load(file);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void save() {
        if (config != null) {
            try {
                config.save(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public YamlConfiguration getConfig() {
        return config;
    }

    public static Settings getInstance() {
        return instance;
    }

}
