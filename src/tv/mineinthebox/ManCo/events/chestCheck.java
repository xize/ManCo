package tv.mineinthebox.ManCo.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import tv.mineinthebox.ManCo.chestList;
import tv.mineinthebox.ManCo.manCo;
import tv.mineinthebox.ManCo.configuration.configuration;

public class chestCheck implements Listener {

	@SuppressWarnings("deprecation")
	@EventHandler
	public void setChestState(EntityChangeBlockEvent e) {
		if(chestList.getFallingStateChest.containsKey(e.getEntity())) {
			e.getBlock().setType(Material.CHEST);
			if(e.getBlock().getType() == Material.CHEST) {
				e.getBlock().setData((byte) 3);
				Chest chest = (Chest) e.getBlock().getState();
				chestList.getCrateList.put(chestList.getFallingStateChest.get(e.getEntity()), chest);
				chestList.chestLocations.put(chest.getLocation(), e.getBlock());
				chestList.getFallingStateChest.remove(e.getEntity());
				chestList.setRandomItems(chest);
			}
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onOpenCrate(PlayerInteractEvent e) {
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			Block block = e.getClickedBlock();
			if(block.getType() == Material.CHEST) {
				Chest chest = (Chest) e.getClickedBlock().getState();
				if(chestList.getCrateList.containsKey(e.getPlayer().getName())){
					Chest chestFromList = chestList.getCrateList.get(e.getPlayer().getName());
					if(!chest.equals(chestFromList)){
						if(chestList.getCrateList.containsValue(chest)) {
							e.getPlayer().sendMessage(ChatColor.GREEN + "[ManCo] " + ChatColor.GRAY + "this crate does not belongs to you!");
							e.setCancelled(true);
						}
					}
				} else if(!chestList.getCrateList.containsKey(e.getPlayer().getName())) {
					if(chestList.getCrateList.containsValue(chest)) {
						e.getPlayer().sendMessage(ChatColor.GREEN + "[ManCo] " + ChatColor.GRAY + "this crate does not belongs to you!");
						e.setCancelled(true);
					}
				}
			}
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void blockPlace(BlockPlaceEvent e) {
		ItemStack item = e.getItemInHand();
		if(item.getType() == Material.CHEST) {
			for(BlockFace face : BlockFace.values()) {
				Block block = e.getBlock().getRelative(face);
				if(block.getType() == Material.CHEST) {
					if(chestList.chestLocations.containsKey(block.getLocation())) {
						e.getPlayer().sendMessage(ChatColor.RED + "you are not allowed to place a chest near a ManCo crate!");
						e.setCancelled(true);
						block.setData((byte) 3);
						break;
					}
				}
			}
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void blockBreak(BlockBreakEvent e) {
		for(BlockFace face : BlockFace.values()) {
			Block block = e.getBlock().getRelative(face);

			if(block.getType() == Material.CHEST) {
				if(chestList.chestLocations.containsKey(block.getLocation())) {
					e.getPlayer().sendMessage(ChatColor.RED + "you are not allowed to break a ManCo crate!");
					e.setCancelled(true);
					block.setData((byte) 3);
					break;
				}
			}
		}
	}

	@EventHandler
	public void getInventory(final InventoryOpenEvent e) {
		if(e.getInventory().getType() == InventoryType.CHEST) {
			if(e.getInventory().getHolder() instanceof Chest) {
				final Chest chest = (Chest) e.getInventory().getHolder();
				if(chestList.getCrateList.containsKey(e.getPlayer().getName())){
					Chest chestFromList = chestList.getCrateList.get(e.getPlayer().getName());
					if(!chest.equals(chestFromList)){
						if(chestList.getCrateList.containsValue(chest)) {
							e.setCancelled(true);
						}
					} else {
						if(chestList.schedulerTime.contains(e.getPlayer().getName())) {
							e.setCancelled(true);
							return;
						}
						final Player p = (Player) e.getPlayer();
						e.setCancelled(true);
						chestList.schedulerTime.add(e.getPlayer().getName());
						Bukkit.getScheduler().scheduleSyncDelayedTask(manCo.getPlugin(), new Runnable() {

							@Override
							public void run() {
								p.sendMessage(ChatColor.GREEN + "opening crate 5");
								p.playSound(chest.getLocation(), Sound.CHEST_OPEN, 0, 1);
								p.playSound(chest.getLocation(), Sound.CHEST_OPEN, 1, 0);
								p.playSound(chest.getLocation(), Sound.CHEST_CLOSE, 1, 0);
								p.playSound(chest.getLocation(), Sound.CHEST_OPEN, 1, 0);
							}

						}, 50);
						Bukkit.getScheduler().scheduleSyncDelayedTask(manCo.getPlugin(), new Runnable() {

							@Override
							public void run() {
								p.sendMessage(ChatColor.GREEN + "opening crate 4");
								p.playSound(chest.getLocation(), Sound.CHEST_OPEN, 0, 1);
								p.playSound(chest.getLocation(), Sound.CHEST_OPEN, 1, 0);
								p.playSound(chest.getLocation(), Sound.CHEST_CLOSE, 1, 0);
								p.playSound(chest.getLocation(), Sound.CHEST_OPEN, 1, 0);
							}

						}, 100);
						Bukkit.getScheduler().scheduleSyncDelayedTask(manCo.getPlugin(), new Runnable() {

							@Override
							public void run() {
								p.sendMessage(ChatColor.GREEN + "opening crate 3");
								p.playSound(chest.getLocation(), Sound.CHEST_OPEN, 0, 1);
								p.playSound(chest.getLocation(), Sound.CHEST_OPEN, 1, 0);
								p.playSound(chest.getLocation(), Sound.CHEST_CLOSE, 1, 0);
								p.playSound(chest.getLocation(), Sound.CHEST_OPEN, 1, 0);
							}


						}, 150);
						Bukkit.getScheduler().scheduleSyncDelayedTask(manCo.getPlugin(), new Runnable() {

							@Override
							public void run() {
								p.sendMessage(ChatColor.GREEN + "opening crate 2");
								p.playSound(chest.getLocation(), Sound.CHEST_OPEN, 0, 1);
								p.playSound(chest.getLocation(), Sound.CHEST_OPEN, 1, 0);
								p.playSound(chest.getLocation(), Sound.CHEST_CLOSE, 1, 0);
								p.playSound(chest.getLocation(), Sound.CHEST_OPEN, 1, 0);
							}

						}, 200);
						Bukkit.getScheduler().scheduleSyncDelayedTask(manCo.getPlugin(), new Runnable() {

							@Override
							public void run() {
								p.sendMessage(ChatColor.GREEN + "opening crate 1");
								p.playSound(chest.getLocation(), Sound.CHEST_OPEN, 0, 1);
								p.playSound(chest.getLocation(), Sound.CHEST_OPEN, 1, 0);
								p.playSound(chest.getLocation(), Sound.CHEST_CLOSE, 1, 0);
								p.playSound(chest.getLocation(), Sound.CHEST_OPEN, 1, 0);
							}

						}, 250);
						Bukkit.getScheduler().scheduleSyncDelayedTask(manCo.getPlugin(), new Runnable() {

							@Override
							public void run() {
								try {
									p.sendMessage(ChatColor.GREEN + "opening crate...");
									p.playSound(chest.getLocation(), Sound.CHEST_OPEN, 0, 1);
									p.playSound(chest.getLocation(), Sound.CHEST_OPEN, 1, 0);
									p.playSound(chest.getLocation(), Sound.CHEST_CLOSE, 1, 0);
									p.playSound(chest.getLocation(), Sound.CHEST_OPEN, 1, 0);
									if(e.isCancelled()) {
										e.setCancelled(false);
										chestList.ItemsFromChest.put(e.getPlayer().getName(), e.getInventory().getContents());
										chestList.getCrateList.remove(e.getPlayer().getName());
										chestList.getCrateList2.put(e.getPlayer().getName(), chest);
										p.openInventory(e.getInventory());
										chestList.schedulerTime.remove(e.getPlayer().getName());
									}	
								} catch(NullPointerException e) {
									//supress this
								}
							}

						}, 300);
					}
				}
			}
		}
	}

	@EventHandler
	public void chestClose(InventoryCloseEvent e) {
		if(e.getInventory().getType() == InventoryType.CHEST) {
			if(e.getInventory().getHolder() instanceof Chest) {
				Chest chest = (Chest) e.getInventory().getHolder();
				if(chestList.getCrateList2.containsKey(e.getPlayer().getName())) {
					Chest chestFromCrateList = chestList.getCrateList2.get(e.getPlayer().getName());
					if(chest.equals(chestFromCrateList)) {
						ItemStack[] items = chestList.ItemsFromChest.get(e.getPlayer().getName());
						if(!configuration.isUnCrateMessageDisabled()) {
							StringBuilder build = new StringBuilder();
							for(int i = 0; i < items.length; i++) {
								if(items[i] != null) {
									if(i == (items.length - 1)) {
										build.append(items[i].getType().name().toLowerCase().replace("_", " ") + "").toString();
									} else {
										build.append(items[i].getType().name().toLowerCase().replace("_", " ") + ", ").toString();	
									}
								}
							}
							Bukkit.broadcastMessage(ChatColor.GREEN + "[ManCo] " + ChatColor.GRAY + e.getPlayer().getName() + ChatColor.GRAY + " has uncrated the following items!, " + build.toString());
						}
						chestList.getCrateList2.remove(e.getPlayer().getName());
						chestList.ItemsFromChest.remove(e.getPlayer().getName());
						chestList.chestLocations.remove(chest.getLocation());
						chest.getBlock().breakNaturally();
					}
				}
			}
		}
	}

	@EventHandler
	public void PlayeronLeave(PlayerQuitEvent e) {
		if(chestList.getCrateList.containsKey(e.getPlayer().getName())) {
			Chest chest = chestList.getCrateList.get(e.getPlayer().getName());
			chest.getBlock().breakNaturally();
			chestList.getCrateList.remove(e.getPlayer().getName());
			chestList.chestLocations.remove(chest.getLocation());
		}
		if(chestList.ItemsFromChest.containsKey(e.getPlayer().getName())) {
			chestList.ItemsFromChest.remove(e.getPlayer().getName());
		}
		if(chestList.getCrateList2.containsKey(e.getPlayer().getName())) {
			Chest chest = chestList.getCrateList2.get(e.getPlayer().getName());
			chest.getBlock().breakNaturally();
			chestList.getCrateList2.remove(e.getPlayer().getName());
			chestList.chestLocations.remove(chest.getLocation());
		}
		if(chestList.schedulerTime.contains(e.getPlayer().getName())) {
			chestList.schedulerTime.remove(e.getPlayer().getName());
		}
	}

	@EventHandler
	public void PlayeronLeave(PlayerKickEvent e) {
		if(chestList.getCrateList.containsKey(e.getPlayer().getName())) {
			Chest chest = chestList.getCrateList.get(e.getPlayer().getName());
			chest.getBlock().breakNaturally();
			chestList.getCrateList.remove(e.getPlayer().getName());
			chestList.chestLocations.remove(chest.getLocation());
		}
		if(chestList.ItemsFromChest.containsKey(e.getPlayer().getName())) {
			chestList.ItemsFromChest.remove(e.getPlayer().getName());
		}
		if(chestList.getCrateList2.containsKey(e.getPlayer().getName())) {
			Chest chest = chestList.getCrateList2.get(e.getPlayer().getName());
			chest.getBlock().breakNaturally();
			chestList.getCrateList2.remove(e.getPlayer().getName());
			chestList.chestLocations.remove(chest.getLocation());
		}
		if(chestList.schedulerTime.contains(e.getPlayer().getName())) {
			chestList.schedulerTime.remove(e.getPlayer().getName());
		}
	}

	public static void destroyChestOnDisable() {
		for(Player p : Bukkit.getOnlinePlayers()) {
			if(chestList.getCrateList.containsKey(p.getPlayer().getName())) {
				Chest chest = chestList.getCrateList.get(p.getPlayer().getName());
				chest.getBlock().breakNaturally();
				chestList.getCrateList.remove(p.getPlayer().getName());
				chestList.chestLocations.remove(chest.getLocation());
			}
			if(chestList.ItemsFromChest.containsKey(p.getPlayer().getName())) {
				chestList.ItemsFromChest.remove(p.getPlayer().getName());
			}
			if(chestList.getCrateList2.containsKey(p.getPlayer().getName())) {
				Chest chest = chestList.getCrateList2.get(p.getPlayer().getName());
				chest.getBlock().breakNaturally();
				chestList.getCrateList2.remove(p.getPlayer().getName());
				chestList.chestLocations.remove(chest.getLocation());
			}
			if(chestList.schedulerTime.contains(p.getPlayer().getName())) {
				chestList.schedulerTime.remove(p.getPlayer().getName());
			}
		}
	}

}
