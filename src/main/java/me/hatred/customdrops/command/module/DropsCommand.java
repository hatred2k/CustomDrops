package me.hatred.customdrops.command.module;

import me.hatred.customdrops.CustomDrops;
import me.hatred.customdrops.command.BaseCommand;
import me.hatred.customdrops.command.CommandException;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.Plugin;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DropsCommand extends BaseCommand {

    public DropsCommand(Plugin plugin) {
        super(plugin);
    }

    @Override
    public boolean requiresPermission() {
        return true;
    }

    @Override
    public String getPermission() {
        return "customdrops.admin";
    }

    @Override
    protected void onCommand(CommandSender sender, String[] args) throws CommandException {
        if (args.length == 0) {
            sender.sendMessage(CustomDrops.LINE);
            sender.sendMessage(CustomDrops.color("&7&l* &a/cd reload &8- &7reloads plugin/config"));
            sender.sendMessage(CustomDrops.color("&7&l* &a/cd check <mob> &8- &7shows current mob drops"));
            sender.sendMessage(CustomDrops.LINE);
        } else if (args.length == 1) {
            if (args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("r")) {
                CustomDrops.getInstance().reloadConfig();
                sender.sendMessage(CustomDrops.color("&7[&aCustomDrops&7] &7Plugin reloaded!"));
            } else if (args[0].equalsIgnoreCase("check") || args[0].equalsIgnoreCase("view")) {
                sender.sendMessage(CustomDrops.color("&7[&aCustomDrops&7] &7Invalid syntax: &c/cd view <mob>"));
            } else if (args[0].equalsIgnoreCase("debug")) {
                String repl = "DAMAGE_ALL:10";
                String substring = repl.substring(repl.lastIndexOf(":") + 1);
                System.out.println(substring);
            }
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("check") || args[0].equalsIgnoreCase("view")) {
                String mob = StringUtils.join(args, ' ', 1, args.length);
                if (!CustomDrops.getInstance().getConfig().getConfigurationSection("mobs").contains(mob.toUpperCase())) {
                    sender.sendMessage(CustomDrops.color("&7[&aCustomDrops&7] &cThat mob either has no drops or is not in the config."));
                    return;
                }
                ConfigurationSection defined = CustomDrops.getInstance().getConfig().getConfigurationSection("mobs." + mob.toUpperCase() + ".drops");
                String name = mob.toUpperCase();
                sender.sendMessage(CustomDrops.LINE);
                sender.sendMessage(CustomDrops.color("&aMob: &7" + CustomDrops.convert(name)));
                sender.sendMessage(CustomDrops.color("&aDrops:"));
                for (String key : defined.getKeys(false)) {
                    int amount = defined.getInt(key+".amount");
                    sender.sendMessage(CustomDrops.color("&7&l* &a" + CustomDrops.convert(key) + " &7(" + amount + ")"));
                }
                sender.sendMessage(CustomDrops.LINE);
            }
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return Collections.emptyList();
    }

}
