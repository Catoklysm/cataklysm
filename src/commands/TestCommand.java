package commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import main.Cataklysm;

public class TestCommand implements CommandExecutor{
	@Override
	public boolean onCommand(CommandSender sender, Command command, String lable, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(player.hasPermission("cataklysm.test")) {
				
				player.sendMessage("The time is: " + player.getLocation().getWorld().getTime());

			} else
				player.sendMessage(Cataklysm.Permission + "§c[Permission: §6cataklysm.test§c]");
		} else
			sender.sendMessage(Cataklysm.ConsoleCommandFail);
		return false;
	}
}
