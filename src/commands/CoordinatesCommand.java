package commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import main.Cataklysm;

public class CoordinatesCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command command, String lable, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(player.hasPermission("cataklysm.coordinates")) {
				if(args.length == 0) {
					player.sendMessage("§aYou are at the following coordinates: §b" + player.getLocation().getBlockX() + " X " + player.getLocation().getBlockY() + " Y " + player.getLocation().getBlockZ() + " Z§a in §b" + player.getLocation().getWorld().getEnvironment() + "§a.");
					player.sendMessage("§aTo send your position to another player use: §6/coordinates [player] §a!");
				} else if(args.length == 1) {
					Player target = Bukkit.getPlayer(args[0]);
					if(target != null) {
						player.sendMessage("§aYou sent your coordinates to §e" + target.getName());
						target.sendMessage("§e" + player.getName() + " §ahas sent you their coordinates: §b" + player.getLocation().getBlockX() + " X " + player.getLocation().getBlockY() + " Y " + player.getLocation().getBlockZ() + " Z§a in §b" + player.getLocation().getWorld().getEnvironment() + "§a.");
						target.sendMessage("§aTo send your position to another player use: §6/coordinates [player] §a!");
					} else
						player.sendMessage("§6" + args[0] + " §cis not §aonline§c!");
				} else
					player.sendMessage("§aPlease use §6/coordinates [player]");
			} else
				player.sendMessage(Cataklysm.Permission + "§c[Permission: §6cataklysm.coordinates§c]");
		} else
			sender.sendMessage(Cataklysm.ConsoleCommandFail);
		return false;
	}
}
