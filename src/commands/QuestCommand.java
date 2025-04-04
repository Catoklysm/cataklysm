package commands;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import main.Cataklysm;

public class QuestCommand implements CommandExecutor{
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {


		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(player.hasPermission("cataklysm.quest")) {

				Random rand = new Random();
                int randRequired = rand.nextInt(15);
                int randReward = rand.nextInt(5);
                int difficulty = 1;
                
                int amountDifficulty = 1;
				Material requiredEasy = null;
				Material requiredMedium = null;
				Material requiredHard = null;
				int requiredAmount = randRequired * amountDifficulty;
				Material rewardEasy = null;
				Material rewardMedium = null;
				Material rewardHard = null;
				int rewardAmount = randReward * difficulty;
				
				
				Inventory inv = Bukkit.createInventory(null, 9, "§d§lServer Boosts");
				ItemStack easy = Cataklysm.createCustomItem(requiredEasy, "§aEasy", "§6Required§7: §e" + requiredEasy + " §fx §e" + requiredAmount, "§6Reward:§7 §e" + rewardEasy + " §fx " + rewardAmount);
				ItemStack medium = Cataklysm.createCustomItem(requiredMedium, "§eMedium", "§6Required§7: §e" + requiredMedium + " §fx §e" + requiredAmount, "§6Reward:§7 §e" + rewardMedium + " §fx " + rewardAmount);
				ItemStack hard = Cataklysm.createCustomItem(requiredHard, "§cHard", "§6Required§7: §e" + requiredHard + " §fx §e" + requiredAmount, "§6Reward:§7 §e" + rewardHard + " §fx " + rewardAmount);
				ItemStack glass = Cataklysm.createCustomItem(Material.LIGHT_GRAY_STAINED_GLASS_PANE, "§7");
				for (int i = 0; i < inv.getSize(); i++) {
				    inv.setItem(i, glass);
				}
				inv.setItem(1, easy);
				inv.setItem(4, medium);
				inv.setItem(7, hard);
				
				player.openInventory(inv);
				
			}else 
				player.sendMessage(Cataklysm.Permission + "§c[Permission: §6cataklysm.quest§c]");
		} else 
			sender.sendMessage(Cataklysm.ConsoleCommandFail);
		
		
		return false;
	}
	
}
