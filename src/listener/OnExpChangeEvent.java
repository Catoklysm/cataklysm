package listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.plugin.Plugin;

import main.Cataklysm;

public class OnExpChangeEvent implements Listener {

	private Plugin plugin = Cataklysm.getPlugin(Cataklysm.class);
	
	@EventHandler
	public void onExpChange(PlayerExpChangeEvent e) {

			if(plugin.getConfig().getBoolean("XPBoost")) {
		Player p = e.getPlayer();
		int XP = e.getAmount();
		p.giveExp(XP);
			}
	}
	
	
}
