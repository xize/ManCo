package tv.mineinthebox.ManCo.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.block.Chest;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

import tv.mineinthebox.ManCo.manCo;
import tv.mineinthebox.ManCo.configuration.configuration;
import tv.mineinthebox.ManCo.utils.normalCrateList;
import tv.mineinthebox.ManCo.utils.rareCrate;
import tv.mineinthebox.ManCo.utils.rareCrateList;

public class chestOpenTimer_protected implements Listener {

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
					final String crateName = rareCrateList.rareCrates.get(e.getPlayer().getName());
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
								if(rareCrate.crateHasEffect(crateName)) {
									e.getPlayer().getWorld().playEffect(chest.getLocation(), Effect.MOBSPAWNER_FLAMES, 100);
									e.getPlayer().getWorld().playEffect(chest.getLocation(), Effect.ENDER_SIGNAL, 100);
									chestCheck.playRespectedSound(Sound.AMBIENCE_CAVE, chest.getLocation());
									chestCheck.playRespectedSound(Sound.AMBIENCE_RAIN, chest.getLocation());
									chestCheck.playRespectedSound(Sound.ANVIL_BREAK, chest.getLocation());
									chestCheck.playRespectedSound(Sound.AMBIENCE_THUNDER, chest.getLocation());
									chestCheck.playRespectedSound(Sound.WOLF_DEATH, chest.getLocation());
									
								}
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
								if(rareCrate.crateHasEffect(crateName)) {
									e.getPlayer().getWorld().playEffect(chest.getLocation(), Effect.MOBSPAWNER_FLAMES, 100);
									e.getPlayer().getWorld().playEffect(chest.getLocation(), Effect.ENDER_SIGNAL, 100);
									chestCheck.playRespectedSound(Sound.AMBIENCE_CAVE, chest.getLocation());
									chestCheck.playRespectedSound(Sound.AMBIENCE_RAIN, chest.getLocation());
									chestCheck.playRespectedSound(Sound.ANVIL_BREAK, chest.getLocation());
									chestCheck.playRespectedSound(Sound.AMBIENCE_THUNDER, chest.getLocation());
									chestCheck.playRespectedSound(Sound.WOLF_DEATH, chest.getLocation());
								}
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
								if(rareCrate.crateHasEffect(crateName)) {
									e.getPlayer().getWorld().playEffect(chest.getLocation(), Effect.MOBSPAWNER_FLAMES, 100);
									e.getPlayer().getWorld().playEffect(chest.getLocation(), Effect.ENDER_SIGNAL, 100);
									chestCheck.playRespectedSound(Sound.AMBIENCE_CAVE, chest.getLocation());
									chestCheck.playRespectedSound(Sound.AMBIENCE_RAIN, chest.getLocation());
									chestCheck.playRespectedSound(Sound.ANVIL_BREAK, chest.getLocation());
									chestCheck.playRespectedSound(Sound.AMBIENCE_THUNDER, chest.getLocation());
									chestCheck.playRespectedSound(Sound.WOLF_DEATH, chest.getLocation());
								}
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
								if(rareCrate.crateHasEffect(crateName)) {
									e.getPlayer().getWorld().playEffect(chest.getLocation(), Effect.MOBSPAWNER_FLAMES, 100);
									e.getPlayer().getWorld().playEffect(chest.getLocation(), Effect.ENDER_SIGNAL, 100);
									chestCheck.playRespectedSound(Sound.AMBIENCE_CAVE, chest.getLocation());
									chestCheck.playRespectedSound(Sound.AMBIENCE_RAIN, chest.getLocation());
									chestCheck.playRespectedSound(Sound.ANVIL_BREAK, chest.getLocation());
									chestCheck.playRespectedSound(Sound.AMBIENCE_THUNDER, chest.getLocation());
									chestCheck.playRespectedSound(Sound.WOLF_DEATH, chest.getLocation());
								}
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
								if(rareCrate.crateHasEffect(crateName)) {
									e.getPlayer().getWorld().playEffect(chest.getLocation(), Effect.MOBSPAWNER_FLAMES, 100);
									e.getPlayer().getWorld().playEffect(chest.getLocation(), Effect.ENDER_SIGNAL, 100);
									chestCheck.playRespectedSound(Sound.AMBIENCE_CAVE, chest.getLocation());
									chestCheck.playRespectedSound(Sound.AMBIENCE_RAIN, chest.getLocation());
									chestCheck.playRespectedSound(Sound.ANVIL_BREAK, chest.getLocation());
									chestCheck.playRespectedSound(Sound.AMBIENCE_THUNDER, chest.getLocation());
									chestCheck.playRespectedSound(Sound.WOLF_DEATH, chest.getLocation());
								}
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
									if(rareCrate.crateHasEffect(crateName)) {
										e.getPlayer().getWorld().playEffect(chest.getLocation(), Effect.MOBSPAWNER_FLAMES, 100);
										e.getPlayer().getWorld().playEffect(chest.getLocation(), Effect.ENDER_SIGNAL, 100);
										chestCheck.playRespectedSound(Sound.AMBIENCE_CAVE, chest.getLocation());
										chestCheck.playRespectedSound(Sound.AMBIENCE_RAIN, chest.getLocation());
										chestCheck.playRespectedSound(Sound.ANVIL_BREAK, chest.getLocation());
										chestCheck.playRespectedSound(Sound.AMBIENCE_THUNDER, chest.getLocation());
										chestCheck.playRespectedSound(Sound.WOLF_DEATH, chest.getLocation());
									}
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
}
