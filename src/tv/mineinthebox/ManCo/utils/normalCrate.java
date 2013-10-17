package tv.mineinthebox.ManCo.utils;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import tv.mineinthebox.ManCo.manCo;

public class normalCrate {
	public static String getCrateFoundMessage() {
		try {
			File f = new File(manCo.getPlugin().getDataFolder() + File.separator + "items.yml");
			if(f.exists()) {
				FileConfiguration con = YamlConfiguration.loadConfiguration(f);
				return ChatColor.translateAlternateColorCodes('&', con.getString("CrateFoundMessage"));
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static boolean isUnCrateMessageDisabled() {
		try {
			File f = new File(manCo.getPlugin().getDataFolder() + File.separator + "items.yml");
			if(f.exists()) {
				FileConfiguration con = YamlConfiguration.loadConfiguration(f);
				return con.getBoolean("disableUncrateMessage");
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static int getCrateSpawnHeight(Player p) {
		if(p.getLocation().getY() < 100) {
			return 120;
		} else {
			return 256;
		}
	}

}
