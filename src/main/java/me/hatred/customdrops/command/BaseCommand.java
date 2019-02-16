package me.hatred.customdrops.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.Plugin;

import java.util.Collections;
import java.util.List;

public abstract class BaseCommand implements CommandExecutor, TabCompleter {

    private Plugin plugin;

    public BaseCommand(Plugin plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {
            if (this.requiresPermission() && !sender.hasPermission(this.getPermission()))
                throw new CommandException(Collections.singletonList("You do not have permission to execute this command."));
            this.onCommand(sender, args);
        } catch (CommandException e) {
            for (String message : e.getMessages()) {
                sender.sendMessage(ChatColor.RED + message);
            }
        }

        return true;
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (this.requiresPermission() && !sender.hasPermission(this.getPermission()))
            sender.sendMessage(ChatColor.RED + "You do not have permission to execute this command.");
        return this.onTabComplete(sender, args);
    }

    public abstract boolean requiresPermission();

    public abstract String getPermission();

    protected abstract void onCommand(CommandSender sender, String[] args) throws CommandException;

    public abstract List<String> onTabComplete(CommandSender sender, String[] args);
}