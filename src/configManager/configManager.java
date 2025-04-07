package configManager;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import main.Cataklysm;

import java.io.File;
import java.io.IOException;

public class configManager {

    public static class PlayerData {
        private final File file;
        private final FileConfiguration config;

        public PlayerData(File file, FileConfiguration config) {
            this.file = file;
            this.config = config;
        }

        public File getFile() {
            return file;
        }

        public FileConfiguration getConfig() {
            return config;
        }

        public void save() {
            try {
                config.save(file);
                Cataklysm.getInstance().getLogger().info("Saved data for " + file.getName());
            } catch (IOException e) {
                Cataklysm.getInstance().getLogger().severe("Failed to save data for " + file.getName());
                e.printStackTrace();
            }
        }
    }

    public static PlayerData getPlayerData(Player player) {
        File dataFolder = new File(Cataklysm.getInstance().getDataFolder(), "playerData");
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }

        File playerFile = new File(dataFolder, player.getUniqueId() + ".yml");
        if (!playerFile.exists()) {
            try {
                playerFile.createNewFile();
            } catch (IOException e) {
                Cataklysm.getInstance().getLogger().severe("Failed to create data file for " + player.getName());
                e.printStackTrace();
            }
        }

        FileConfiguration config = YamlConfiguration.loadConfiguration(playerFile);
        return new PlayerData(playerFile, config);
    }
} 