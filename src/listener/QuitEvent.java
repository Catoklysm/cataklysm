package listener;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import commands.VanishCommand;
import configManager.configManager;

public class QuitEvent implements Listener {
	
	public static List<Player> vanished = new ArrayList<>();
	@EventHandler
	public void OnQuit(PlayerQuitEvent e) {
		
		Player p = e.getPlayer();
		configManager.getPlayerData(p);
		configManager.getPlayerData(p).save();
		
			if(VanishCommand.vanished.contains(p)){
			VanishCommand.vanished.remove(p);
			e.setQuitMessage(null);
			}else e.setQuitMessage("§c[-] §7" + p.getName());
	}

}