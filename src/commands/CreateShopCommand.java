package commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import main.Cataklysm;

public class CreateShopCommand implements CommandExecutor, Listener{

	public static List<Player> createshop = new ArrayList<>();
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String lable, String[] args) {
		
		if(sender instanceof Player) {
			Player player = (Player) sender;
		
			if(createshop.contains(player)) {
				createshop.remove(player);
				player.sendMessage("�aShop creation canceled!");
				return false;
			}
			
			player.sendMessage("�aYou can now place a chest, barrel or shulker to create a shop!");
			createshop.add(player);
			
	        new BukkitRunnable() {
	            public void run() {
					player.sendMessage("�aShop creation has ended!");
					createshop.remove(player);
	            }
	        }.runTaskLater(Cataklysm.getInstance(), 200);

			

		} else
		sender.sendMessage(Cataklysm.ConsoleCommandFail);
		return false;
	}

}
