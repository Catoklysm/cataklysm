package commands;

import java.util.ArrayList;
import java.util.List;


import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import main.Cataklysm;

public class VanishCommand implements CommandExecutor, Listener{

	public static List<Player> vanished = new ArrayList<>();
	private Plugin plugin = Cataklysm.getPlugin(Cataklysm.class);	
	
	public boolean onCommand(CommandSender sender, Command command, String lable, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			
			if(args.length == 0) {
			if(player.hasPermission("cataklysm.vanish")) {
					if(vanished.contains(player)) {
				//stellt Vanish aus
					vanished.remove(player);
					Bukkit.broadcastMessage("§a[+] §7" + player.getName());
					//überprüft GameMode um Fly zu entziehen
					if(player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE) {
						player.setAllowFlight(false);
					}
					//Stellt verschiedene Sachen ein und Player Feedback
					player.setCanPickupItems(true);
					player.setCollidable(true);
					player.sendMessage("§aYou are no longer vanished!");
					//Macht den Spieler unsichtbar
					for
					(Player all : Bukkit.getServer().getOnlinePlayers())
                    { all.showPlayer(plugin, player); }
					//stellt Vanish an
					} else { if(!vanished.contains(player)) {
					
					vanished.add(player);
					Bukkit.broadcastMessage("§c[-] §7" + player.getName());
					//Stellt verschiedene Sachen ein und Player Feedback
					player.setAllowFlight(true);
					player.setCanPickupItems(false);
					player.setCollidable(false);
					player.sendMessage("§aYou are now vanished!");
					for
					(Player all : Bukkit.getServer().getOnlinePlayers())
						if(all.hasPermission("cataklysm.vanish.see")){}
						else
                    { all.hidePlayer(plugin, player); }
				} //Restlicher Player Feedback
			}
				} else
					player.sendMessage(Cataklysm.Permission + "§c[Permission: §6cataklysm.vanish§c]");
			
			
			//---------Vanish andren Spielern geben----------------
			
			} else if(args.length == 1) {
			if(player.hasPermission("cataklysm.vanish.give")) {
				
				Player target = Bukkit.getPlayer(args[0]);
				if(target != null) {
					
					if(vanished.contains(target)) {
				//stellt Vanish aus
					vanished.remove(target);
					//überprüft GameMode um Fly zu entziehen
					if(target.getGameMode() == GameMode.SURVIVAL || target.getGameMode() == GameMode.ADVENTURE) {
						target.setAllowFlight(false);
						Bukkit.broadcastMessage("§a[+] §7" + target.getName());
					}
					//Stellt verschiedene Sachen ein und Player Feedback
					target.setCanPickupItems(true);
					target.setCollidable(true);
					target.sendMessage("§aYou are no longer vanished!");
					player.sendMessage("§aYou unvanished §6" + target.getName() + "§a!");
					//Macht den Spieler unsichtbar
					for
					(Player all : Bukkit.getServer().getOnlinePlayers())
                    { all.showPlayer(plugin, target); }
					//stellt Vanish an
					} else if(vanished.contains(target) == false) {
					
					vanished.add(target);
					Bukkit.broadcastMessage("§c[-] §7" + target.getName());
					//Stellt verschiedene Sachen ein und Player Feedback
					target.setAllowFlight(true);
					target.setCanPickupItems(false);
					target.setCollidable(false);
					target.sendMessage("§aYou are now vanished!");
					player.sendMessage("§aYou put §6" + target.getName() + " §ain vanish!");
					//macht den Spieler wieder sichtbar
					for
					(Player all : Bukkit.getServer().getOnlinePlayers())
						if(all.hasPermission("cataklysm.vanish.see")){}
						else
                    { all.hidePlayer(plugin, target); }
					
					
				} //Restlicher Player Feedback
			} else
				player.sendMessage("§6" + args[0] + " §cis not §aonline§c!");
			} else
				player.sendMessage(Cataklysm.Permission + "§c[Permission: §6cataklysm.vanish.give§c]");
		} else
			player.sendMessage("§cPlease use §6/vanish [Spieler]§c!"); 
	} else
		sender.sendMessage(Cataklysm.ConsoleCommandFail);
		return false;
	}
} //~Exakter war hier 2020 uwu
