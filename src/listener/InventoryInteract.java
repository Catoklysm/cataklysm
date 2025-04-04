package listener;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import main.Cataklysm;

public class InventoryInteract implements Listener {
    
    private final Plugin plugin = Cataklysm.getPlugin(Cataklysm.class);

    @EventHandler
    public void OnInventoryInteract(InventoryInteractEvent e) {
        Player player = (Player) e.getWhoClicked();
        Bukkit.broadcastMessage("[DEBUG] " + player.getName() + " interacted with an inventory.");

        if (hasCharm(player)) {
        	Bukkit.broadcastMessage("[DEBUG] " + player.getName() + " has a charm. Starting effect task.");

            BukkitRunnable task = new BukkitRunnable() {
                @Override
                public void run() {
                    if (!hasCharm(player)) {
                    	Bukkit.broadcastMessage("[DEBUG] " + player.getName() + " no longer has a charm. Stopping task.");
                        this.cancel();
                        return;
                    }
                    Bukkit.broadcastMessage("[DEBUG] " + player.getName() + " is receiving charm effects.");
                    player.addPotionEffect(new PotionEffect(charmType(player), 100, 0));
                    durabilityDecrease(player);
                }
            };
            task.runTaskTimer(plugin, 0, 100);
        }
    }

    public static boolean hasCharm(Player player) {
        boolean hasCharm = getItemStack(player) != null;
        Bukkit.broadcastMessage("[DEBUG] Checking if " + player.getName() + " has a charm: " + hasCharm);
        return hasCharm;
    }

    public static ItemStack getItemStack(Player player) {
        if (player == null) return null;

        for (ItemStack stack : player.getInventory().getContents()) {
            if (stack == null || !stack.hasItemMeta()) continue;

            ItemMeta meta = stack.getItemMeta();
            if (meta == null) continue;

            String itemName = meta.getDisplayName();
            List<String> itemLore = meta.getLore();

            if (itemName != null && itemName.contains("§eCharm") && itemLore != null && itemLore.size() > 1) {
                Bukkit.getLogger().info("[DEBUG] Found charm for " + player.getName());
                return stack;
            }
        }
        Bukkit.broadcastMessage("[DEBUG] No charm found for " + player.getName());
        return null;
    }

    public static PotionEffectType charmType(Player player) {
        ItemStack stack = getItemStack(player);
        if (stack == null) return null;

        ItemMeta meta = stack.getItemMeta();
        if (meta == null) return null;

        List<String> itemLore = meta.getLore();
        if (itemLore == null || itemLore.isEmpty()) return null;

        String loreLine = itemLore.get(0);
        Bukkit.broadcastMessage("[DEBUG] Checking charm effect for " + player.getName() + ": " + loreLine);

        if (loreLine.contains("Effect:")) {
            if (loreLine.contains("Speed")) return PotionEffectType.SPEED;
            if (loreLine.contains("Haste")) return PotionEffectType.HASTE;
            if (loreLine.contains("Strength")) return PotionEffectType.STRENGTH;
            if (loreLine.contains("Jump Boost")) return PotionEffectType.JUMP_BOOST;
            if (loreLine.contains("Regeneration")) return PotionEffectType.REGENERATION;
            if (loreLine.contains("Resistance")) return PotionEffectType.RESISTANCE;
            if (loreLine.contains("Water Breathing")) return PotionEffectType.WATER_BREATHING;
            if (loreLine.contains("Invisibility")) return PotionEffectType.INVISIBILITY;
            if (loreLine.contains("Night Vision")) return PotionEffectType.NIGHT_VISION;
            if (loreLine.contains("Health Boost")) return PotionEffectType.HEALTH_BOOST;
        }
        return null;
    }

    public static void durabilityDecrease(Player player) {
        ItemStack stack = getItemStack(player);
        if (stack == null) return;

        ItemMeta meta = stack.getItemMeta();
        if (meta == null) return;

        List<String> itemLore = meta.getLore();
        if (itemLore == null || itemLore.size() < 2) {
        	Bukkit.broadcastMessage("[DEBUG] Invalid charm detected for " + player.getName());
            return;
        }

        String loreLine = itemLore.get(1);
        Pattern lastIntPattern = Pattern.compile("[^0-9]+([0-9]+)$");
        Matcher matcher = lastIntPattern.matcher(loreLine);

        if (matcher.find()) {
            try {
                int newDurability = Math.max(0, Integer.parseInt(matcher.group(1)) - 1);
                itemLore.set(1, "§r§7Durability: " + newDurability);
                meta.setLore(itemLore);
                stack.setItemMeta(meta);

                Bukkit.broadcastMessage("[DEBUG] " + player.getName() + "'s charm durability now " + newDurability);

                if (newDurability == 0) {
                    player.getInventory().remove(stack);
                    player.sendMessage("§cYour " + meta.getDisplayName() + "§c has broken!");
                    player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1, 1);
                }
            } catch (NumberFormatException e) {
            	Bukkit.broadcastMessage("[DEBUG] Failed to parse durability for " + player.getName());
            }
        }
    }
}
