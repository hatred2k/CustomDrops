package me.hatred.customdrops;

import me.hatred.customdrops.command.BaseCommand;
import me.hatred.customdrops.command.module.DropsCommand;
import me.hatred.customdrops.listener.MobListener;
import me.hatred.customdrops.util.FileManager;
import me.hatred.customdrops.util.LogUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

public final class CustomDrops extends JavaPlugin {

    public static FileManager config;
    private static CustomDrops instance;
    private Map<String, BaseCommand> commands;

    @Override
    public void onEnable() {
        instance = this;
        this.commands = new HashMap<>();
        this.commands.put("customdrops", new DropsCommand(this));
        loadCommands();
        config = new FileManager(this, "config.yml");
        config.saveDefaultConfig();
        LogUtils.log(LogUtils.Level.INFO, instance, "-------------------------------------");
        LogUtils.log(LogUtils.Level.INFO, instance, "Enabling CustomDrops " + instance.getDescription().getVersion() + " by HatredPvP");
        LogUtils.log(LogUtils.Level.INFO, instance, "-------------------------------------");
        Bukkit.getPluginManager().registerEvents(new MobListener(), this);
    }

    @Override
    public void onDisable() {
        LogUtils.log(LogUtils.Level.INFO, instance, "--------------------------------------");
        LogUtils.log(LogUtils.Level.INFO, instance, "Disabling CustomDrops " + instance.getDescription().getVersion() + " by HatredPvP");
        LogUtils.log(LogUtils.Level.INFO, instance, "--------------------------------------");
        instance = null;
    }

    public static CustomDrops getInstance() {
        return instance;
    }

    public static String color(String c) {
        return ChatColor.translateAlternateColorCodes('&', c);
    }

    public static String LINE = ChatColor.GRAY.toString() + ChatColor.STRIKETHROUGH
            + "-----------------------------";

    public void loadCommands() {
        this.commands.forEach((name, command) -> this.getCommand(name).setExecutor(command));
    }

    public static String convert(String str) {
        char ch[] = str.toCharArray();
        for (int i = 0; i < str.length(); i++) {
            if (i == 0 && ch[i] != ' ' ||
                    ch[i] != ' ' && ch[i - 1] == ' ') {
                if (ch[i] >= 'a' && ch[i] <= 'z') {
                    ch[i] = (char)(ch[i] - 'a' + 'A');
                }
            }
            else if (ch[i] >= 'A' && ch[i] <= 'Z')
                ch[i] = (char)(ch[i] + 'a' - 'A');
        }
        String st = new String(ch);
        return st;
    }
}
