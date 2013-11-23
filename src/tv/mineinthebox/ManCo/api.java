package tv.mineinthebox.ManCo;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import tv.mineinthebox.ManCo.configuration.configuration;
import tv.mineinthebox.ManCo.events.cratescheduler;
import tv.mineinthebox.ManCo.events.moneyCheck;
import tv.mineinthebox.ManCo.exceptions.ChestHasNoOwnerException;
import tv.mineinthebox.ManCo.exceptions.InvalidChestStorageException;
import tv.mineinthebox.ManCo.exceptions.InvalidRareCrateException;
import tv.mineinthebox.ManCo.exceptions.NoValidMoneyObjectException;
import tv.mineinthebox.ManCo.exceptions.PlayerOnSlabException;
import tv.mineinthebox.ManCo.utils.iconomy;
import tv.mineinthebox.ManCo.utils.normalCrate;
import tv.mineinthebox.ManCo.utils.normalCrateList;
import tv.mineinthebox.ManCo.utils.rareCrate;
import tv.mineinthebox.ManCo.utils.rareCrateList;
import tv.mineinthebox.ManCo.utils.util;

public class api implements Listener {

	private static HashMap<Entity, ItemStack[]> entityChest = new HashMap<Entity, ItemStack[]>();

	private ArrayList<String> getRareCrateList() {
		return rareCrate.getRareCrateList();
	}

	public ItemStack[] convertArrayListToItemStackArray(ArrayList<ItemStack> array) {
		ItemStack[] items = array.toArray(new ItemStack[array.size()]);
		return items;
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
			entityChest.put(entity, items);
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	private void fillChestApi(EntityChangeBlockEvent e) throws InvalidChestStorageException {
		if(entityChest.containsKey(e.getEntity())) {
			e.setCancelled(true);
			e.getBlock().setTypeIdAndData(Material.CHEST.getId(), (byte) 1, true);
			Chest chest = (Chest) e.getBlock().getState();
			if(entityChest.get(e.getEntity()).length < 27) {
				for(ItemStack item : entityChest.get(e.getEntity())) {
					chest.getInventory().addItem(item);
				}
				entityChest.remove(e.getEntity());
			} else {
				//memory safety in case it throws allready.
				entityChest.remove(e.getEntity());
				throw new InvalidChestStorageException("[ManCo-API]InvalidChestStorage: the ItemStack[] amount is more than the single chest could obtain!");
			}
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
		if(util.isIconomyEnabled()) {
			meta.setDisplayName(ChatColor.GREEN + "[ManCo]" + ChatColor.GOLD + ":money" + " " + ChatColor.GRAY + amountInCurrency + iconomy.getSymbol());
		} else {
			meta.setDisplayName(ChatColor.GREEN + "[ManCo]" + ChatColor.GOLD + ":money" + " " + ChatColor.GRAY + amountInCurrency + "$");	
		}
		item.setItemMeta(meta);
		item.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
		item.setAmount(amount);
		return item;
	}

	public ItemStack createMoneyItem(int amountInCurrency, int amount) {
		ItemStack item = new ItemStack(Material.PAPER);
		ItemMeta meta = item.getItemMeta();
		if(util.isIconomyEnabled()) {
			meta.setDisplayName(ChatColor.GREEN + "[ManCo]" + ChatColor.GOLD + ":money" + " " + ChatColor.GRAY + amountInCurrency + iconomy.getSymbol());
		} else {
			meta.setDisplayName(ChatColor.GREEN + "[ManCo]" + ChatColor.GOLD + ":money" + " " + ChatColor.GRAY + amountInCurrency + "$");	
		}
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
						Double money;
						if(util.isIconomyEnabled()) {
							money = Double.parseDouble(ChatColor.stripColor(args[1].replace(iconomy.getSymbol(), "")));
						} else {
							money = Double.parseDouble(ChatColor.stripColor(args[1].replace("$", "")));	
						}
						return money;
					}
				}
			}
		}
		throw new NoValidMoneyObjectException("this item is not a ManCo generated money object!");
	}

}
