package tv.mineinthebox.ManCo;

import java.io.File;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
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
import tv.mineinthebox.ManCo.utils.schematics.pasteSchematic;

public class command implements CommandExecutor {

	@SuppressWarnings("deprecation")
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
								sender.sendMessage(ChatColor.RED + "could not spawn a crate because this player is standing on a block which would break the crate!");
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
							if(configuration.isDebugMode()) {
								ManCo.log("CommandType: /mc reload", logType.debug);
								ManCo.log("scheduler status both needs to be null here: \n" + "isNull("+configuration.isNull(cratescheduler.task) + "), isNull(" + configuration.isNull(cratescheduler.task2)+")", logType.debug);
								ManCo.log("=[normalCrateList memory]=", logType.debug);
								ManCo.log("ID: chestLocations: " + normalCrateList.chestLocations.size() + " memory entries", logType.debug);
								ManCo.log("ID: getCrateList: " + normalCrateList.getCrateList.size() + " memory entries", logType.debug);
								ManCo.log("ID: getCrateList2: " + normalCrateList.getCrateList2.size() + " memory entries", logType.debug);
								ManCo.log("ID: getFallingStateChest: " + normalCrateList.getFallingStateChest.size() + " memory entries", logType.debug);
								ManCo.log("ID: ItemsFromChest: " + normalCrateList.ItemsFromChest.size()+ " memory entries", logType.debug);
								ManCo.log("ID: ListDataValues should not be get higher: " + normalCrateList.ListDataValues.size() + " memory entries", logType.debug);
								ManCo.log("ID: schedulerTime needs to be 0: " + normalCrateList.schedulerTime.size() + " memory entries", logType.debug);
								ManCo.log("=[rareCrateList memory]=", logType.debug);
								ManCo.log("ID: chestLocations: " + rareCrateList.chestLocations.size() + " memory entries", logType.debug);
								ManCo.log("ID: getCrateList: " + rareCrateList.getCrateList.size() + " memory entries", logType.debug);
								ManCo.log("ID: getCrateList2: " + rareCrateList.getCrateList2.size() + " memory entries", logType.debug);
								ManCo.log("ID: getFallingStateChest: " + rareCrateList.getFallingStateChest.size() + " memory entries", logType.debug);
								ManCo.log("ID: ItemsFromChest: " + rareCrateList.ItemsFromChest.size() + " memory entries", logType.debug);
								ManCo.log("ID: rareCrates: " + rareCrateList.rareCrates.size() + " memory entries", logType.debug);
								ManCo.log("ID: schedulerTime: " + rareCrateList.schedulerTime.size() + " memory entries", logType.debug);
								ManCo.log("=[rareCrate]=", logType.debug);
								ManCo.log("ID: rareCrates loaded from config: " + rareCrate.getRareCrateList().size(), logType.debug);
							}
							cratescheduler.startScheduler();
							if(!rareCrate.getRareCrateList().isEmpty()) {
								cratescheduler.startRareScheduler();
							}
							if(!pasteSchematic.locations.isEmpty()) {
								pasteSchematic.saveHashMap();
							}
							pasteSchematic.task.cancel();
							pasteSchematic.loadSavedHashMap();
							pasteSchematic.scheduleBuild();
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
							sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/manco rarecrate create <nameOfCrate> " + ChatColor.WHITE + "allows you to create a new rare crate in rareCrates.yml");
							sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/manco rarecrate remove <nameOfCrate> " + ChatColor.WHITE + "allows you to remove a rare crate in rareCrates.yml");
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
									sender.sendMessage(ChatColor.RED + "could not spawn a crate because this player is standing on a block which would break the crate!");
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
												sender.sendMessage(ChatColor.RED + "could not spawn a crate because this player is standing on a block which would break the crate!");
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
											cratescheduler.doRareCrateNative(p, i, true);
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
					if(args[0].equalsIgnoreCase("rarecrate")) {
						if(args[1].equalsIgnoreCase("create")) {
							if(sender.hasPermission("manco.createRareCrate")) {
								try {
									File f = new File(ManCo.getPlugin().getDataFolder() + File.separator + "rareCrates.yml");
									if(f.exists()) {
										FileConfiguration con = YamlConfiguration.loadConfiguration(f);
										if(!con.isSet("rarecrates."+args[2]+".enable")) {
											ArrayList<String> a = new ArrayList<String>();
											a.add("264:0:32");
											a.add("264:0:12");
											a.add("265:0:5");
											a.add("322:1:2");
											con.set("rarecrates."+ args[2] +".enable", true);
											con.set("rarecrates."+ args[2] +".dropRateChance", 5);
											con.set("rarecrates."+ args[2] +".crateFoundMessage", "&7%p has found a &1(Rare)&7 ManCo crate!");
											con.set("rarecrates."+ args[2] +".needKey.enabled", false);
											con.set("rarecrates."+ args[2] +".needKey.material", Material.BLAZE_ROD.getId());
											con.set("rarecrates."+ args[2] +".needKey.price", 3.0);
											con.set("rarecrates."+ args[2] +".effect", false);
											con.set("rarecrates."+ args[2] +".items", a.toArray());
											con.save(f);
											a.clear();
											sender.sendMessage(configuration.getPrefix() + ChatColor.GRAY + "successfully created a new rareCrate please check rareCrate.yml for futher configuration and reload the plugin.");
										} else {
											sender.sendMessage(ChatColor.RED + "it seems this crate entry already exists!");
										}
									}
								} catch(Exception e) {
									e.printStackTrace();
								}
							} else {
								sender.sendMessage(ChatColor.RED + "you aren't allowed to create configuration nodes in rareCrates.yml!");
							}
						} else if(args[1].equalsIgnoreCase("remove")) {
							if(sender.hasPermission("manco.removeRareCrate")) {
								try {
									File f = new File(ManCo.getPlugin().getDataFolder() + File.separator + "rareCrates.yml");
									if(f.exists()) {
										FileConfiguration con = YamlConfiguration.loadConfiguration(f);
										if(con.isSet("rarecrates."+args[2]+".enable")) {
											con.set("rarecrates."+args[2], null);
											con.save(f);
											sender.sendMessage(configuration.getPrefix() + ChatColor.GRAY + "successfully removed rare crate from configuration!, please use /mc reload to refesh the configurations");
										} else {
											sender.sendMessage(ChatColor.RED + "no such name for rare crate found in the configuration!");
										}
									}
								} catch(Exception e) {
									e.printStackTrace();
								}
							} else {
								sender.sendMessage(ChatColor.RED + "you aren't allowed to remove configuration nodes in rareCrates.yml!");
							}
						} else {
							sender.sendMessage(ChatColor.RED + "we don't know nothing about this argument!");
						}
						return false;
					}else if(args[0].equalsIgnoreCase("buy")) {
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
