package listener;
import org.bukkit.Material;
import org.bukkit.Nameable;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Container;
import org.bukkit.block.TileState;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import commands.CreateShopCommand;
import configManager.configManager;
import main.Cataklysm;

public class BlockPlace implements Listener {

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		
	    Player player = (Player) e.getPlayer();
	    Material block = e.getBlock().getType();
	    
	    if(CreateShopCommand.createshop.contains(player)) {
	    if(block.name().endsWith("SHULKER_BOX") || block == Material.CHEST || block == Material.BARREL){
	    	
	    	
	    	new BukkitRunnable() {
	    	    public void run() {
	    	        if (e.getBlockPlaced().getState() instanceof Container) {
	    	            Container container = (Container) e.getBlockPlaced().getState();

	    	            if (container.getInventory().getItem(13) != null && container.getInventory().getItem(13).getType() != Material.AIR) {
	    	                player.sendMessage("§cYou must leave slot 13 empty to create a shop.");
	    	                return;
	    	            }
	    	            
	    	            // Store UUID silently using PersistentDataContainer
	    	            if (container instanceof TileState tileState) {
	    	                NamespacedKey key = new NamespacedKey(Cataklysm.getInstance(), "owner_uuid");
	    	                tileState.getPersistentDataContainer().set(key, PersistentDataType.STRING, player.getUniqueId().toString());
	    	                
	    	                NamespacedKey shopIDKey = new NamespacedKey(Cataklysm.getInstance(), "shop_id");
	    	                String shopID = container.getWorld().getName() + "," + container.getX() + "," + container.getY() + "," + container.getZ();
	    	                tileState.getPersistentDataContainer().set(shopIDKey, PersistentDataType.STRING, shopID);
	    	                
	    	                ((Nameable) tileState).setCustomName("§e" + player.getDisplayName() + "'s §eTrade Shop");
	    	                
	    	                tileState.update();
	    	                
	    					configManager.PlayerData data = configManager.getPlayerData(player);
	    					FileConfiguration Config = data.getConfig();
	    					
	    					Config.set("shop." + shopID + ".costItem", Material.DIAMOND.name());
	    					Config.set("shop." + shopID + ".costAmount", 1);
	    					data.save();
	    					
	    	                
	    	            } else {
	    	                container.update();
	    	            }
	    	        }
	    	    }
	    	}.runTaskLater(Cataklysm.getInstance(), 1L);
	    	
	    }
	  }
		
	}
	
}
