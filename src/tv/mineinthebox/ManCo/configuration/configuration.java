package tv.mineinthebox.ManCo.configuration;

import java.io.File;
import java.util.ArrayList;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.FileConfigurationOptions;
import org.bukkit.configuration.file.YamlConfiguration;

import tv.mineinthebox.ManCo.manCo;

public class configuration {
	
	public static void createConfig() {
		try {
			File f = new File(manCo.getPlugin().getDataFolder() + File.separator + "items.yml");
			if(!f.exists()) {
				FileConfiguration con = YamlConfiguration.loadConfiguration(f);
				FileConfigurationOptions opt = con.options();
				if(!con.isSet("time")) {
					con.set("time", 90000);
				}
				if(!con.isSet("roundsPerTime")) {
					con.set("roundsPerTime", 3);
				}
				opt.header("Default configuration file for manCo crates!\nscheme DataValue:subDataValue:Amount");
				ArrayList<String> list = new ArrayList<String>();
				list.add("98:1:1");
				list.add("20:0:3");
				con.set("item.ids", list);
				con.save(f);
				list.clear();
			} else {
				FileConfiguration con = YamlConfiguration.loadConfiguration(f);
				if(!con.isSet("time")) {
					con.set("time", 90000);
					con.save(f);
				}
				if(!con.isSet("roundsPerTime")) {
					con.set("roundsPerTime", 3);
					con.save(f);
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static int roundsPerTime() {
		try {
			File f = new File(manCo.getPlugin().getDataFolder() + File.separator + "items.yml");
			if(f.exists()) {
				FileConfiguration con = YamlConfiguration.loadConfiguration(f);
				return con.getInt("roundsPerTime");
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public static int getTime() {
		try {
			File f = new File(manCo.getPlugin().getDataFolder() + File.separator + "items.yml");
			if(f.exists()) {
				FileConfiguration con = YamlConfiguration.loadConfiguration(f);
				return con.getInt("time");
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

}
