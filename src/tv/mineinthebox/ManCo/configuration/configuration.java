package tv.mineinthebox.ManCo.configuration;

import java.io.File;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.FileConfigurationOptions;
import org.bukkit.configuration.file.YamlConfiguration;
import tv.mineinthebox.ManCo.manCo;

public class configuration {
	
	@SuppressWarnings("deprecation")
	public static void createConfig() {
		try {
			File f = new File(manCo.getPlugin().getDataFolder() + File.separator + "items.yml");
			if(!f.exists()) {
				FileConfiguration con = YamlConfiguration.loadConfiguration(f);
				FileConfigurationOptions opt = con.options();
				opt.header("Default configuration file for manCo crates!\nscheme DataValue:subDataValue:Amount");
				ArrayList<String> list = new ArrayList<String>();
				list.add("98:1:1");
				list.add("20:0:3");
				con.set("item.ids", list);
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
					con.set("disableUncrateMessage", false);
				}
				if(!con.isSet("spawnCrateNearby")) {
					con.set("spawnCrateNearby", false);
				}
				if(!con.isSet("rarecrates")) {
					ArrayList<String> a= new ArrayList<String>();
					a.add("264:0:32");
					a.add("264:0:12");
					a.add("265:0:5");
					a.add("322:1:2");
					ArrayList<String> list1 = new ArrayList<String>();
					list1.add("264:0:32");
					list1.add("264:0:12");
					list1.add("265:0:5");
					list1.add("322:1:2");
					list1.add("MONEY_10:0:2");
					con.set("rarecrates.rarecrate1.enable", true);
					con.set("rarecrates.rarecrate1.dropRateChance", 5);
					con.set("rarecrates.rarecrate1.crateFoundMessage", "&7%p has found a &1(Rare)&7 ManCo crate!");
					con.set("rarecrates.rarecrate1.needKey.enabled", false);
					con.set("rarecrates.rarecrate1.needKey.material", Material.BLAZE_ROD.getId());
					con.set("rarecrates.rarecrate1.needKey.price", 3.0);
					con.set("rarecrates.rarecrate1.effect", false);
					con.set("rarecrates.rarecrate1.items", a.toArray());
					
					con.set("rarecrates.anothercrate.enable", false);
					con.set("rarecrates.anothercrate.dropRateChance", 2);
					con.set("rarecrates.anothercrate.crateFoundMessage", "&7%p has found a &1(Rare)&7 ManCo crate!");
					con.set("rarecrates.anothercrate.needKey.enabled", false);
					con.set("rarecrates.anothercrate.needKey.material", Material.BLAZE_ROD.getId());
					con.set("rarecrates.anothercrate.needKey.price", 3.0);
					con.set("rarecrates.anothercrate.effect", false);
					con.set("rarecrates.anothercrate.items", list1.toArray());
					list1.clear();
					a.clear();
				}
				if(!con.isSet("enablePerWorld")) {
					for(World w : Bukkit.getWorlds()) {
						con.set("enablePerWorld."+w.getName(), true);
					}
					
				} else if(con.isSet("enablePerWorld")) {
					//check for new worlds
					for(World w : Bukkit.getWorlds()) {
						if(!con.isSet("enablePerWorld."+w.getName())) {
							con.set("enablePerWorld."+w.getName(), true);
						}
					}
				}
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
					con.set("disableUncrateMessage", false);
					con.save(f);
				}
				if(!con.isSet("spawnCrateNearby")) {
					con.set("spawnCrateNearby", false);
					con.save(f);
				}
				if(!con.isSet("rarecrates")) {
					ArrayList<String> a= new ArrayList<String>();
					a.add("264:0:32");
					a.add("264:0:12");
					a.add("265:0:5");
					a.add("322:1:2");
					ArrayList<String> list = new ArrayList<String>();
					list.add("264:0:32");
					list.add("264:0:12");
					list.add("265:0:5");
					list.add("322:1:2");
					con.set("rarecrates.rarecrate1.enable", true);
					con.set("rarecrates.rarecrate1.dropRateChance", 5);
					con.set("rarecrates.rarecrate1.crateFoundMessage", "&7%p has found a &1(Rare)&7 ManCo crate!");
					con.set("rarecrates.rarecrate1.needKey.enabled", false);
					con.set("rarecrates.rarecrate1.needKey.material", Material.BLAZE_ROD.getId());
					con.set("rarecrates.rarecrate1.needKey.price", 3.0);
					con.set("rarecrates.rarecrate1.effect", false);
					con.set("rarecrates.rarecrate1.items", a.toArray());
					
					con.set("rarecrates.anothercrate.enable", false);
					con.set("rarecrates.anothercrate.dropRateChance", 2);
					con.set("rarecrates.anothercrate.crateFoundMessage", "&7%p has found a &1(Rare)&7 ManCo crate!");
					con.set("rarecrates.anothercrate.needKey.enabled", false);
					con.set("rarecrates.anothercrate.needKey.material", Material.BLAZE_ROD.getId());
					con.set("rarecrates.anothercrate.needKey.price", 3.0);
					con.set("rarecrates.anothercrate.effect", false);
					con.set("rarecrates.anothercrate.items", list.toArray());
					list.clear();
					a.clear();
					con.save(f);
				}
				if(!con.isSet("enablePerWorld")) {
					for(World w : Bukkit.getWorlds()) {
						con.set("enablePerWorld."+w.getName(), true);
						con.save(f);
					}
					
				} else if(con.isSet("enablePerWorld")) {
					//check for new worlds
					for(World w : Bukkit.getWorlds()) {
						if(!con.isSet("enablePerWorld."+w.getName())) {
							con.set("enablePerWorld."+w.getName(), true);
							con.save(f);
						}
					}
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static boolean isRareCrate(String crateName) {
		try {
			File f = new File(manCo.getPlugin().getDataFolder() + File.separator + "items.yml");
			if(f.exists()) {
				FileConfiguration con = YamlConfiguration.loadConfiguration(f);
				if(con.isSet("rarecrates."+crateName)) {
					if(con.getBoolean("rarecrates."+crateName+".enable")) {
						return true;
					} else {
						return false;
					}
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean isPluginDisabledForWorld(World w) {
		try {
			File f = new File(manCo.getPlugin().getDataFolder() + File.separator + "items.yml");
			if(f.exists()) {
				FileConfiguration con = YamlConfiguration.loadConfiguration(f);
				if(con.isSet("enablePerWorld."+w.getName())) {
					if(con.getBoolean("enablePerWorld."+w.getName()) == false) {
						return true;
					} else {
						return false;
					}
				} else {
					return false;
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean spawnCrateNearby() {
		try {
			File f = new File(manCo.getPlugin().getDataFolder() + File.separator + "items.yml");
			if(f.exists()) {
				FileConfiguration con = YamlConfiguration.loadConfiguration(f);
				return con.getBoolean("spawnCrateNearby");
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return false;
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
