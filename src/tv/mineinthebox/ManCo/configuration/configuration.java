package tv.mineinthebox.ManCo.configuration;

import java.io.File;
import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.FileConfigurationOptions;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

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
					con.set("roundsPerTime", 2);
				}
				if(!con.isSet("useIconomy.enabled")) {
					con.set("useIconomy.enabled", false);
				}
				if(!con.isSet("useIconomy.price")) {
					con.set("useIconomy.price", 10.0);
				}
				if(!con.isSet("CrateFoundMessage")) {
					con.set("CrateFoundMessage", "&7%p has found a ManCo crate!");
				}
				if(!con.isSet("disableUncrateMessage")) {
					con.set("disableUncraftMessage", false);
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
					con.set("roundsPerTime", 2);
					con.save(f);
				}
				if(!con.isSet("useIconomy.enabled")) {
					con.set("useIconomy.enabled", false);
					con.save(f);
				}
				if(!con.isSet("useIconomy.price")) {
					con.set("useIconomy.price", 10.0);
					con.save(f);
				}
				if(!con.isSet("CrateFoundMessage")) {
					con.set("CrateFoundMessage", "&7%p has found a ManCo crate!");
					con.save(f);
				}
				if(!con.isSet("disableUncrateMessage")) {
					con.set("disableUncraftMessage", false);
					con.save(f);
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
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
				return con.getBoolean("disableUncraftMessage");
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
	
	public static boolean isEconomyEnabled() {
		try {
			File f = new File(manCo.getPlugin().getDataFolder() + File.separator + "items.yml");
			if(f.exists()) {
				FileConfiguration con = YamlConfiguration.loadConfiguration(f);
				if(con.getBoolean("useIconomy.enabled")) {
					return true;
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static double returnIconomyPrice() {
		try {
			File f = new File(manCo.getPlugin().getDataFolder() + File.separator + "items.yml");
			if(f.exists()) {
				FileConfiguration con = YamlConfiguration.loadConfiguration(f);
				return con.getDouble("useIconomy.price");
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return 0.0;
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
