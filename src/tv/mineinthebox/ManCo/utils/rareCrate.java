package tv.mineinthebox.ManCo.utils;

import java.io.File;
import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import tv.mineinthebox.ManCo.logType;
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
	
	public static boolean isCrateKeyEnabled(String crateName) {
		try {
			File f = new File(manCo.getPlugin().getDataFolder() + File.separator + "items.yml");
			if(f.exists()) {
				FileConfiguration con = YamlConfiguration.loadConfiguration(f);
				return con.getBoolean("rarecrates."+crateName+".needKey.enabled");
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static double returnKeyPrice(String crateName) {
		try {
			File f = new File(manCo.getPlugin().getDataFolder() + File.separator + "items.yml");
			if(f.exists()) {
				FileConfiguration con = YamlConfiguration.loadConfiguration(f);
				return con.getDouble("rarecrates."+crateName+".needKey.price");
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public static ItemStack getKey(String crateName) {
		try {
			File f = new File(manCo.getPlugin().getDataFolder() + File.separator + "items.yml");
			if(f.exists()) {
				FileConfiguration con = YamlConfiguration.loadConfiguration(f);
				try {
				@SuppressWarnings("deprecation")
				ItemStack item = new ItemStack(Material.getMaterial(con.getInt("rarecrates."+crateName+".needKey.material")));
				item.setAmount(1);
				ItemMeta meta = item.getItemMeta();
				meta.setDisplayName(ChatColor.GOLD + "ManCo Key: " + crateName);
				item.setItemMeta(meta);
				item.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
				return item;
				} catch(IllegalArgumentException e) {
					manCo.log("a exception has been occuried, the key from crate " + crateName + " is a invalid data value!", logType.severe);
				}
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
