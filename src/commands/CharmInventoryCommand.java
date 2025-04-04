package commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import main.Cataklysm;

public class CharmInventoryCommand implements CommandExecutor{
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {


		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(player.hasPermission("cataklysm.menu")) {

				
					Inventory inv = Bukkit.createInventory(null, 27, "§e§lCharm Inventory");

					player.openInventory(inv);
				
			}else 
				player.sendMessage(Cataklysm.Permission + "§c[Permission: §6cataklysm.menu§c]");
		} else 
			sender.sendMessage(Cataklysm.ConsoleCommandFail);
		
		
		return false;
	}
	
}
