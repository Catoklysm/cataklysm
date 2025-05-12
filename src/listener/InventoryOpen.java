package listener;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.bukkit.block.TileState;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import configManager.configManager;
import main.Cataklysm;

public class InventoryOpen implements Listener {
	
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onInventoryOpen(InventoryOpenEvent e) {
		if (!e.getView().getTitle().contains("§eTrade Shop")) return;

		Player player = (Player) e.getPlayer();
		ShopContext context = getShopContext(e.getInventory().getHolder());
		if (context == null) return;

		if (!isValidShop(context, player)) return;

		// Proceed with opening the GUI
		e.setCancelled(false);
		openInv(context.ownerUUID.toString(), player, context.shopID, context.container);
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onInventoryClose(InventoryCloseEvent e) {
		if (!e.getView().getTitle().contains("§eTrade Shop")) return;

		Player player = (Player) e.getPlayer();
		ShopContext context = getShopContext(e.getInventory().getHolder());
		if (context == null) return;

		if (!isValidShop(context, player)) return;

		context.container.getInventory().clear(13);
	}

	
	private static class ShopContext {
		public final Container container;
		public final String shopID;
		public final UUID ownerUUID;

		public ShopContext(Container container, String shopID, UUID ownerUUID) {
			this.container = container;
			this.shopID = shopID;
			this.ownerUUID = ownerUUID;
		}
	}
	
	private ShopContext getShopContext(InventoryHolder holder) {
		if (!(holder instanceof Container container)) return null;

		Block block = container.getBlock();
		if (!(block.getState() instanceof TileState tileState)) return null;

		PersistentDataContainer pdc = tileState.getPersistentDataContainer();
		NamespacedKey ownerKey = new NamespacedKey(Cataklysm.getInstance(), "owner_uuid");
		NamespacedKey shopIdKey = new NamespacedKey(Cataklysm.getInstance(), "shop_id");

		String uuidString = pdc.get(ownerKey, PersistentDataType.STRING);
		String shopID = pdc.get(shopIdKey, PersistentDataType.STRING);
		if (uuidString == null || shopID == null) return null;
		
		UUID ownerUUID = UUID.fromString(uuidString);
		return new ShopContext(container, shopID, ownerUUID);
	}
	
	private boolean isValidShop(ShopContext context, Player player) {
		configManager.PlayerData data = configManager.getPlayerData(context.ownerUUID);
		FileConfiguration config = data.getConfig();

		return config.contains("shop." + context.shopID) || !player.getUniqueId().equals(context.ownerUUID);
	}
	
	public static Map<UUID, String> activeShopUUID = new HashMap<>();
	public static Map<UUID, String> activeShopID = new HashMap<>();
	
	public static void openInv(String uuidString, Player player, String shopID, Container container) {
		UUID ownerUUID = UUID.fromString(uuidString);


		configManager.PlayerData data = configManager.getPlayerData(ownerUUID);
		FileConfiguration Config = data.getConfig();

		Material costItem = Material.matchMaterial(Config.getString("shop." + shopID + ".costItem"));
		int costAmount = Config.getInt("shop." + shopID + ".costAmount", 1);

		if (costItem == null) {
			player.sendMessage("§cThis shop is misconfigured. Please contact an admin.");
			return;
		}

		activeShopUUID.put(player.getUniqueId(), ownerUUID.toString());
		activeShopID.put(player.getUniqueId(), shopID);
		
		ItemStack shop = Cataklysm.createCustomItem(
			 costItem,
			"§a§lShop",
			"§6Cost§7: §e" + costAmount + " " + costItem,
			"§7Sawp items in slot to buy"
		);
		shop.setAmount(costAmount);
		
		ItemStack shopOwner = Cataklysm.createCustomItem(
			 costItem,
			"§a§lShop",
			"§6Cost§7: §e" + costAmount + " " + costItem,
			"§7Click with item to set sold item and amount.",
			"§7Shift-Left click to delete Shop"
		);
		shopOwner.setAmount(costAmount);
		
		if (player.getUniqueId().equals(ownerUUID)) {
			
			container.getInventory().setItem(13, shopOwner);
			
		} else {
			
			container.getInventory().setItem(13, shop);
			
		}

	}
	
}
