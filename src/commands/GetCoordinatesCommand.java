package commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import main.Cataklysm;

public class GetCoordinatesCommand implements CommandExecutor{
	@Override
	public boolean onCommand(CommandSender sender, Command command, String lable, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(player.hasPermission("cataklysm.getcoordinates")) {
				if(args.length == 0) {
					player.sendMessage("§aYou are at the following coordinates: §b" + player.getLocation().getBlockX() + " X " + player.getLocation().getBlockY() + " Y " + player.getLocation().getBlockZ() + " Z§a in §b" + player.getLocation().getWorld().getEnvironment() + "§a.");
					player.sendMessage("§aTo find the position of a player use §6/getcoordinates [player] §a!");
				} else if(args.length == 1) {
					Player target = Bukkit.getPlayer(args[0]);
					if(target != null) {
						player.sendMessage("§e" + target.getName() + " §ais located at: §b" + target.getLocation().getBlockX() + " X " + target.getLocation().getBlockY() + " Y " + target.getLocation().getBlockZ() + " Z§a in §b" + target.getLocation().getWorld().getEnvironment() + "§a.");
					} else
						player.sendMessage("§6" + args[0] + " §cis not §aonline§c!");
				} else
					player.sendMessage("§aPlease use §6/getcoordinates [player]");
			} else
				player.sendMessage(Cataklysm.Permission + "§c[Permission: §6cataklysm.getcoordinates§c]");
		} else
			sender.sendMessage(Cataklysm.ConsoleCommandFail);
		return false;
	}
}
