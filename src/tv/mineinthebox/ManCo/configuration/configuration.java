package tv.mineinthebox.ManCo.configuration;

import java.io.File;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.FileConfigurationOptions;
import org.bukkit.configuration.file.YamlConfiguration;

import tv.mineinthebox.ManCo.logType;
import tv.mineinthebox.ManCo.manCo;

public class configuration {
	
	public static void createConfigs() {
		createNormalCrateConfig();
		createRareCrateConfig();
		createDefaultConfiguration();
	}

	public static void createNormalCrateConfig() {
		try {
			File f = new File(manCo.getPlugin().getDataFolder() + File.separator + "normalCrates.yml");
			if(!f.exists()) {
				FileConfiguration con = YamlConfiguration.loadConfiguration(f);
				FileConfigurationOptions opt = con.options();
				opt.header("This is the default Normal crate configuration adjust or add more random items which can be found in the crates\nhowever you cannot get more than 3 slots filled in the chest!\n\nsmall schematic how to use these lines!\nDataValue:subDataValue:Amount if there is no subvalue use 0 instead\nfor more information about data values visit http://minecraft.gamepedia.com/File:DataValuesBeta.png");
				ArrayList<String> crates = new ArrayList<String>();
				crates.add("98:1:1");
				crates.add("20:0:3");
				crates.add("383:100:1");
				crates.add("420:0:3");
				crates.add("329:0:1");
				crates.add("MONEY_30:0:2");
				con.set("crates", crates);
				con.save(f);
				crates.clear();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("deprecation")
	public static void createRareCrateConfig() {
		try {
			File f = new File(manCo.getPlugin().getDataFolder() + File.separator + "rareCrates.yml");
			if(!f.exists()) {
				FileConfiguration con = YamlConfiguration.loadConfiguration(f);
				FileConfigurationOptions opt = con.options();
				opt.header("this is the default configuration for rareCrates, here you can add as many rare crates as you want and define if players need a ManCo key or not.\nnote that these chests still gets destroyed on relogs or kicks but they cannot get the drops!\n\nsmall schematic how to use these lines!\nDataValue:subDataValue:Amount if there is no subvalue use 0 instead\nfor more information about data values visit http://minecraft.gamepedia.com/File:DataValuesBeta.png");
				ArrayList<String> a = new ArrayList<String>();
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
				con.save(f);
				list1.clear();
				a.clear();

			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void createDefaultConfiguration() {
		try {
			File f = new File(manCo.getPlugin().getDataFolder() + File.separator + "config.yml");
			if(!f.exists()) {
				FileConfiguration con = YamlConfiguration.loadConfiguration(f);
				FileConfigurationOptions opt = con.options();
				opt.header("Default configuration for ManCo supply crates\nhere you can specify the prefixes, suffixes, enable iConomy and more");
				con.set("DropTime", 90000);
				con.set("dropRoundsPerTime", 1);
				con.set("useIconomy.enabled", false);
				con.set("useIconomy.buyCratePrice", 10);
				con.set("CrateFound.message", "&7%p has found a ManCo crate!");
				con.set("CrateFound.enableMessage", true);
				con.set("CratePrefix", "&2[ManCo]");
				con.set("disableCrateProtection", false);
				con.set("disableUncrateMessage", false);
				con.set("spawnCrateNearby", false);
				for(World w : Bukkit.getWorlds()) {
					if(w.getName().contains("_nether")) {
						//ignore nether worlds!
					} else {
						con.set("enablePerWorld."+w.getName(), true);	
					}
				}
				con.save(f);
			} else {
				//check for new worlds!
				manCo.log("config.yml found! checking for new worlds", logType.info);
				FileConfiguration con = YamlConfiguration.loadConfiguration(f);
				boolean bol = false;
				for(World w : Bukkit.getWorlds()) {
					if(w.getName().contains("_nether")) {
						//ignore nether worlds!
					} else {
						if(!con.isSet("enablePerWorld."+w.getName())) {
							con.set("enablePerWorld."+w.getName(), true);
							con.save(f);
							manCo.log("new world found! " + w.getName(), logType.info);
							bol = true;
						}
					}
				}
				if(!bol) {
					manCo.log("no new worlds found!", logType.info);
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static boolean isChestProtectionOff() {
		try {
			File f = new File(manCo.getPlugin().getDataFolder() + File.separator + "config.yml");
			if(f.exists()) {
				FileConfiguration con = YamlConfiguration.loadConfiguration(f);
				if(con.getBoolean("disableCrateProtection")) {
					return false;
				} else {
					return true;
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static String getPrefix() {
		try {
			File f = new File(manCo.getPlugin().getDataFolder() + File.separator + "config.yml");
			if(f.exists()) {
				FileConfiguration con = YamlConfiguration.loadConfiguration(f);
				return ChatColor.translateAlternateColorCodes('&', con.getString("CratePrefix") + " ");
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static boolean isRareCrate(String crateName) {
		try {
			File f = new File(manCo.getPlugin().getDataFolder() + File.separator + "rareCrates.yml");
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
			File f = new File(manCo.getPlugin().getDataFolder() + File.separator + "config.yml");
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
			File f = new File(manCo.getPlugin().getDataFolder() + File.separator + "config.yml");
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
			File f = new File(manCo.getPlugin().getDataFolder() + File.separator + "config.yml");
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
			File f = new File(manCo.getPlugin().getDataFolder() + File.separator + "config.yml");
			if(f.exists()) {
				FileConfiguration con = YamlConfiguration.loadConfiguration(f);
				return con.getDouble("useIconomy.buyCratePrice");
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return 0.0;
	}
	
	public static int roundsPerTime() {
		try {
			File f = new File(manCo.getPlugin().getDataFolder() + File.separator + "config.yml");
			if(f.exists()) {
				FileConfiguration con = YamlConfiguration.loadConfiguration(f);
				return con.getInt("dropRoundsPerTime");
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public static int getTime() {
		try {
			File f = new File(manCo.getPlugin().getDataFolder() + File.separator + "config.yml");
			if(f.exists()) {
				FileConfiguration con = YamlConfiguration.loadConfiguration(f);
				return con.getInt("DropTime");
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public static boolean isCrateDropMessageDisabled() {
		try {
			File f = new File(manCo.getPlugin().getDataFolder() + File.separator + "config.yml");
			if(f.exists()) {
				FileConfiguration con = YamlConfiguration.loadConfiguration(f);
				if(con.getBoolean("CrateFound.enableMessage")) {
					return false;
				} else {
					return true;
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
