package commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import main.Cataklysm;

public class SetHomeCommand implements CommandExecutor, Listener{
	
	private Plugin plugin = Cataklysm.getPlugin(Cataklysm.class);
	
	public boolean onCommand(CommandSender sender, Command command, String lable, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			
			if(player.hasPermission("cataklysm.home.set")) {
				
				if (plugin.getConfig().contains(player.getUniqueId() + ".home")) {
				    Location oldhome = Location.deserialize(plugin.getConfig().getConfigurationSection(player.getUniqueId() + ".home").getValues(false));
					plugin.getConfig().set(player.getUniqueId() + ".oldhome.world", oldhome.getWorld().getName());
					plugin.getConfig().set(player.getUniqueId() + ".oldhome.x", oldhome.getX());
					plugin.getConfig().set(player.getUniqueId() + ".oldhome.y", oldhome.getY());
					plugin.getConfig().set(player.getUniqueId() + ".oldhome.z", oldhome.getZ());
					plugin.saveConfig();
				}

				Location location = player.getLocation();
				plugin.getConfig().set(player.getUniqueId() + ".home.world", location.getWorld().getName());
				plugin.getConfig().set(player.getUniqueId() + ".home.x", Math.round(location.getX() * 2.0) / 2.0);
				plugin.getConfig().set(player.getUniqueId() + ".home.y", Math.round(location.getY()));
				plugin.getConfig().set(player.getUniqueId() + ".home.z", Math.round(location.getZ() * 2.0) / 2.0);
				plugin.getConfig().set(player.getUniqueId() + ".home.yaw", Math.round(location.getYaw() / 45.0f) * 45.0f);
				plugin.saveConfig();
				player.sendMessage("§aYour Home has been sucessfully set.");
				
				} else
					player.sendMessage(Cataklysm.Permission + "§c[Permission: §6cataklysm.home.set§c]");
			
	} else
		sender.sendMessage(Cataklysm.ConsoleCommandFail);
		return false;
	}
	
	
	
} //Catoklysm was here 2025 uwu
