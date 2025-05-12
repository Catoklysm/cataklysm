package listener;

import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryDragEvent;

public class InventoryDrag implements Listener{
	@EventHandler
	public void onInventoryDrag(InventoryDragEvent e) {
        Player player = (Player) e.getWhoClicked();
		
	    if (e.getView().getTitle().contains("§eTrade Shop")) {
	        
	        boolean isOwner = false;
	        String shopUUID = InventoryOpen.activeShopUUID.get(player.getUniqueId());
	        
	        if (shopUUID != null) {
	            UUID ownerUUID = UUID.fromString(shopUUID);
	            isOwner = player.getUniqueId().equals(ownerUUID);
	        }

	        if (!isOwner) {
	            for (int slot : e.getRawSlots()) {
	                if (slot < e.getView().getTopInventory().getSize()) {
	                    e.setCancelled(true);
	                    return;
	                }
	            }
	        }
	    }
	}
}
