package listener;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.bukkit.block.TileState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import main.Cataklysm;

public class PlayerInteract implements Listener {
	
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onPlayerInteract(PlayerInteractEvent e) {

	        if (!e.hasBlock()) return;

	        Block block = e.getClickedBlock();
	        if (!(block.getState() instanceof Container container)) return;

	        String customName = container.getCustomName();
	        if (customName == null || !customName.contains("§eTrade Shop")) return;
		
			e.setCancelled(true);
			
			Player player = (Player) e.getPlayer();
			Inventory inv = Bukkit.createInventory(null, 27, "§e§lTrade Shop");
			
	        new BukkitRunnable() {
	            public void run() {
	    			player.openInventory(inv);
	            }
	        }.runTaskLater(Cataklysm.getInstance(), 1L);
			
			
			if (block.getState() instanceof TileState tileState) {
			    NamespacedKey key = new NamespacedKey(Cataklysm.getInstance(), "owner_uuid");
			    String uuidString = tileState.getPersistentDataContainer().get(key, PersistentDataType.STRING);
			    
			    if (uuidString != null) {
			        @SuppressWarnings("unused")
					UUID ownerUUID = UUID.fromString(uuidString);

			    	} else Cataklysm.getInstance().getLogger().severe("Item Trade Shop is missing a UUID. Name: " + container.getCustomName() + " at location: " + container.getLocation());
			    }
			}
		
		
	}
	
