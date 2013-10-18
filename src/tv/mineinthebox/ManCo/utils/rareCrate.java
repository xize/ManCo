package tv.mineinthebox.ManCo.utils;

import java.io.File;
import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import tv.mineinthebox.ManCo.manCo;

public class rareCrate {
	
	public static ArrayList<String> getRareCrateList() {
		ArrayList<String> list = new ArrayList<String>();
		if(!list.isEmpty()) {
			list.clear();
		}
		try {
			File f = new File(manCo.getPlugin().getDataFolder() + File.separator + "items.yml");
			if(f.exists()) {
				FileConfiguration con = YamlConfiguration.loadConfiguration(f);
				for(String crateName : con.getConfigurationSection("rarecrates").getKeys(false)) {
					if(con.getBoolean("rarecrates."+crateName+".enable")) {
						list.add(crateName);	
					}
				}
				return list;
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<String>();
	}
	
	public static int getRareCrateChance(String RareCrateName) {
		if(getRareCrateList().contains(RareCrateName)) {
			try {
				File f = new File(manCo.getPlugin().getDataFolder() + File.separator + "items.yml");
				if(f.exists()) {
					FileConfiguration con = YamlConfiguration.loadConfiguration(f);
					return con.getInt("rarecrates."+RareCrateName+".dropRateChance");
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return 0;
	}
	
	public static String getCrateFoundMessage(String crateName) {
		try {
			File f = new File(manCo.getPlugin().getDataFolder() + File.separator + "items.yml");
			if(f.exists()) {
				FileConfiguration con = YamlConfiguration.loadConfiguration(f);
				return ChatColor.translateAlternateColorCodes('&', con.getString("rarecrates."+crateName+".crateFoundMessage"));
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static boolean crateHasEffect(String path) {
		try {
			File f = new File(manCo.getPlugin().getDataFolder() + File.separator + "items.yml");
			if(f.exists()) {
				FileConfiguration con = YamlConfiguration.loadConfiguration(f);
				return con.getBoolean("rarecrates."+path+".effect");
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
