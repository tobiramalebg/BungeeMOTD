package fr.gonzyui.utils;

import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class ConfigurationUtil {
    private final Plugin plugin;

    public ConfigurationUtil(Plugin plugin) {
        this.plugin = plugin;
    }

    public Configuration getConfiguration(String file) {
        File dataFolder = this.plugin.getDataFolder();
        file = file.replace("%datafolder%", dataFolder.toPath().toString());
        try {
            return ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(file));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void createConfiguration(String file) {
        try {
            File dataFolder = this.plugin.getDataFolder();
            file = file.replace("%datafolder%", dataFolder.toPath().toString());
            File configFile = new File(file);
            if (!configFile.exists()) {
                String[] files = file.split("/");
                InputStream inputStream = this.plugin.getClass().getClassLoader().getResourceAsStream(files[files.length - 1]);
                File parentFile = configFile.getParentFile();
                if (parentFile != null)
                    parentFile.mkdirs();
                if (inputStream != null) {
                    Files.copy(inputStream, configFile.toPath(), new java.nio.file.CopyOption[0]);
                    System.out.print(("[%pluginname%] Le fichier " + configFile + " a été crée!").replace("%pluginname%", this.plugin.getDescription().getName()));
                } else {
                    configFile.createNewFile();
                }
            }
        } catch (IOException e) {
            System.out.print("[%pluginname%] Impossible de crée le fichier de configuration!".replace("%pluginname%", this.plugin.getDescription().getName()));
        }
    }

    public void saveConfiguration(Configuration configuration, String file) {
        this.plugin.getProxy().getScheduler().runAsync(this.plugin, () -> {
            try {
                File dataFolder = this.plugin.getDataFolder();
                ConfigurationProvider.getProvider(YamlConfiguration.class).save(configuration, new File(file.replace("%datafolder%", dataFolder.toPath().toString())));
            } catch (IOException e) {
                System.out.print("[%pluginname%] Impossible de sauvegarder le fichier de configuration!".replace("%pluginname%", this.plugin.getDescription().getName()));
            }
        });
    }

    public void deleteConfiguration(String file) {
        this.plugin.getProxy().getScheduler().runAsync(this.plugin, () -> {
            File file1 = new File(file);
            if (file1.exists())
                file1.delete();
        });
    }
}
