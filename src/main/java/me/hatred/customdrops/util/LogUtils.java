package me.hatred.customdrops.util;

import me.hatred.customdrops.CustomDrops;
import org.bukkit.Bukkit;

public class LogUtils {

    public enum Level {
        INFO("", "INFO: "),
        ENABLE("§a", " "),
        SUCCESS("§a", "SUCCESS: "),
        WARNING("§e", "WARNING: "),
        DEBUG("§6", "DEBUG: "),
        SEVERE("§c", "ERROR: ");

        private String color, prefix;

        private Level(String color, String prefix) {
            this.color = color;
            this.prefix = prefix;
        }

        public String getColor() {
            return color;
        }

        public String getPrefix() {
            return prefix;
        }
    }


    public static void log(Level level, CustomDrops plugin, String message) {
        log(level, plugin, message, true);
    }

    public static void log(Level level, CustomDrops plugin, String message, boolean color) {
        log(level, plugin.getName(), message, color);
    }

    public static void log(Level level, String prefix, String message) {
        log(level, prefix, message, true);
    }

    public static void log(Level level, String prefix, String message, boolean color) {
        if (level.equals(Level.DEBUG) && !(CustomDrops.config.getConfig() == null ? true : CustomDrops.config.getConfig().getBoolean("show_debug", true))) {
            return;
        }
        Bukkit.getConsoleSender().sendMessage((color ? level.getColor() : "") + "[" + prefix + "] " + level.getPrefix() + message);
    }

}