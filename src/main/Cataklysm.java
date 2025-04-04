package main;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import commands.BorderIncreaseCommand;
import commands.CoordinatesCommand;
import commands.FlyCommand;
import commands.GetCoordinatesCommand;
import commands.HomeCommand;
import commands.MenuCommand;
import commands.ServerboostCommand;
import commands.SetHomeCommand;
import commands.SpawnCommand;
import commands.TestCommand;
import commands.VanishCommand;
import listener.JoinEvent;
import listener.MobSpawnEvent;
import listener.OnBlockMultiPlaceEvent;
import listener.OnEnterBed;
import listener.OnExpChangeEvent;
import listener.QuitEvent;
import listener.ToggleFlightEvent;
import listener.OnInventoryClick;

public class Cataklysm extends JavaPlugin{

	public static String Permission = "§cYou do not have enough permission to run this command! ";
	public static String ConsoleCommandFail = "You are unable to run this command! ";
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	private static Cataklysm instance;
	public void onEnable() {

		//Event Manager

		instance = this;

		loadConfig();

		Bukkit.getPluginManager().registerEvents(new JoinEvent(), this);
		Bukkit.getPluginManager().registerEvents(new QuitEvent(), this);
		Bukkit.getPluginManager().registerEvents(new OnInventoryClick(), this);
		Bukkit.getPluginManager().registerEvents(new OnExpChangeEvent(), this);
		Bukkit.getPluginManager().registerEvents(new ToggleFlightEvent(), this);
		Bukkit.getPluginManager().registerEvents(new OnBlockMultiPlaceEvent(), this);
		Bukkit.getPluginManager().registerEvents(new OnEnterBed(), this);
		Bukkit.getPluginManager().registerEvents(new MobSpawnEvent(), this);


		//Plugin Enable Console Output
		System.out.println(ANSI_YELLOW + "Cataklysm: Online"+ ANSI_RESET);
		System.out.println(ANSI_YELLOW + "Plugin made by Catoklysm!"+ ANSI_RESET);

		//-------------Commands--------------------

		getCommand("vanish").setExecutor(new VanishCommand());
		getCommand("borderincrease").setExecutor(new BorderIncreaseCommand());
		getCommand("spawn").setExecutor(new SpawnCommand());
		getCommand("menu").setExecutor(new MenuCommand());
		getCommand("home").setExecutor(new HomeCommand());
		getCommand("sethome").setExecutor(new SetHomeCommand());
		getCommand("fly").setExecutor(new FlyCommand());
		getCommand("getcoordinates").setExecutor(new GetCoordinatesCommand());
		getCommand("coordinates").setExecutor(new CoordinatesCommand());
		getCommand("serverboost").setExecutor(new ServerboostCommand());
		getCommand("test").setExecutor(new TestCommand());

		//-------------Commands--------------------
 
		//------------Aray-Lists-------------------



		//------------Aray-Lists-------------------


	}

	public void onDisable() {

		instance.getConfig().set("XPBoost", false);

		saveConfig();

		//Plugin Disable Console Output
		System.out.println(ANSI_YELLOW + "Cataklysm: Online"+ ANSI_RESET);
		System.out.println(ANSI_YELLOW + "Plugin made by Catoklysm!"+ ANSI_RESET);
	}

	public static Plugin getInstance() {

		return instance;
	}

	//Config
	public void loadConfig() {
		getConfig().options().copyDefaults(true);
		saveConfig();
	}
	
	//------------Used in OnBlockMultiPlaceEvent & OnEnterBed-------------------
	
    public final static ArrayList<Boolean> NightSkip = new ArrayList<>();
public static void NightSkip(World world) {
	
    NightSkip.add(true);
	
    BukkitRunnable task = (BukkitRunnable) new BukkitRunnable() {
        @Override
        public void run() {
        	
			if(Environment.valueOf("NORMAL") != null && world.getTime() > 12000 && NightSkip.contains(true)) {
				
				long currentTime = world.getTime();
				world.setTime(currentTime + 100);
				
			} else
				this.cancel();
			if(world.getTime() < 12000) {
				NightSkip.remove(true);
			}

        }
        
        
    };
    
    task.runTaskTimer(instance, 0, 1); // Run every tick
}

//------------Used in Inventory GUI Creation-------------------

public static ItemStack createCustomItem(Material material, String name, String... lore) {
    ItemStack item = new ItemStack(material);
    ItemMeta meta = item.getItemMeta();
    
    if (meta != null) {
        meta.setDisplayName(name); // Set the custom name
        meta.setLore(Arrays.asList(lore)); // Set the lore
        item.setItemMeta(meta);
    }
    
    return item;
}


}


