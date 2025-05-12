package commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import main.Cataklysm;

public class ServerboostCommand implements CommandExecutor{
	
	@SuppressWarnings("unused")
	private Plugin plugin = Cataklysm.getPlugin(Cataklysm.class);
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {


		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(player.hasPermission("cataklysm.serverboost")) {
					
					Inventory inv = Bukkit.createInventory(null, 9, "§d§lServer Boosts");
					ItemStack potion = Cataklysm.createCustomItem(Material.POTION, "§d§lMystical Potion", "§6Random positive effect that lasts 10 minutes", "§6Cost§7: §e8 Diamonds", "§7(Could give an effect that's already active!)");
					ItemStack xp = Cataklysm.createCustomItem(Material.EXPERIENCE_BOTTLE, "§a§lExtreme Experience", "§6Doubles XP for 30 minutes", "§6Cost§7: §e16 Diamonds");
					ItemStack placeholder = Cataklysm.createCustomItem(Material.RED_STAINED_GLASS_PANE, "§cComming soon...");
					ItemStack glass = Cataklysm.createCustomItem(Material.LIGHT_GRAY_STAINED_GLASS_PANE, "§7");
					for (int i = 0; i < inv.getSize(); i++) {
					    inv.setItem(i, glass);
					}
					inv.setItem(1, potion);
					inv.setItem(4, xp);
					inv.setItem(7, placeholder);
					
					player.openInventory(inv);
					
					
				
			}else 
				player.sendMessage(Cataklysm.Permission + "§c[Permission: §6cataklysm.serverboost§c]");
		} else 
			sender.sendMessage(Cataklysm.ConsoleCommandFail);
		
		
		return false;
	}
}
