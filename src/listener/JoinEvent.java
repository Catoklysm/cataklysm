package listener;

import org.bukkit.Color;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.bukkit.Bukkit;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.plugin.Plugin;

import commands.VanishCommand;
import main.Cataklysm;

public class JoinEvent implements Listener {
	
	private Plugin plugin = Cataklysm.getPlugin(Cataklysm.class);	
	
	public String getTime(){
	    Calendar cal = Calendar.getInstance();
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    return sdf.format(cal.getTime());
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void OnJoin(PlayerJoinEvent e) {
		
		//VANISH 
		
		Player p = e.getPlayer();
			if(p.hasPlayedBefore() == true){
			
				e.setJoinMessage("§a[+] §7" + p.getName());
		}
		for(Player vanish : VanishCommand.vanished) {
			
			if(p.hasPermission("cataklysm.vanish.see")) {
			} else
				p.hidePlayer(vanish);
		}
		
		
		if(p.hasPlayedBefore() == false){
		    plugin.getConfig().set(p.getUniqueId().toString() + ".firstjoin", getTime());
		    plugin.saveConfig();
			e.setJoinMessage(null);
			Bukkit.getWorld("world").setClearWeatherDuration(600 * 20);
			Bukkit.getWorld("world").setThunderDuration(0);
			Bukkit.getWorld("world").setWeatherDuration(0);
			Bukkit.broadcastMessage("§6Welcome " + p.getName() + "!");
			p.sendMessage("§6");
			p.sendMessage("§aOur Server has a limited Worldborder that we extend together!");
			p.sendMessage("§eUse §6/menu §eto access helpful commands!");
			p.sendMessage("§6Spawn reserves a range of 320 Blocks around 0, 0!");
			p.sendMessage("§6Use the pressure plates to leave Spawn!");
			p.sendMessage("§6This server supports the §eSimple Voice Chat §6Mod!");
			p.sendMessage("§aStaff is always happy to help! Don't be shy to reach out!");
			p.sendMessage("§6");
				
			Location loc = p.getLocation();
			 
			Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			    @Override
		        public void run() {
		            final Firework f = p.getWorld().spawn(loc, Firework.class);
		            FireworkMeta fm = f.getFireworkMeta();

		            fm.addEffect(FireworkEffect.builder()
		                    .flicker(true)
		                    .trail(true)
		                    .with(FireworkEffect.Type.BALL)
		                    .withColor(Color.fromRGB(255, 132, 240), Color.fromRGB(153, 231, 255), Color.WHITE)
		                    .build());

		            fm.setPower(1);
		            f.setFireworkMeta(fm);
			    }
			}, 5L);
			
		}
	}

}