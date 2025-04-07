package commands;


import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import configManager.configManager;
import main.Cataklysm;

public class SetHomeCommand implements CommandExecutor, Listener{
	
    @Override
	public boolean onCommand(CommandSender sender, Command command, String lable, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			
			if(player.hasPermission("cataklysm.home.set")) {
				
				configManager.PlayerData data = configManager.getPlayerData(player);
				FileConfiguration Config = data.getConfig();
				
				if (Config.contains("home")) {
				    Location oldhome = Location.deserialize(Config.getConfigurationSection("home").getValues(false));
					Config.set("oldhome.world", oldhome.getWorld().getName());
					Config.set("oldhome.x", oldhome.getX());
					Config.set("oldhome.y", oldhome.getY());
					Config.set("oldhome.z", oldhome.getZ());
					data.save();
				    Cataklysm.getInstance().getLogger().info("Set old home for " + player.getName());
				}

				Location location = player.getLocation();
				Config.set("home.world", location.getWorld().getName());
				Config.set("home.x", Math.round(location.getX() * 2.0) / 2.0);
				Config.set("home.y", Math.round(location.getY()));
				Config.set("home.z", Math.round(location.getZ() * 2.0) / 2.0);
				Config.set("home.yaw", Math.round(location.getYaw() / 45.0f) * 45.0f);
				data.save();
				player.sendMessage("§aYour Home has been sucessfully set.");
				
				} else
					player.sendMessage(Cataklysm.Permission + "§c[Permission: §6cataklysm.home.set§c]");
			
	} else
		sender.sendMessage(Cataklysm.ConsoleCommandFail);
		return false;
	}
	
	
	
} //Catoklysm was here 2025 uwu
