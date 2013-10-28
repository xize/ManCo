package tv.mineinthebox.ManCo.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.enchantments.Enchantment;
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

import tv.mineinthebox.ManCo.manCo;
import tv.mineinthebox.ManCo.configuration.configuration;
import tv.mineinthebox.ManCo.utils.normalCrate;
import tv.mineinthebox.ManCo.utils.normalCrateList;
import tv.mineinthebox.ManCo.utils.rareCrate;
import tv.mineinthebox.ManCo.utils.rareCrateList;

public class chestCheck implements Listener {

	@SuppressWarnings("deprecation")
	@EventHandler
	public void setChestState(EntityChangeBlockEvent e) {
		if(normalCrateList.getFallingStateChest.containsKey(e.getEntity())) {
			e.getBlock().setType(Material.CHEST);
			if(e.getBlock().getType() == Material.CHEST) {
				e.getBlock().setData((byte) 3);
				Chest chest = (Chest) e.getBlock().getState();
				normalCrateList.getCrateList.put(normalCrateList.getFallingStateChest.get(e.getEntity()), chest);
				normalCrateList.chestLocations.put(chest.getLocation(), e.getBlock());
				normalCrateList.getFallingStateChest.remove(e.getEntity());
				normalCrateList.setRandomItems(chest);
			}
			e.setCancelled(true);
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void setRareChestState(EntityChangeBlockEvent e) {
		if(rareCrateList.getFallingStateChest.containsKey(e.getEntity())) {
			e.getBlock().setType(Material.CHEST);
			if(e.getBlock().getType() == Material.CHEST) {
				//deserialize the name;)
				//args[0] is the player name, args[1] is the RareCrate name from the configuration!
				String[] args = rareCrateList.getFallingStateChest.get(e.getEntity()).split(",");
				if(rareCrate.crateHasEffect(args[1])) {
					e.getEntity().getWorld().playEffect(e.getEntity().getLocation(), Effect.MOBSPAWNER_FLAMES, 100);
					e.getEntity().getWorld().playEffect(e.getEntity().getLocation(), Effect.ENDER_SIGNAL, 100);
					playRespectedSound(Sound.AMBIENCE_CAVE, e.getBlock().getLocation());
					playRespectedSound(Sound.AMBIENCE_RAIN, e.getBlock().getLocation());
					playRespectedSound(Sound.ANVIL_BREAK, e.getBlock().getLocation());
					playRespectedSound(Sound.AMBIENCE_THUNDER, e.getBlock().getLocation());
					playRespectedSound(Sound.WOLF_DEATH, e.getBlock().getLocation());
				}
				e.getBlock().setData((byte) 3);
				Chest chest = (Chest) e.getBlock().getState();
				rareCrateList.getCrateList.put(args[0], chest);
				rareCrateList.chestLocations.put(chest.getLocation(), e.getBlock());
				rareCrateList.getFallingStateChest.remove(e.getEntity());
				rareCrateList.setRandomItems(chest, args[1]);
			}
			e.setCancelled(true);
		}
	}
	
	public static void playRespectedSound(final Sound sound, final Location loc) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(manCo.getPlugin(), new Runnable() {

			@Override
			public void run() {
				loc.getWorld().playSound(loc, sound, 3, 3);
			}

		}, 15);
	}

	@EventHandler
	public void onOpenCrate(PlayerInteractEvent e) {
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			Block block = e.getClickedBlock();
			if(block.getType() == Material.CHEST) {
				Chest chest = (Chest) e.getClickedBlock().getState();
				if(normalCrateList.getCrateList.containsKey(e.getPlayer().getName())){
					Chest chestFromList = normalCrateList.getCrateList.get(e.getPlayer().getName());
					if(!chest.equals(chestFromList)){
						if(normalCrateList.getCrateList.containsValue(chest)) {
							e.getPlayer().sendMessage(ChatColor.GREEN + configuration.getPrefix() + ChatColor.GRAY + "this crate does not belongs to you!");
							e.setCancelled(true);
						}
					}
				} else if(!normalCrateList.getCrateList.containsKey(e.getPlayer().getName())) {
					if(normalCrateList.getCrateList.containsValue(chest)) {
						e.getPlayer().sendMessage(ChatColor.GREEN + configuration.getPrefix() + ChatColor.GRAY + "this crate does not belongs to you!");
						e.setCancelled(true);
					}
				}
				
				if(rareCrateList.getCrateList.containsKey(e.getPlayer().getName())){
					Chest chestFromList = rareCrateList.getCrateList.get(e.getPlayer().getName());
					if(!chest.equals(chestFromList)){
						if(rareCrateList.getCrateList.containsValue(chest)) {
							e.getPlayer().sendMessage(ChatColor.GREEN + configuration.getPrefix() + ChatColor.GRAY + "this crate does not belongs to you!");
							e.setCancelled(true);
						}
					}
				} else if(!rareCrateList.getCrateList.containsKey(e.getPlayer().getName())) {
					if(rareCrateList.getCrateList.containsValue(chest)) {
						e.getPlayer().sendMessage(ChatColor.GREEN + configuration.getPrefix() + ChatColor.GRAY + "this crate does not belongs to you!");
						e.setCancelled(true);
					}
				}
			}
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void blockPlace(BlockPlaceEvent e) {
		if(configuration.isPluginDisabledForWorld(e.getPlayer().getWorld())) {
			return;
		}
		ItemStack item = e.getItemInHand();
		if(item.getType() == Material.CHEST) {
			for(BlockFace face : BlockFace.values()) {
				Block block = e.getBlock().getRelative(face);
				if(block.getType() == Material.CHEST) {
					if(normalCrateList.chestLocations.containsKey(block.getLocation())) {
						e.getPlayer().sendMessage(ChatColor.RED + "you are not allowed to place a chest near a ManCo crate!");
						e.setCancelled(true);
						block.setData((byte) 3);
						break;
					} else if(rareCrateList.chestLocations.containsKey(block.getLocation())) {
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
		if(configuration.isPluginDisabledForWorld(e.getPlayer().getWorld())) {
			return;
		}
		for(BlockFace face : BlockFace.values()) {
			Block block = e.getBlock().getRelative(face);

			if(block.getType() == Material.CHEST) {
				if(normalCrateList.chestLocations.containsKey(block.getLocation())) {
					e.getPlayer().sendMessage(ChatColor.RED + "you are not allowed to break a ManCo crate!");
					e.setCancelled(true);
					block.setData((byte) 3);
					break;
				} else if(rareCrateList.chestLocations.containsKey(block.getLocation())) {
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
		if(configuration.isPluginDisabledForWorld(e.getPlayer().getWorld())) {
			return;
		}
		if(e.getInventory().getType() == InventoryType.CHEST) {
			if(e.getInventory().getHolder() instanceof Chest) {
				final Chest chest = (Chest) e.getInventory().getHolder();
				if(normalCrateList.getCrateList.containsKey(e.getPlayer().getName())){
					Chest chestFromList = normalCrateList.getCrateList.get(e.getPlayer().getName());
					if(!chest.equals(chestFromList)){
						if(normalCrateList.getCrateList.containsValue(chest)) {
							e.setCancelled(true);
						}
					} else {
						if(normalCrateList.schedulerTime.contains(e.getPlayer().getName())) {
							e.setCancelled(true);
							return;
						}
						final Player p = (Player) e.getPlayer();
						e.setCancelled(true);
						normalCrateList.schedulerTime.add(e.getPlayer().getName());
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
									if(chest.getLocation().distance(p.getLocation()) > 6) {
										p.sendMessage(ChatColor.GREEN + configuration.getPrefix() + ChatColor.GRAY + "you are to far from range to open this crate!");
										e.setCancelled(true);
										normalCrateList.schedulerTime.remove(e.getPlayer().getName());
										return;
									}
									p.sendMessage(ChatColor.GREEN + "opening crate...");
									p.playSound(chest.getLocation(), Sound.CHEST_OPEN, 0, 1);
									p.playSound(chest.getLocation(), Sound.CHEST_OPEN, 1, 0);
									p.playSound(chest.getLocation(), Sound.CHEST_CLOSE, 1, 0);
									p.playSound(chest.getLocation(), Sound.CHEST_OPEN, 1, 0);
									if(e.isCancelled()) {
										e.setCancelled(false);
										normalCrateList.ItemsFromChest.put(e.getPlayer().getName(), e.getInventory().getContents());
										normalCrateList.getCrateList.remove(e.getPlayer().getName());
										normalCrateList.getCrateList2.put(e.getPlayer().getName(), chest);
										p.openInventory(e.getInventory());
										normalCrateList.schedulerTime.remove(e.getPlayer().getName());
									}	
								} catch(NullPointerException e) {
									//supress this
								}
							}

						}, 300);
					}
				} else if(rareCrateList.getCrateList.containsKey(e.getPlayer().getName())) {
					Chest chestFromList = rareCrateList.getCrateList.get(e.getPlayer().getName());
					final Player p = (Player) e.getPlayer();
					if(!chest.equals(chestFromList)){
						if(rareCrateList.getCrateList.containsValue(chest)) {
							e.setCancelled(true);
						}
					} else {
						if(rareCrateList.schedulerTime.contains(e.getPlayer().getName())) {
							e.setCancelled(true);
							return;
						}
						if(rareCrate.isCrateKeyEnabled(rareCrateList.rareCrates.get(e.getPlayer().getName()))) {
							String crateName = rareCrateList.rareCrates.get(e.getPlayer().getName());
							if(e.getPlayer().getItemInHand().getType() == rareCrate.getKey(crateName).getType()) {
							
								if(e.getPlayer().getItemInHand().hasItemMeta()) {
									if(e.getPlayer().getItemInHand().containsEnchantment(Enchantment.DURABILITY)) {
										if(e.getPlayer().getItemInHand().getItemMeta().getDisplayName().equals(rareCrate.getKey(crateName).getItemMeta().getDisplayName())) {
											// it now can be opened with all the pleasure :)
											p.sendMessage(ChatColor.GREEN + configuration.getPrefix() + ChatColor.GRAY + "succeeded key works!");
											ItemStack item = e.getPlayer().getItemInHand();
											item.setAmount(item.getAmount() - 1);
											e.getPlayer().setItemInHand(item);
										} else {
											p.sendMessage(ChatColor.GREEN + configuration.getPrefix() + ChatColor.GRAY + "Invalid key!, you need the " + crateName +  " key in your hand to open this crate!");
											e.setCancelled(true);
											return;
										}
									} else {
										p.sendMessage(ChatColor.GREEN + configuration.getPrefix() + ChatColor.GRAY + "you need the " + crateName +  " key in your hand to open this crate!");
										e.setCancelled(true);
										return;
									}
								} else {
									p.sendMessage(ChatColor.GREEN + configuration.getPrefix() + ChatColor.GRAY + "you need the " + crateName +  " key in your hand to open this crate!");
									e.setCancelled(true);
									return;
								}
							} else {
								p.sendMessage(ChatColor.GREEN + configuration.getPrefix() + ChatColor.GRAY + "you need the " + crateName +  " key in your hand to open this crate!");
								e.setCancelled(true);
								return;
							}
						}
						e.setCancelled(true);
						rareCrateList.schedulerTime.add(e.getPlayer().getName());
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
								if(chest.getLocation().distance(p.getLocation()) > 6) {
									p.sendMessage(ChatColor.GREEN + configuration.getPrefix() + ChatColor.GRAY + "you are to far from range to open this crate!");
									e.setCancelled(true);
									rareCrateList.schedulerTime.remove(p.getName());
									return;
								}
								try {
									p.sendMessage(ChatColor.GREEN + "opening crate...");
									p.playSound(chest.getLocation(), Sound.CHEST_OPEN, 0, 1);
									p.playSound(chest.getLocation(), Sound.CHEST_OPEN, 1, 0);
									p.playSound(chest.getLocation(), Sound.CHEST_CLOSE, 1, 0);
									p.playSound(chest.getLocation(), Sound.CHEST_OPEN, 1, 0);
									if(e.isCancelled()) {
										e.setCancelled(false);
										rareCrateList.ItemsFromChest.put(e.getPlayer().getName(), e.getInventory().getContents());
										rareCrateList.getCrateList.remove(e.getPlayer().getName());
										rareCrateList.getCrateList2.put(e.getPlayer().getName(), chest);
										p.openInventory(e.getInventory());
										rareCrateList.schedulerTime.remove(e.getPlayer().getName());
										if(rareCrateList.rareCrates.containsKey(p.getName())) {
											rareCrateList.rareCrates.remove(p.getName());
										}
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
		if(configuration.isPluginDisabledForWorld(e.getPlayer().getWorld())) {
			return;
		}
		if(e.getInventory().getType() == InventoryType.CHEST) {
			if(e.getInventory().getHolder() instanceof Chest) {
				Chest chest = (Chest) e.getInventory().getHolder();
				if(normalCrateList.getCrateList2.containsKey(e.getPlayer().getName())) {
					Chest chestFromCrateList = normalCrateList.getCrateList2.get(e.getPlayer().getName());
					if(chest.equals(chestFromCrateList)) {
						ItemStack[] items = normalCrateList.ItemsFromChest.get(e.getPlayer().getName());
						if(!normalCrate.isUnCrateMessageDisabled()) {
							StringBuilder build = new StringBuilder();
							for(int i = 0; i < items.length; i++) {
								if(items[i] != null) {
									if(i == (items.length - 1)) {
										if(items[i].hasItemMeta()) {
											build.append(items[i].getItemMeta().getDisplayName().replace("_", " ") + "").toString();
										} else {
											build.append(items[i].getType().name().toLowerCase().replace("_", " ") + "").toString();	
										}
									} else {
										if(items[i].hasItemMeta()) {
											build.append(items[i].getItemMeta().getDisplayName().replace("_", " ") + ", ").toString();
										} else {
											build.append(items[i].getType().name().toLowerCase().replace("_", " ") + ", ").toString();	
										}	
									}
								}
							}
							Bukkit.broadcastMessage(ChatColor.GREEN + configuration.getPrefix() + ChatColor.GRAY + e.getPlayer().getName() + ChatColor.GRAY + " has uncrated the following items!, " + build.toString());
						}
						normalCrateList.getCrateList2.remove(e.getPlayer().getName());
						normalCrateList.ItemsFromChest.remove(e.getPlayer().getName());
						normalCrateList.chestLocations.remove(chest.getLocation());
						chest.getBlock().breakNaturally();
					}
				} else if(rareCrateList.getCrateList2.containsKey(e.getPlayer().getName())) {
					Chest chestFromCrateList = rareCrateList.getCrateList2.get(e.getPlayer().getName());
					if(chest.equals(chestFromCrateList)) {
						ItemStack[] items = rareCrateList.ItemsFromChest.get(e.getPlayer().getName());
						if(!normalCrate.isUnCrateMessageDisabled()) {
							StringBuilder build = new StringBuilder();
							for(int i = 0; i < items.length; i++) {
								if(items[i] != null) {
									if(i == (items.length - 1)) {
										if(items[i].hasItemMeta()) {
											build.append(items[i].getItemMeta().getDisplayName().replace("_", " ") + "").toString();
										} else {
											build.append(items[i].getType().name().toLowerCase().replace("_", " ") + "").toString();	
										}
									} else {
										if(items[i].hasItemMeta()) {
											build.append(items[i].getItemMeta().getDisplayName().replace("_", " ") + ", ").toString();
										} else {
											build.append(items[i].getType().name().toLowerCase().replace("_", " ") + ", ").toString();	
										}	
									}
								}
							}
							Bukkit.broadcastMessage(ChatColor.GREEN + configuration.getPrefix() + ChatColor.GRAY + e.getPlayer().getName() + ChatColor.GRAY + " has uncrated the following items!, " + build.toString());
						}
						rareCrateList.getCrateList2.remove(e.getPlayer().getName());
						rareCrateList.ItemsFromChest.remove(e.getPlayer().getName());
						rareCrateList.chestLocations.remove(chest.getLocation());
						chest.getBlock().breakNaturally();
					}
				}
			}
		}
	}

	@EventHandler
	public void PlayeronLeave(PlayerQuitEvent e) {
		if(normalCrateList.getCrateList.containsKey(e.getPlayer().getName())) {
			Chest chest = normalCrateList.getCrateList.get(e.getPlayer().getName());
			chest.getBlock().breakNaturally();
			normalCrateList.getCrateList.remove(e.getPlayer().getName());
			normalCrateList.chestLocations.remove(chest.getLocation());
		}
		if(normalCrateList.ItemsFromChest.containsKey(e.getPlayer().getName())) {
			normalCrateList.ItemsFromChest.remove(e.getPlayer().getName());
		}
		if(normalCrateList.getCrateList2.containsKey(e.getPlayer().getName())) {
			Chest chest = normalCrateList.getCrateList2.get(e.getPlayer().getName());
			chest.getBlock().breakNaturally();
			normalCrateList.getCrateList2.remove(e.getPlayer().getName());
			normalCrateList.chestLocations.remove(chest.getLocation());
		}
		if(normalCrateList.schedulerTime.contains(e.getPlayer().getName())) {
			normalCrateList.schedulerTime.remove(e.getPlayer().getName());
		}

		if(rareCrateList.getCrateList.containsKey(e.getPlayer().getName())) {
			Chest chest = rareCrateList.getCrateList.get(e.getPlayer().getName());
			chest.getInventory().clear();
			chest.getBlock().breakNaturally();
			rareCrateList.getCrateList.remove(e.getPlayer().getName());
			rareCrateList.chestLocations.remove(chest.getLocation());
		}
		if(rareCrateList.ItemsFromChest.containsKey(e.getPlayer().getName())) {
			rareCrateList.ItemsFromChest.remove(e.getPlayer().getName());
		}
		if(rareCrateList.getCrateList2.containsKey(e.getPlayer().getName())) {
			Chest chest = rareCrateList.getCrateList2.get(e.getPlayer().getName());
			chest.getBlock().breakNaturally();
			chest.getInventory().clear();
			rareCrateList.getCrateList2.remove(e.getPlayer().getName());
			rareCrateList.chestLocations.remove(chest.getLocation());
		}
		if(rareCrateList.schedulerTime.contains(e.getPlayer().getName())) {
			rareCrateList.schedulerTime.remove(e.getPlayer().getName());
		}
		if(rareCrateList.rareCrates.containsKey(e.getPlayer().getName())) {
			rareCrateList.rareCrates.remove(e.getPlayer().getName());
		}
	}

	@EventHandler
	public void PlayeronLeave(PlayerKickEvent e) {
		if(normalCrateList.getCrateList.containsKey(e.getPlayer().getName())) {
			Chest chest = normalCrateList.getCrateList.get(e.getPlayer().getName());
			chest.getBlock().breakNaturally();
			normalCrateList.getCrateList.remove(e.getPlayer().getName());
			normalCrateList.chestLocations.remove(chest.getLocation());
		}
		if(normalCrateList.ItemsFromChest.containsKey(e.getPlayer().getName())) {
			normalCrateList.ItemsFromChest.remove(e.getPlayer().getName());
		}
		if(normalCrateList.getCrateList2.containsKey(e.getPlayer().getName())) {
			Chest chest = normalCrateList.getCrateList2.get(e.getPlayer().getName());
			chest.getBlock().breakNaturally();
			normalCrateList.getCrateList2.remove(e.getPlayer().getName());
			normalCrateList.chestLocations.remove(chest.getLocation());
		}
		if(normalCrateList.schedulerTime.contains(e.getPlayer().getName())) {
			normalCrateList.schedulerTime.remove(e.getPlayer().getName());
		}

		if(rareCrateList.getCrateList.containsKey(e.getPlayer().getName())) {
			Chest chest = rareCrateList.getCrateList.get(e.getPlayer().getName());
			chest.getInventory().clear();
			chest.getBlock().breakNaturally();
			rareCrateList.getCrateList.remove(e.getPlayer().getName());
			rareCrateList.chestLocations.remove(chest.getLocation());
		}
		if(rareCrateList.ItemsFromChest.containsKey(e.getPlayer().getName())) {
			rareCrateList.ItemsFromChest.remove(e.getPlayer().getName());
		}
		if(rareCrateList.getCrateList2.containsKey(e.getPlayer().getName())) {
			Chest chest = rareCrateList.getCrateList2.get(e.getPlayer().getName());
			chest.getInventory().clear();
			chest.getBlock().breakNaturally();
			rareCrateList.getCrateList2.remove(e.getPlayer().getName());
			rareCrateList.chestLocations.remove(chest.getLocation());
		}
		if(rareCrateList.schedulerTime.contains(e.getPlayer().getName())) {
			rareCrateList.schedulerTime.remove(e.getPlayer().getName());
		}
		if(rareCrateList.rareCrates.containsKey(e.getPlayer().getName())) {
			rareCrateList.rareCrates.remove(e.getPlayer().getName());
		}
	}

	public static void destroyChestOnDisable() {
		for(Player p : Bukkit.getOnlinePlayers()) {
			if(normalCrateList.getCrateList.containsKey(p.getPlayer().getName())) {
				Chest chest = normalCrateList.getCrateList.get(p.getPlayer().getName());
				chest.getBlock().breakNaturally();
				normalCrateList.getCrateList.remove(p.getPlayer().getName());
				normalCrateList.chestLocations.remove(chest.getLocation());
			}
			if(normalCrateList.ItemsFromChest.containsKey(p.getPlayer().getName())) {
				normalCrateList.ItemsFromChest.remove(p.getPlayer().getName());
			}
			if(normalCrateList.getCrateList2.containsKey(p.getPlayer().getName())) {
				Chest chest = normalCrateList.getCrateList2.get(p.getPlayer().getName());
				chest.getBlock().breakNaturally();
				normalCrateList.getCrateList2.remove(p.getPlayer().getName());
				normalCrateList.chestLocations.remove(chest.getLocation());
			}
			if(normalCrateList.schedulerTime.contains(p.getPlayer().getName())) {
				normalCrateList.schedulerTime.remove(p.getPlayer().getName());
			}

			if(rareCrateList.getCrateList.containsKey(p.getPlayer().getName())) {
				Chest chest = rareCrateList.getCrateList.get(p.getPlayer().getName());
				chest.getInventory().clear();
				chest.getBlock().breakNaturally();
				rareCrateList.getCrateList.remove(p.getPlayer().getName());
				rareCrateList.chestLocations.remove(chest.getLocation());
			}
			if(rareCrateList.ItemsFromChest.containsKey(p.getPlayer().getName())) {
				rareCrateList.ItemsFromChest.remove(p.getPlayer().getName());
			}
			if(rareCrateList.getCrateList2.containsKey(p.getPlayer().getName())) {
				Chest chest = rareCrateList.getCrateList2.get(p.getPlayer().getName());
				chest.getInventory().clear();
				chest.getBlock().breakNaturally();
				rareCrateList.getCrateList2.remove(p.getPlayer().getName());
				rareCrateList.chestLocations.remove(chest.getLocation());
			}
			if(rareCrateList.schedulerTime.contains(p.getPlayer().getName())) {
				rareCrateList.schedulerTime.remove(p.getPlayer().getName());
			}
			if(rareCrateList.rareCrates.containsKey(p.getName())) {
				rareCrateList.rareCrates.remove(p.getName());
			}
		}
	}

}
