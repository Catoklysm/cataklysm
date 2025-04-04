package listener;

import java.util.HashMap;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import main.Cataklysm;




public class OnInventoryClick implements Listener {
	
	private Plugin plugin = Cataklysm.getPlugin(Cataklysm.class);
	
    public static int removeItems(Inventory inventory, Material type, int amount) {
    	 
        if(type == null || inventory == null)
            return -1;       
        if (amount <= 0)
            return -1;
 
        if (amount == Integer.MAX_VALUE) {
            inventory.remove(type);
            return 0;
        }
 
        HashMap<Integer,ItemStack> retVal = inventory.removeItem(new ItemStack(type,amount));
 
        int notRemoved = 0;
        for(ItemStack item: retVal.values()) {
            notRemoved+=item.getAmount();
        }
        return notRemoved;
    }

	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
	    Player player = (Player) e.getWhoClicked();
	    ItemStack clicked = e.getCurrentItem();

	    // Prevent errors with empty slots
	    if (clicked == null || clicked.getType() == Material.AIR) {
	        return;
	    }

	 
// ------------------------------------------------ World Border Increase ------------------------------------------------
	    
	    
	    if (e.getView().getTitle().equals("§b§lWorld Border Increase")) {
		    double worldborderSize = Bukkit.getWorld("world").getWorldBorder().getSize();
	        e.setCancelled(true);

	        Material clickedType = clicked.getType();

	        // Count total items of the clicked type in inventory
	        int totalAmount = 0;
	        for (ItemStack stack : player.getInventory().getContents()) {
	            if (stack != null && stack.getType() == clickedType) {
	                totalAmount += stack.getAmount();
	            }
	        }

	        // Handle different materials
	        if (clicked.hasItemMeta() && clicked.getItemMeta().hasDisplayName()) {
	            String itemName = clicked.getItemMeta().getDisplayName();
	        
	        if (clickedType == Material.DIAMOND && itemName.equals("§b§lMagical Diamond") && totalAmount >= 32) {
	            removeItems(player.getInventory(), Material.DIAMOND, 32);
	            Bukkit.getWorld("world").getWorldBorder().setSize(worldborderSize + 32);
	            Bukkit.broadcastMessage("§a" + player.getName() + "§6 increased the Worldborder by §b1 Chunk§6!");
	        } else if (clickedType == Material.NETHERITE_INGOT && itemName.equals("§7§lAncient Netherite Ingot") && totalAmount >= 1) {
	            removeItems(player.getInventory(), Material.NETHERITE_INGOT, 1);
	            Bukkit.getWorld("world").getWorldBorder().setSize(worldborderSize + (32 * 5));
	            Bukkit.broadcastMessage("§a" + player.getName() + "§6 increased the Worldborder by §b5 Chunks§6!");
	        } else if (clickedType == Material.NETHER_STAR && itemName.equals("§b§ka§d§l Star of the Nether §b§ka") && totalAmount >= 1) {
	            removeItems(player.getInventory(), Material.NETHER_STAR, 1);
	            Bukkit.getWorld("world").getWorldBorder().setSize(worldborderSize + (32 * 25));
	            Bukkit.broadcastMessage("§a" + player.getName() + "§6 increased the Worldborder by §b§l25 Chunks§6!");
	        }
	        }
	        
	        
// ------------------------------------------------ Menu ------------------------------------------------
	        
	    }else 	    if (e.getView().getTitle().equals("§a§lMenu")) {
	        e.setCancelled(true);

	        Material clickedType = clicked.getType();

	        // Handle different materials
	        if (clicked.hasItemMeta() && clicked.getItemMeta().hasDisplayName()) {
	            String itemName = clicked.getItemMeta().getDisplayName();
	        if (clickedType == Material.GRASS_BLOCK && itemName.equals("§a§lSpawn")) {
	        	
	            player.chat("/spawn");
	        } else 	        if (clickedType == Material.LIGHT_BLUE_STAINED_GLASS && itemName.equals("§b§lWorld Border Increase")) {
	        	
	            player.chat("/borderincrease");
	        }else 	        if (clickedType == Material.CHEST && itemName.equals("§6§lHome")) {
	        	
	            player.chat("/home");
	        }else 	        if (clickedType == Material.EMERALD && itemName.equals("§5§lServer Boost")) {
	        	
	            player.chat("/serverboost");
	        
	        }
	        }
	        
// ------------------------------------------------ Boost ------------------------------------------------	        
	        
	    } else if (e.getView().getTitle().equals("§d§lServer Boosts")) {
	        e.setCancelled(true);

	        Material clickedType = clicked.getType();
	        Material CostItem = null;
	        int CostAmount = 0;

	        // Handle different materials
	        if (clicked.hasItemMeta() && clicked.getItemMeta().hasDisplayName()) {
	            String itemName = clicked.getItemMeta().getDisplayName();
	        
	            //Sets Cost
	            
	        if (clickedType == Material.POTION && itemName.equals("§d§lMystical Potion")) {
	        	
	        	CostItem = Material.DIAMOND;
	        	CostAmount = 8;
	            
	        } else if (clickedType == Material.EXPERIENCE_BOTTLE && itemName.equals("§a§lExtreme Experience")) {
	        	
	        	CostItem = Material.DIAMOND;
	        	CostAmount = 16;
	            
	        }
	        
	        if (CostItem != null) {
	            // Check if the required item is in the inventory
	            int foundAmount = 0;
	            for (ItemStack stack : player.getInventory().getContents()) {
	                if (stack != null && stack.getType() == CostItem) {
	                    foundAmount += stack.getAmount();
	                }
	            }

	            // If the player has enough of the required item, proceed
	            if (foundAmount >= CostAmount) {
	                removeItems(player.getInventory(), CostItem, CostAmount);
	                
	                if (clickedType == Material.POTION && itemName.equals("§d§lMystical Potion")) {
	                	
	                    Bukkit.broadcastMessage("§a" + player.getName() + "§6 has activated a random positive effect!");
	                    int minutes = 10;
	                    int time = minutes * 20 * 60;
	                    
	                    Random rand = new Random();
	                    int random = rand.nextInt(9);
	                    int strength = rand.nextInt(2);
	                    
	                    for
						(Player all : Bukkit.getServer().getOnlinePlayers())
	                    
	                    if(random == 0) {            
		                    all.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, time, strength));
	                    }else if(random == 1) {
		                    all.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, time, 0));
	                    }else if(random == 2) {
		                    all.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, time, strength));
	                    }else if(random == 3) {
		                    all.addPotionEffect(new PotionEffect(PotionEffectType.HASTE, time, strength));
	                    }else if(random == 4) {
		                    all.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, time, strength));
	                    }else if(random == 5) {
		                    all.addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH, time, strength));
	                    }else if(random == 6) {
		                    all.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, time, 0));
	                    }else if(random == 7) {
		                    all.addPotionEffect(new PotionEffect(PotionEffectType.LUCK, time, strength));
	                    }else if(random == 8) {
		                    all.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, time, 0));
	                    }
	                    
	                    
// ------------------------------------------------ Experience Boost ------------------------------------------------
	                    
	                } else if (clickedType == Material.EXPERIENCE_BOTTLE && itemName.equals("§a§lExtreme Experience")) {
	                	
	                    int minutes = 30;
	                    int time = minutes * 20 * 60;
	                    Bukkit.broadcastMessage("§a" + player.getName() + "§6 has activated §b§lDouble XP§6 for " + minutes + " minutes!");
        				plugin.getConfig().set("XPBoost", true);
        				plugin.saveConfig();
	            		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Cataklysm.getInstance(), new Runnable()
	            		{

	            			@Override
							public void run() {

	            				plugin.getConfig().set("XPBoost", false);
	            				plugin.saveConfig();
	    	                    Bukkit.broadcastMessage("§b§lDouble XP§c has ended!");
							}

	            		}, time);     
	                    
	                } 
	            }
	        }
	        
	        
	        
	        }
	        
	        
	        
	    }
	    	
	    	
	}
}