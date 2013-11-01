package tv.mineinthebox.ManCo;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import tv.mineinthebox.ManCo.configuration.configuration;
import tv.mineinthebox.ManCo.events.chestCheck;
import tv.mineinthebox.ManCo.events.cratescheduler;
import tv.mineinthebox.ManCo.events.handler;
import tv.mineinthebox.ManCo.utils.iconomy;
import tv.mineinthebox.ManCo.utils.normalCrate;
import tv.mineinthebox.ManCo.utils.normalCrateList;
import tv.mineinthebox.ManCo.utils.rareCrate;
import tv.mineinthebox.ManCo.utils.rareCrateList;
import tv.mineinthebox.ManCo.utils.util;
import tv.mineinthebox.ManCo.utils.vanish;
import tv.mineinthebox.ManCo.utils.worldguard;

public class command implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String CommandLabel, String[] args) {
		if(cmd.getName().equalsIgnoreCase("manco")) {
			if(sender.hasPermission("manco.command")) {
				if(args.length == 0) {
					if(sender.hasPermission("manco.spawn")) {
						if(sender instanceof Player) {
							Player p = (Player) sender;
							if(rareCrateList.rareCrates.containsKey(p.getName()) || normalCrateList.getFallingStateChest.containsValue(p.getName()) || rareCrateList.getCrateList2.containsKey(p.getName()) || rareCrateList.getCrateList.containsKey(p.getName()) || normalCrateList.getCrateList.containsKey(p.getName()) || normalCrateList.getCrateList2.containsKey(p.getName())) {
								sender.sendMessage(ChatColor.RED + "could not create a crate because you allready have a non used crate!");
								return false;
							}
							if(util.isSlab(p.getLocation().getBlock())) {
								sender.sendMessage(ChatColor.RED + "could not spawn a crate because this player is standing on a slab!");
								return false;
							}
							if(vanish.isVanished(p)) {
								sender.sendMessage(ChatColor.RED + "you cannot spawn a crate for yourself when you are vanished!");
								return false;
							}
							if(util.isWorldGuardEnabled()) {
								if(!worldguard.canPlayerBuild(p)) {
									sender.sendMessage(ChatColor.RED + "you cannot spawn a crate for yourself when you are in a protected worldguard region!");
									return false;
								}
							}
							if(configuration.isPluginDisabledForWorld(p.getWorld())) {
								sender.sendMessage(ChatColor.RED + "you cannot spawn a crate in a disabled world!");
								return false;
							}
							Location loc = p.getLocation();
							loc.setY(normalCrate.getCrateSpawnHeight(p));
							Entity entity = p.getWorld().spawnFallingBlock(loc, Material.CHEST, (byte) 1);
							normalCrateList.getFallingStateChest.put(entity, p.getName());
							if(configuration.isCrateDropMessageDisabled()) {
								sender.sendMessage(ChatColor.GREEN + configuration.getPrefix() + ChatColor.GRAY + "you've successfully spawned a crate for yourself!");
							} else {
								Bukkit.broadcastMessage(ChatColor.GREEN + configuration.getPrefix() + normalCrate.getCrateFoundMessage().replace("%p", p.getName()));
								sender.sendMessage(ChatColor.GREEN + configuration.getPrefix() + ChatColor.GRAY + "you've successfully spawned a crate for yourself!");
							}
						} else {
							sender.sendMessage("a console cannot have a location !");
						}
					} else {
						sender.sendMessage(ChatColor.RED + "you are not allowed to use this command!");
					}
				} else if(args.length == 1) {
					if(args[0].equalsIgnoreCase("reload")) {
						if(sender.hasPermission("manco.reload")) {
							cratescheduler.task.cancel();
							cratescheduler.task2.cancel();
							cratescheduler.task = null;
							cratescheduler.task2 = null;
							chestCheck.destroyChestOnDisable();
							cratescheduler.startScheduler();
							if(!rareCrate.getRareCrateList().isEmpty()) {
								cratescheduler.startRareScheduler();
							}
							handler.restartListeners();
							sender.sendMessage(ChatColor.GREEN + configuration.getPrefix() + ChatColor.GRAY + "reload successfully");
						} else {
							sender.sendMessage(ChatColor.RED + "you are not allowed to use this command!");
						}
					} else if(args[0].equalsIgnoreCase("help")) {
						if(sender.hasPermission("manco.help.admin")) {
							sender.sendMessage(ChatColor.GOLD + ".oO___[ManCo supplycrates]___Oo.");
							sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/manco " + ChatColor.WHITE + ": spawn a supplycrate for yourself");
							sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/manco <player> " + ChatColor.WHITE + ": spawn a supplycrate for a player");
							sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/manco <player> rarecrateID " + ChatColor.WHITE + ": spawn a rare crate for the defined player");
							sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/manco reload " + ChatColor.WHITE + ": reloads the plugin");
							sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/manco buy " + ChatColor.WHITE + ": buy a ManCo crate!");
							sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/manco buy key list " + ChatColor.WHITE + ": shows a list of rare keys");
							sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/manco buy key <keyName> " + ChatColor.WHITE + ": here you can buy a key!");
						} else if(sender.hasPermission("manco.help.default")) {
							sender.sendMessage(ChatColor.GOLD + ".oO___[ManCo supplycrates default help]___Oo.");
							sender.sendMessage(ChatColor.DARK_GRAY + "Default: " + ChatColor.GRAY + "/manco buy " + ChatColor.WHITE + ": allows you to buy a crate!");
							sender.sendMessage(ChatColor.DARK_GRAY + "Default: " + ChatColor.GRAY + "/manco buy key list " + ChatColor.WHITE + ": shows all rare crate key IDs!");
							sender.sendMessage(ChatColor.DARK_GRAY + "Default: " + ChatColor.GRAY + "/manco buy key <keyName> " + ChatColor.WHITE + ": buy a rare crate key!");
						} else {
							sender.sendMessage(ChatColor.RED + "you are not allowed to use this command!");
						}
					} else if(args[0].equalsIgnoreCase("buy")) {
						if(sender.hasPermission("manco.canbuy")) {
							if(configuration.isEconomyEnabled()) {
								if(util.isIconomyEnabled()) {
									if(sender instanceof Player) {
										Player p = (Player) sender;
										if(!(rareCrateList.rareCrates.containsKey(p.getName()) || normalCrateList.getFallingStateChest.containsValue(p.getName()) || rareCrateList.getCrateList2.containsKey(p.getName()) || rareCrateList.getCrateList.containsKey(p.getName()) || normalCrateList.getCrateList.containsKey(p.getName()) || normalCrateList.getCrateList2.containsKey(p.getName()))) {
											if(util.isWorldGuardEnabled()) {
												if(worldguard.canPlayerBuild(p)) {
													if(iconomy.debitMoney(p, configuration.returnIconomyPrice())) { 
														Location loc = p.getLocation();
														loc.setY(normalCrate.getCrateSpawnHeight(p));
														Entity entity = p.getWorld().spawnFallingBlock(loc, Material.CHEST, (byte) 1);
														normalCrateList.getFallingStateChest.put(entity, p.getName());
														Bukkit.broadcastMessage(ChatColor.GREEN + configuration.getPrefix() + ChatColor.GRAY + p.getName() + " has bought a ManCo crate!");
													} else {
														sender.sendMessage(ChatColor.RED + "you have not enough money to buy a ManCo crate!");
													}
												} else {
													sender.sendMessage(ChatColor.RED + "you can't build in this protected region, so we couldn't deliver the ManCo crate!");
												}
											} else {
												if(iconomy.debitMoney(p, configuration.returnIconomyPrice())) { 
													Location loc = p.getLocation();
													loc.setY(normalCrate.getCrateSpawnHeight(p));
													Entity entity = p.getWorld().spawnFallingBlock(loc, Material.CHEST, (byte) 1);
													normalCrateList.getFallingStateChest.put(entity, p.getName());
													Bukkit.broadcastMessage(ChatColor.GREEN + configuration.getPrefix() + ChatColor.GRAY + p.getName() + " has bought a ManCo crate!");
												} else {
													sender.sendMessage(ChatColor.RED + "you have not enough money to buy a ManCo crate!");
												}
											}
										} else {
											sender.sendMessage(ChatColor.RED + "you need to open your existed crate first in order to buy a new one!");
										}
									}
								} else {
									sender.sendMessage(ChatColor.GREEN + configuration.getPrefix() + ChatColor.GRAY + "we weren't able to find iConomy or vault!");
								}
							} else {
								sender.sendMessage(ChatColor.GREEN + configuration.getPrefix() + ChatColor.GRAY + "iConomy has been disabled in the configuration");
							}
						} else {
							sender.sendMessage(ChatColor.RED + "you are not allowed to buy ManCo crates!");
						}
					} else {
						if(sender.hasPermission("manco.spawn")) {
							Player p = Bukkit.getPlayer(args[0]);
							if(p instanceof Player) {
								if(rareCrateList.rareCrates.containsKey(p.getName()) || normalCrateList.getFallingStateChest.containsValue(p.getName()) || rareCrateList.getCrateList2.containsKey(p.getName()) || rareCrateList.getCrateList.containsKey(p.getName()) || normalCrateList.getCrateList.containsKey(p.getName()) || normalCrateList.getCrateList2.containsKey(p.getName())) {
									sender.sendMessage(ChatColor.RED + "could not create a crate because this player has allready a non used crate!");
									return false;
								}
								if(util.isSlab(p.getLocation().getBlock())) {
									sender.sendMessage(ChatColor.RED + "could not spawn a crate because this player is standing on a slab!");
									return false;
								}
								if(vanish.isVanished(p)) {
									sender.sendMessage(ChatColor.RED + "you cannot spawn a crate for yourself when you are vanished!");
									return false;
								}
								if(util.isWorldGuardEnabled()) {
									if(!worldguard.canPlayerBuild(p)) {
										sender.sendMessage(ChatColor.RED + "you cannot spawn a crate for this player when he is in a protected worldguard region!");
										return false;
									}
								}
								if(configuration.isPluginDisabledForWorld(p.getWorld())) {
									sender.sendMessage(ChatColor.RED + "you cannot spawn a crate in a disabled world!");
									return false;
								}
								Location loc = p.getLocation();
								loc.setY(normalCrate.getCrateSpawnHeight(p));
								Entity entity = p.getWorld().spawnFallingBlock(loc, Material.CHEST, (byte) 1);
								normalCrateList.getFallingStateChest.put(entity, p.getName());
								if(configuration.isCrateDropMessageDisabled()) {
									sender.sendMessage(ChatColor.GREEN + configuration.getPrefix() + ChatColor.GRAY + "you've successfully spawned a ManCo crate for " + p.getName() + "!");
								} else {
									sender.sendMessage(ChatColor.GREEN + configuration.getPrefix() + ChatColor.GRAY + "you've successfully spawned a ManCo crate for " + p.getName() + "!");
									Bukkit.broadcastMessage(ChatColor.GREEN + configuration.getPrefix() + normalCrate.getCrateFoundMessage().replace("%p", p.getName()));
								}	
							} else {
								sender.sendMessage(ChatColor.RED + "this player is not online!");
							}
						} else {
							sender.sendMessage(ChatColor.RED + "you are not allowed to use this command!");
						}
					}
				} else if(args.length == 2) {
					if(sender.hasPermission("manco.spawn")) {
						Player p = Bukkit.getPlayer(args[0]);
						if(p instanceof Player) {
							if(configuration.isRareCrate(args[1])) {
								if(rareCrate.getRareCrateList().contains(args[1])) {
									for(int i = 0; i < rareCrate.getRareCrateList().size(); i++) {
										if(rareCrate.getRareCrateList().get(i).contains(args[1])) {
											if(rareCrateList.rareCrates.containsKey(p.getName()) || normalCrateList.getFallingStateChest.containsValue(p.getName()) || rareCrateList.getCrateList2.containsKey(p.getName()) || rareCrateList.getCrateList.containsKey(p.getName()) || normalCrateList.getCrateList.containsKey(p.getName()) || normalCrateList.getCrateList2.containsKey(p.getName())) {
												sender.sendMessage(ChatColor.RED + "could not create a crate because you allready have a non used crate!");
												return false;
											}
											if(vanish.isVanished(p)) {
												sender.sendMessage(ChatColor.RED + "you cannot spawn a crate for yourself when you are vanished!");
												return false;
											}
											if(util.isSlab(p.getLocation().getBlock())) {
												sender.sendMessage(ChatColor.RED + "could not spawn a crate because this player is standing on a slab!");
												return false;
											}
											if(util.isWorldGuardEnabled()) {
												if(!worldguard.canPlayerBuild(p)) {
													sender.sendMessage(ChatColor.RED + "you cannot spawn a crate for yourself when you are in a protected worldguard region!");
													return false;
												}
											}
											if(configuration.isPluginDisabledForWorld(p.getWorld())) {
												sender.sendMessage(ChatColor.RED + "you cannot spawn a crate in a disabled world!");
												return false;
											}
											cratescheduler.doRareCrateNative(p, i);
											sender.sendMessage(ChatColor.GREEN + "successfully spawned a rare crate to player " + p.getName());
											break;
										}
									}
								} else {
									sender.sendMessage(ChatColor.RED + "rare crate couldn't be found in the load memory please reload the plugin!");
								}
							} else {
								sender.sendMessage(ChatColor.RED + "this crate does not exist!, type \"/mc buy key list\" to see in all rare crates!");
							}
						} else {
							sender.sendMessage(ChatColor.RED + "this player is not online!");
						}
					} else {
						sender.sendMessage(ChatColor.RED + "you don't have permission to spawn crates!");
					}
				} else if(args.length == 3) {
					if(args[0].equalsIgnoreCase("buy")) {
						if(args[1].equalsIgnoreCase("key")) {
							if(sender.hasPermission("manco.canbuy")) {
								if(args[2].equalsIgnoreCase("list") || args[2].equalsIgnoreCase("help")) {
									sender.sendMessage(ChatColor.GOLD + ".oO___[ManCo key list]___Oo.");
									sender.sendMessage(ChatColor.GREEN + "available rare crate keys!");
									for(String s : rareCrate.getRareCrateList()) {
										if(rareCrate.isCrateKeyEnabled(s)) {
											sender.sendMessage(ChatColor.GRAY + "key: " + s + ChatColor.GREEN + " price: " + rareCrate.returnKeyPrice(s));
										}
									}
								} else {
									if(util.isIconomyEnabled()) {
										if(sender instanceof Player) {
											Player p = (Player) sender;
											if(rareCrate.getRareCrateList().contains(args[2])) {
												if(iconomy.debitMoney(p, rareCrate.returnKeyPrice(args[2]))) {
													sender.sendMessage(ChatColor.GREEN + configuration.getPrefix() + ChatColor.GRAY + "you successfully bought one " + args[2] + " key!");
													p.getInventory().addItem(rareCrate.getKey(args[2]));
												} else {
													sender.sendMessage(ChatColor.GREEN + configuration.getPrefix() + ChatColor.GRAY + "you don't have money enough to buy this key");
												}
											} else {
												sender.sendMessage(ChatColor.GREEN + configuration.getPrefix() + ChatColor.GRAY + "couldn't find this key!, please use /mc buy key list");
											}
										} else {
											sender.sendMessage(ChatColor.RED + "a console cannot buy crate keys!");
										}
									}
								}
							} else {
								sender.sendMessage(ChatColor.RED + "you don't have permission to use this command!");
							}
						}
					}
				}
			} else {
				sender.sendMessage(ChatColor.RED + "you don't have permission to use this command!");
			}
		}
		return false;
	}

}
