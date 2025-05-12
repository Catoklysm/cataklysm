package listener;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import configManager.configManager;
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
    
    public static int giveItems(Inventory inventory, Material type, int amount) {
        if (type == null || inventory == null || amount <= 0)
            return -1;

        ItemStack item = new ItemStack(type, amount);
        inventory.addItem(item);
        return amount;
    }
    
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
	    Player player = (Player) e.getWhoClicked();
	    ItemStack clicked = e.getCurrentItem();

	   if (clicked == null || clicked.getType() == Material.AIR && !e.getView().getTitle().contains("§eTrade Shop")) {
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
	        
	     // ------------------------------------------------ Trade Shop ------------------------------------------------	        
	        
	    } else if (e.getView().getTitle().contains("§eTrade Shop")) {
	    	
	    	e.setCancelled(true);

	    	//player.sendMessage("Click: " + e.getClick() + ", Action: " + e.getAction() + ", Slot: " + e.getSlot() + ", Raw Slot: " + e.getRawSlot() + ", Cursor: " + e.getCursor());
	    	
	    	Map<UUID, String> activeShopUUID = InventoryOpen.activeShopUUID;
	    	Map<UUID, String> activeShopID = InventoryOpen.activeShopID;	    	
	    	String shopUUID = activeShopUUID.get(player.getUniqueId());  // Retrieve shopID from the map
	    	String shopID = activeShopID.get(player.getUniqueId());  // Retrieve shopID from the map


	        if (shopUUID == null) {
	            player.sendMessage("§cYour Session is experied. Error 1");
	            return;
	        }

	        if (shopID == null) {
	            player.sendMessage("§cYour Session is experied. Error 2");
	            return;
	        }

	        UUID ownerUUID = UUID.fromString(shopUUID);
			configManager.PlayerData data = configManager.getPlayerData(ownerUUID);
			FileConfiguration config = data.getConfig();

	        if (!config.contains("shop." + shopID)) {
	            player.sendMessage("§cThis shop does not exist or has been deleted.");
	            return;
	        }

	        Material costItem = Material.matchMaterial(config.getString("shop." + shopID + ".costItem"));
	        int costAmount = config.getInt("shop." + shopID + ".costAmount", 1);

	        if (costItem == null) {
	        	player.sendMessage("§cThis shop is misconfigured. Please contact the owner or an admin.");
	            return;
	        }

//---------------------------------Owner Inventory----------------------------------
	        
			if (player.getUniqueId().equals(ownerUUID)) {

				if ((e.getRawSlot() == 13)) {
					e.setCancelled(true); 
				} else{
					e.setCancelled(false); 
					return;
				}
				
				if(e.getClick() == ClickType.LEFT || e.getClick() == ClickType.RIGHT){
					
					boolean emptyCursor = (e.getCursor() == null || e.getCursor().getType() == Material.AIR);
					if(emptyCursor && e.getRawSlot() < e.getView().getTopInventory().getSize()) return;
					
					//Set Shop Price and Item
					
					if(e.getSlot() == 13 && !emptyCursor) {
						
						config.set("shop." + shopID + ".costItem", e.getCursor().getType().toString());
						config.set("shop." + shopID + ".costAmount", e.getCursor().getAmount());
						data.save();
						
					    player.sendMessage("§aShop cost updated.");
					    player.closeInventory();

					} 

				
				} else if(e.getClick() == ClickType.SHIFT_LEFT && (e.getRawSlot() == 13)){
					
					Inventory inv = Bukkit.createInventory(null, 27, "§cDELETE §eTrade Shop");
					
					ItemStack glass = Cataklysm.createCustomItem(Material.LIGHT_GRAY_STAINED_GLASS_PANE, "§7");
					ItemStack deleteShop = Cataklysm.createCustomItem(
							 Material.RED_STAINED_GLASS_PANE,
							"§c§lCONFIRM SHOP DELETE",
							"§7Shift Right Click to confirm!",
							"§7Contents will presist."
						);
					
					for (int i = 0; i < inv.getSize(); i++) {
					    inv.setItem(i, glass);
					}
					
					inv.setItem(13, deleteShop);
					 player.openInventory(inv);
				}
				
				//Shop Deletion
				if (e.getView().getTitle().contains("§cDELETE §eTrade Shop") && e.getRawSlot() == 13 && (e.getClick() == ClickType.SHIFT_RIGHT)) {
					
				    String[] parts = shopID.split(",");
				    if (parts.length == 4) {
				        String worldName = parts[0];
				        int x = Integer.parseInt(parts[1]);
				        int y = Integer.parseInt(parts[2]);
				        int z = Integer.parseInt(parts[3]);

				        World world = Bukkit.getWorld(worldName);
				        if (world == null) {
				        	return;
				        }
				            Location location = new Location(world, x, y, z);
				            Block block = location.getBlock();
					
				    if (block.getState() instanceof Container container) {
				    	
				        	container.setCustomName(null);
				        	container.update(true, true);		
				        
				        	PersistentDataContainer dataContainer = container.getPersistentDataContainer();
				        	dataContainer.remove(new NamespacedKey(Cataklysm.getInstance(), "owner_uuid"));
				        	dataContainer.remove(new NamespacedKey(Cataklysm.getInstance(), "shop_id"));
				        	container.update(true, true);
				        
							config.set("shop." + shopID, null);
							data.save();
						
	        			player.sendMessage("§cYour shop has been deleted.");
	        			player.closeInventory();
							}
	            		}
					
				}
//---------------------------------User Inventory----------------------------------
			}else { 
				
			if (e.isShiftClick() || e.getClick() == ClickType.DOUBLE_CLICK || e.getClick() == ClickType.NUMBER_KEY) {
			        e.setCancelled(true);
			        return;
			}
			
	        ItemStack cursor = e.getCursor();
	        ItemStack current = e.getCurrentItem();

	        boolean hasItemOnCursor = cursor != null && cursor.getType() != Material.AIR;
	        boolean isTargetEmpty = current == null || current.getType() == Material.AIR;

	        // Block all placement into empty slots
	        if (hasItemOnCursor && isTargetEmpty && e.getRawSlot() < e.getView().getTopInventory().getSize()) {
	            e.setCancelled(true);
	            return;
	        }
				
			if (e.getRawSlot() < e.getView().getTopInventory().getSize()) {
		        // Allow only if player is offering the exact correct item & amount
		        if (e.getCursor() != null
		                && e.getCursor().getType() == costItem
		                && e.getCursor().getAmount() == costAmount
		                && e.getCurrentItem() != null
		                && e.getCurrentItem().getType() != Material.AIR
		                && e.getCurrentItem().getType() != costItem) {

		            e.setCancelled(false);

		        } else {
		            e.setCancelled(true);
		        }
		        return;
		    }

			if (e.getClick() == ClickType.DOUBLE_CLICK
	    		    || e.getAction() == InventoryAction.COLLECT_TO_CURSOR
	    		    || e.isShiftClick()
	    		    || e.getClick() == ClickType.NUMBER_KEY
	    		    || e.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY
	    		    || e.getAction() == InventoryAction.SWAP_WITH_CURSOR) {
	    		    return;
	    		}
			
		    e.setCancelled(false);
		    
				}
	    	}
	    
	    if(e.isCancelled() == true) {
	    	Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Cataklysm.getInstance(), new Runnable()
			{   @Override
				public void run() {
				player.updateInventory();
				}
			}, 2); 
	    }
	    
		}

	  
	
    
	public static int getFreeSpace(Inventory inventory, Material type) {
	    int space = 0;
	    int maxStackSize = type.getMaxStackSize();

	    for (int slot = 0; slot <= 35; slot++) {
	        ItemStack item = inventory.getItem(slot);

	        if (item == null || item.getType() == Material.AIR) {
	            space += maxStackSize;
	        } else if (item.getType() == type) {
	            int remaining = maxStackSize - item.getAmount();
	            if (remaining > 0) {
	                space += remaining;
	            }
	        }
	    }

	    return space;
	}
	public static int getItemAmount(Inventory inventory, Material type) {
	    int amount = 0;

	    for (int slot = 0; slot <= 35; slot++) {
	        ItemStack item = inventory.getItem(slot);

	        if (item != null && item.getType() == type) {
	            amount += inventory.getItem(slot).getAmount();
	        }
	    }

	    return amount;
	}
	    	
}
