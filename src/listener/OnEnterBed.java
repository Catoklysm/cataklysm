package listener;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;

import main.Cataklysm;

public class OnEnterBed implements Listener{
	

	
	@EventHandler
	 public void onPlayerSleep(PlayerBedEnterEvent event) {
        Player player = event.getPlayer();
        World world = player.getLocation().getWorld();
        
        if (event.getBed() == null || event.getBedEnterResult() != PlayerBedEnterEvent.BedEnterResult.OK) {
            return;
        }
        
        if(!Cataklysm.NightSkip.contains(true) && world.getEnvironment() == Environment.NORMAL) {
		Cataklysm.NightSkip(world);
        Bukkit.broadcastMessage("§a" + player.getName() + "§6 went to bed! Skipping night");
        }
						}

	@EventHandler
	public void onPlayerWakeUp(PlayerBedLeaveEvent event) {
        Player player = event.getPlayer();
        World world = player.getLocation().getWorld();
    
        if(Cataklysm.NightSkip.contains(true)) {
		Cataklysm.NightSkip.remove(true);
		
		if(world.getTime() > 1000 && world.getTime() < 23460) {
        Bukkit.broadcastMessage("§a" + player.getName() + "§6 got out of bed! No longer skipping night!");
		}
      }
	}

}
