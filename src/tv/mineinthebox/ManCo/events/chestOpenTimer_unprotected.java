package tv.mineinthebox.ManCo.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;

import tv.mineinthebox.ManCo.manCo;
import tv.mineinthebox.ManCo.configuration.configuration;
import tv.mineinthebox.ManCo.utils.normalCrateList;
import tv.mineinthebox.ManCo.utils.rareCrate;
import tv.mineinthebox.ManCo.utils.rareCrateList;

public class chestOpenTimer_unprotected implements Listener {

	@EventHandler
	public void getInventory(final InventoryOpenEvent e) {
		if(configuration.isPluginDisabledForWorld(e.getPlayer().getWorld())) {
			return;
		}
		if(e.getInventory().getType() == InventoryType.CHEST) {
			if(e.getInventory().getHolder() instanceof Chest) {
				final Chest chest = (Chest) e.getInventory().getHolder();
				if(normalCrateList.getCrateList.containsValue(chest)) {
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
									String crateOwner = configuration.getNormalChestOwner(chest);
									normalCrateList.ItemsFromChest.put(e.getPlayer().getName(), e.getInventory().getContents());
									normalCrateList.getCrateList.remove(crateOwner);
									normalCrateList.getCrateList2.put(e.getPlayer().getName(), chest);
									p.openInventory(e.getInventory());
									normalCrateList.schedulerTime.remove(e.getPlayer().getName());
								}
							} catch(NullPointerException e) {
								//supress this
							}
						}

					}, 300);
				} else if(rareCrateList.getCrateList.containsValue(chest)) {
					if(rareCrateList.schedulerTime.contains(e.getPlayer().getName())) {
						e.setCancelled(true);
						return;
					}
					final Player p = (Player) e.getPlayer();
					e.setCancelled(true);
					final String crateOwner = configuration.getRareChestOwner(chest);
					final String crateName = rareCrateList.rareCrates.get(crateOwner);
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
							try {
								if(chest.getLocation().distance(p.getLocation()) > 6) {
									p.sendMessage(ChatColor.GREEN + configuration.getPrefix() + ChatColor.GRAY + "you are to far from range to open this crate!");
									e.setCancelled(true);
									rareCrateList.schedulerTime.remove(e.getPlayer().getName());
									return;
								}
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
									String crateOwner = configuration.getRareChestOwner(chest);
									rareCrateList.ItemsFromChest.put(e.getPlayer().getName(), e.getInventory().getContents());
									rareCrateList.getCrateList.remove(crateOwner);
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
