package listener;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class MobSpawnEvent implements Listener {

	@EventHandler
	public void onMobSpawn(CreatureSpawnEvent e){
		
		World world = e.getLocation().getWorld();
		Location loc = e.getLocation();
		Material spawnBlock = loc.clone().subtract(0, 1, 0).getBlock().getType();
		
		if(world.getEnvironment() == Environment.THE_END && e.getEntityType() == EntityType.ENDERMAN && spawnBlock == Material.END_STONE) {
			
			
			
            Random rand = new Random();
            int random = rand.nextInt(500);
			if(random == 5) {
				e.setCancelled(true);
                int phantomCount = rand.nextInt(2) + 2;
                
                for (int i = 0; i < phantomCount; i++) {
                    world.spawnEntity(loc, EntityType.PHANTOM);
                }
			}
			if(random == 100 || random == 75 || random == 50 || random == 25) {
				e.setCancelled(true);	
                    world.spawnEntity(loc, EntityType.ENDERMITE);

			}
		}
		
	}
	
}
