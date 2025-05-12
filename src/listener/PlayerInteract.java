package listener;

import java.util.UUID;

import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.bukkit.block.TileState;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.persistence.PersistentDataType;

import configManager.configManager;
import main.Cataklysm;

public class PlayerInteract implements Listener {
	
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onPlayerInteract(PlayerInteractEvent e) {

		
//----------------------------------Trade Shop---------------------------------------------
		
		if (!e.hasBlock()) return;

		Block block = e.getClickedBlock();
		if (!(block.getState() instanceof Container)) return;

		Player player = e.getPlayer();

		if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.PHYSICAL) {
			return;
		}
		
		if (block.getState() instanceof TileState tileState) {
			NamespacedKey ownerKey = new NamespacedKey(Cataklysm.getInstance(), "owner_uuid");
			String uuidString = tileState.getPersistentDataContainer().get(ownerKey, PersistentDataType.STRING);

			if (uuidString == null) {
				return;
			}

			NamespacedKey shopIdKey = new NamespacedKey(Cataklysm.getInstance(), "shop_id");
			String shopID = tileState.getPersistentDataContainer().get(shopIdKey, PersistentDataType.STRING);

			if (shopID == null) {
				return;
			}
			
			UUID ownerUUID = UUID.fromString(uuidString);
			configManager.PlayerData data = configManager.getPlayerData(ownerUUID);
			FileConfiguration Config = data.getConfig();
			
			if (!Config.contains("shop." + shopID)) {
				return;
			}

			if(!player.hasPermission("cataklysm.shop.view")) {
				player.sendMessage(Cataklysm.Permission + "§c[Permission: §6cataklysm.shop.view§c]");
				return;
			}
			
			if (block.getState() instanceof Container container) {
				e.setCancelled(true);
				Inventory inv = container.getInventory();
				player.openInventory(inv);
			}
		}
	}
		
		
	}
	
