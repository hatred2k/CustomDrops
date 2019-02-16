package me.hatred.customdrops.util;

import me.hatred.customdrops.CustomDrops;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.logging.Level;

public class FileManager {

    private CustomDrops Plugin;
    public String fileName;
    private JavaPlugin plugin;
    public File ConfigFile;
    private FileConfiguration Configuration;

    public FileManager(CustomDrops Plugin) {
        this.Plugin = Plugin;
    }

    public FileManager(JavaPlugin plugin, String fileName) {
        if (plugin == null) {
            throw new IllegalArgumentException("plugin cannot be null");
        }
        this.plugin = plugin;
        this.fileName = fileName;
        File dataFolder = plugin.getDataFolder();
        if (dataFolder == null) {
            throw new IllegalStateException();
        }
        this.ConfigFile = new File(dataFolder.toString() + File.separatorChar + this.fileName);
    }

    public void reloadConfig() {
        InputStreamReader f = null;
        try {
            f = new InputStreamReader(new FileInputStream(this.ConfigFile), "UTF-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }

        this.Configuration = YamlConfiguration.loadConfiguration(f);

        InputStream defConfigStream = this.plugin.getResource(this.fileName);
        if (defConfigStream != null) {
            @SuppressWarnings("deprecation")
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defConfigStream));
            this.Configuration.setDefaults(defConfig);
        }
        if (defConfigStream != null)
            try {
                defConfigStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        if (f != null)
            try {
                f.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    public FileConfiguration getConfig() {
        if (this.Configuration == null) {
            reloadConfig();
        }
        return this.Configuration;
    }

    public void saveConfig() {
        if ((this.Configuration == null) || (this.ConfigFile == null)) {
            return;
        }
        try {
            getConfig().save(this.ConfigFile);
        } catch (IOException ex) {
            this.plugin.getLogger().log(Level.SEVERE, "Could not save config to " + this.ConfigFile, ex);
        }
    }

    public void saveDefaultConfig() {
        if (!this.ConfigFile.exists()) {
            this.plugin.saveResource(this.fileName, false);
        }
    }
}
