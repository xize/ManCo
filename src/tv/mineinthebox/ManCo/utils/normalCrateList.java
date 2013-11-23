package tv.mineinthebox.ManCo.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import tv.mineinthebox.ManCo.logType;
import tv.mineinthebox.ManCo.ManCo;

@SuppressWarnings("deprecation")
public class normalCrateList {
	public static HashMap<Entity, String> getFallingStateChest = new HashMap<Entity, String>();
	public static HashMap<String, Chest> getCrateList = new HashMap<String, Chest>();
	public static HashMap<String, Chest> getCrateList2 = new HashMap<String, Chest>();
	public static ArrayList<String> ListDataValues = new ArrayList<String>();
	public static HashMap<String, ItemStack[]> ItemsFromChest = new HashMap<String, ItemStack[]>();
	public static HashMap<Location, Block> chestLocations = new HashMap<Location, Block>();
	public static ArrayList<String> schedulerTime = new ArrayList<String>();

	public static void setRandomItems(Chest chest) {
		try {
			File f = new File(ManCo.getPlugin().getDataFolder() + File.separator + "normalCrates.yml");
			if(f.exists()) {
				FileConfiguration con = YamlConfiguration.loadConfiguration(f);
				for(String idDate : con.getStringList("crates")) {
					ListDataValues.add(idDate);
				}
				chest.getInventory().setItem(0, getItemStack());
				chest.getInventory().setItem(1, getItemStack());
				chest.getInventory().setItem(2, getItemStack());
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static ItemStack getItemStack() {
		try {
			Random rand = new Random();
			int number = rand.nextInt(ListDataValues.size() + 0);
			String[] splitItemName = ListDataValues.get(number).split(":");
			if(splitItemName[0].startsWith("MONEY_")) {
				String subMoney = splitItemName[0].substring("MONEY_".length());
				ItemStack item = new ItemStack(Material.PAPER);
				ItemMeta meta = item.getItemMeta();
				if(util.isIconomyEnabled()) {
					meta.setDisplayName(ChatColor.GREEN + "[ManCo]" + ChatColor.GOLD + ":money" + " " + ChatColor.GRAY + subMoney + iconomy.getSymbol());	
				} else {
					meta.setDisplayName(ChatColor.GREEN + "[ManCo]" + ChatColor.GOLD + ":money" + " " + ChatColor.GRAY + subMoney + "$");
				}
				item.setItemMeta(meta);
				item.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
				item.setAmount(Integer.parseInt(splitItemName[2]));
				return item;
			} else {
				int itemID = Integer.parseInt(splitItemName[0]);
				int subData = Integer.parseInt(splitItemName[1]);
				int amount = Integer.parseInt(splitItemName[2]);
				ItemStack item = new ItemStack(Material.getMaterial(itemID));
				item.setAmount(amount);
				item.setDurability((byte) subData);
				return item;	
			}
		} catch(IllegalArgumentException e) {
			ManCo.log("it seems one of your items in items.yml is wrong!", logType.severe);
		}
		return null;
	}
}
