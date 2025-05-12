package commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import main.Cataklysm;
import listener.ToggleFlightEvent;

public class FlyCommand implements CommandExecutor {
	
    private final Plugin plugin = Cataklysm.getPlugin(Cataklysm.class);
	public static List<Player> fly = new ArrayList<>();
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String lable, String[] args) 
		{
		if(sender instanceof Player)
		{
			Player player = (Player) sender;
			
			
				if(player.hasPermission("cataklysm.fly")){
						if(args.length == 0) {
						if(player.getAllowFlight()) 
						{
						player.setAllowFlight(false);
						player.sendMessage("§aYou have disabled flight!");
						} 
						else if(player.hasPermission("cataklysm.fly")) 
							{
							if(args.length == 0) 
							{
							player.setAllowFlight(true);
							player.sendMessage("§aYou have enabled flight!");
							}
					} 
					else
					player.sendMessage(Cataklysm.Permission + "§c[Permission: §6cataklysm.fly§c]");
					} 
					
						else if(args.length == 1) 
						{
						if(player.hasPermission("cataklysm.fly.give")) 
						{
						Player target = Bukkit.getPlayer(args[0]);
						if(target != null)
							{
							if(target.getAllowFlight()) 
							{
							target.setAllowFlight(false);
							fly.remove(target);
							target.sendMessage("§aYour flight has been disabled!");
							player.sendMessage("§aYou disabled §e" + target.getName() + "§a's flight!");
							} 
							else if(target.getAllowFlight() == false) 
							{
							target.setAllowFlight(true);
							fly.add(target);
							target.sendMessage("§aYour flight has been enabled!");
							player.sendMessage("§aYou enabled §e" + target.getName() + "§a's flight!");
							}
						} 
						else
						player.sendMessage("§6" + args[0] + " §cis not §aonline§c!");
						}
						else 
						player.sendMessage(Cataklysm.Permission + "§c[Permission: §6cataklysm.fly.give§c]");
						}
						
			} else if(ToggleFlightEvent.hasFlightCrystal(player) && player.hasPermission("cataklysm.fly.crystal")){
				
					if(player.getAllowFlight()) 
					{
					player.setAllowFlight(false);
					player.sendMessage("§aYou have disabled flight!");
					} else {
						player.setAllowFlight(true);
						player.sendMessage("§aYou have enabled flight!");
					}
					
		            BukkitRunnable flightStop = (BukkitRunnable) new BukkitRunnable() {
		                @Override
		                public void run() {
		                    if (player.isFlying() == false ) { // Stop task if player stops flying
		                    	if (player.getAllowFlight()) { 
			                        player.setAllowFlight(false);
			                        player.sendMessage("§cYour Flight has been deactivated due to inactivity!");
		                    	}
		                        this.cancel();
		                        return;
		                    }
		                }
		                
		                
		            }; // Run every 8 seconds (160 ticks)
		            
		            flightStop.runTaskTimer(plugin, 120, 160); // Run every 8 seconds (160 ticks)
					
			}
			else if(player.hasPermission("cataklysm.fly.crystal"))player.sendMessage("§cYou do not have a Flight Crystal or lack permission! " + "§c[Permission: §6cataklysm.fly§c]");
			
			}
			else 	
			sender.sendMessage(Cataklysm.ConsoleCommandFail);
		
		return false;
	}
}
