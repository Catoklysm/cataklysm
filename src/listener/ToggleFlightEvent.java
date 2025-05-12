package listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import commands.FlyCommand;
import main.Cataklysm;

public class ToggleFlightEvent implements Listener {
    
    private final Plugin plugin = Cataklysm.getPlugin(Cataklysm.class);
    private final Map<Player, BukkitRunnable> flightTimers = new HashMap<>();

    @EventHandler
    public void onToggleFlight(PlayerToggleFlightEvent e) {
        Player player = e.getPlayer();

        if (player.isFlying() == false) { 
            // Player just started flying
            if (flightTimers.containsKey(player)) {
                return; // Prevent duplicate timers
            }

            BukkitRunnable task = (BukkitRunnable) new BukkitRunnable() {
                @Override
                public void run() {
                    if (player.isFlying() == false) { // Stop task if player stops flying
                        this.cancel();
                        flightTimers.remove(player);
                        return;
                    }
//-------------------------------------------Happening Every 5 Seconds-------------------------------------------
                    
                    
                    if (hasFlightCrystal(player)) {
                        durabilityDecrease(player);          
                    } else if(player.hasPermission("cataklysm.fly") || FlyCommand.fly.contains(player)){
                    	return;
                    } else {
	                    player.sendMessage("§cYour Flight has been disabled due to missing Flight Crystal!");
                        player.setAllowFlight(false);
                        this.cancel();
                        flightTimers.remove(player);
                    }
                }     
            };
            task.runTaskTimer(plugin, 0, 100); // Run every 5 seconds (100 ticks)
            flightTimers.put(player, task);

        } else { 
            // Player stopped flying
            if (flightTimers.containsKey(player)) {
                flightTimers.get(player).cancel();
                flightTimers.remove(player);
            }
        }
    }
    
	public static boolean hasFlightCrystal(Player player) {
	    if (player == null) return false;

	    String itemName = null;
	    List<String> itemLore = new ArrayList<>();

	    for (ItemStack stack : player.getInventory().getContents()) {
	        if (stack == null || !stack.hasItemMeta()) {
	            continue;
	        }

	        // Get the ItemMeta
	        ItemMeta meta = stack.getItemMeta();
	        if (meta == null) {
	            continue;
	        }

	        itemName = meta.getItemName();
	        itemLore = meta.getLore();

	        if (itemName != null && itemName.equals("§d§lFlight Crystal")) {
	        	
	            if (itemLore == null || itemLore.size() <= 1) {
	                invalidCrystal(player, stack, itemName, itemLore);
	                continue;
	            }
	        	
	            String loreLine = itemLore.get(1);
	            if (loreLine != null && itemLore.size() > 1 && loreLine.contains("Durability:")) {
	                return true;
	            } else {
	            	invalidCrystal(player, stack, itemName, itemLore);
	            }
	        }
	    }
	    return false;
	}
	
	public void durabilityDecrease(Player player) {
	    if (player == null) return;
	    
	    for (ItemStack stack : player.getInventory().getContents()) {
	        if (stack == null || !stack.hasItemMeta()) { 
	            continue; 
	        }

	        ItemMeta meta = stack.getItemMeta();
	        if (meta == null) {
	            continue;
	        }

	        String itemName = meta.getItemName();
	        List<String> itemLore = meta.getLore();

	        if (itemName != null && itemName.equals("§d§lFlight Crystal") && itemLore != null && itemLore.size() > 1) {
	            String loreLine = itemLore.get(1); // Get the second line of lore

                
	            // Extract the durability number
	            Pattern lastIntPattern = Pattern.compile("[^0-9]+([0-9]+)$");
	            Matcher matcher = lastIntPattern.matcher(loreLine);
	            if (matcher.find()) { 
	            	
	            	try {
	            		
                    long parsedValue = Long.parseLong(matcher.group(1)); // Use long to detect overflow
                    int currentDurability = (parsedValue > Integer.MAX_VALUE) ? Integer.MAX_VALUE : (int) parsedValue;
                    int newDurability = Math.max(0, currentDurability - 1); // Ensure durability doesn't go negative

	                // Update the lore with the new durability
	                itemLore.set(1, "§r§7Durability: " + newDurability);
	                meta.setLore(itemLore);
	                stack.setItemMeta(meta);

	                // If durability reaches 0, remove the item
	                if (newDurability == 0) {
	                    player.getInventory().remove(stack);
	                    player.sendMessage("§cYour Flight Crystal has broken!");
	                    player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1, 1);
	                    if (!hasFlightCrystal(player)) {
                        player.setAllowFlight(false);
	                    }
	                }
	            } catch (NumberFormatException e) {
	                    // Handle invalid number (too large, corrupted, etc.)
	                    invalidCrystal(player, stack, itemName, itemLore);      
	                }

	                return;
	            } else {
	            	
	            	invalidCrystal(player, stack, itemName, itemLore);
           
	            }
	        }
	    }
	}
	
	public static void invalidCrystal(Player player, ItemStack stack, String itemName, List<String> itemLore) {
		
        player.getInventory().remove(stack);
        player.sendMessage("§cYour Flight Crystal has broken! Cataklysm Flight Crystal ERROR: Invalid Crystal");
        Bukkit.getConsoleSender().sendMessage("Player " + player.getName() + " with UUID " + player.getUniqueId() + " has caused Cataklysm Flight Crystal ERROR: Invalid Crystal");
        Bukkit.getConsoleSender().sendMessage("Checking item: " + (itemName != null ? itemName : "No Name"));
        Bukkit.getConsoleSender().sendMessage("Item Lore: " + (itemLore != null ? String.join(" | ", itemLore) : "No Lore"));
        player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1, 1);
        if (!hasFlightCrystal(player)) {
        player.setAllowFlight(false);
		
	}
	
	}
	
}
