package listener;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Container;
import org.bukkit.block.TileState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import commands.CreateShopCommand;
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

	    	            // Set pretty display name
	    	            container.setCustomName("§e" + player.getName() + "'s §eTrade Shop");

	    	            // Store UUID silently using PersistentDataContainer
	    	            if (container instanceof TileState tileState) {
	    	                NamespacedKey key = new NamespacedKey(Cataklysm.getInstance(), "owner_uuid");
	    	                tileState.getPersistentDataContainer().set(key, PersistentDataType.STRING, player.getUniqueId().toString());
	    	                tileState.update();
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
