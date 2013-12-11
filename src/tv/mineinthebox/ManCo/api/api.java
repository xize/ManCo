package tv.mineinthebox.ManCo.api;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import tv.mineinthebox.ManCo.ManCo;
import tv.mineinthebox.ManCo.configuration.configuration;
import tv.mineinthebox.ManCo.events.cratescheduler;
import tv.mineinthebox.ManCo.events.moneyCheck;
import tv.mineinthebox.ManCo.exceptions.ChestHasNoOwnerException;
import tv.mineinthebox.ManCo.exceptions.InvalidCrateMethodException;
import tv.mineinthebox.ManCo.exceptions.InvalidRareCrateException;
import tv.mineinthebox.ManCo.exceptions.NoValidMoneyObjectException;
import tv.mineinthebox.ManCo.exceptions.PlayerOnSlabException;
import tv.mineinthebox.ManCo.utils.normalCrate;
import tv.mineinthebox.ManCo.utils.normalCrateList;
import tv.mineinthebox.ManCo.utils.rareCrate;
import tv.mineinthebox.ManCo.utils.rareCrateList;
import tv.mineinthebox.ManCo.utils.util;

public class api {

	private ArrayList<String> getRareCrateList() {
		return rareCrate.getRareCrateList();
	}

	public ItemStack[] convertArrayListToItemStackArray(ArrayList<ItemStack> array) {
		ItemStack[] items = array.toArray(new ItemStack[array.size()]);
		return items;
	}
	
	public void createManCoSupportedCrate(ItemStack[] items, crateEnum rareCrate, String YourCrateName, boolean useKey, boolean useEffects, Double price, Player p, String message) throws InvalidCrateMethodException {
		if(rareCrate == crateEnum.rareCrate) {
			Location loc = p.getLocation();
			loc.setY(normalCrate.getCrateSpawnHeight(loc));
			Entity entity = p.getWorld().spawnFallingBlock(loc, Material.CHEST, (byte)1);
			api_ManCoSupportedEvents.addFallState(entity, p.getName()+":"+YourCrateName+":"+useKey+":"+useEffects+":"+price);
			api_ManCoSupportedEvents.addItemsForChestPreparation(p, items);
			if(!configuration.isCrateDropMessageDisabled()){
				Bukkit.broadcastMessage(ChatColor.GREEN + configuration.getPrefix() + message);
			}
		} else {
			throw new InvalidCrateMethodException("[ManCo-API]InvalidCrateMethodException: the crate enum is used in the wrong method!, this method can only obtain the rareCrate enum");
		}
	}

	public boolean isCrate(Chest chest) {
		if(normalCrateList.getCrateList.containsValue(chest)) {
			return true;
		} else if(normalCrateList.getCrateList2.containsValue(chest)) {
			return true;
		}
		return false;
	}

	public boolean isRareCrate(Chest chest) {
		if(rareCrateList.getCrateList.containsValue(chest)) {
			return true;
		} else if(rareCrateList.getCrateList2.containsValue(chest)) {
			return true;
		}
		return false;
	}

	public void destroyCrate(Player p) {
		configuration.clearPlayerCrate(p);
	}

	public void destroyCrate(String name) {
		configuration.clearPlayerCrate(name);
	}

	public String getCrateOwner(Chest chest) throws ChestHasNoOwnerException {
		String normal = configuration.getNormalChestOwner(chest);
		String rare = configuration.getRareChestOwner(chest);
		if(normal != null) {
			return normal;
		} else if(rare != null) {
			return rare;
		} else {
			throw new ChestHasNoOwnerException("[ManCo-API]ChestHasNoOwnerException: this chest has no ManCo ownership!");
		}
	}


	public void spawnCrate(Location loc, ItemStack[] items) throws PlayerOnSlabException {
		if(util.isSlab(loc.getBlock())) {
			throw new PlayerOnSlabException("[ManCo-API]PlayerOnSlabException: this crate cannot fall on a slab!\ndebug information:\nVersion: + " + ManCo.getPlugin().getDescription().getVersion() +  "\nLocation: "+loc.toString() + "\nitems: "+items.toString());
		} else {
			int y = normalCrate.getCrateSpawnHeight(loc);
			loc.setY(loc.getY() + y);
			FallingBlock entity = loc.getWorld().spawnFallingBlock(loc, Material.CHEST, (byte) 1);
			api_ManCoUnsuportedEvents.add(entity, items);
		}
	}

	public void spawnCrate(Player p) throws PlayerOnSlabException {
		if(util.isSlab(p.getLocation().getBlock())) {
			throw new PlayerOnSlabException("[ManCo-API]PlayerOnSlabException: this crate cannot fall on a slab!\ndebug information:\nVersion: + " + ManCo.getPlugin().getDescription().getVersion() +  "\nLocation: "+p.getLocation().toString());
		} else {
			cratescheduler.doCrateNative(p, true);
		}
	}

	public boolean hasCrate(Player p) {
		if(normalCrateList.getFallingStateChest.containsValue(p.getName())) {
			return true;
		} else if(rareCrateList.rareCrates.containsKey(p.getName())) {
			return true;
		} else if(normalCrateList.getCrateList.containsKey(p.getName())) {
			return true;
		} else if(normalCrateList.getCrateList2.containsKey(p.getName())) {
			return true;
		} else if(rareCrateList.getCrateList.containsKey(p.getName())) {
			return true;
		} else if(rareCrateList.getCrateList2.containsKey(p.getName())) {
			return true;
		}
		return false;
	}

	public boolean hasCrate(String playername) {
		if(normalCrateList.getFallingStateChest.containsValue(playername)) {
			return true;
		} else if(rareCrateList.rareCrates.containsKey(playername)) {
			return true;
		} else if(normalCrateList.getCrateList.containsKey(playername)) {
			return true;
		} else if(normalCrateList.getCrateList2.containsKey(playername)) {
			return true;
		} else if(rareCrateList.getCrateList.containsKey(playername)) {
			return true;
		} else if(rareCrateList.getCrateList2.containsKey(playername)) {
			return true;
		}
		return false;
	}

	public boolean isFalling(Player p) {
		if(hasCrate(p)) {
			if(normalCrateList.getFallingStateChest.containsValue(p.getName())) {
				return true;
			} else if(rareCrateList.rareCrates.containsKey(p.getName())) {
				return true;
			}
		}
		return false;
	}

	public Location getFallingCrateLocation(Player p) {
		if(normalCrateList.getFallingStateChest.containsValue(p.getName())) {
			Entity entity = configuration.getEntityFromHashMap(p, crateEnum.normalCrate);
			return entity.getLocation();
		} else if(rareCrateList.rareCrates.containsKey(p.getName())) {
			Entity entity = configuration.getEntityFromHashMap(p, crateEnum.rareCrate);
			return entity.getLocation();
		}
		return null;
	}

	public Chest getCrate(Player p) throws ChestHasNoOwnerException {
		if(hasCrate(p.getName())) {
			if(normalCrateList.getCrateList.containsKey(p.getName())) {
				return normalCrateList.getCrateList.get(p.getName());
			} else if(normalCrateList.getCrateList2.containsKey(p.getName())) {
				return normalCrateList.getCrateList2.get(p.getName());
			} else if(rareCrateList.getCrateList.containsKey(p.getName())) {
				return rareCrateList.getCrateList.get(p.getName());
			} else if(rareCrateList.getCrateList2.containsKey(p.getName())) {
				return rareCrateList.getCrateList2.get(p.getName());
			}
		}
		throw new ChestHasNoOwnerException("[ManCo-API]ChestHasNoOwnerException: your chest has no ownership!, in getCrate(Player p);");
	}

	public void spawnRareCrate(Player p, String crateName) throws InvalidRareCrateException {
		if(!rareCrate.getRareCrateList().contains(crateName)) {
			throw new InvalidRareCrateException("[ManCo-API]InvalidRareCrateException: invalid crate name!");
		} else {
			for(int i = 0; i < getRareCrateList().size(); i++) {
				if(i == getRareCrateList().size()) {
					if(getRareCrateList().get(i) == crateName) {
						cratescheduler.doRareCrateNative(p, i, true);
						break;
					} else {
						throw new InvalidRareCrateException("[ManCo-API]InvalidRareCrateException: invalid crate name! maybe the name is case sensitive?");
					}
				} else {
					if(getRareCrateList().get(i) == crateName) {
						cratescheduler.doRareCrateNative(p, i, true);
						break;
					}	
				}
			}
		}
	}

	//enable this in onEnable.
	public void cancelDefaultSchedulers() {
		cratescheduler.task.cancel();
		cratescheduler.task2.cancel();
		cratescheduler.task = null;
		cratescheduler.task2 = null;
	}

	public ItemStack createMoneyItem(Double amountInCurrency, int amount) {
		ItemStack item = new ItemStack(Material.PAPER);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.GREEN + "[ManCo]" + ChatColor.GOLD + ":money" + " " + ChatColor.GRAY + amountInCurrency + "$");
		item.setItemMeta(meta);
		item.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
		item.setAmount(amount);
		return item;
	}

	public ItemStack createMoneyItem(int amountInCurrency, int amount) {
		ItemStack item = new ItemStack(Material.PAPER);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.GREEN + "[ManCo]" + ChatColor.GOLD + ":money" + " " + ChatColor.GRAY + amountInCurrency + "$");
		item.setItemMeta(meta);
		item.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
		item.setAmount(amount);
		return item;
	}
	
	public boolean isMoneyObject(ItemStack item) {
		if(item.hasItemMeta()) {
			if(item.getItemMeta().hasEnchant(Enchantment.DURABILITY)) {
				String[] args = item.getItemMeta().getDisplayName().split(" ");
				if(args[0].equals(moneyCheck.prefix)) {
					return true;
				}
			}
		}
		return false;
	}

	public Double getMoneyDeserialized(ItemStack item) throws NoValidMoneyObjectException {
		if(item.getType() == Material.PAPER) {
			if(item.hasItemMeta()) {
				if(item.getItemMeta().hasEnchant(Enchantment.DURABILITY)) {
					String[] args = item.getItemMeta().getDisplayName().split(" ");
					if(args[0].equals(moneyCheck.prefix)) {
						Double money = Double.parseDouble(ChatColor.stripColor(args[1].replace("$", "")));
						return money;
					}
				}
			}
		}
		throw new NoValidMoneyObjectException("this item is not a ManCo generated money object!");
	}

}
