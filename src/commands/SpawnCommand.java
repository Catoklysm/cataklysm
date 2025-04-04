package commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import main.Cataklysm;

public class SpawnCommand implements CommandExecutor, Listener{

	
	public boolean onCommand(CommandSender sender, Command command, String lable, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			
			if(args.length == 0) {
			if(player.hasPermission("cataklysm.spawn")) {

		          player.teleport(Bukkit.getWorld("world").getSpawnLocation());
		          player.sendMessage("§aYou have been teleported to spawn");
				
				} else
					player.sendMessage(Cataklysm.Permission + "§c[Permission: §6cataklysm.spawn§c]");
			
			} else if(args.length == 1) {
			if(player.hasPermission("cataklysm.spawn.send")) {
				Player target = Bukkit.getPlayer(args[0]);
				if(target != null) {
					
			          target.teleport(Bukkit.getWorld("world").getSpawnLocation());
			          target.sendMessage("§aYou have been teleported to spawn");
			          player.sendMessage("§aYou have sent §6" + target.getName() + "§a to Spawn!");
					
				} else
					player.sendMessage("§6" + args[0] + " §cis not §aonline§c!");	
				
			} else
				player.sendMessage(Cataklysm.Permission + "§c[Permission: §6cataklysm.spawn.send§c]");
		} else
			player.sendMessage("§cPlease use §6/Spawn§c!"); 
	} else
		sender.sendMessage(Cataklysm.ConsoleCommandFail);
		return false;
	}
} //Catoklysm was here 2025 uwu
