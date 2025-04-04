package listener;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockMultiPlaceEvent;
import org.bukkit.inventory.ItemStack;

import main.Cataklysm;

public class OnBlockMultiPlaceEvent implements Listener {


    @EventHandler
    public void onBlockMultiPlace(BlockMultiPlaceEvent e) {
        Player player = e.getPlayer();
        ItemStack itemMain = player.getInventory().getItemInMainHand();
        ItemStack itemOff = player.getInventory().getItemInOffHand();

        if (isSleepingBag(itemMain) || isSleepingBag(itemOff)) {
            e.setCancelled(true);
            
            ArrayList<Boolean> NightSkip = Cataklysm.NightSkip;
            
            World world = e.getPlayer().getLocation().getWorld();
            if (world.getEnvironment() == Environment.NORMAL && world.getTime() > 12000 && !NightSkip.contains(true)) {
                Bukkit.broadcastMessage("§a" + player.getName() + "§6 slept in a sleeping bag! Skipping Night");
				world.getEnvironment();
                
            // Create a new repeating task
				Cataklysm.NightSkip(world);
			
            } else if(world.getEnvironment() != Environment.NORMAL) {
                	player.sendMessage("§cYou can only sleep in the Overworld!");	
            	} else
            	if (world.getTime() < 12000) { 
                  	player.sendMessage("§cYou can only sleep at night!");	
            	} else 
                	if (NightSkip.contains(true)) {
                	player.sendMessage("§cThe night is already being skipped!");	
                }
            
		
        }
    }
    
    private boolean isSleepingBag(ItemStack item) {
        return item != null && item.hasItemMeta() && item.getItemMeta().hasDisplayName()
                && item.getItemMeta().getDisplayName().contains("Sleeping Bag")
                && isBed(item);
    }
    
    private boolean isBed(ItemStack item) {
        if (item == null) return false;

        return item.getType().name().endsWith("_BED");
    }
    
}
