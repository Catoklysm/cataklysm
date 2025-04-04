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

public class BorderIncreaseCommand implements CommandExecutor{
	
	@SuppressWarnings("unused")
	private Plugin plugin = Cataklysm.getPlugin(Cataklysm.class);
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {


		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(player.hasPermission("cataklysm.borderincrease")) {
					
					Inventory inv = Bukkit.createInventory(null, 9, "§b§lWorld Border Increase");
					ItemStack diamond = Cataklysm.createCustomItem(Material.DIAMOND, "§b§lMagical Diamond", "§6Worldborder Increase§7: §b§l1 Chunk", "§6Cost§7: §e32 Diamonds");
					ItemStack netherite = Cataklysm.createCustomItem(Material.NETHERITE_INGOT, "§7§lAncient Netherite Ingot", "§6Worldborder Increase§7: §b§l5 Chunks", "§6Cost§7: §e1 Netherite Ingot");
					ItemStack netherstar = Cataklysm.createCustomItem(Material.NETHER_STAR, "§b§ka§d§l Star of the Nether §b§ka", "§6Worldborder Increase§7: §b§l25 Chunks", "§6Cost§7: §e1 Nether Star");
					ItemStack glass = Cataklysm.createCustomItem(Material.LIGHT_GRAY_STAINED_GLASS_PANE, "§7");
					for (int i = 0; i < inv.getSize(); i++) {
					    inv.setItem(i, glass);
					}
					inv.setItem(1, diamond);
					inv.setItem(4, netherite);
					inv.setItem(7, netherstar);
					
					player.openInventory(inv);
					
					
				
			}else 
				player.sendMessage(Cataklysm.Permission + "§c[Permission: §6cataklysm.borderincrease§c]");
		} else 
			sender.sendMessage(Cataklysm.ConsoleCommandFail);
		
		
		return false;
	}
}
