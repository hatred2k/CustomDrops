package me.hatred.customdrops.listener;

import me.hatred.customdrops.CustomDrops;
import me.hatred.customdrops.util.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class MobListener implements Listener {

    @EventHandler
    public void onDeath(EntityDeathEvent e) {
        LivingEntity mob = e.getEntity();
        if (!CustomDrops.getInstance().getConfig().getConfigurationSection("mobs").contains(mob.getName().toUpperCase())) {
            return;
        }
        ConfigurationSection defined = CustomDrops.getInstance().getConfig().getConfigurationSection("mobs." + mob.getName().toUpperCase() + ".drops");
        if (CustomDrops.getInstance().getConfig().getBoolean("remove_default_loot")) {
            e.getDrops().clear();
        }
        for (String key : defined.getKeys(false)) {
            String amount = defined.getString(key+".amount");
            if (amount.equalsIgnoreCase("random")) {
                Random rand = new Random();
                int min = defined.getInt(key+".min");
                int max = defined.getInt(key+".max");
                amount = String.valueOf(rand.nextInt(max - min + 1) + min);
            }
            Material loot = Material.valueOf(key);
            HashMap<Material, ItemStack> items = new HashMap<>();
            if (defined.contains(key+".name") && defined.contains(key+".lore")) {
                ItemStack drop = new ItemBuilder(loot).setName(CustomDrops.color(defined.getString(key + ".name")))
                        .setLore(colorize(defined.getStringList(key + ".lore")))
                        .toItemStack();
                items.put(loot, drop);
            } else if (defined.contains(key+".name")) {
                ItemStack drop = new ItemBuilder(loot).setName(CustomDrops.color(defined.getString(key + ".name")))
                        .toItemStack();
                items.put(loot, drop);
            } else {
                ItemStack drop = new ItemBuilder(loot).toItemStack();
                items.put(loot, drop);
            }
            for (int i = 0; i < Integer.parseInt(amount); i++) {
                mob.getLocation().getWorld().dropItem(mob.getLocation(), items.get(loot));
            }
        }
    }

    private List<String> colorize(List<String> list) {
        return list.stream().map(line-> ChatColor.translateAlternateColorCodes('&',line)).collect(Collectors.toList());
    }
}
