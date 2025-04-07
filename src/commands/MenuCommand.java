package commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import configManager.configManager;
import main.Cataklysm;

public class MenuCommand implements CommandExecutor{
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {


		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(player.hasPermission("cataklysm.menu")) {
					
				
			    int ticks = player.getStatistic(Statistic.PLAY_ONE_MINUTE);
				int seconds = ticks / 20;
				int minutes = seconds / 60;
				int hours = minutes / 60;
				int remainingMinutes = minutes % 60;
			    int days = hours / 24;  // 24 hours = 1 day
			    int remainingHours = hours % 24;  // Remaining hours after days
			    
				configManager.PlayerData data = configManager.getPlayerData(player);
				FileConfiguration config = data.getConfig();
				
				String firstJoinTime = config.getString("firstjoin");
			    if (firstJoinTime == null) {
			        firstJoinTime = "Unknown"; // If the first join time is not found, set it as "Unknown"
			    }
				
				ItemStack skull = Cataklysm.createCustomItem(Material.PLAYER_HEAD, "§e§l" + player.getName(), "§6Playtime§7: §e" + days + " §6days, §e" + remainingHours + " §6hours and §e" + remainingMinutes + " §6minutes", "§6Joined§7: §e" + firstJoinTime + " UTC+0"); // Create a new ItemStack of the Player Head type.
				SkullMeta skullMeta = (SkullMeta) skull.getItemMeta(); // Get the created item's ItemMeta and cast it to SkullMeta so we can access the skull properties
				skullMeta.setOwningPlayer(player); // Set the skull's owner so it will adapt the skin of the provided username (case sensitive).
				skull.setItemMeta(skullMeta); // Apply the modified meta to the initial created item
				
				
					Inventory inv = Bukkit.createInventory(null, 27, "§a§lMenu");
					ItemStack borderMenu = Cataklysm.createCustomItem(Material.LIGHT_BLUE_STAINED_GLASS, "§b§lWorld Border Increase", "§eOpens the World Border Increase Menu", "§7You can purchase World Border Increases here!");
					ItemStack spawn = Cataklysm.createCustomItem(Material.GRASS_BLOCK, "§a§lSpawn", "§eTeleports you to spawn");
					ItemStack home = Cataklysm.createCustomItem(Material.CHEST, "§6§lHome", "§eTeleports you to your Home");
					ItemStack boostMenu = Cataklysm.createCustomItem(Material.EMERALD, "§5§lServer Boost", "§eOpens the Server Boost Menu", "§7You can purchase global boosts here!");
					ItemStack glass = Cataklysm.createCustomItem(Material.LIGHT_GRAY_STAINED_GLASS_PANE, "§7");
					for (int i = 0; i < inv.getSize(); i++) {
					    inv.setItem(i, glass);
					}
					inv.setItem(4, skull);
					inv.setItem(10, borderMenu);
					inv.setItem(12, spawn);
					inv.setItem(14, home);
					inv.setItem(16, boostMenu);
					
					player.openInventory(inv);
					
					
				
			}else 
				player.sendMessage(Cataklysm.Permission + "§c[Permission: §6cataklysm.menu§c]");
		} else 
			sender.sendMessage(Cataklysm.ConsoleCommandFail);
		
		
		return false;
	}
}