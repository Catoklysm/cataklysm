package commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import configManager.configManager;
import main.Cataklysm;

public class HomeCommand implements CommandExecutor, Listener{
	
	public boolean onCommand(CommandSender sender, Command command, String lable, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			
			if(player.hasPermission("cataklysm.home")) {
				
				configManager.PlayerData data = configManager.getPlayerData(player);
				FileConfiguration config = data.getConfig();

				if (config.contains("home")) {
				    World world = Bukkit.getWorld(config.getString("home.world"));
				    double x = config.getDouble("home.x");
				    double y = config.getDouble("home.y");
				    double z = config.getDouble("home.z");
				    float yaw = (float) config.getDouble("home.yaw");

				    if (world == null) {
				        player.sendMessage("§cError: The world §6'" + config.getString("home.world") + "'§c is not loaded correctly.");
				        return false;
				    }
				  //  Location home = new Location(world, Math.round(x) + 0.5, y , Math.round(z) + 0.5, Math.round(yaw / 45.0f) * 45.0f, 0);
				    Location home = new Location(world, x, y , z, yaw, 0);
				    player.teleport(home);
			        player.sendMessage("§aYou have been teleported to your home");
				} else
					player.sendMessage("§cYou have no home set! Set one using §6/sethome§c!");
				
				} else
					player.sendMessage(Cataklysm.Permission + "§c[Permission: §6cataklysm.home§c]");
			
	} else
		sender.sendMessage(Cataklysm.ConsoleCommandFail);
		return false;
	}
} //Catoklysm was here 2025 uwu
